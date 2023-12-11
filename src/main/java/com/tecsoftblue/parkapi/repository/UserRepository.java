package com.tecsoftblue.parkapi.repository;

import com.tecsoftblue.parkapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
