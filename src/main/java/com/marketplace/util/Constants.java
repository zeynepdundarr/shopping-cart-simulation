package com.marketplace.util;

public final class Constants {
    private Constants() {}

    // General Cart Constants
    public static final int MAX_UNIQUE_ITEMS = 10;
    public static final int MAX_TOTAL_ITEMS = 30;
    public static final double MAX_CART_AMOUNT_TL = 500000;

    // Item Types and Category IDs
    public static final int CATEGORY_ID_DIGITAL_ITEM = 7889;
    public static final int CATEGORY_ID_VAS_ITEM = 3242;
    public static final int CATEGORY_ID_FURNITURE = 1001;
    public static final int CATEGORY_ID_ELECTRONICS = 3004;
    public static final int CATEGORY_ID_CATEGORY_PROMOTION = 3003;

    // Seller IDs
    public static final int SELLER_ID_VAS_ITEM = 5003;

    // Quantity Limits
    public static final int MAX_QUANTITY_DIGITAL_ITEM = 5;
    public static final int MAX_QUANTITY_VAS_ITEM_PER_DEFAULT_ITEM = 3;

    // Promotion IDs
    public static final int PROMOTION_ID_SAME_SELLER = 9909;
    public static final int PROMOTION_ID_CATEGORY = 5676;
    public static final int PROMOTION_ID_TOTAL_PRICE = 1232;

    // Promotion Discount Rates and Thresholds
    public static final double SAME_SELLER_DISCOUNT_RATE = 0.10; // 10%
    public static final double CATEGORY_PROMOTION_DISCOUNT_RATE = 0.05; // 5%
    public static final double[] PROMO_THRESHOLDS = {500, 5000, 10000, 50000};
    public static final double[] PROMO_DISCOUNTS = {250, 500, 1000, 2000};
}
