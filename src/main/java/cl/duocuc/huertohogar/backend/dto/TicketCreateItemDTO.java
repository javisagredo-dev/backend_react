package cl.duocuc.huertohogar.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
// Un ítem del carrito cuando el front quiere crear una compra
//Lo manda el frontend dentro del body del POST
//Lo usa el backend para saber: “qué producto y cuánta cantidad quiere comprar”

@Data
public class TicketCreateItemDTO {
    private Long productId;
    private BigDecimal amount;
}

