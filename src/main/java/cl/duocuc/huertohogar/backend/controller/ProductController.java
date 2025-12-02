package cl.duocuc.huertohogar.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.huertohogar.backend.dto.ProductDTO;
import cl.duocuc.huertohogar.backend.entity.Product;
import cl.duocuc.huertohogar.backend.mapper.ProductMapper;
import cl.duocuc.huertohogar.backend.service.ProductService;



@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProductsDTO();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return productService.getProductByIdDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(productMapper.toDTO(savedProduct));
    }
   
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.getProductById(id)
            .map(existingProduct -> {
                existingProduct.setName(productDetails.getName());
                existingProduct.setDescription(productDetails.getDescription());
                existingProduct.setPrice(productDetails.getPrice());
                existingProduct.setUrlImage(productDetails.getUrlImage());
                if (productDetails.getCategory() != null) {
                    existingProduct.setCategory(productDetails.getCategory());
                }
                Product updatedProduct = productService.saveProduct(existingProduct);
                return ResponseEntity.ok(productMapper.toDTO(updatedProduct));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        if (productService.getProductById(id).isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    

}
