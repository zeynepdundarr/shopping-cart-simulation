package com.marketplace.domain.promotion;
import com.marketplace.domain.cart.Cart;

public interface Promotion {
    double applyPromotion(Cart cart, double totalPriceBeforeDiscount);
    int getPromotionId();
    String getPromotionName();
}