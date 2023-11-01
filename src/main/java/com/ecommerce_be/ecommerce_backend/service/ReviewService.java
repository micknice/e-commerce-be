package com.ecommerce_be.ecommerce_backend.service;

import com.ecommerce_be.ecommerce_backend.model.Review;
import com.ecommerce_be.ecommerce_backend.model.dao.ReviewDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {this.reviewDAO = reviewDAO;}

    public List<Review> getReviews() {
        return reviewDAO.findAll();
    }
}
