package test.promotion;

import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.cart.CartService;
import com.marketplace.domain.item.DefaultItem;
import com.marketplace.domain.promotion.TotalPricePromotion;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TotalPricePromotionTest {

    private Cart cart;
    private CartService cartService;
    private TotalPricePromotion totalPricePromotion;

    @Before
    public void setUp() {
        cart = mock(Cart.class);
        cartService = mock(CartService.class);
        totalPricePromotion = new TotalPricePromotion();
    }

    @Test
    public void testApplyPromotionForLowRange() {
        // Arrange
        DefaultItem defaultItem = new DefaultItem(1, "Mobile Phone", 3000.0, 3003, 4002, 1);
        when(cart.getItems()).thenReturn(Arrays.asList(defaultItem));

        // Act
        double totalPriceBeforeDiscount = 600.0;
        when(cartService.calculateTotalPriceBeforeDiscount(cart)).thenReturn(totalPriceBeforeDiscount);

        // Assert
        double discount = totalPricePromotion.applyPromotion(cart, totalPriceBeforeDiscount);
        assertEquals("Discount must be 250 for total price >= 500 and < 5000", 250, discount, 0.01);
    }


    @Test
    public void testApplyPromotionForMidRange() {
        // Arrange
        DefaultItem defaultItem = new DefaultItem(1, "Mobile Phone", 6000.0, 3003, 4002, 1);
        when(cart.getItems()).thenReturn(Arrays.asList(defaultItem));

        // Act
        double totalPriceBeforeDiscount = 6000.0;
        when(cartService.calculateTotalPriceBeforeDiscount(cart)).thenReturn(totalPriceBeforeDiscount);

        // Assert
        double discount = totalPricePromotion.applyPromotion(cart, totalPriceBeforeDiscount);
        assertEquals("Discount must be 500 for total price >= 5000 and < 10000", 500, discount, 0.01);
    }

    @Test
    public void testApplyPromotionForHighRange() {
        // Arrange
        DefaultItem defaultItem = new DefaultItem(1, "Mobile Phone", 11000.0, 3003, 4002, 1);
        when(cart.getItems()).thenReturn(Arrays.asList(defaultItem));

        // Act
        double totalPriceBeforeDiscount = 16000.0;
        when(cartService.calculateTotalPriceBeforeDiscount(cart)).thenReturn(totalPriceBeforeDiscount);

        // Assert
        double discount = totalPricePromotion.applyPromotion(cart, totalPriceBeforeDiscount);
        assertEquals("Discount must be 1000 for total price >= 10000 and < 50000", 1000, discount, 0.01);
    }

    @Test
    public void testApplyPromotionForVeryHighRange() {
        // Arrange
        DefaultItem defaultItem = new DefaultItem(1, "Mobile Phone", 51000.0, 3003, 4002, 1);
        when(cart.getItems()).thenReturn(Arrays.asList(defaultItem));

        double totalPriceBeforeDiscount = 51000.0;
        when(cartService.calculateTotalPriceBeforeDiscount(cart)).thenReturn(totalPriceBeforeDiscount);

        // Act
        double discount = totalPricePromotion.applyPromotion(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals("Discount must be 2000 for total price >= 50000", 2000, discount, 0.01);
    }
}
