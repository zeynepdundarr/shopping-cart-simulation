package test.promotion;

import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.item.Item;
import com.marketplace.domain.promotion.CategoryPromotion;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class CategoryPromotionTest {
    private Cart cart;
    private  CategoryPromotion promotion;

    @Before
    public void setUp() {
        cart = mock(Cart.class);
        promotion = new CategoryPromotion();
    }

    @Test
    public void shouldApplyPromotion() {
        // Arrange
        Item discountItem = mock(Item.class);
        Item nonDiscountItem = mock(Item.class);

        double discountItemPrice = 100.0;
        double nonDiscountItemPrice = 200.0;
        double totalPriceBeforeDiscount = 300.0;

        when(discountItem.getCategoryID()).thenReturn(3003);
        when(discountItem.calculateItemTotalPrice()).thenReturn(discountItemPrice);
        when(nonDiscountItem.getCategoryID()).thenReturn(9999);
        when(nonDiscountItem.calculateItemTotalPrice()).thenReturn(nonDiscountItemPrice);
        when(cart.getItems()).thenReturn(Arrays.asList(discountItem, nonDiscountItem));

        double expectedDiscount = discountItemPrice * 0.05;

        // Act
        double discount = promotion.applyPromotion(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals(expectedDiscount, discount, 0.01);
    }

    @Test
    public void shouldApplyPromotionWithEmptyCart() {
        // Act
        when(cart.getItems()).thenReturn(Arrays.asList());
        double totalDiscount = promotion.applyPromotion(cart, 0.0);

        // Assert
        assertEquals(0.0, totalDiscount, 0.01);
    }

    @Test
    public void shouldGetPromotionId() {
        // Assert
        assertEquals(5676, promotion.getPromotionId());
    }
}
