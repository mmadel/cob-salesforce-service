package com.cob.salesforce.security;

import com.cob.salesforce.security.exception.RestAccessDeniedHandler;
import com.cob.salesforce.security.exception.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    @Qualifier("handlerRestAccessDenied")
    RestAccessDeniedHandler restAccessDeniedHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter =
                new DelegatingJwtGrantedAuthoritiesConverter(
                        new JwtGrantedAuthoritiesConverter(),
                        new KeycloakJwtRolesConverter());
        httpSecurity.oauth2ResourceServer().jwt().jwtAuthenticationConverter(
                jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt)));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/authentication/tokens","/counters/**").permitAll()
                        .antMatchers("salesforce-service/api/**").hasAnyRole("administrator", "normal")
                        .antMatchers().hasRole("administrator")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt();
                    oauth2.authenticationEntryPoint(restAuthenticationEntryPoint);
                    oauth2.accessDeniedHandler(restAccessDeniedHandler);
                });

        return httpSecurity.build();
    }

}
