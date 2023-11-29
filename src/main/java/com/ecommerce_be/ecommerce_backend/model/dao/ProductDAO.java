package com.ecommerce_be.ecommerce_backend.model.dao;

import com.ecommerce_be.ecommerce_backend.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDAO extends ListCrudRepository<Product, Long> {

    List<Product> findByCategory(String category);
//
    @Query("SELECT p FROM Product p WHERE p.sub_category = :subCategory")
    List<Product> findBySubCategory(@Param("subCategory") String subCategory);

}
