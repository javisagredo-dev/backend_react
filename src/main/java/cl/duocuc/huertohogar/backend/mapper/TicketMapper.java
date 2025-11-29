package cl.duocuc.huertohogar.backend.mapper;

import cl.duocuc.huertohogar.backend.dto.TicketItemDTO;
import cl.duocuc.huertohogar.backend.dto.TicketResponseDTO;
import cl.duocuc.huertohogar.backend.entity.Ticket;
import cl.duocuc.huertohogar.backend.entity.TicketDetail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketMapper {

    public TicketResponseDTO toResponseDTO(Ticket ticket) {
        List<TicketItemDTO> items = ticket.getDetails().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());

        return TicketResponseDTO.builder()
                .ticketId(ticket.getId())
                .userMail(ticket.getUser().getEmail())
                .purchaseDate(ticket.getPurchaseDate())
                .total(ticket.getTotal())
                .items(items)
                .build();
    }

    public TicketItemDTO toItemDTO(TicketDetail detail) {
        return TicketItemDTO.builder()
                .productId(detail.getProduct().getId())
                .productName(detail.getProduct().getName())
                .price(detail.getProduct().getPrice())
                .amount(detail.getAmount())
                .subtotal(detail.getSubtotal())
                .build();
    }
}