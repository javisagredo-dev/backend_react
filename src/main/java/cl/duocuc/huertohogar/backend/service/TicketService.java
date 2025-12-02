package cl.duocuc.huertohogar.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    // ****************************************************
    // CREAR TICKET SIN USERID – SOLO USUARIO DEL TOKEN 
    // ****************************************************
    public TicketResponseDTO createTicket(TicketCreateRequestDTO request) {

        // Obtener usuario desde el token JWT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        String email = auth.getName();
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email not found in token");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        return createTicketForUser(request, user);
    }


    private TicketResponseDTO createTicketForUser(TicketCreateRequestDTO request, User user) {
        try {
            Ticket ticket = new Ticket();
            ticket.setUser(user);

            ticket.setPurchaseDate(
                request.getPurchaseDate() != null ? request.getPurchaseDate() : LocalDateTime.now()
            );

            if (request.getItems() != null) {
                for (TicketCreateItemDTO item : request.getItems()) {
                    Product product = productRepository.findById(item.getProductId()).orElse(null);
                    if (product == null) continue;

                    TicketDetail detail = new TicketDetail();
                    detail.setTicket(ticket);
                    detail.setProduct(product);
                    detail.setAmount(item.getAmount());
                    detail.setSubtotal(item.getSubtotal());
                    ticket.getDetails().add(detail);
                }
            }

            ticket.setTotal(request.getTotal());
            return ticketMapper.toResponseDTO(ticketRepository.save(ticket));
        } catch (Exception e) {
            throw new RuntimeException("Error creating ticket: " + e.getMessage(), e);
        }
    }


    // ****************************************************
    // OBTENER TICKETS DEL USUARIO LOGUEADO
    // ****************************************************
    public List<TicketResponseDTO> getMyTickets() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) return List.of();

        return ticketRepository.findByUserOrderByPurchaseDateDesc(user)
                .stream()
                .map(ticketMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    // ****************************************************
    // OBTENER UN TICKET POR ID (si pertenece al usuario)
    // ****************************************************
    public TicketResponseDTO getTicketById(Long ticketId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        return ticketRepository.findById(ticketId)
                .filter(t -> t.getUser().equals(user)) // seguridad extra
                .map(ticketMapper::toResponseDTO)
                .orElse(null);
    }
}
