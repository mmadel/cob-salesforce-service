package com.cob.salesforce.triggers;

import com.cob.salesforce.services.transition.impl.SchedulerTransitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class FollowupScheduler {
    @Autowired
    SchedulerTransitionService schedulerTransitionService;

//    @Scheduled(cron = "0 0 * * * *")
//    public void fetchMaintenancesTask() {
//        String uuid = "03239b56-66c3-11ee-8c99-0242ac120002";
//        schedulerTransitionService.execute(uuid);
//    }
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(4);
        taskScheduler.initialize();
        return taskScheduler;
    }
}
