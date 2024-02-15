package test.cart;

import com.marketplace.application.ShoppingCartManager;
import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.cart.CartService;
import com.marketplace.domain.item.Item;
import com.marketplace.domain.promotion.PromotionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class CartServiceTest {

    @Mock
    private PromotionManager promotionManager;

    @Mock
    private ShoppingCartManager cartManager;

    private CartService cartService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(promotionManager);
    }

    @Test
    public void calculateTotalPriceBeforeDiscount_ShouldCalculateCorrectTotal() {
        Cart cart = new Cart();
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        when(item1.calculateItemTotalPrice()).thenReturn(100.0);
        when(item2.calculateItemTotalPrice()).thenReturn(200.0);

        cart.addItem(item1);
        cart.addItem(item2);

        double total = cartService.calculateTotalPriceBeforeDiscount(cart);

        assertEquals(300.0, total, 0.01);
    }

    @Test
    public void addItemToCart_ShouldAddItemSuccessfully() {
        Cart cart = new Cart();
        Item item = mock(Item.class);
        cartService.addItemToCart(cart, item);

        assertTrue("Item should be added to the cart successfully.",cart.getItems().contains(item));
    }

    @Test
    public void removeItemFromCart_ShouldRemoveItemSuccessfully() {
        Cart cart = new Cart();
        Item item = mock(Item.class);
        when(item.getID()).thenReturn(1);

        cart.addItem(item);
        cartService.removeItemFromCart(cart, 1);

        assertFalse("Item should be removed from the cart successfully.", cart.getItems().contains(item));
    }

    @Test
    public void getPromotionDetails_ShouldReturnPromotionDetails() {
        Cart cart = new Cart();
        cart.addItem(mock(Item.class));
        PromotionManager.PromotionDetails expectedPromotionDetails = mock(PromotionManager.PromotionDetails.class);

        when(promotionManager.getPromotionDetails(eq(cart), anyDouble())).thenReturn(expectedPromotionDetails);

        PromotionManager.PromotionDetails promotionDetails = cartService.getPromotionDetails(cart);

        assertEquals(expectedPromotionDetails, promotionDetails);
    }

    @Test
    public void resetCart_ShouldRemoveAllItems() {
        Cart cart = new Cart();
        cart.addItem(mock(Item.class));
        cart.addItem(mock(Item.class));

        cartService.resetCart(cart);

        assertTrue("Cart should be empty after reset.", cart.getItems().isEmpty());
    }
}
