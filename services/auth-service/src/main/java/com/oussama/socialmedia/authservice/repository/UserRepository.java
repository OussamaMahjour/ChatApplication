package com.oussama.socialmedia.authservice.repository;

import com.oussama.socialmedia.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    void deleteUserByUsername(String username);
}
