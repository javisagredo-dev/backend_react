package cl.duocuc.huertohogar.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TicketResponseDTO {
    private Long ticketId;
    private String userMail;
    private LocalDateTime purchaseDate;
    private BigDecimal total;
    private List<TicketItemDTO> items;
}