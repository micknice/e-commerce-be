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
    public ProductResponse getProductsByCategory(
            @PathVariable String category,
            @RequestParam (name="page", required = false, defaultValue = "0") String page,
            @RequestParam (name="items", required = false, defaultValue = "12") String items,
            @RequestParam (name="sortBy", required = false, defaultValue="id") String sortBy,
            @RequestParam (name="orderBy", required= false, defaultValue="asc") String orderBy) {
        int productCount = productService.getCategoryProductCount(category);
        List<Product> allPaginatedProducts  = productService.getPaginatedProductsByCategory(category, Integer.parseInt(page), Integer.parseInt(items), sortBy, orderBy);
        //TODO: ERROR HANDLING FOR SAD PATHS ON  REQUEST PARAMS. WRITE TESTS.

//
        return new ProductResponse(productCount, allPaginatedProducts);
    }

    @CrossOrigin(origins="*")
    @GetMapping("/category/*/subCategory/{subCategory}")
    public ProductResponse getProductsBySubCategory(
            @PathVariable String subCategory,
            @RequestParam (name="page", required = false, defaultValue = "0") String page,
            @RequestParam (name="items", required = false, defaultValue = "12") String items,
            @RequestParam (name="sortBy", required = false, defaultValue="id") String sortBy,
            @RequestParam (name="orderBy", required= false, defaultValue="asc") String orderBy) {

        int productCount = productService.getSubCategoryProductCount(subCategory);
//
        List<Product> allPaginatedProducts  = productService.getPaginatedProductsBySubCategory(subCategory, Integer.parseInt(page), Integer.parseInt(items), sortBy, orderBy);
        //TODO: ERROR HANDLING FOR SAD PATHS ON  REQUEST PARAMS. WRITE TESTS.
//
        return new ProductResponse(productCount, allPaginatedProducts);
    }
    @CrossOrigin(origins="*")
    @GetMapping("/id/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product  = productService.getProductByProductId(id);
        return product;
    }
}
