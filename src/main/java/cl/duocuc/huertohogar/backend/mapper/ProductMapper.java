package cl.duocuc.huertohogar.backend.mapper;

import cl.duocuc.huertohogar.backend.dto.ProductDTO;
import cl.duocuc.huertohogar.backend.entity.Category;
import cl.duocuc.huertohogar.backend.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .urlImage(product.getUrlImage())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }

    /**
     * Convierte un ProductDTO en una entidad Product.
     * La Category se pasa ya cargada desde el servicio.
     */
    public Product fromDTO(ProductDTO dto, Category category) {
        Product product = new Product();
        product.setId(dto.getId()); // puede ser null en create
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUrlImage(dto.getUrlImage());
        product.setCategory(category);
        return product;
    }

    public void updateEntityFromDTO(ProductDTO dto, Product product, Category category) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUrlImage(dto.getUrlImage());
        product.setCategory(category);
    }
}