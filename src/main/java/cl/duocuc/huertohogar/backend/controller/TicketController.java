package cl.duocuc.huertohogar.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.huertohogar.backend.dto.ErrorResponse;
import cl.duocuc.huertohogar.backend.dto.TicketCreateRequestDTO;
import cl.duocuc.huertohogar.backend.dto.TicketResponseDTO;
import cl.duocuc.huertohogar.backend.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // ---------------------------------------------------------
    // CREAR TICKET (usa el usuario del token, no userId del body)
    // ---------------------------------------------------------
    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody TicketCreateRequestDTO request) {
        TicketResponseDTO response = ticketService.createTicket(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ---------------------------------------------------------
    // LISTAR SOLO LOS TICKETS DEL USUARIO LOGUEADO
    // ---------------------------------------------------------
    @GetMapping("/my")
    public ResponseEntity<List<TicketResponseDTO>> getMyTickets() {
        List<TicketResponseDTO> tickets = ticketService.getMyTickets();
        return ResponseEntity.ok(tickets);
    }

    // ---------------------------------------------------------
    // OBTENER TICKET POR ID (solo si pertenece al usuario)
    // ---------------------------------------------------------
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long ticketId) {
        TicketResponseDTO ticket = ticketService.getTicketById(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
