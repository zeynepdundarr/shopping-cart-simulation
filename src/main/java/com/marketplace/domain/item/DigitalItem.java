package com.marketplace.domain.item;

import static com.marketplace.util.Constants.CATEGORY_ID_DIGITAL_ITEM;

public class DigitalItem extends Item {

    public DigitalItem(int id, String name, double price, int sellerID, int quantity) {
        super(id, name, price, CATEGORY_ID_DIGITAL_ITEM, sellerID, quantity);
    }

    @Override
    public double calculateItemTotalPrice() {
        return this.price * this.quantity;
    }
}