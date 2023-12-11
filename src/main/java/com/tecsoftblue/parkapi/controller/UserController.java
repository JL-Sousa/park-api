package com.tecsoftblue.parkapi.controller;

import com.tecsoftblue.parkapi.dto.CreateUserDTO;
import com.tecsoftblue.parkapi.dto.UserPasswordDTO;
import com.tecsoftblue.parkapi.dto.UserResponseDTO;
import com.tecsoftblue.parkapi.dto.mapper.UserMapper;
import com.tecsoftblue.parkapi.entities.User;
import com.tecsoftblue.parkapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(
            @Valid @RequestBody CreateUserDTO request, UriComponentsBuilder uriBuilder) {
        User user = userService.save(UserMapper.toUser(request));
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(UserMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(UserMapper.toListDTO(users));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id, @Valid @RequestBody UserPasswordDTO dto) {
        User user = userService.editPassword(
                id,
                dto.getCurrentPassword(),
                dto.getNewPassword(),
                dto.getConfirmPassword()
        );
        return ResponseEntity.noContent().build();
    }
}
