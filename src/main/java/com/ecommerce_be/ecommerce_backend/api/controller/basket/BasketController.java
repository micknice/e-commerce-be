package com.ecommerce_be.ecommerce_backend.api.controller.basket;

import com.ecommerce_be.ecommerce_backend.model.Basket;
import com.ecommerce_be.ecommerce_backend.model.LocalUser;
import com.ecommerce_be.ecommerce_backend.model.dao.BasketDAO;
import com.ecommerce_be.ecommerce_backend.service.BasketService;
import com.ecommerce_be.ecommerce_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/basket")
public class BasketController {
    private BasketService basketService;
    private UserService userService;

    private BasketDAO basketDAO;
//    @Autowired
    public BasketController(BasketService basketService, UserService userService) {
        this.basketService = basketService;
        this.userService = userService;
    }

    @CrossOrigin(origins="*")
    @GetMapping("/{userId}")
    public ResponseEntity<String> getBasket(@AuthenticationPrincipal LocalUser user, @PathVariable Long userId) {

        if (!userService.userHasPermissionToUser(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String basketItems = basketService.getBasketByUserId(Math.toIntExact(userId));
        if (basketItems == null) {
            return ResponseEntity.ok("[]");
        }
        String responseString = "[" + basketItems + "]";
        return ResponseEntity.ok(responseString);
    }

    @CrossOrigin(origins="*")
    @PatchMapping("/{userId}/itemAdd/{productId}")
    public ResponseEntity<String> patchBasketAdd(
            @AuthenticationPrincipal LocalUser user,
            @PathVariable Long userId,
            @PathVariable Long productId) {
        System.out.println("user" + user);
        if (!userService.userHasPermissionToUser(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String basketItems = basketService.getBasketByUserId(Math.toIntExact(userId));
        if (basketItems == null) {
            String newBasketItems = String.valueOf(productId);
            Basket newBasket = basketService.createNewBasket(Math.toIntExact(userId),newBasketItems);
            return ResponseEntity.ok(String.valueOf(newBasket));
        }
        String newItem = String.valueOf(productId);
        Basket updatedBasket = basketService.updateBasket(Math.toIntExact(userId), newItem);
        return ResponseEntity.ok("");
    }


}
