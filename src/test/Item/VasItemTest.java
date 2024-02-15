package test.Item;
import com.marketplace.domain.item.VasItem;
import org.junit.Test;

import static org.testng.AssertJUnit.assertEquals;


public class VasItemTest {

    @Test
    public void testCalculateItemTotalPrice() {
        // Arrange
        int id = 1;
        String name = "VasItem";
        double price = 150.0;
        int quantity = 3;
        int linkedItemID = 400;
        VasItem item = new VasItem(id, name, price, quantity, linkedItemID);

        // Act
        double totalPrice = item.calculateItemTotalPrice();

        // Assert
        assertEquals(450.0, totalPrice, 0.01);
    }
}