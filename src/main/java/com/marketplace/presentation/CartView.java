package com.marketplace.presentation;

import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.item.Item;
import com.marketplace.domain.item.VasItem;
import com.marketplace.domain.promotion.PromotionManager.PromotionDetails;
import com.marketplace.observer.CartObserver;
import com.marketplace.observer.OperationResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class CartView implements CartObserver {

    public CartView() {}

    @Override
    public void onOperationCompleted(OperationResult result) {
        displayResult(result);
    }

    public void displayResult(OperationResult result) {
        System.out.println("{\"result\":" + result.isSuccess() + ", \"message\":\"" + result.getMessage() + "\"}");
    }

    public void displayCart(Cart cart, PromotionDetails promotionDetails) {
        JSONObject result = new JSONObject();
        JSONArray itemsArray = new JSONArray();

        for (Item item : cart.getItems()) {
            JSONObject itemObject = new JSONObject();
            itemObject.put("itemId", item.getID());
            itemObject.put("categoryId", item.getCategoryID());
            itemObject.put("sellerId", item.getSellerID());
            itemObject.put("price", item.getPrice());
            itemObject.put("quantity", item.getQuantity());

            JSONArray vasItemsArray = new JSONArray();
            for (VasItem vasItem : item.getVasItemList()) {
                JSONObject vasItemObject = new JSONObject();
                vasItemObject.put("vasItemId", vasItem.getID());
                vasItemObject.put("vasCategoryId", vasItem.getCategoryID());
                vasItemObject.put("vasSellerId", vasItem.getSellerID());
                vasItemObject.put("price", vasItem.getPrice());
                vasItemObject.put("quantity", vasItem.getQuantity());
                vasItemsArray.put(vasItemObject);
            }

            itemObject.put("vasItems", vasItemsArray);
            itemsArray.put(itemObject);
        }

        JSONObject message = new JSONObject();
        message.put("items", itemsArray);

        message.put("totalAmount", promotionDetails.getFinalPrice() + promotionDetails.getMaxDiscount());
        message.put("appliedPromotionId", promotionDetails.getMaxDiscountPromotionId());
        message.put("totalDiscount", promotionDetails.getMaxDiscount());

        JSONObject promotionDiscountsJson = new JSONObject();
        for (Map.Entry<Integer, Double> entry : promotionDetails.getPromotionDiscounts().entrySet()) {
            promotionDiscountsJson.put(String.valueOf(entry.getKey()), entry.getValue());
        }
        message.put("promotionDiscounts", promotionDiscountsJson);

        result.put("result", true);
        result.put("message", message);
        System.out.println(result.toString(4));
    }
}