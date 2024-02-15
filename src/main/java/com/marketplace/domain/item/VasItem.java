package com.marketplace.domain.item;

import static com.marketplace.util.Constants.CATEGORY_ID_VAS_ITEM;
import static com.marketplace.util.Constants.SELLER_ID_VAS_ITEM;

public class VasItem extends Item {

    private int linkedItemID;

    public VasItem(int id, String name, double price, int quantity, int linkedItemID) {
        super(id, name, price, CATEGORY_ID_VAS_ITEM, SELLER_ID_VAS_ITEM, quantity);
        this.linkedItemID = linkedItemID;
    }

    @Override
    public double calculateItemTotalPrice() {
        return this.price * this.quantity;
    }

    public int getLinkedItemID() {
        return this.linkedItemID;
    }

}