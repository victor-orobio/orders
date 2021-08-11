package com.service.orders.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.service.orders.common.OrderTestData.defaultDetail;
import static com.service.orders.common.OrderTestData.defaultOrder;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing Order")
@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @DisplayName("Order cost calculation")
    @Test
    void calculateCost(){
        Order.OrderId orderId = new Order.OrderId(1L);
        Order.ClientId clientId = new Order.ClientId(33L);
        Item.ItemId appleId = new Item.ItemId(2L);
        Item.ItemId orangeId = new Item.ItemId(3L);
        Integer expectedCost = 850;
        Order order = defaultOrder()
                .withOrderId(orderId)
                .withClientId(clientId)
                .withOrderDetail(new OrderedItems(
                        defaultDetail()
                                .withOrderId(orderId)
                                .withItemId(appleId)
                                .withQuantity(10)
                                .withCost(60).build(),
                        defaultDetail()
                                .withOrderId(orderId)
                                .withItemId(orangeId)
                                .withQuantity(10)
                                .withCost(25).build())
                ).build();

        Integer cost = order.calculateCost();

        assertThat(cost).isEqualByComparingTo(expectedCost);

    }
}
