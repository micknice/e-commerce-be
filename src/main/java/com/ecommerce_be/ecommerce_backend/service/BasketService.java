package com.ecommerce_be.ecommerce_backend.service;

import com.ecommerce_be.ecommerce_backend.model.Basket;
import com.ecommerce_be.ecommerce_backend.model.dao.BasketDAO;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    private BasketDAO basketDAO;


    public BasketService(BasketDAO basketDAO) {
        this.basketDAO = basketDAO;
    }
    public String getBasketByUserId(Integer userId) {
        String basketItems = basketDAO.findItemsByUser_id(userId);
        System.out.println("basket items @ b service");
        System.out.println(basketItems);
        return basketItems;
    }

    public Basket createNewBasket(Integer userId, String newBasketItems) {
        Basket basket = new Basket();
        basket.setUser_id(userId);
        basket.setItems(newBasketItems);
        return basketDAO.save(basket);
    }

    public Basket updateBasket(Integer userId, String newBasketItems) {
        Basket basket = basketDAO.findByUser_id(userId);
        String basketItems = basket.getItems();
        basket.setItems(basketItems + ',' + newBasketItems);
        return basketDAO.save(basket);
    }

}
