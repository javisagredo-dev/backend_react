package cl.duocuc.huertohogar.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
// Representa un ítem del ticket ya creado cuando se lo devuelves al front
//Lo construye el mapper a partir de TicketDetail
//Va dentro de la respuesta (TicketResponseDTO)

@Data
@Builder
public class TicketItemDTO {
    private Long productId;
    private String productName;   // opcional: útil en la respuesta
    private BigDecimal price;     // precio unitario
    private BigDecimal amount;    // cantidad comprada
    private BigDecimal subtotal;  // price * amount
}
