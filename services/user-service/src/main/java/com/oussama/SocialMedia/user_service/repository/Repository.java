package com.oussama.SocialMedia.user_service.repository;

import com.oussama.SocialMedia.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface Repository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);
    public User findUserByUsername(String username);


    @Query("SELECT u FROM User u WHERE u.deleted = true AND u.deletedAt <= :cutoff")
    public List<User> findExpiredDeletions(@Param("cutoff") LocalDateTime cutoff);

    @Modifying
    @Query(value = "UPDATE User u SET u.username=:username  WHERE u.email = :email")
    @Transactional
    public User updateUsernameByEmail(@Param("status") String username,@Param("email") String email);
}
