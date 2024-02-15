
package com.marketplace.commands;
import com.marketplace.application.ShoppingCartManager;

public class ResetCartCommand implements Command {
    private ShoppingCartManager cartManager;

    public ResetCartCommand(ShoppingCartManager cartManager) {
        this.cartManager = cartManager;
    }

    @Override
    public void execute() {
        cartManager.resetCart();
    }
}