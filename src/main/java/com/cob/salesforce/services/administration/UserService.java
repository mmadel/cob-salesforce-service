package com.cob.salesforce.services.administration;

import com.cob.salesforce.entities.ClinicUserEntity;
import com.cob.salesforce.entities.UserEntity;
import com.cob.salesforce.models.ClinicModel;
import com.cob.salesforce.models.UserModel;
import com.cob.salesforce.models.security.KeyCloakUser;
import com.cob.salesforce.repositories.ClinicRepository;
import com.cob.salesforce.repositories.ClinicUserRepository;
import com.cob.salesforce.repositories.UserRepository;
import com.cob.salesforce.services.administration.keycloak.KeyCloakUsersCreatorService;
import com.cob.salesforce.services.administration.keycloak.KeyCloakUsersFinderService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    KeyCloakUsersCreatorService keyCloakUsersCreatorService;
    @Autowired
    KeyCloakUsersFinderService keyCloakUsersService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClinicUserRepository clinicUserRepository;
    @Autowired
    ClinicRepository clinicRepository;

    public UserModel create(UserModel userModel) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        KeyCloakUser keyCloakUser = KeyCloakUser.builder()
                .username(userModel.getName())
                .firstName("firstName" + generateRandom())
                .lastName("lastName" + generateRandom())
                .email(userModel.getName() + "@mail.com")
                .password(userModel.getPassword())
                .roles(Arrays.asList(userModel.getUserRole()))
                .build();
        String createdUserId = keyCloakUsersCreatorService.create(keyCloakUser).getId();
        userModel.setUuid(createdUserId);
        UserEntity createdUserEntity = createDBUser(userModel);
        assignUserToClinics(createdUserEntity, userModel.getClinics().stream().map(ClinicModel::getId).collect(Collectors.toList()));
        return userModel;
    }

    public List<UserModel> getAll() {
        List<UserModel> userModels = new ArrayList<>();
        List<UserRepresentation> kcUsers = keyCloakUsersService.getAllUsers();
        List<String> userRepresentationIdList = new ArrayList<>();
        kcUsers.forEach(userRepresentation -> {
            UserModel userModel = new UserModel();
            String userRole = keyCloakUsersService.getUserRole(userRepresentation.getUsername());
            if (userRole != null) {
                userModel.setName(userRepresentation.getUsername());
                userModel.setUuid(userRepresentation.getId());
                userModel.setUserRole(userRole);
                userRepresentationIdList.add(userRepresentation.getId());
                userModels.add(userModel);
            }

        });
        List<Long> clinicIds = new ArrayList<>();
        Map<String, List<ClinicModel>> userClinicMap = new HashMap<>();
        clinicUserRepository.findByUsers(userRepresentationIdList).forEach(userClinicEntity -> {
            clinicIds.add(Long.parseLong(userClinicEntity.getClinicId()));
            List<ClinicModel> clinics = userClinicMap.get(userClinicEntity.getUser().getUuid());
            if (clinics == null) {
                List<ClinicModel> list = new ArrayList<>();
                ClinicModel clinicModel = new ClinicModel();
                clinicModel.setId(Long.parseLong(userClinicEntity.getClinicId()));
                list.add(clinicModel);
                userClinicMap.put(userClinicEntity.getUser().getUuid(), list);
            } else {
                ClinicModel clinicModel = new ClinicModel();
                clinicModel.setId(Long.parseLong(userClinicEntity.getClinicId()));
                clinics.add(clinicModel);
                userClinicMap.put(userClinicEntity.getUser().getUuid(), clinics);
            }
        });
        clinicRepository.findAllById(clinicIds).forEach(clinicEntity -> {
            userClinicMap.entrySet().stream().forEach(stringListEntry -> {
                List<ClinicModel> list = stringListEntry.getValue();
                list.forEach(model -> {
                    if (model.getId().equals(clinicEntity.getId())) {
                        model.setName(clinicEntity.getName());
                    }
                });
            });
        });
        userModels.forEach(model -> {
            userClinicMap.entrySet().stream().forEach(stringListEntry -> {
                if (stringListEntry.getKey().equals(model.getUuid()))
                    model.setClinics(stringListEntry.getValue());
            });
        });
        return userModels;
    }

    private UserEntity createDBUser(UserModel userModel) {
        UserEntity user = new UserEntity();
        user.setName(userModel.getName());
        user.setUuid(userModel.getUuid());
        return userRepository.save(user);
    }

    private void assignUserToClinics(UserEntity createdUserEntity, List<Long> clinics) {
        List<ClinicUserEntity> userToClinics = new ArrayList<>();
        clinics.forEach(clinicId -> {
            ClinicUserEntity entity = new ClinicUserEntity();
            entity.setClinicId(clinicId.toString());
            entity.setUser(createdUserEntity);
            userToClinics.add(entity);
        });
        clinicUserRepository.saveAll(userToClinics);
    }

    private String generateRandom() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
