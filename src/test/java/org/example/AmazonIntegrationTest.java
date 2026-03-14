package org.example;

import org.example.Amazon.Amazon;
import org.example.Amazon.Database;
import org.example.Amazon.Item;
import org.example.Amazon.ShoppingCartAdaptor;
import org.example.Amazon.Cost.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AmazonIntegrationTest {

    Database database;
    ShoppingCartAdaptor cart;
    Amazon amazon;

    @BeforeEach
    void setup() {
        database = new Database();
        database.resetDatabase();

        cart = new ShoppingCartAdaptor(database);

        amazon = new Amazon(cart,
                List.of(
                        new RegularCost(),
                        new ExtraCostForElectronics(),
                        new DeliveryPrice()
                )
        );
    }

    @Test
    @DisplayName("specification-based")
    void testCalculatePrice() {

        Item electronicItem = new Item(ItemType.ELECTRONIC, "Laptop", 1, 1000);
        Item otherItem = new Item(ItemType.OTHER, "Book", 2, 20);

        amazon.addToCart(electronicItem);
        amazon.addToCart(otherItem);

        double total = amazon.calculate();

        assertTrue(total > 0);
    }

    @Test
    @DisplayName("structural-based")
    void testMultipleItemsAddedToCart() {

        Item item1 = new Item(ItemType.OTHER, "Pen", 3, 2);
        Item item2 = new Item(ItemType.ELECTRONIC, "Headphones", 1, 100);

        amazon.addToCart(item1);
        amazon.addToCart(item2);

        assertEquals(2, cart.getItems().size());
    }
}
