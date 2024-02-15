package test.Item;
import com.marketplace.domain.item.DefaultItem;
import com.marketplace.domain.item.VasItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultItemTest {

    @Test
    public void testCalculateItemTotalPrice() {
        // Arrange
        int id = 1;
        String name = "DefaultItem";
        double price = 100.0;
        int categoryID = 200;
        int sellerID = 300;
        int quantity = 2;
        DefaultItem item = new DefaultItem(id, name, price, categoryID, sellerID, quantity);

        // Act
        double totalPrice = item.calculateItemTotalPrice();

        // Assert
        assertEquals(200.0, totalPrice, 0.01);
    }
    @Test
    public void testGetVasItemsCount_WithNoVasItems() {
        // Arrange
        DefaultItem item = new DefaultItem(1, "DefaultItem", 100.0, 200, 300, 2);

        // Act
        int vasItemsCount = item.getVasItemsCount();

        // Assert
        assertEquals(0, vasItemsCount);
    }

    @Test
    public void testGetVasItemsCount_WithVasItems() {
        // Arrange
        DefaultItem item = new DefaultItem(1, "DefaultItem", 100.0, 200, 300, 2);
        VasItem vasItem1 = new VasItem(2, "VasItem1", 50.0, 1, 1);
        VasItem vasItem2 = new VasItem(3, "VasItem2", 75.0, 1, 1);

        item.addVasItem(vasItem1);
        item.addVasItem(vasItem2);

        // Act
        int vasItemsCount = item.getVasItemsCount();

        // Assert
        assertEquals(2, vasItemsCount);
    }

}
