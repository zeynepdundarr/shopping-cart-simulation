package com.marketplace.domain.item;

import java.util.ArrayList;
import java.util.List;

import static com.marketplace.util.Constants.CATEGORY_ID_VAS_ITEM;
import static com.marketplace.util.Constants.SELLER_ID_VAS_ITEM;

public abstract class Item {
    protected int id;
    protected String name;
    protected double price;
    protected int categoryID;
    public int sellerID;
    protected int quantity;
    private final List<VasItem> vasItemList;


    public Item(int id, String name, double price, int categoryID, int sellerID, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryID = categoryID;
        this.sellerID = sellerID;
        this.quantity = quantity;
        this.vasItemList = new ArrayList<>();
    }

    public abstract double calculateItemTotalPrice();

    public int getCategoryID() {
        return categoryID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isVasItem() {
        if (this.categoryID == CATEGORY_ID_VAS_ITEM && this.sellerID == SELLER_ID_VAS_ITEM){
            return true;
        }
        return false;
    }

    public double getPrice() {
        return this.price;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<VasItem> getVasItemList() {
        return vasItemList;
    }
}