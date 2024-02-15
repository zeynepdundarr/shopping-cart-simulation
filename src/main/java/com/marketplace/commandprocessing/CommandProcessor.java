package com.marketplace.commandprocessing;

import org.json.JSONObject;

public class CommandProcessor {

    public static CommandPayload processInput(String inputJson) {
        JSONObject jsonObject = new JSONObject(inputJson);

        String command = jsonObject.has("command") && !jsonObject.isNull("command") ? jsonObject.getString("command") : null;
        JSONObject payload = null;
        if (jsonObject.has("payload") && !jsonObject.isNull("payload")) {
            payload = jsonObject.getJSONObject("payload");
        }
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("Command cannot be null or empty.");
        }
        return new CommandPayload(command, payload);
    }

    public static class CommandPayload {
        private String command;
        private Object payload;

        public CommandPayload(String command, JSONObject payload) {
            this.command = command;
            this.payload = convertPayload(command, payload);
        }

        private Object convertPayload(String command, JSONObject payload) {
            switch (command) {
                case "addItem": {
                    int itemId = payload.getInt("itemId");
                    String name = payload.getString("name");
                    int categoryId = payload.getInt("categoryId");
                    int sellerId = payload.getInt("sellerId");
                    double price = payload.getDouble("price");
                    int quantity = payload.getInt("quantity");

                    return new AddItemPayload(
                            itemId,
                            name,
                            categoryId,
                            sellerId,
                            price,
                            quantity
                    );
                }
                case "addVasItem": {
                    int itemId = payload.getInt("itemId");
                    String name = payload.getString("name");
                    int categoryId = payload.getInt("categoryId");
                    int sellerId = payload.getInt("sellerId");
                    double price = payload.getDouble("price");
                    int quantity = payload.getInt("quantity");
                    int linkedItemId = payload.getInt("linkedItemId");

                    return new AddItemPayload(
                            itemId,
                            name,
                            categoryId,
                            sellerId,
                            price,
                            quantity,
                            linkedItemId
                    );
                }
                case "removeItem":
                    return new RemoveItemPayload(
                            payload.getInt("itemId")
                    );
                case "resetCart":
                case "displayCart":
                    return null;
                default:
                    throw new IllegalArgumentException("Unsupported command: " + command);
            }
        }

        public String getCommand() {
            return command;
        }

        public Object getPayload() {
            return payload;
        }
    }

    public static class AddItemPayload {
        private final int itemId;
        private final String name;
        private final int categoryId;
        private final int sellerId;
        private double price;
        private final int quantity;
        private final Integer linkedItemID;

        public AddItemPayload(int itemId, String name, int categoryId, int sellerId, double price, int quantity) {
            this(itemId, name, categoryId, sellerId, price, quantity, null);
        }

        public AddItemPayload(int itemId, String name, int categoryId, int sellerId, double price, int quantity, Integer linkedItemID) {
            this.itemId = itemId;
            this.name = name;
            this.categoryId = categoryId;
            this.sellerId = sellerId;
            this.price = price;
            this.quantity = quantity;
            this.linkedItemID = linkedItemID;
        }

        public int getItemId() { return itemId; }
        public String getName() { return name; }
        public int getCategoryId() { return categoryId; }
        public int getSellerId() { return sellerId; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public int getLinkedItemID() { return linkedItemID; }
    }

    public static class RemoveItemPayload {
        private int itemId;

        public RemoveItemPayload(int itemId) {
            this.itemId = itemId;
        }

        public int getItemId() {
            return itemId;
        }
    }
}
