package test.commandprocessing;

import com.marketplace.application.ShoppingCartManager;
import com.marketplace.commandprocessing.CommandProcessor;
import com.marketplace.commands.*;
import com.marketplace.commandprocessing.CommandHandler;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CommandHandlerTest {

    private CommandHandler commandHandler;
    private ShoppingCartManager cartManager;

    @Before
    public void setUp() {
        cartManager = mock(ShoppingCartManager.class);
        commandHandler = new CommandHandler(cartManager);
    }

    @Captor
    private ArgumentCaptor<CommandProcessor.AddItemPayload> addItemPayloadCaptor;


    @Test
    public void testHandleAddItemCommandWithValidPayload() {
        ShoppingCartManager mockCartManager = Mockito.mock(ShoppingCartManager.class);
        CommandHandler commandHandler = new CommandHandler(mockCartManager);

        addItemPayloadCaptor = ArgumentCaptor.forClass(CommandProcessor.AddItemPayload.class);

        JSONObject payload = new JSONObject();
        payload.put("itemId", 1);
        payload.put("name", "abc");
        payload.put("categoryId", 5);
        payload.put("sellerId", 57);
        payload.put("price", 300);
        payload.put("quantity", 1);

        CommandProcessor.CommandPayload commandPayload = new CommandProcessor.CommandPayload("addItem", payload);

        commandHandler.handleCommand(commandPayload);

        verify(mockCartManager, times(1)).addItemToCart(addItemPayloadCaptor.capture());

        CommandProcessor.AddItemPayload capturedPayload = addItemPayloadCaptor.getValue();

        assertEquals(1, capturedPayload.getItemId(), 0.01);
        assertEquals("abc", capturedPayload.getName());
        assertEquals(5, capturedPayload.getCategoryId(), 0.01);
        assertEquals(57, capturedPayload.getSellerId(), 0.01);
        assertEquals(300, capturedPayload.getPrice(), 0.01);
        assertEquals(1, capturedPayload.getQuantity(), 0.01);
    }

    @Test
    public void testHandleCommand_ExecutesAddVasItemCommand() {
        // Arrange
        ShoppingCartManager mockCartManager = Mockito.mock(ShoppingCartManager.class);
        CommandHandler commandHandler = new CommandHandler(mockCartManager);

        addItemPayloadCaptor = ArgumentCaptor.forClass(CommandProcessor.AddItemPayload.class);

        JSONObject payload = new JSONObject();
        payload.put("itemId", 1);
        payload.put("name", "vasAbc");
        payload.put("categoryId", 3242);
        payload.put("sellerId", 5003);
        payload.put("price", 300);
        payload.put("quantity", 1);

        CommandProcessor.CommandPayload commandPayload = new CommandProcessor.CommandPayload("addItem", payload);

        commandHandler.handleCommand(commandPayload);

        verify(mockCartManager, times(1)).addItemToCart(addItemPayloadCaptor.capture());

        CommandProcessor.AddItemPayload capturedPayload = addItemPayloadCaptor.getValue();

        assertEquals(1, capturedPayload.getItemId(), 0.01);
        assertEquals("vasAbc", capturedPayload.getName());
        assertEquals(3242, capturedPayload.getCategoryId());
        assertEquals(5003, capturedPayload.getSellerId());
        assertEquals(300, capturedPayload.getPrice(), 0.01);
        assertEquals(1, capturedPayload.getQuantity(), 0.01);
    }

    @Test
    public void testExecute_RemoveItemFromCart() {
        ShoppingCartManager mockCartManager = Mockito.mock(ShoppingCartManager.class);
        CommandProcessor.RemoveItemPayload payload = new CommandProcessor.RemoveItemPayload(1);
        RemoveItemCommand removeItemCommand = new RemoveItemCommand(mockCartManager, payload);
        removeItemCommand.execute();
        verify(mockCartManager, times(1)).removeItemFromCart(payload);
    }

    @Test
    public void testHandleCommand_ResetCartCommand() {
        CommandProcessor.CommandPayload commandPayload = new CommandProcessor.CommandPayload("resetCart", null);
        commandHandler.handleCommand(commandPayload);
        verify(cartManager).resetCart();
    }

    @Test
    public void testHandleCommand_DisplayCartCommand() {
        CommandProcessor.CommandPayload commandPayload = new CommandProcessor.CommandPayload("displayCart", null);
        commandHandler.handleCommand(commandPayload);
        verify(cartManager).displayCart();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreationWithInvalidCommand_ThrowsIllegalArgumentException() {
        CommandProcessor.CommandPayload commandPayload= new CommandProcessor.CommandPayload("invalidCommand", null);
    }
}
