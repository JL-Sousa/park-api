package com.tecsoftblue.parkapi.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CreateUserDTO {

    private String username;
    private String password;
}
