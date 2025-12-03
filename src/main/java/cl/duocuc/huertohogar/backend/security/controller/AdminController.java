package cl.duocuc.huertohogar.backend.security.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.huertohogar.backend.dto.UserCreateUpdateDTO;
import cl.duocuc.huertohogar.backend.dto.UserDTO;
import cl.duocuc.huertohogar.backend.entity.Type;
import cl.duocuc.huertohogar.backend.entity.User;
import cl.duocuc.huertohogar.backend.repository.TypeRepository;
import cl.duocuc.huertohogar.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(user -> new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getType() != null ? user.getType().getName() : "CLIENTE"
            ))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> createUser(@RequestBody UserCreateUpdateDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya existe");
        }

        Type type = typeRepository.findByName(dto.getType() != null ? dto.getType() : "USER")
            .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado"));

        User user = User.builder()
            .firstname(dto.getFirstname())
            .lastname(dto.getLastname())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .type(type)
            .build();

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(
            savedUser.getId(),
            savedUser.getFirstname(),
            savedUser.getLastname(),
            savedUser.getEmail(),
            savedUser.getType().getName()
        ));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserCreateUpdateDTO dto) {
        return userRepository.findById(id)
            .map(user -> {
                if (!user.getEmail().equals(dto.getEmail()) && userRepository.findByEmail(dto.getEmail()).isPresent()) {
                    return ResponseEntity.badRequest().body("El email ya existe");
                }
                
                user.setFirstname(dto.getFirstname());
                user.setLastname(dto.getLastname());
                user.setEmail(dto.getEmail());
                
                if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                }

                if (dto.getType() != null) {
                    Type type = typeRepository.findByName(dto.getType())
                        .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado"));
                    user.setType(type);
                }

                User updatedUser = userRepository.save(user);
                return ResponseEntity.ok(new UserDTO(
                    updatedUser.getId(),
                    updatedUser.getFirstname(),
                    updatedUser.getLastname(),
                    updatedUser.getEmail(),
                    updatedUser.getType().getName()
                ));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(user -> {
                userRepository.delete(user);
                return ResponseEntity.ok().body("Usuario eliminado correctamente");
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
