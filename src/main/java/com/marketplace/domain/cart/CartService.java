package com.marketplace.domain.cart;

import com.marketplace.domain.item.DefaultItem;
import com.marketplace.domain.item.Item;
import com.marketplace.domain.promotion.PromotionManager;

import java.util.*;

public class CartService {

    private final PromotionManager promotionManager;

    public CartService(PromotionManager promotionManager) {
        this.promotionManager = promotionManager;
    }

    public double calculateTotalPriceBeforeDiscount(Cart cart) {
        List<Item> items = cart.getItems();
        double totalPrice = 0.0;
        for (Item item : items) {
            totalPrice += item.calculateItemTotalPrice();

            if (item instanceof DefaultItem) {
                DefaultItem defaultItem = (DefaultItem) item;
                double vasItemsTotalPrice = defaultItem.getVasItemList().stream()
                        .mapToDouble(vasItem -> vasItem.getPrice() * vasItem.getQuantity())
                        .sum();
                totalPrice += vasItemsTotalPrice;
            }
        }
        return totalPrice;
    }
    public void addItemToCart(Cart cart, Item item){
        cart.addItem(item);
    }

    public PromotionManager.PromotionDetails getPromotionDetails(Cart cart){
        double totalPriceBeforeDiscount = calculateTotalPriceBeforeDiscount(cart);
        return promotionManager.getPromotionDetails(cart, totalPriceBeforeDiscount);
    }

    public void removeItemFromCart(Cart cart, int itemID) {
        cart.removeItemByID(itemID);
    }

    public void resetCart(Cart cart){
        cart.removeAllItems();
    }

}
