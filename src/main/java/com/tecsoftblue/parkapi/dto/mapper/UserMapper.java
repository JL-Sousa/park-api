package com.tecsoftblue.parkapi.dto.mapper;

import com.tecsoftblue.parkapi.dto.CreateUserDTO;
import com.tecsoftblue.parkapi.dto.UserResponseDTO;
import com.tecsoftblue.parkapi.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static Usuario toUser(CreateUserDTO createDTO) {
        return new ModelMapper().map(createDTO, Usuario.class);
    }

    public static UserResponseDTO toDTO(Usuario user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UserResponseDTO> props =
                new PropertyMap<Usuario, UserResponseDTO>() {
                    @Override
                    protected void configure() {
                        map().setRole(role);
                    }
                };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> toListDTO( List<Usuario> users) {
        return users.stream().map(user -> toDTO(user)).collect(Collectors.toList());
    }
}
