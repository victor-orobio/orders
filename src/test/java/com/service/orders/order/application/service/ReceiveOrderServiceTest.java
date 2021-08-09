package com.service.orders.order.application.service;

import com.service.orders.order.application.port.in.ReceiveOrderCommand;
import com.service.orders.order.application.port.in.ReceiveOrderUseCase;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Order;
import com.service.orders.order.domain.RequestedItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing receive order use case")
@ExtendWith(MockitoExtension.class)
class ReceiveOrderServiceTest {

    @InjectMocks
    private ReceiveOrderService service;

    @BeforeEach
    public void setup() {
        this.service = new ReceiveOrderService();
    }

    @Test
    void receiveOrderSuccess() {
        Order.ClientId clientId = new Order.ClientId(90L);
        List<RequestedItem> requestedItems = Arrays.asList(
                new RequestedItem(new Item.ItemId(10L),10),
                new RequestedItem(new Item.ItemId(11L),10));
        ReceiveOrderCommand command = new ReceiveOrderCommand(clientId,requestedItems);

        Order responseOrder = service.receiveOrder(command);

        assertThat(responseOrder).isNotNull();
        assertThat(responseOrder.getClientId()).isEqualTo(clientId);
        assertThat(responseOrder.getOrderDetail().getDetails().size()).isEqualTo(requestedItems.size());
        assertThat(responseOrder.calculateCost()).isEqualTo(850);

    }
}