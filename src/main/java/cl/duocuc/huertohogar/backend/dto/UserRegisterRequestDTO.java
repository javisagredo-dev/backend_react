package cl.duocuc.huertohogar.backend.dto;


import lombok.Data;

@Data
public class UserRegisterRequestDTO {
    private String name;
    private String lastname;
    private String mail;
    private String password;
}
