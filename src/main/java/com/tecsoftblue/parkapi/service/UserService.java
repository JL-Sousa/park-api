package com.tecsoftblue.parkapi.service;

import com.tecsoftblue.parkapi.entities.User;
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
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(
                    String.format("Username %s já cadastrado", user.getUsername())
            );
        }

    }


    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User editPassword(
            Long id,
            String currentPassword,
            String newPassword,
            String confirmPassword) {

        if(!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }
        User user = getById(id);
        if(!user.getPassword().equals(currentPassword)) {
            throw new PasswordInvalidException("Sua senha não confere.");
        }
        user.setPassword(newPassword);
        return user;
    }

}
