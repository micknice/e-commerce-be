package com.ecommerce_be.ecommerce_backend.model.dao;

import com.ecommerce_be.ecommerce_backend.model.Basket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface BasketDAO extends ListCrudRepository<Basket, Long> {

    @Query("SELECT b.items FROM Basket b WHERE b.user_id = :userId")
    String findItemsByUser_id(@Param("userId") Integer userId);


    @Query("SELECT b FROM Basket b  WHERE b.user_id = :userId")
    Basket findByUser_id(@Param("userId") Integer userId);

}
