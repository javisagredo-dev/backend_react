package cl.duocuc.huertohogar.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ticket_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_detail_id")
    private Long ticketDetailId;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;
}
