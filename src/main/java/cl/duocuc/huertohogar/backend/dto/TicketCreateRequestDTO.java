package cl.duocuc.huertohogar.backend.dto;

import lombok.Data;

import java.util.List;
// Es el request completo para crear un ticket (la compra)
// Es el body del POST /api/tickets
// Lo recibe el controller
// El service lo usa para crear Ticket + TicketDetail y calcular el total
@Data
public class TicketCreateRequestDTO {

    // Puede venir en el body o lo obtienes desde el token JWT en el backend.
    // Lo dejaremos opcional aquí. Si usas JWT, no lo necesitarías en el body.
    private Long userId; 

    private List<TicketCreateItemDTO> items;
}