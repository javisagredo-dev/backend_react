package cl.duocuc.huertohogar.backend.repository;

import cl.duocuc.huertohogar.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
