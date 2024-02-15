package test.commandprocessing;
import com.marketplace.commandprocessing.CommandProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.testng.AssertJUnit.*;

public class CommandProcessorTest {

    private CommandProcessor commandProcessor;

    @Before
    public void setUp() {
        commandProcessor = new CommandProcessor();
    }

    @Test
    public void shouldTestProcessInput_ValidAddItemCommand() {
        String inputJson = "{\"command\":\"addItem\",\"payload\":{\"itemId\":1,\"name\":\"TestItem\",\"categoryId\":10,\"sellerId\":100,\"price\":299.99,\"quantity\":2}}";

        CommandProcessor.CommandPayload result = commandProcessor.processInput(inputJson);

        assertNotNull(result);
        assertEquals("addItem", result.getCommand());
        assertTrue(result.getPayload() instanceof CommandProcessor.AddItemPayload);

        CommandProcessor.AddItemPayload payload = (CommandProcessor.AddItemPayload) result.getPayload();
        assertEquals(1, payload.getItemId());
        assertEquals("TestItem", payload.getName());
        assertEquals(299.99, payload.getPrice(), 0.001);
    }

    @Test
    public void shouldProcessInput_ValidRemoveItemCommand() {
        String inputJson = "{\"command\":\"removeItem\",\"payload\":{\"itemId\":1}}";

        CommandProcessor.CommandPayload result = CommandProcessor.processInput(inputJson);

        assertNotNull(result);
        assertEquals("removeItem", result.getCommand());
        assertTrue(result.getPayload() instanceof CommandProcessor.RemoveItemPayload);

        CommandProcessor.RemoveItemPayload payload = (CommandProcessor.RemoveItemPayload) result.getPayload();
        assertEquals(1, payload.getItemId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessInputWithInvalidCommandThrowsException() {
        String invalidCommandJson = "{\"payload\": {\"itemId\": 1, \"name\": \"ItemName\", \"categoryId\": 5, \"price\": 300.0, \"quantity\": 2}}";
        CommandProcessor.processInput(invalidCommandJson);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldProcessInput_NoCommand() {
        String inputJson = "{\"payload\":{\"itemId\":1}}";
        CommandProcessor.processInput(inputJson);
    }
}