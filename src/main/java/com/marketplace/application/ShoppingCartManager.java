package com.marketplace.application;

import com.marketplace.commandprocessing.CommandProcessor;
import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.cart.CartRepository;
import com.marketplace.domain.cart.CartService;
import com.marketplace.domain.item.DefaultItem;
import com.marketplace.domain.item.DigitalItem;
import com.marketplace.domain.item.Item;
import com.marketplace.domain.item.VasItem;
import com.marketplace.domain.promotion.PromotionManager;
import com.marketplace.presentation.CartView;


public class ShoppingCartManager {

    private final CartService cartService;
    private final CartRepository cartRepository;

    public ShoppingCartManager(CartService cartService, CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    public void addItemToCart(CommandProcessor.AddItemPayload payload) {
        Cart cart = getOrCreateCart();

        int id = payload.getItemId();
        String name = payload.getName();
        int categoryID = payload.getCategoryId();
        double price = payload.getPrice();
        int quantity = payload.getQuantity();
        int sellerID =  payload.getSellerId();

        if (payload.getCategoryId() != 3242 && sellerID != 5003) {
            if (payload.getCategoryId() == 7889 && sellerID != 5003) {
                DigitalItem digitalItem = new DigitalItem(id, name, price, sellerID, quantity);
                cartService.addItemToCart(cart, digitalItem);
            } else {
                Item item = new DefaultItem(id, name, price, categoryID, sellerID, quantity);
                cartService.addItemToCart(cart, item);
            }
        }
    }

    public void addVasItemToCart(CommandProcessor.AddItemPayload payload) {
        if (payload.getCategoryId() != 3242 || payload.getSellerId() != 5003) {
            throw new IllegalStateException("SellerId and CategoryId is not belong to VasItem category.");
        }

        Cart cart = getOrCreateCart();
        Item parentItem = findEligibleParentItemForVasItem(cart, payload.getLinkedItemID());

        if (!(parentItem instanceof DefaultItem)) {
            throw new IllegalStateException("No eligible DefaultItem parent found for VasItem.");
        }

        if (payload.getPrice() > parentItem.getPrice()) {
            throw new IllegalArgumentException("The price of the VasItem cannot be higher than the DefaultItem's price.");
        }

        VasItem vasItem = new VasItem(
                payload.getItemId(),
                payload.getName(),
                payload.getPrice(),
                payload.getQuantity(),
                payload.getLinkedItemID()
        );
        ((DefaultItem) parentItem).addVasItem(vasItem);
    }

    public Item findEligibleParentItemForVasItem(Cart cart, int linkedItemID) {
        for (Item item : cart.getItems()) {
            boolean isEligibleCategory = (item.getCategoryID() == 1001 || item.getCategoryID() == 3004) && linkedItemID == item.getID();
            if (isEligibleCategory && item instanceof DefaultItem) {
                return item;
            }
        }
        return null;
    }

    public void removeItemFromCart(CommandProcessor.RemoveItemPayload payload) {
        int itemId = payload.getItemId();
        Cart cart = getOrCreateCart();
        cartService.removeItemFromCart(cart, itemId);
    }

    private Cart getOrCreateCart() {
        return cartRepository.getCart() == null ? cartRepository.createCart() : cartRepository.getCart();
    }

    public void resetCart(){
        Cart cart = cartRepository.getCart();
        cartService.resetCart(cart);
    }

    public void displayCart() {
        Cart cart = cartRepository.getCart();
        PromotionManager.PromotionDetails promotionDetails = cartService.getPromotionDetails(cart);

        CartView cartView = new CartView();
        cartView.displayCart(cart, promotionDetails);
    }
}
