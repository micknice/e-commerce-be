package com.ecommerce_be.ecommerce_backend.service;

import com.ecommerce_be.ecommerce_backend.api.model.ReviewBody;
import com.ecommerce_be.ecommerce_backend.model.Review;
import com.ecommerce_be.ecommerce_backend.model.dao.ReviewDAO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
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
    public Review insertNewReview(int product_id, Long userId, ReviewBody reqBody) {
        Date date = new Date();
        String timeStamp = String.valueOf(new Timestamp(date.getTime()));
        Double rating = reqBody.getRating() / 5;
        Review newReview = new Review();
        newReview.setProduct_id(product_id);
        newReview.setAuthor(reqBody.getAuthor());
        newReview.setTitle(reqBody.getTitle());
        newReview.setBody(reqBody.getBody());
        newReview.setRating(rating);
        newReview.setDate(timeStamp);
        return reviewDAO.save(newReview);
    }
}
