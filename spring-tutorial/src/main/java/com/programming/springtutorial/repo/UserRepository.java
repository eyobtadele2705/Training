package com.programming.springtutorial.repo;

import com.programming.springtutorial.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Integer> {

    Optional<UserData> findByEmail(String email);
}
