package cl.duocuc.huertohogar.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//Es la respuesta completa de un ticket (cabecera + detalle)
//Lo devuelve el controller en:
//POST /api/tickets (compra recién creada)
//GET /api/tickets/{id} (consultar una compra)

//Lo usa el frontend para mostrar el “comprobante/boleta”.
@Data
@Builder
public class TicketResponseDTO {
    private Long ticketId;
    private String userMail;
    private LocalDateTime purchaseDate;
    private BigDecimal total;
    private List<TicketItemDTO> items;
}