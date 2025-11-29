package cl.duocuc.huertohogar.backend.mapper;

import org.springframework.stereotype.Component;

import cl.duocuc.huertohogar.backend.dto.ProductDTO;
import cl.duocuc.huertohogar.backend.entity.Product;

@Component
public class ProductMapper {
    
    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setUrlImage(product.getUrlImage());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        return dto;
    }
}
