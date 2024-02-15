package com.marketplace.application;

import com.marketplace.commandprocessing.CommandHandler;
import com.marketplace.commandprocessing.CommandProcessor;
import com.marketplace.domain.cart.CartRepository;
import com.marketplace.domain.cart.CartService;
import com.marketplace.domain.promotion.*;

import static com.marketplace.util.Constants.*;

public class ShoppingCartApplication {

    private static ShoppingCartManager cartManager;
    private static CommandHandler commandHandler;

    public static void main(String[] args) {
        initializeComponents();
        processCommands();
    }

    private static void initializeComponents() {
        PromotionService promotionService = new PromotionService();
        CartRepository cartRepository = new CartRepository();
        PromotionManager promotionManager = new PromotionManager(promotionService);

        CartService cartService = new CartService(promotionManager);
        cartManager = new ShoppingCartManager(cartService, cartRepository);
        commandHandler = new CommandHandler(cartManager);
    }

    private static void processCommands() {
        String[] jsonInputs = {
                // Add DefaultItem
               "{\"command\":\"addItem\",\"payload\":{\"itemId\":2, \"name\":\"abc\",\"categoryId\":"+CATEGORY_ID_FURNITURE+",\"sellerId\":57,\"price\":300,\"quantity\":1}}",

                // Add DefaultItem
                "{\"command\":\"addItem\",\"payload\":{\"itemId\":4, \"name\":\"abc\",\"categoryId\":"+CATEGORY_ID_ELECTRONICS+",\"sellerId\":57,\"price\":300,\"quantity\":1}}",

                // Add DefaultItem
                "{\"command\":\"addItem\",\"payload\":{\"itemId\":4, \"name\":\"abc\",\"categoryId\":"+CATEGORY_ID_ELECTRONICS+",\"sellerId\":57,\"price\":300,\"quantity\":1}}",

                // Add max 4 VasItems
                "{\"command\":\"addVasItem\",\"payload\":{\"itemId\":22, \"name\":\"VasItem1\",\"categoryId\":"+CATEGORY_ID_VAS_ITEM+",\"sellerId\":5003,\"price\":30,\"quantity\":4,\"linkedItemId\":2}}",

                // The price of the VasItem cannot be higher than the DefaultItem's price.
                "{\"command\":\"addVasItem\",\"payload\":{\"itemId\":22, \"name\":\"VasItem2\",\"categoryId\":"+CATEGORY_ID_VAS_ITEM+",\"sellerId\":5003,\"price\":3000,\"quantity\":4,\"linkedItemId\":2}}",

                // VasItem only can be added to DefaultItems
                // No eligible DefaultItem parent found for VasItem.
                "{\"command\":\"addVasItem\",\"payload\":{\"itemId\":22, \"name\":\"VasItem3\",\"categoryId\":"+CATEGORY_ID_VAS_ITEM+",\"sellerId\":5003,\"price\":3000,\"quantity\":4,\"linkedItemId\":4}}",

                "{\"command\":\"removeItem\", \"payload\":{\"itemId\":2}}",
                "{\"command\":\"resetCart\"}",
                "{\"command\":\"displayCart\"}"
        };

        for (String inputJson : jsonInputs) {
            try {
                CommandProcessor.CommandPayload commandPayload = CommandProcessor.processInput(inputJson);
                commandHandler.handleCommand(commandPayload);
            } catch (Exception e) {
                System.err.println("Error processing command: " + e.getMessage());
            }
        }
    }
}
