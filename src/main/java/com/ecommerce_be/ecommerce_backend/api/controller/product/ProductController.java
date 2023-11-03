package com.ecommerce_be.ecommerce_backend.api.controller.product;

import com.ecommerce_be.ecommerce_backend.model.Product;
import com.ecommerce_be.ecommerce_backend.service.ProductService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @CrossOrigin(origins="*")
    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @CrossOrigin(origins="*")
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        List<Product> allProducts  = this.getProducts();
        List<Product> filteredProducts = allProducts
                .stream()
                .filter(product -> category.equals(product.getCategory()))
                .collect(Collectors.toList());
        return filteredProducts;

    }

    @CrossOrigin(origins="*")
    @GetMapping("/subCategory/{subCategory}")
    public List<Product> getProductsBySubCategory(@PathVariable String subCategory) {
        List<Product> allProducts  = this.getProducts();
        List<Product> filteredBySubCategoryProducts = allProducts
                .stream()
                .filter(product -> subCategory.equals(product.getSub_category()))
                .collect(Collectors.toList());
        return filteredBySubCategoryProducts;
    }
    @CrossOrigin(origins="*")
    @GetMapping("/id/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product  = productService.getProductByProductId(id);
        return product;
    }
}
