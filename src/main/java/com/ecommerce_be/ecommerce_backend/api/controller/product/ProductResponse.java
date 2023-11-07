package com.ecommerce_be.ecommerce_backend.api.controller.product;

import com.ecommerce_be.ecommerce_backend.model.Product;

import java.util.List;

public class ProductResponse {
    private int productCount;
    private List<Product> products;

    public ProductResponse(int productCount, List<Product> products) {
        this.productCount = productCount;
        this.products = products;
    }

    public int getProductCount() {
        return productCount;
    }

    public List<Product> getProducts() {
        return products;
    }
}

