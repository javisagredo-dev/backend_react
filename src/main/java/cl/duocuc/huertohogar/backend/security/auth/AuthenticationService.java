package cl.duocuc.huertohogar.backend.security.auth;

import cl.duocuc.huertohogar.backend.entity.Type;
import cl.duocuc.huertohogar.backend.entity.User;
import cl.duocuc.huertohogar.backend.repository.UserRepository;
import cl.duocuc.huertohogar.backend.repository.TypeRepository;
import cl.duocuc.huertohogar.backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TypeRepository typeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Método para manejar el registro de nuevos usuarios (
    // opcional, pero incluido para completitud)
   @SuppressWarnings("null")
public AuthenticationResponse register(RegisterRequest request) {
    Type adminType = typeRepository.findByName("ADMIN")
            .orElseThrow(() -> new RuntimeException("Type ADMIN not found"));
    
    var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .type(adminType)
            .build();

    repository.save(user);

    String jwtToken = jwtService.generateToken(user);

    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
}

    // Método para manejar el inicio de sesión y generar el token
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Usa AuthenticationManager para validar las credenciales.
        // Si las credenciales son inválidas, lanzará una excepción AuthenticationException.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        // Si llega aquí, la autenticación fue exitosa. Buscamos al usuario para generar el token.
        User user = repository.findByEmail(request.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Email no registrado"));// En un caso real, manejarías esta excepción si el usuario no se encuentra tras una autenticación exitosa

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
