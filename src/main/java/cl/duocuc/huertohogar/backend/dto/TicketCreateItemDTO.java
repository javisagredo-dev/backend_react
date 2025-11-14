package cl.duocuc.huertohogar.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketCreateItemDTO {
    private Long productId;
    private BigDecimal amount;
}