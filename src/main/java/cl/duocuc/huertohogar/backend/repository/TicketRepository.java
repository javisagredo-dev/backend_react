package cl.duocuc.huertohogar.backend.repository;


import cl.duocuc.huertohogar.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
