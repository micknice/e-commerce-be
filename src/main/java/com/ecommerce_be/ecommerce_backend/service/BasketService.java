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

    public Basket updateBasketAdd(Integer userId, String newBasketItems) {
        Basket basket = basketDAO.findByUser_id(userId);
        String basketItems = basket.getItems();
        basket.setItems(basketItems + ',' + newBasketItems);
        return basketDAO.save(basket);
    }public Basket updateBasketRemove(Integer userId, String itemToRemove) {
        Basket basket = basketDAO.findByUser_id(userId);
        String basketItems = basket.getItems();
        String newBasketItems = removeNumberFromString(basketItems, Integer.parseInt(itemToRemove));
        basket.setItems(newBasketItems);
        return basketDAO.save(basket);
    }

    private String removeNumberFromString(String inputStr, int numberToRemove) {
        String[] numbers = inputStr.split(",");
        int indexToRemove = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (Integer.parseInt(numbers[i]) == numberToRemove) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove == -1) {
            return inputStr;
        }

        if (numbers.length > 1) {
            if (indexToRemove == 0) {
                numbers = removeElement(numbers, indexToRemove);
                if (numbers.length > 1) {
                    numbers[0] = numbers[0].replaceFirst("^,", "");
                }
            } else if (indexToRemove == numbers.length - 1) {
                numbers = removeElement(numbers, indexToRemove);
                numbers[numbers.length - 1] = numbers[numbers.length - 1].replaceAll(",$", "");
            } else {
                numbers = removeElement(numbers, indexToRemove);
            }
        }
        String resultStr = String.join(",", numbers);

        return resultStr;
    }

    private static String[] removeElement(String[] array, int index) {
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, newArray.length - index);
        return newArray;
    }

}
