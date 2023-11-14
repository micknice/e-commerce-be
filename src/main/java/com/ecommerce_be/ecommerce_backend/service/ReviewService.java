package com.ecommerce_be.ecommerce_backend.service;

import com.ecommerce_be.ecommerce_backend.model.Review;
import com.ecommerce_be.ecommerce_backend.model.dao.ReviewDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {this.reviewDAO = reviewDAO;}

    public List<Review> getReviews() {
        return reviewDAO.findAll();
    }

    public List<Review> getReviewsByProductId(int id) {
        List<Review> allReviews = reviewDAO.findAll();
        List<Review> filteredReviews = allReviews.stream()
                .filter(review -> review.getProduct_id().equals(id))
                .collect(Collectors.toList());
        return filteredReviews;
    }
}
