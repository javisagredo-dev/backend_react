package cl.duocuc.huertohogar.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateUpdateDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String type; // "ADMIN" o "USER"
}
