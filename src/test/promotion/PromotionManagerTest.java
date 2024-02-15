package test.promotion;

import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.promotion.PromotionManager;
import com.marketplace.domain.promotion.PromotionManager.PromotionDetails;
import com.marketplace.domain.promotion.PromotionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PromotionManagerTest {

    @Mock
    private PromotionService promotionService;
    @Mock
    private Cart cart;

    private PromotionManager promotionManager;

    @Before
    public void setUp() {
        promotionService = mock(PromotionService.class);
        promotionManager = new PromotionManager(promotionService);
    }

    @Test
    public void getPromotionDetails_ReturnsCorrectDetails() {
        // Arrange
        double totalPriceBeforeDiscount = 1000.0;
        PromotionDetails mockPromotionDetails = new PromotionDetails(Map.of(9909, 100.0), 100.0, 900.0, 9909);
        when(promotionService.calculatePromotionDetails(cart, totalPriceBeforeDiscount)).thenReturn(mockPromotionDetails);

        // Act
        PromotionDetails returnedPromotionDetails = promotionManager.getPromotionDetails(cart, totalPriceBeforeDiscount);

        // Assert
        assertEquals("The final price should match", mockPromotionDetails.getFinalPrice(), returnedPromotionDetails.getFinalPrice(), 0.01);
        assertEquals("The max discount should match", mockPromotionDetails.getMaxDiscount(), returnedPromotionDetails.getMaxDiscount(), 0.01);
        assertEquals("The max discount promotion ID should match", mockPromotionDetails.getMaxDiscountPromotionId(), returnedPromotionDetails.getMaxDiscountPromotionId());
        assertEquals("The promotion discounts map should match", mockPromotionDetails.getPromotionDiscounts(), returnedPromotionDetails.getPromotionDiscounts());
    }
}
