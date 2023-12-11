package com.tecsoftblue.parkapi.service;

import com.tecsoftblue.parkapi.entities.User;
import com.tecsoftblue.parkapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado.")
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
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }
        User user = getById(id);
        if(!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Sua senha não confere.");
        }
        user.setPassword(newPassword);
        return user;
    }

}
