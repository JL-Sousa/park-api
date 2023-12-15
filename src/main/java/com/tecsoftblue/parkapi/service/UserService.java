package com.tecsoftblue.parkapi.service;

import com.tecsoftblue.parkapi.entities.Usuario;
import com.tecsoftblue.parkapi.exception.EntityNotFoundException;
import com.tecsoftblue.parkapi.exception.PasswordInvalidException;
import com.tecsoftblue.parkapi.exception.UsernameUniqueViolationException;
import com.tecsoftblue.parkapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Usuario save(Usuario user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(
                    String.format("Username %s já cadastrado", user.getUsername())
            );
        }

    }


    @Transactional(readOnly = true)
    public Usuario getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public Usuario editPassword(
            Long id,
            String currentPassword,
            String newPassword,
            String confirmPassword) {

        if(!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }
        Usuario user = getById(id);
        if(!user.getPassword().equals(currentPassword)) {
            throw new PasswordInvalidException("Sua senha não confere.");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public Usuario getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Usuário com %s não encontrado", username))
        );
    }

    @Transactional(readOnly = true)
    public Usuario.Role  getRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }
}
