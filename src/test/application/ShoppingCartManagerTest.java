package test.application;

import com.marketplace.application.ShoppingCartManager;
import com.marketplace.commandprocessing.CommandProcessor;
import com.marketplace.domain.cart.Cart;
import com.marketplace.domain.cart.CartRepository;
import com.marketplace.domain.cart.CartService;
import com.marketplace.domain.item.DefaultItem;
import com.marketplace.domain.item.DigitalItem;

import com.marketplace.domain.item.Item;

import com.marketplace.domain.item.VasItem;
import com.marketplace.domain.promotion.PromotionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShoppingCartManagerTest {
    private CartService cartService;
    private CartRepository cartRepository;
    private ShoppingCartManager shoppingCartManager;
    private Cart cart;
    private Item eligibleItem;
    private Item ineligibleItem;
    private DefaultItem defaultItem;

    @Before
    public void setUp() {
        cartRepository = mock(CartRepository.class);
        cartService = mock(CartService.class);
        shoppingCartManager = new ShoppingCartManager(cartService, cartRepository);
    }

    @Test
    public void shouldAddDigitalItemToCart() {
        cart = mock(Cart.class);
        when(cartRepository.getCart()).thenReturn(cart);
        CommandProcessor.AddItemPayload payload = new CommandProcessor.AddItemPayload(1, "Digital Item", 7889, 5001, 150.0, 1);
        doNothing().when(cartService).addItemToCart(eq(cart), any(DigitalItem.class));
        shoppingCartManager.addItemToCart(payload);
        verify(cartService).addItemToCart(eq(cart), any(DigitalItem.class));
    }

    @Test
    public void shouldAddDefaultItemToCart() {
        cart = mock(Cart.class);
        when(cartRepository.getCart()).thenReturn(cart);
        CommandProcessor.AddItemPayload payload = new CommandProcessor.AddItemPayload(1, "VasItem", 1, 2, 50, 1, 2);
        doNothing().when(cartService).addItemToCart(eq(cart), any(DefaultItem.class));
        shoppingCartManager.addItemToCart(payload);
        verify(cartService).addItemToCart(eq(cart), any(DefaultItem.class));
    }

    @Test
    public void shouldAddVasItemToCart_WhenEligibleParentFound() {
        cart = mock(Cart.class);
        defaultItem = mock(DefaultItem.class);
        when(cartRepository.getCart()).thenReturn(cart);
        when(cart.getItems()).thenReturn(Collections.singletonList(defaultItem));
        when(defaultItem.getID()).thenReturn(2);
        when(defaultItem.getPrice()).thenReturn(2000.0);
        when(defaultItem.getCategoryID()).thenReturn(1001);

        CommandProcessor.AddItemPayload payload = new CommandProcessor.AddItemPayload(1, "VasItem", 3242, 5003, 50, 1, 2);
        doNothing().when(defaultItem).addVasItem(any(VasItem.class));

        shoppingCartManager.addVasItemToCart(payload);

        verify(defaultItem, times(1)).addVasItem(any(VasItem.class));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_WhenNoEligibleParentFound() {
        cart = mock(Cart.class);
        when(cartRepository.getCart()).thenReturn(cart);
        when(cart.getItems()).thenReturn(new ArrayList<>());

        CommandProcessor.AddItemPayload payload = new CommandProcessor.AddItemPayload(1, "VasItem", 3242, 5003, 50, 1, 2);
        shoppingCartManager.addVasItemToCart(payload);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_WhenParentItemIsNotDefault() {
        Cart cart = mock(Cart.class);
        when(cartRepository.getCart()).thenReturn(cart);
        Item nonDefaultItem = mock(Item.class);
        when(cart.getItems()).thenReturn(Collections.singletonList(nonDefaultItem));
        when(nonDefaultItem.getID()).thenReturn(2);

        CommandProcessor.AddItemPayload payload = new CommandProcessor.AddItemPayload(1, "VasItem", 3242, 5003, 50, 1, 2);
        shoppingCartManager.addVasItemToCart(payload);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_WhenVasItemPriceHigherParentItemPrice() {
        Cart cart = mock(Cart.class);
        when(cartRepository.getCart()).thenReturn(cart);
        DefaultItem defaultItem = mock(DefaultItem.class);
        when(defaultItem.getPrice()).thenReturn(200.0);
        when(cart.getItems()).thenReturn(Collections.singletonList(defaultItem));
        when(defaultItem.getID()).thenReturn(2);
        when(defaultItem.getCategoryID()).thenReturn(1001);
        CommandProcessor.AddItemPayload payload = new CommandProcessor.AddItemPayload(1, "VasItem", 3242, 5003, 500, 1, 2);
        shoppingCartManager.addVasItemToCart(payload);
    }


    @Test
    public void findEligibleParentItemForVasItem_ReturnsCorrectItem() {
        cart = mock(Cart.class);
        eligibleItem = mock(DefaultItem.class);
        ineligibleItem = mock(Item.class);
        defaultItem = mock(DefaultItem.class);

        when(eligibleItem.getCategoryID()).thenReturn(1001);
        when(eligibleItem.getID()).thenReturn(2);

        when(defaultItem.getCategoryID()).thenReturn(3004);
        when(defaultItem.getID()).thenReturn(3);

        when(ineligibleItem.getCategoryID()).thenReturn(2002);
        when(ineligibleItem.getID()).thenReturn(4);
        when(cart.getItems()).thenReturn(Arrays.asList(ineligibleItem, eligibleItem, defaultItem));

        Item foundItem = shoppingCartManager.findEligibleParentItemForVasItem(cart, 2);

        assertEquals("Should return the correct eligible parent item.", eligibleItem, foundItem);
    }

    @Test
    public void findEligibleParentItemForVasItem_WhenNoEligibleItem_ReturnsNull() {
        cart = mock(Cart.class);
        eligibleItem = mock(DefaultItem.class);
        ineligibleItem = mock(Item.class);
        defaultItem = mock(DefaultItem.class);

        when(eligibleItem.getCategoryID()).thenReturn(1001);
        when(eligibleItem.getID()).thenReturn(2);

        when(defaultItem.getCategoryID()).thenReturn(3004);
        when(defaultItem.getID()).thenReturn(3);

        when(ineligibleItem.getCategoryID()).thenReturn(2002);
        when(ineligibleItem.getID()).thenReturn(4);
        when(cart.getItems()).thenReturn(Arrays.asList(ineligibleItem, defaultItem));

        Item foundItem = shoppingCartManager.findEligibleParentItemForVasItem(cart, 1);

        assertNull("Should return null when no eligible parent item is found.", foundItem);
    }

        @Test
        public void shouldRemoveItemFromCart() {
            Cart cart = mock(Cart.class);
            when(cartRepository.getCart()).thenReturn(cart);

            CommandProcessor.RemoveItemPayload payload = new CommandProcessor.RemoveItemPayload(1);
            shoppingCartManager.removeItemFromCart(payload);

            verify(cartService).removeItemFromCart(cart, 1);
        }

        @Test
        public void shouldResetCart() {
            Cart cart = mock(Cart.class);

            when(cartRepository.getCart()).thenReturn(cart);

            shoppingCartManager.resetCart();

            verify(cartService).resetCart(cart);
        }

        @Test
        public void shouldDisplayCart() {
            Cart cart = mock(Cart.class);
            PromotionManager.PromotionDetails mockPromotionDetails = mock(PromotionManager.PromotionDetails.class);

            when(cartRepository.getCart()).thenReturn(cart);
            when(cartService.getPromotionDetails(cart)).thenReturn(mockPromotionDetails);

            shoppingCartManager.displayCart();
            verify(cartService).getPromotionDetails(cart);
        }

    }


