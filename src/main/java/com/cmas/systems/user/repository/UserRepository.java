package com.cmas.systems.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmas.systems.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
