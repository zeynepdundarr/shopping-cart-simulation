package com.marketplace.domain.promotion;

import com.marketplace.domain.item.Item;
import com.marketplace.domain.cart.Cart;
import java.util.List;

import static com.marketplace.util.Constants.PROMOTION_ID_SAME_SELLER;
import static com.marketplace.util.Constants.SAME_SELLER_DISCOUNT_RATE;

public class SameSellerPromotion implements Promotion {

    private static final String PROMOTION_NAME = "Same Seller Promotion";

    @Override
    public double applyPromotion(Cart cart, double totalPriceBeforeDiscount) {
        List<Item> items = cart.getItems();

        if (items.size() <= 1) {
            return 0.0;
        }

        Integer firstSellerId = items.stream()
                .map(Item::getSellerID)
                .findFirst()
                .orElse(null);

        boolean allSameSeller = items.stream()
                .allMatch(item -> item.getSellerID() == firstSellerId);

        if (firstSellerId != null && allSameSeller) {
            double itemsTotal = items.stream()
                    .mapToDouble(Item::calculateItemTotalPrice)
                    .sum();
            return itemsTotal * SAME_SELLER_DISCOUNT_RATE;
        }
        return 0.0;
    }

    @Override
    public int getPromotionId() {
        return PROMOTION_ID_SAME_SELLER;
    }

    @Override
    public String getPromotionName(){
        return  PROMOTION_NAME ;
    }
}

