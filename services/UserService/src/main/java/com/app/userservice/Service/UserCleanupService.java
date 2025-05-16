package com.app.userservice.Service;

import com.app.userservice.Entity.User;
import com.app.userservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserCleanupService {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 2 * * *") // Every day at 2 AM
    public void deleteOldSoftDeletedUsers() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        List<User> usersToDelete = userRepository.findByDeletedAtBefore(cutoffDate);
        userRepository.deleteAll(usersToDelete);
    }
}
