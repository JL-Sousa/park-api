package com.tecsoftblue.parkapi;

import com.tecsoftblue.parkapi.dto.CreateUserDTO;
import com.tecsoftblue.parkapi.dto.UserResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/user/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/user/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201() {
        UserResponseDTO responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUserDTO("tody@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }
}
