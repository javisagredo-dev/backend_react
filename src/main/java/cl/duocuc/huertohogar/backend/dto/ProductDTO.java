package cl.duocuc.huertohogar.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String urlImage;
    private Long categoryId;
    private String categoryName;
}
