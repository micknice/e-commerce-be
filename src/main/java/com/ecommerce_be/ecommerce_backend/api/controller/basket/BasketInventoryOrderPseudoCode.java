//package com.ecommerce_be.ecommerce_backend.api.controller.basket;
//
//import com.ecommerce_be.ecommerce_backend.model.Basket;
//import com.ecommerce_be.ecommerce_backend.model.Product;
//
//import java.util.Date;
//
//
//public class BasketInventoryOrderPseudoCode {
//
//    private Basket basket;
//
//    private BasketDAO basketDAO;
//
//    public Basket addItemToBasket(Long productId, Long basketId) {
//
//        BasketItem newBasketItem = new BasketItem();
//        newBasketItem.set(productId);
//        Date creationTime = new Date().;
//        creationTime.setTime(System.currentTimeMillis());
//        newBasketItem.set(creationTime);
//        Basket currentBasket = basketDAO.getBasket().whereBasketId(basketId);
//        currentBasket
//
//    }
//
//
//    public Product  getProductByProductId(Long productId) {
//
//
//        void basketService.restockInventoryFromExpiredBasketItems();
//
//        get product
//
//    }
//
//    public void  restockInventoryFromExpiredBasketItems() {
//        if (withinCheckWindow) {
//            List<BasketItem> expiredItems= basketDAO.getItems().where(creationTime < System.currentTimeMillis() - 30 mins in millis)
//            for (basketItem of expiredItems) {
//                inventoryService.incrementByProductId(basketItem.getProductId());
//            }
//        }
//    }
//}
