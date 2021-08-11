package com.service.orders.order.application.service;

import com.service.orders.common.OrderTestData;
import com.service.orders.order.adapter.out.persistence.OrderQueryAdapter;
import com.service.orders.order.application.port.in.ReceiveOrderCommand;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Offer;
import com.service.orders.order.domain.Order;
import com.service.orders.order.domain.RequestedItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.service.orders.common.OrderTestData.listItems;
import static com.service.orders.common.OrderTestData.listOffers;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Testing receive order use case")
@ExtendWith(MockitoExtension.class)
class ReceiveOrderServiceTest {

    @InjectMocks
    private ReceiveOrderService service;

    @Mock
    private OrderQueryAdapter queryAdapter;

    @BeforeEach
    public void setup() {
        this.service = new ReceiveOrderService(queryAdapter);
    }

    @Test
    void receiveOrderSuccess() {
        Order.ClientId clientId = new Order.ClientId(90L);
        Item.ItemId appleId = new Item.ItemId(10L);
        Item.ItemId orangeId = new Item.ItemId(11L);
        List<RequestedItem> requestedItems = Arrays.asList(
                new RequestedItem(appleId.getValue(),10),
                new RequestedItem(orangeId.getValue(),10));
        ReceiveOrderCommand command = new ReceiveOrderCommand(clientId,requestedItems);
        Map<Long, Offer> offers = listOffers();
        Map<Long, Item> items = listItems();

        BDDMockito.given(queryAdapter.findAllItems()).willReturn(items);
        BDDMockito.given(queryAdapter.findAllOffers()).willReturn(offers);
        Order responseOrder = service.receiveOrder(command);

        assertThat(responseOrder).isNotNull();
        assertThat(responseOrder.getClientId()).isEqualTo(clientId);
        assertThat(responseOrder.getOrderedItems().getDetails().size()).isEqualTo(requestedItems.size());
        assertThat(responseOrder.getOfferedDiscounts().getDetails().size()).isEqualTo(requestedItems.size());
        assertThat(responseOrder.calculateCost()).isEqualTo(475);

    }
}