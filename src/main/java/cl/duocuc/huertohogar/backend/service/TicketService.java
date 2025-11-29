package cl.duocuc.huertohogar.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cl.duocuc.huertohogar.backend.dto.*;
import cl.duocuc.huertohogar.backend.entity.*;
import cl.duocuc.huertohogar.backend.mapper.TicketMapper;
import cl.duocuc.huertohogar.backend.repository.*;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TicketMapper ticketMapper;

    public TicketResponseDTO createTicket(TicketCreateRequestDTO request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) return null;
        return createTicketForUser(request, user);
    }

    private TicketResponseDTO createTicketForUser(TicketCreateRequestDTO request, User user) {
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setPurchaseDate(request.getPurchaseDate() != null ? request.getPurchaseDate() : LocalDateTime.now());
        
        for (TicketCreateItemDTO item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product == null) continue;
            
            TicketDetail detail = new TicketDetail();
            detail.setTicket(ticket);
            detail.setProduct(product);
            detail.setAmount(item.getAmount());
            detail.setSubtotal(item.getSubtotal());  // Usar subtotal del front
            ticket.getDetails().add(detail);
        }
        
        ticket.setTotal(request.getTotal());  // Usar total del front
        return ticketMapper.toResponseDTO(ticketRepository.save(ticket));
    }

    public List<TicketResponseDTO> getTicketsByUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();
        return getTicketsForUser(user);
    }

    public TicketResponseDTO getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .map(ticketMapper::toResponseDTO)
                .orElse(null);
    }

    private List<TicketResponseDTO> getTicketsForUser(User user) {
        return ticketRepository.findByUserOrderByPurchaseDateDesc(user).stream()
                .map(ticketMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


}