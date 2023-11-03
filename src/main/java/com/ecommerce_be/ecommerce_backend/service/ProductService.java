package com.ecommerce_be.ecommerce_backend.service;

import com.ecommerce_be.ecommerce_backend.model.Product;
import com.ecommerce_be.ecommerce_backend.model.dao.ProductDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    public List<Product> getProducts() {
        return productDAO.findAll();
    }

    public Optional<Product> getProductByProductId(Long productId) {
        return productDAO.findById(productId);
    }
}
