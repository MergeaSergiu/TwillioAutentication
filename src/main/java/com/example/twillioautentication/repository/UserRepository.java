package com.example.twillioautentication.repository;

import com.example.twillioautentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
