package com.marketplace.commandprocessing;

import com.marketplace.commands.*;
import com.marketplace.application.ShoppingCartManager;

public class CommandHandler {

    private ShoppingCartManager cartManager;

    public CommandHandler(ShoppingCartManager cartManager) {
        this.cartManager = cartManager;
    }

    public void handleCommand(CommandProcessor.CommandPayload commandPayload) {
        Command command = null;

        switch (commandPayload.getCommand()) {
            case "addItem":
                if (commandPayload.getPayload() instanceof CommandProcessor.AddItemPayload) {
                    command = new AddItemCommand(cartManager, (CommandProcessor.AddItemPayload) commandPayload.getPayload());
                }
                break;
            case "addVasItem":
                if (commandPayload.getPayload() instanceof CommandProcessor.AddItemPayload) {
                    command = new AddVasItemCommand(cartManager, (CommandProcessor.AddItemPayload) commandPayload.getPayload());
                }
                break;
            case "removeItem":
                if (commandPayload.getPayload() instanceof CommandProcessor.RemoveItemPayload) {
                    command = new RemoveItemCommand(cartManager, (CommandProcessor.RemoveItemPayload) commandPayload.getPayload());
                }
                break;
            case "resetCart":
                if (commandPayload.getPayload() == null) {
                    command = new ResetCartCommand(cartManager);
                }
                break;
            case "displayCart":
                if (commandPayload.getPayload() == null) {
                    command = new DisplayCartCommand(cartManager);
                }
                break;
        }

        if (command != null) {
            command.execute();
        } else {
            System.err.println("Command not recognized or payload was invalid.");
        }
    }
}
