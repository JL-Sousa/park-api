package com.tecsoftblue.parkapi.repository;

import com.tecsoftblue.parkapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    @Query("select u.role from Usuario u where u.username like :username ")
    Usuario.Role findRoleByUsername(String username);
}
