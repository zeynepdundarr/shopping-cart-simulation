package test.promotion;

import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.item.Item;
import com.marketplace.domain.item.VasItem;
import com.marketplace.domain.promotion.SameSellerPromotion;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SameSellerPromotionTest {

    private Cart cart;
    private SameSellerPromotion promotion;

    @Before
    public void setUp() {
        cart = mock(Cart.class);
        promotion = new SameSellerPromotion();
    }

    @Test
    public void applyPromotion_ShouldBeAllVasItems() {
        // Arrange
        double totalPriceBeforeDiscount = 4000;
        VasItem vasItem1 = mock(VasItem.class);
        VasItem vasItem2 = mock(VasItem.class);
        when(vasItem1.getSellerID()).thenReturn(2000);
        when(vasItem2.getSellerID()).thenReturn(2000);
        when(cart.getItems()).thenReturn(Arrays.asList(vasItem1, vasItem2));

        // Act
        double discount = promotion.applyPromotion(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals(0.0, discount, 0.01);
    }

    @Test
    public void applyPromotion_ShouldBeMixedSellers() {
        // Arrange
        double totalPriceBeforeDiscount = 300;
        Item item1 = createMockItemWithSeller(1, 100.0);
        Item item2 = createMockItemWithSeller(2, 200.0);
        when(cart.getItems()).thenReturn(Arrays.asList(item1, item2));
        // Act
        double discount = promotion.applyPromotion(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals(0.0, discount, 0.01);
    }

    @Test
    public void applyPromotion_ShouldBeSingleNonVasItem() {
        // Arrange
        double totalPriceBeforeDiscount = 100.0;
        Item singleNonVasItem = createMockItemWithSeller(1, 100.0);
        when(cart.getItems()).thenReturn(Arrays.asList(singleNonVasItem));

        // Act
        double discount = promotion.applyPromotion(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals(0.0, discount,0.01);
    }

    @Test
    public void applyPromotion_ShouldBeSingleSeller() {
        // Arrange
        double totalPriceBeforeDiscount = 100.0;
        Item item1 = createMockItemWithSeller(1, 100.0);
        Item item2 = createMockItemWithSeller(1, 200.0);
        when(cart.getItems()).thenReturn(Arrays.asList(item1, item2));

        // Act
        double discount = promotion.applyPromotion(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals(30.0, discount, 0.01);
    }

    private Item createMockItemWithSeller(int sellerId, double price) {
        Item item = mock(Item.class);
        when(item.getSellerID()).thenReturn(sellerId);
        when(item.calculateItemTotalPrice()).thenReturn(price);
        return item;
    }

    @Test
    public void applyPromotion_ShouldBeOneVasOneNonVasItem() {
        // Arrange
        double totalPriceBeforeDiscount = 100.0;
        int sellerId = 1;
        Item nonVasItem = createMockItemWithSeller(sellerId, 100.0);
        VasItem vasItem = mock(VasItem.class);
        when(cart.getItems()).thenReturn(Arrays.asList(nonVasItem, vasItem));

        // Act
        double discount = promotion.applyPromotion(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals(0, discount, 0.01);
    }
}
