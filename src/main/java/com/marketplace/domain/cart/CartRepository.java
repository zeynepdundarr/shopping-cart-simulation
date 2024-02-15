package com.marketplace.domain.cart;

import com.marketplace.presentation.CartView;

public class CartRepository {
    public Cart cart;
    private CartView cartView;
    public CartRepository() {
        this.cart = null;
        this.cartView = new CartView();
    }

    public Cart getCart() {
        if (cart == null) {
            cart = createCart();
        }
        return cart;
    }

    public Cart createCart() {
        Cart newCart = new Cart();
        newCart.addObserver(cartView);
        return newCart;
    }
}