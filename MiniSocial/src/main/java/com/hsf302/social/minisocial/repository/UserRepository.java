package com.hsf302.social.minisocial.repository;

import com.hsf302.social.minisocial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    //Tìm theo email
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByUserName(String username);
}
