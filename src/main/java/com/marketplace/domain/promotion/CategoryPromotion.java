package com.marketplace.domain.promotion;

import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.item.Item;

import java.util.List;

import static com.marketplace.util.Constants.*;

public class CategoryPromotion implements Promotion {
    private static final String PROMOTION_NAME = "Category Promotion";

    @Override
    public double applyPromotion(Cart cart, double totalPriceBeforeDiscount) {
        List<Item> items = cart.getItems();
        double totalDiscount = 0.0;

        for (Item item : items) {
            if (item.getCategoryID() == CATEGORY_ID_CATEGORY_PROMOTION) {
                double discount = item.calculateItemTotalPrice() * CATEGORY_PROMOTION_DISCOUNT_RATE;
                totalDiscount += discount;
            }
        }
        return totalDiscount;
    }
    
    @Override
    public int getPromotionId() {
        return PROMOTION_ID_CATEGORY;
    }

    @Override
    public String getPromotionName(){
        return  PROMOTION_NAME ;
    }
}