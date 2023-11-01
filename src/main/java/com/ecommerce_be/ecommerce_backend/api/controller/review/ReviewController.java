package com.ecommerce_be.ecommerce_backend.api.controller.review;

import com.ecommerce_be.ecommerce_backend.model.Review;
import com.ecommerce_be.ecommerce_backend.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {this.reviewService = reviewService;}

    @GetMapping
    public List<Review> getReviews() {return reviewService.getReviews();}

}
