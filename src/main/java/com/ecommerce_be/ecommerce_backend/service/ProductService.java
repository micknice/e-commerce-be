package com.ecommerce_be.ecommerce_backend.service;

import com.ecommerce_be.ecommerce_backend.model.Product;
import com.ecommerce_be.ecommerce_backend.model.dao.ProductDAO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    public List<Product> getProducts() {
        return productDAO.findAll();
    }

    public int getCategoryProductCount(String category) {
        List<Product> products = productDAO.findAll();
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());
        return filteredProducts.size();
    }
    public int getSubCategoryProductCount(String subCategory) {
        List<Product> products = productDAO.findAll();
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getSub_category().equals(subCategory))
                .collect(Collectors.toList());
        return filteredProducts.size();
    }

    public List<Product> getPaginatedProductsByCategory(String category, int page, int items, String sortBy, String orderBy) {
        List<Product> allProducts = productDAO.findAll();

        // filter by category
        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());

        // sort by sortBy param
        Comparator<Product> comparator = null;
        if ("id".equals(sortBy)) {
            comparator = Comparator.comparing(Product::getId);
        } else if ("name".equals(sortBy)) {
            comparator = Comparator.comparing(Product::getName);
        } else if ("price".equals(sortBy)) {
            comparator = Comparator.comparing(Product::getPrice);
        }
        //reverse order if orderBy="desc"
        if (comparator != null) {
            if ("desc".equals(orderBy)) {
                comparator = comparator.reversed();
            }
            filteredProducts.sort(comparator);
        }

        // calculate  start and end index for pagination
        int startIndex = page * items;
        int endIndex = Math.min(startIndex + items, filteredProducts.size());

        return filteredProducts.subList(startIndex, endIndex);
    }

    public List<Product> getPaginatedProductsBySubCategory(String subCategory, int page, int items, String sortBy, String orderBy) {
//        List<Product> allProducts = productDAO.findAll();
        List<Product> filteredProducts = productDAO.findBySubCategory(subCategory);

//        // filter by category
//        List<Product> filteredProducts = allProducts.stream()
//                .filter(product -> product.getSub_category().equals(subCategory))
//                .collect(Collectors.toList());

        // sort by sortBy param
        Comparator<Product> comparator = null;
        if ("id".equals(sortBy)) {
            comparator = Comparator.comparing(Product::getId);
        } else if ("name".equals(sortBy)) {
            comparator = Comparator.comparing(Product::getName);
        } else if ("price".equals(sortBy)) {
            comparator = Comparator.comparing(Product::getPrice);
        }
        //reverse order if orderBy="desc"
        if (comparator != null) {
            if ("desc".equals(orderBy)) {
                comparator = comparator.reversed();
            }
            filteredProducts.sort(comparator);
        }

        // calculate  start and end index for pagination
        int startIndex = page * items;
        int endIndex = Math.min(startIndex + items, filteredProducts.size());

        return filteredProducts.subList(startIndex, endIndex);
    }

    public Optional<Product> getProductByProductId(Long productId) {
        return productDAO.findById(productId);
    }
}
