package test.promotion;

import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.promotion.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertTrue;

public class PromotionServiceTest {

    private PromotionService promotionService;
    private Cart cart;
    private Promotion promotion1;
    private Promotion promotion2;
    private Promotion promotion3;

    @Before
    public void setUp() {
        promotionService = new PromotionService();
        cart = mock(Cart.class);

        promotion1 = mock(Promotion.class);
        promotion2 = mock(Promotion.class);
        promotion3 = mock(Promotion.class);
    }

    @Test
    public void shouldCalculatePromotionDetails() {
        // Arrange
        when(promotion1.applyPromotion(any(Cart.class), anyDouble())).thenReturn(15.0);
        when(promotion2.applyPromotion(any(Cart.class), anyDouble())).thenReturn(20.0);
        when(promotion3.applyPromotion(any(Cart.class), anyDouble())).thenReturn(25.0);

        when(promotion1.getPromotionId()).thenReturn(9909);
        when(promotion2.getPromotionId()).thenReturn(5676);
        when(promotion3.getPromotionId()).thenReturn(1232);

        promotionService.setPromotions(Arrays.asList(promotion1, promotion2, promotion3));
        double totalPriceBeforeDiscount = 100.0;

        // Act
        PromotionManager.PromotionDetails details = promotionService.calculatePromotionDetails(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals(25.0, details.getMaxDiscount(), 0.01);
        assertEquals(75.0, details.getFinalPrice(), 0.01);
        Map<Integer, Double> expectedDiscounts = Map.of(
                9909, 15.0,
                5676, 20.0,
                1232, 25.0

        );
        assertEquals(expectedDiscounts, details.getPromotionDiscounts());
    }

    @Test
    public void shouldApplyMaxPromotionNoPromotions() {
        // Arrange
        promotionService.setPromotions(Collections.emptyList());

        // Act
        double result = promotionService.applyMaxPromotion(cart, 100000);

        // Assert
        assertEquals("Expected no discount when there are no promotions.", 0, result, 0.01);
    }


    @Test
    public void shouldApplyMaxPromotionWithMultiplePromotions() {
        // Arrange
        Cart cart = mock(Cart.class);
        double totalPriceBeforeDiscount = 200.0;

        when(promotion1.applyPromotion(any(Cart.class), anyDouble())).thenReturn(10.0);
        when(promotion2.applyPromotion(any(Cart.class), anyDouble())).thenReturn(20.0);
        when(promotion3.applyPromotion(any(Cart.class), anyDouble())).thenReturn(30.0);
        promotionService.setPromotions(Arrays.asList(promotion1, promotion2, promotion3));

        // Act
        double result = promotionService.applyMaxPromotion(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals(30.0, result, 0.01);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldApplyMaxPromotionThrowsExceptionForExcessiveTotal() {
        Cart cart = new Cart();
        double totalPriceBeforeDiscount = 600000;
        when(promotion1.applyPromotion(cart, totalPriceBeforeDiscount)).thenReturn(50000.0);
        when(promotion2.applyPromotion(cart, totalPriceBeforeDiscount)).thenReturn(40000.0);
        promotionService.applyMaxPromotion(cart, totalPriceBeforeDiscount);
    }

    @Test
    public void shouldCalculateDiscounts() {
        // Arrange
        when(promotion1.applyPromotion(any(Cart.class), anyDouble())).thenReturn(10.0);
        when(promotion2.applyPromotion(any(Cart.class), anyDouble())).thenReturn(20.0);
        when(promotion1.getPromotionId()).thenReturn(9909);
        when(promotion2.getPromotionId()).thenReturn(5676);
        promotionService.setPromotions(Arrays.asList(promotion1, promotion2));

        // Act
        Map<Integer, Double> discounts = promotionService.calculateDiscounts(cart, 100.0);

        // Assert
        assertNotNull( "Discounts map should not be null", discounts);
        assertEquals(2, discounts.size());
        assertTrue("Discounts map should contain Promo1", discounts.containsKey(9909));
        assertTrue( "Discounts map should contain Promo2", discounts.containsKey(5676));
        assertEquals(10.0, discounts.get(9909), 0.01);
        assertEquals(20.0, discounts.get(5676), 0.01);
    }
}