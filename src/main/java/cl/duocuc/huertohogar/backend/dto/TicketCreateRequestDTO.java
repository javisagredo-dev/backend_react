package cl.duocuc.huertohogar.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketCreateRequestDTO {

    // Puede venir en el body o lo obtienes desde el token JWT en el backend.
    // Lo dejaremos opcional aquí. Si usas JWT, no lo necesitarías en el body.
    private Long userId; 

    private List<TicketCreateItemDTO> items;
}