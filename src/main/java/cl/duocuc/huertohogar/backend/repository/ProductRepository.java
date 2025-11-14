package cl.duocuc.huertohogar.backend.repository;

import cl.duocuc.huertohogar.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}