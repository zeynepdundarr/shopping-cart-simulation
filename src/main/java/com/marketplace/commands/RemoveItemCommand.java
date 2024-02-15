package com.marketplace.commands;
import com.marketplace.application.ShoppingCartManager;
import com.marketplace.commandprocessing.CommandProcessor;

public class RemoveItemCommand implements Command {
    private ShoppingCartManager cartManager;
    private CommandProcessor.RemoveItemPayload payload;

    public RemoveItemCommand(ShoppingCartManager cartManager, CommandProcessor.RemoveItemPayload payload) {
        this.cartManager = cartManager;
        this.payload = payload;
    }

    @Override
    public void execute() {
        cartManager.removeItemFromCart(payload);
    }
}