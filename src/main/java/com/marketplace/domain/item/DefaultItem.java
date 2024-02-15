package com.marketplace.domain.item;

import static com.marketplace.util.Constants.MAX_QUANTITY_VAS_ITEM_PER_DEFAULT_ITEM;

public class DefaultItem extends Item {

    public DefaultItem(int id, String name, double price, int categoryID, int sellerID, int quantity) {
        super(id, name, price, categoryID, sellerID, quantity);
    }

    @Override
    public double calculateItemTotalPrice() {
        return this.getPrice() * this.getQuantity();
    }

    public void addVasItem(VasItem vasItem) {
        int currentTotalQuantity = getVasItemList().stream()
                .mapToInt(VasItem::getQuantity)
                .sum();
        int allowedToAdd = MAX_QUANTITY_VAS_ITEM_PER_DEFAULT_ITEM - currentTotalQuantity;

        if (allowedToAdd <= 0) {
            throw new IllegalStateException("Cannot add more VasItems to this DefaultItem.");
        }

        int quantityToAdd = Math.min(vasItem.getQuantity(), allowedToAdd);

        VasItem adjustedVasItem = new VasItem(vasItem.getID(), vasItem.getName(), vasItem.getPrice(), quantityToAdd, vasItem.getLinkedItemID());

        getVasItemList().add(adjustedVasItem);
    }

    public int getVasItemsCount() {
        return getVasItemList().size();
    }
}
