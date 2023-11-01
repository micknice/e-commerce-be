package com.ecommerce_be.ecommerce_backend.model.dao;

import com.ecommerce_be.ecommerce_backend.model.Review;
import org.springframework.data.repository.ListCrudRepository;

public interface ReviewDAO extends ListCrudRepository<Review,Long> {
}
