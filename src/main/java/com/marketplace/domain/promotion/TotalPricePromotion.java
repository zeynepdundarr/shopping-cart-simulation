package com.marketplace.domain.promotion;

import com.marketplace.domain.cart.Cart;
import com.marketplace.util.Constants;

import static com.marketplace.util.Constants.PROMOTION_ID_TOTAL_PRICE;

public class TotalPricePromotion implements Promotion {
    private static final String PROMOTION_NAME = "Total Price Promotion";

    @Override
    public double applyPromotion(Cart cart, double totalPriceBeforeDiscount) {
        double discount = 0.0;

        for (int i = 0; i < Constants.PROMO_THRESHOLDS.length; i++) {
            if ((i < Constants.PROMO_THRESHOLDS.length - 1 && totalPriceBeforeDiscount >= Constants.PROMO_THRESHOLDS[i] && totalPriceBeforeDiscount < Constants.PROMO_THRESHOLDS[i + 1]) ||
                    (i == Constants.PROMO_THRESHOLDS.length - 1 && totalPriceBeforeDiscount >= Constants.PROMO_THRESHOLDS[i])) {
                discount = Constants.PROMO_DISCOUNTS[i];
                break;
            }
        }

        return discount;
    }

    @Override
    public int getPromotionId() {
        return PROMOTION_ID_TOTAL_PRICE;
    }

    @Override
    public String getPromotionName(){
        return  PROMOTION_NAME ;
    }
}
