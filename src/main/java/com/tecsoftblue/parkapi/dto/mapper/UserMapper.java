package com.tecsoftblue.parkapi.dto.mapper;

import com.tecsoftblue.parkapi.dto.CreateUserDTO;
import com.tecsoftblue.parkapi.dto.UserResponseDTO;
import com.tecsoftblue.parkapi.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(CreateUserDTO createDTO) {
        return new ModelMapper().map(createDTO, User.class);
    }

    public static UserResponseDTO toDTO(User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDTO> props =
                new PropertyMap<User, UserResponseDTO>() {
                    @Override
                    protected void configure() {
                        map().setRole(role);
                    }
                };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> toListDTO( List<User> users) {
        return users.stream().map(user -> toDTO(user)).collect(Collectors.toList());
    }
}
