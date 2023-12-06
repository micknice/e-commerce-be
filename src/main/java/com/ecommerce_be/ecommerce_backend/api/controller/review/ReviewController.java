package com.ecommerce_be.ecommerce_backend.api.controller.review;

import com.ecommerce_be.ecommerce_backend.api.model.ReviewBody;
import com.ecommerce_be.ecommerce_backend.model.LocalUser;
import com.ecommerce_be.ecommerce_backend.model.Review;
import com.ecommerce_be.ecommerce_backend.service.ReviewService;
import com.ecommerce_be.ecommerce_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;
    private UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @CrossOrigin(origins="*")
    @GetMapping("/{product_id}")
    public List<Review> getReviewsById(@PathVariable int product_id) {
        return reviewService.getReviewsByProductId(product_id);
    }

    @CrossOrigin(origins="*")
    @PostMapping("/{product_id}/user/{userId}")
    public ResponseEntity<Review> postNewReview(@AuthenticationPrincipal LocalUser user, @PathVariable int product_id, @PathVariable Long userId, @RequestBody ReviewBody reqBody) {
        if (!userService.userHasPermissionToUser(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String author = reqBody.getAuthor();
        System.out.println(author);
        Review newReview = reviewService.insertNewReview(product_id, userId, reqBody);
        return ResponseEntity.ok(newReview);
    }
}
