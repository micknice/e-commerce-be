package com.ecommerce_be.ecommerce_backend.api.controller.review;

import com.ecommerce_be.ecommerce_backend.model.Review;
import com.ecommerce_be.ecommerce_backend.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {this.reviewService = reviewService;}
    @CrossOrigin(origins="*")
    @GetMapping("/{product_id}")
    public List<Review> getReviewsById(@PathVariable int product_id) {
        return reviewService.getReviewsByProductId(product_id);
    }

}
