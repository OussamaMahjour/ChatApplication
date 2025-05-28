package com.oussama.SocialMedia.user_service.schedule;


import com.oussama.SocialMedia.user_service.entity.User;
import com.oussama.SocialMedia.user_service.repository.Repository;
import com.oussama.SocialMedia.user_service.service.Service;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SoftDeleteScheduler {

    private final Service userService;

    @Scheduled(cron="0 0 0 30 * ?")
    public void softDelete() {
       userService.hardDeleteUsers();
    }

}
