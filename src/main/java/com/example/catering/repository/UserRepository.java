package com.example.catering.repository;
import com.example.catering.entity.User;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAllByRoles_Name(String roleName);
}
