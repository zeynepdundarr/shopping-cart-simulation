package test.cart;

import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.cart.CartRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertSame;


public class CartRepositoryTest {

    private CartRepository cartRepository;

    @Before
    public void setUp() {
        cartRepository = new CartRepository();
    }

    @Test
    public void shouldGetCartReturnsNonNullCart() {
        Cart cart = cartRepository.getCart();
        assertNotNull(cart);
    }

    @Test
    public void shouldGetCartAlwaysReturnsSameInstance() {
        Cart firstCartInstance = cartRepository.getCart();
        Cart secondCartInstance = cartRepository.getCart();
        assertSame(firstCartInstance, secondCartInstance);
    }
}