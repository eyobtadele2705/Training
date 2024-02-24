package com.programming.security.repo;

import com.programming.security.models.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetail, Integer> {

    Optional<UserDetail> findByUsername(String username);
}
