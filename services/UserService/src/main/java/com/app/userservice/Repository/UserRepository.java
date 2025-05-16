package com.app.userservice.Repository;

import com.app.userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long>{
    // only fetch users that have never been deleted
    Optional<User> findByIdAndDeletedAtIsNull(Long id);
    List<User> findAllByDeletedAtIsNull();
    Optional<User> findByEmail(String email);
    // for your cleanup job: find those soft‑deleted more than 30 days ago
    List<User> findByDeletedAtBefore(LocalDateTime cutoff);

}
