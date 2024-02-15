package com.marketplace.commands;
import com.marketplace.application.ShoppingCartManager;
import com.marketplace.commandprocessing.CommandProcessor;

public class AddItemCommand implements Command {
    private ShoppingCartManager cartManager;
    private CommandProcessor.AddItemPayload payload;

    public AddItemCommand(ShoppingCartManager cartManager, CommandProcessor.AddItemPayload payload) {
        this.cartManager = cartManager;
        this.payload = payload;
    }

    @Override
    public void execute() {
        cartManager.addItemToCart(payload);
    }
}