package com.marketplace.domain.cart;

import com.marketplace.domain.item.Item;
import com.marketplace.domain.item.VasItem;
import com.marketplace.observer.CartObserver;
import com.marketplace.observer.OperationResult;

import java.util.*;

import static com.marketplace.util.Constants.*;

public class Cart {

    public List<Item> getItems() {
        return items;
    }

    private final List<Item> items;

    public Map<Item, Integer> getItemQuantities() {
        return itemQuantities;
    }

    private final Map<Item, Integer> itemQuantities;
    private final List<CartObserver> observers;

    public Cart() {
        this.items = new ArrayList<>();
        this.itemQuantities = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    public void addObserver(CartObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(OperationResult result) {
        for (CartObserver observer : observers) {
            observer.onOperationCompleted(result);
        }
    }

    public void addItem( Item item) {
        try {
            int currentQuantity = getItemQuantities().getOrDefault(item, 0);
            int currentQuantityOfDigitalItems = getSizeOfItemsByCategory(CATEGORY_ID_DIGITAL_ITEM);
            int uniqueItemSize = getSizeOfUniqueItemsExcludingVas();

            if (getItems().size() >= MAX_TOTAL_ITEMS){
                throw new IllegalStateException("The total number of products cannot exceed 30.");
            }

            if (item.getCategoryID() == CATEGORY_ID_DIGITAL_ITEM && currentQuantityOfDigitalItems >= MAX_QUANTITY_DIGITAL_ITEM) {
                throw new IllegalStateException("The maximum quantity of DigitalItem that can be added is 5.");
            }

            if (!(item instanceof VasItem) & uniqueItemSize >= MAX_UNIQUE_ITEMS) {
                throw new IllegalStateException("The maximum quantity of an item that can be added into cart is 10.");
            }
            getItems().add(item);
            getItemQuantities().put(item, currentQuantity + 1);
            notifyObservers(new OperationResult(true, "Item added successfully."));
        } catch (IllegalStateException e) {
            notifyObservers(new OperationResult(false, e.getMessage()));
            throw e;
        }
    }

    public Item getItemById(int id) {
        for (Item item : items) {
            if (item.getID() == id) {
                return item;
            }
        }
        return null;
    }

    public void removeItemByID(int itemID) {
        try {
            Item item = getItemById(itemID);

            if (items.contains(item)) {
                items.remove(item);
                int currentQuantity = itemQuantities.getOrDefault(item, 0);

                if (currentQuantity > 1) {
                    itemQuantities.put(item, currentQuantity - 1);
                } else {
                    itemQuantities.remove(item);
                }
            } else {
                throw new NoSuchElementException("Item not found in the cart.");
            }
            notifyObservers(new OperationResult(true, "Item removed successfully."));
        }catch (IllegalStateException e){
            notifyObservers(new OperationResult(false, e.getMessage()));
            throw e;
        }
    }

    public void removeAllItems() {
        try {
            this.items.clear();
            notifyObservers(new OperationResult(true, "Cart reset successfully."));
        }catch(Exception e){
            notifyObservers(new OperationResult(false, e.getMessage()));
            throw e;
        }
    }

    public int getSizeOfItemsByCategory(int categoryId) {
        Map<Integer, List<Item>> groupedItems = new HashMap<>();

        for (Item item : items) {
            groupedItems.computeIfAbsent(item.getCategoryID(), k -> new ArrayList<>()).add(item);
        }
        return groupedItems.getOrDefault(categoryId, new ArrayList<>()).size();
    }

    public int getSizeOfUniqueItemsExcludingVas() {
        Set<Integer> uniqueItems = new HashSet<>();

        for (Item item : getItems()) {
            if (!(item instanceof VasItem)) {
                uniqueItems.add(item.getID());
            }
        }
        return uniqueItems.size();
    }
}


