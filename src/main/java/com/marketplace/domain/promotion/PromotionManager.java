package com.marketplace.domain.promotion;

import com.marketplace.domain.cart.Cart;

import java.util.Map;

public class PromotionManager {

    private final PromotionService promotionService;

    public PromotionManager(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public PromotionDetails getPromotionDetails(Cart cart, double totalPriceBeforeDiscount) {
        return promotionService.calculatePromotionDetails(cart, totalPriceBeforeDiscount);
    }
    public static class PromotionDetails {
        private final Map<Integer, Double> promotionDiscounts;
        private final double maxDiscount;
        private final double finalPrice;
        private final Integer maxDiscountPromotionId; // New field to store the promotion ID

        public PromotionDetails(Map<Integer, Double> promotionDiscounts, double maxDiscount, double finalPrice, Integer maxDiscountPromotionId) {
            this.promotionDiscounts = promotionDiscounts;
            this.maxDiscount = maxDiscount;
            this.finalPrice = finalPrice;
            this.maxDiscountPromotionId = maxDiscountPromotionId; // Initialize the new field
        }

        public Map<Integer, Double> getPromotionDiscounts() { return promotionDiscounts; }
        public double getMaxDiscount() { return maxDiscount; }
        public double getFinalPrice() { return finalPrice; }
        public Integer getMaxDiscountPromotionId() { return maxDiscountPromotionId; } // Getter for the promotion ID
    }
}

