package cl.duocuc.huertohogar.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duocuc.huertohogar.backend.dto.ProductDTO;
import cl.duocuc.huertohogar.backend.entity.Product;
import cl.duocuc.huertohogar.backend.mapper.ProductMapper;
import cl.duocuc.huertohogar.backend.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<ProductDTO> getAllProductsDTO(){
        return productRepository.findAll().stream()
            .map(productMapper::toDTO)
            .collect(Collectors.toList());
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Optional<ProductDTO> getProductByIdDTO(Long id){
        return productRepository.findById(id)
            .map(productMapper::toDTO);
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

}
