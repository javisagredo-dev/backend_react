package cl.duocuc.huertohogar.backend.security.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.huertohogar.backend.dto.UserDTO;
import cl.duocuc.huertohogar.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String sayHelloAdmin() {
        return "Hola, Admin!";
    }
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('admin:read')")
    public String viewDashboard() {
        return "Vista del Dashboard de Admin";
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usuarios = userRepository.findAll().stream()
            .map(user -> new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getType() != null ? user.getType().getName() : "CLIENTE"
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }
}

