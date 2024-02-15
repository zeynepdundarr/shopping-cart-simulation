package test.Item;
import com.marketplace.domain.item.DigitalItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class DigitalItemTest {

    @Test
    public void testCalculateItemTotalPrice() {
        // Arrange
        int id = 1;
        String name = "DigitalItem";
        double price = 50.0;
        int sellerID = 300;
        int quantity = 4;
        DigitalItem item = new DigitalItem(id, name, price, sellerID, quantity);

        // Act
        double totalPrice = item.calculateItemTotalPrice();

        // Assert
        assertEquals(200.0, totalPrice,0.01);
    }
}
