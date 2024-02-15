package com.marketplace.commands;

import com.marketplace.application.ShoppingCartManager;
import com.marketplace.commandprocessing.CommandProcessor;

public class AddVasItemCommand implements Command {
    private ShoppingCartManager cartManager;
    private CommandProcessor.AddItemPayload payload;

    public AddVasItemCommand(ShoppingCartManager cartManager, CommandProcessor.AddItemPayload payload) {
        this.cartManager = cartManager;
        this.payload = payload;
    }

    @Override
    public void execute() {
        cartManager.addVasItemToCart(payload);
    }
}