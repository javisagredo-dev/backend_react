package cl.duocuc.huertohogar.backend.repository;

import cl.duocuc.huertohogar.backend.entity.Ticket;
import cl.duocuc.huertohogar.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserOrderByPurchaseDateDesc(User user);
}
