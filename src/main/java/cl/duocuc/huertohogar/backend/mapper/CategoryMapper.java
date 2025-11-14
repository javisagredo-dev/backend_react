package cl.duocuc.huertohogar.backend.mapper;


import cl.duocuc.huertohogar.backend.dto.CategoryDTO;
import cl.duocuc.huertohogar.backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
