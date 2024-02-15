package com.marketplace.domain.promotion;

import com.marketplace.domain.cart.Cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.marketplace.util.Constants.MAX_CART_AMOUNT_TL;

public class PromotionService {

    private List<Promotion> promotions;

    public PromotionService() {
        initializePromotions();
    }

    private void initializePromotions() {
        promotions = new ArrayList<>(Arrays.asList(
                new SameSellerPromotion(),
                new TotalPricePromotion(),
                new CategoryPromotion()
        ));
    }
    public void setPromotions(List<Promotion> newPromotions) {
        this.promotions = newPromotions;
    }

    public PromotionManager.PromotionDetails calculatePromotionDetails(Cart cart, double totalPriceBeforeDiscount) {
        Map<Integer, Double> promotionDiscounts = calculateDiscounts(cart, totalPriceBeforeDiscount);
        double maxDiscount = 0.0;
        Integer maxDiscountPromotionId = null;

        for (Map.Entry<Integer, Double> entry : promotionDiscounts.entrySet()) {
            if (entry.getValue() > maxDiscount) {
                maxDiscount = entry.getValue();
                maxDiscountPromotionId = entry.getKey();
            }
        }

        double finalPrice = totalPriceBeforeDiscount - maxDiscount;
        return new PromotionManager.PromotionDetails(promotionDiscounts, maxDiscount, finalPrice, maxDiscountPromotionId);
    }

    public Map<Integer, Double> calculateDiscounts(Cart cart, double totalPriceBeforeDiscount) {
        Map<Integer, Double> discounts = new HashMap<>();
        for (Promotion promotion : promotions) {
            double discount = promotion.applyPromotion(cart, totalPriceBeforeDiscount);
            discounts.put(promotion.getPromotionId(), discount);
        }
        return discounts;
    }

    public double applyMaxPromotion(Cart cart, double totalPriceBeforeDiscount) {
        double discount = 0;
        double maxDiscount = 0;

        for (Promotion promotion : promotions) {
            if (promotion != null) {
                discount = promotion.applyPromotion(cart, totalPriceBeforeDiscount);
                if (discount > maxDiscount) {
                    maxDiscount = discount;
                }
            }
        }

        if (totalPriceBeforeDiscount - maxDiscount > MAX_CART_AMOUNT_TL) {
            throw new IllegalArgumentException("The total amount (including vas items) of the Cart cannot exceed 500,000 TL");
        }

        return maxDiscount;
    }
}
