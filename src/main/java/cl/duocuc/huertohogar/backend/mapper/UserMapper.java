package cl.duocuc.huertohogar.backend.mapper;

import cl.duocuc.huertohogar.backend.dto.UserRegisterRequestDTO;
import cl.duocuc.huertohogar.backend.dto.UserResponseDTO;
import cl.duocuc.huertohogar.backend.entity.Role;
import cl.duocuc.huertohogar.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .mail(user.getMail())
                .typeName(user.getType() != null ? user.getType().getName() : null)
                .build();
    }

    /**
     * Convierte un DTO de registro en una entidad User.
     * El password debería venir ya hasheado desde el service.
     */
    public User fromRegisterDTO(UserRegisterRequestDTO dto, String passwordHash, Role type) {
        User user = new User();
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setMail(dto.getMail());
        user.setPassword(passwordHash);
        user.setType(type);
        // createdAt lo puede setear la BD o la entidad con @PrePersist
        return user;
    }
}
