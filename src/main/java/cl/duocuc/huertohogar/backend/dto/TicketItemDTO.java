package cl.duocuc.huertohogar.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TicketItemDTO {
    private Long productId;
    private String productName;   // opcional: útil en la respuesta
    private BigDecimal price;     // precio unitario
    private BigDecimal amount;    // cantidad comprada
    private BigDecimal subtotal;  // price * amount
}
