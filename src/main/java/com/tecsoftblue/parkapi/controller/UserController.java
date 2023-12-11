package com.tecsoftblue.parkapi.controller;

import com.tecsoftblue.parkapi.dto.CreateUserDTO;
import com.tecsoftblue.parkapi.dto.UserPasswordDTO;
import com.tecsoftblue.parkapi.dto.UserResponseDTO;
import com.tecsoftblue.parkapi.dto.mapper.UserMapper;
import com.tecsoftblue.parkapi.entities.User;
import com.tecsoftblue.parkapi.exception.ErrorMessage;
import com.tecsoftblue.parkapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Tag(name = "Usuarios", description = "Contém todas operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Criar um novo usuário",
            description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Recurso criado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Usuário e-mail já cadastrado no sistema",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)

                            )
                    ),
                    @ApiResponse(responseCode = "422",
                            description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)

                            )
                    )


            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(
            @Valid @RequestBody CreateUserDTO request, UriComponentsBuilder uriBuilder) {
        User user = userService.save(UserMapper.toUser(request));
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(UserMapper.toDTO(user));
    }

    @Operation(summary = "Recuperar um usuário pelo id",
            description = "Recuperar um usuário pelo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso recuperado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Recurso não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @Operation(summary = "Listar todos os usuários",
            description = "Listar todos os usuários cadastrados",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista com todos os usuários cadastrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UserResponseDTO.class)))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(UserMapper.toListDTO(users));
    }

    @Operation(summary = "Atualizar senha",
            description = "Atualizar senha",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Senha atualizada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Senha não confere",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Recurso nào encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    )
            }
    )
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
