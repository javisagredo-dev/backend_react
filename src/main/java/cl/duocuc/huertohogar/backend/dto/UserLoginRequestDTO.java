package cl.duocuc.huertohogar.backend.dto;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
    private String mail;
    private String password;
}
