
package com.marketplace.commands;
import com.marketplace.application.ShoppingCartManager;

public class DisplayCartCommand implements Command {
    private final ShoppingCartManager cartManager;

    public DisplayCartCommand(ShoppingCartManager cartManager) {
        this.cartManager = cartManager;
    }

    @Override
    public void execute() {
        cartManager.displayCart();
    }
}