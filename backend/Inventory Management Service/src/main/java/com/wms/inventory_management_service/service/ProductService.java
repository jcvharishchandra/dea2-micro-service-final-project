package com.wms.inventory_management_service.service;

import com.wms.inventory_management_service.dto.request.ProductRequest;
import com.wms.inventory_management_service.dto.response.ProductResponse;
import com.wms.inventory_management_service.exception.ConflictException;
import com.wms.inventory_management_service.exception.ResourceNotFoundException;
import com.wms.inventory_management_service.model.Product;
import com.wms.inventory_management_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        return mapToResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        productRepository.findByProductName(request.getProductName())
                .ifPresent(product -> {
                    throw new ConflictException("Product with name '" + request.getProductName() + "' already exists");
                });

        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setUnitPrice(request.getUnitPrice());
        product.setCategory(request.getCategory());

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.findByProductName(request.getProductName())
                .ifPresent(existingProduct -> {
                    if (!existingProduct.getProductId().equals(productId)) {
                        throw new ConflictException("Product with name '" + request.getProductName() + "' already exists");
                    }
                });

        product.setProductName(request.getProductName());
        product.setUnitPrice(request.getUnitPrice());
        product.setCategory(request.getCategory());

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getUnitPrice(),
                product.getCategory()
        );
    }
}
