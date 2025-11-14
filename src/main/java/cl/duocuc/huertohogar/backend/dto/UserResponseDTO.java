package cl.duocuc.huertohogar.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String lastname;
    private String mail;
    private String typeName; // CLIENT o ADMIN
}
