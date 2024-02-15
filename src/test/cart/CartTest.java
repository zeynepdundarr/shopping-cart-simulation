package test.cart;

import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.item.DefaultItem;
import com.marketplace.domain.item.Item;
import com.marketplace.domain.item.VasItem;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static com.marketplace.util.Constants.MAX_TOTAL_ITEMS;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class CartTest {

    private Cart cart;
    private Item item1;
    private Item item2;

    @Before
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void testAddItem() {
        item1 = mock(Item.class);
        item2 = mock(Item.class);
        when(item1.calculateItemTotalPrice()).thenReturn(100.0);
        when(item2.calculateItemTotalPrice()).thenReturn(200.0);
        cart.addItem(item1);
        cart.addItem(item2);
        assertEquals(2, cart.getItems().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testMaxUniqueItemsConstraint() {
        for (int i = 0; i < 10; i++) {
            DefaultItem mockItem = mock(DefaultItem.class);
            cart.addItem(mockItem);
            when(mockItem.getID()).thenReturn(i);
        }

        assertEquals(10, cart.getSizeOfUniqueItemsExcludingVas());
        cart.addItem(mock(DefaultItem.class));
    }

    @Test
    public void testVasItemsNotCountingTowardsLimit() {
        for (int i = 0; i < 10; i++) {
            DefaultItem mockItem = mock(DefaultItem.class);
            cart.addItem(mockItem);
            when(mockItem.getID()).thenReturn(i);
        }

        cart.addItem(mock(VasItem.class));
        cart.addItem(mock(VasItem.class));
        assertEquals(10, cart.getSizeOfUniqueItemsExcludingVas());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveItemNotInCart() {
        Item nonExistingItem = mock(Item.class);
        cart.removeItemByID(-1);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddTooManyDigitalItems() {
        Item digitalItem = mock(Item.class);
        when(digitalItem.getCategoryID()).thenReturn(7889);

        for (int i = 0; i <= 5; i++) {
            cart.addItem(digitalItem);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testAddTooManyUniqueItems() {
        Item defaultItem = mock(Item.class);
        when(defaultItem.getCategoryID()).thenReturn(7889);

        for (int i = 0; i <= 10; i++) {
            cart.addItem(defaultItem);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testAddTooManyItems() {
        for (int i = 0; i <= 30; i++) {
            cart.addItem(mock(DefaultItem.class));
        }
    }

    @Test
    public void testRemoveItem() {
        item1 = mock(Item.class);
        cart.addItem(item1);
        cart.removeItemByID(item1.getID());
        assertEquals(0, cart.getItems().size());
        assertFalse(cart.getItems().contains(item1));
    }


    @Test(expected = IllegalStateException.class)
    public void testAddItem_ExceedsMaxProducts() {
        for (int i = 0; i <= MAX_TOTAL_ITEMS; i++) {
            cart.addItem(mock(Item.class));
        }
        item1 = mock(Item.class);
        cart.addItem(item1);
    }


    @Test
    public void testGetSizeOfItemsByCategory() {
        int categoryId1 = 1;
        int categoryId2 = 2;

        Item item1 = mock(Item.class);
        when(item1.getCategoryID()).thenReturn(categoryId1);
        Item item2 = mock(Item.class);
        when(item2.getCategoryID()).thenReturn(categoryId1);
        Item item3 = mock(Item.class);
        when(item3.getCategoryID()).thenReturn(categoryId2);

        cart.addItem(item1);
        cart.addItem(item2);
        cart.addItem(item3);

        int sizeCategory1 = cart.getSizeOfItemsByCategory(categoryId1);
        int sizeCategory2 = cart.getSizeOfItemsByCategory(categoryId2);

        assertEquals(2, sizeCategory1);
        assertEquals(1, sizeCategory2);
    }

    @Test
    public void testGetSizeOfUniqueItemsExcludingVas() {
        Item item1 = mock(Item.class);
        when(item1.getID()).thenReturn(1);
        Item item2 = mock(Item.class);
        when(item2.getID()).thenReturn(1);
        Item item3 = mock(Item.class);
        when(item3.getID()).thenReturn(2);
        VasItem vasItem = mock(VasItem.class);
        when(vasItem.getID()).thenReturn(3);

        cart.addItem(item1);
        cart.addItem(item2);
        cart.addItem(item3);
        cart.addItem(vasItem);

        int uniqueItemCount = cart.getSizeOfUniqueItemsExcludingVas();
        assertEquals(2, uniqueItemCount);
    }
}