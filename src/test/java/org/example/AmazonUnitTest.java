package org.example;

import org.example.Amazon.Amazon;
import org.example.Amazon.Item;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.ShoppingCart;
import org.example.Amazon.Cost.PriceRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AmazonUnitTest {

    @Test
    @DisplayName("specification-based")
    void testCalculateUsesRules() {

        ShoppingCart cart = mock(ShoppingCart.class);
        PriceRule rule = mock(PriceRule.class);

        when(cart.getItems()).thenReturn(List.of());
        when(rule.priceToAggregate(any())).thenReturn(50.0);

        Amazon amazon = new Amazon(cart, List.of(rule));

        double result = amazon.calculate();

        assertEquals(50.0, result);
    }

    @Test
    @DisplayName("structural-based")
    void testAddToCartCallsCart() {

        ShoppingCart cart = mock(ShoppingCart.class);
        Amazon amazon = new Amazon(cart, List.of());

        Item item = new Item(ItemType.OTHER, "TestItem", 1, 10);

        amazon.addToCart(item);

        verify(cart).add(item);
    }
}
