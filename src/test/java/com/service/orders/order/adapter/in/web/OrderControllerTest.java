package com.service.orders.order.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.orders.common.ResponseHandler;
import com.service.orders.order.adapter.in.web.dto.OrderInputData;
import com.service.orders.order.application.port.in.ReceiveOrderCommand;
import com.service.orders.order.application.port.in.ReceiveOrderUseCase;
import com.service.orders.order.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.service.orders.common.OrderTestData.defaultDetail;
import static com.service.orders.common.OrderTestData.defaultOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Testing Orders Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController controller;

    @MockBean
    private ResponseHandler responseHandler;

    @MockBean
    private ReceiveOrderUseCase receiveOrderUseCaseService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();
/*
    @BeforeEach
    void setUp() {
        this.controller = new OrderController(responseHandler, receiveOrderUseCaseService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
*/
    @DisplayName("Receiving order")
    @Test
    void receiveOrderSuccess() throws Exception {
        Order.OrderId orderId = new Order.OrderId(1L);
        Order.ClientId clientId = new Order.ClientId(33L);
        Item.ItemId appleId = new Item.ItemId(10L);
        Item.ItemId orangeId = new Item.ItemId(11L);
        List<RequestedItem> requestedItems = getRequestedItems(appleId, orangeId);
        OrderInputData orderInputData = new OrderInputData(clientId.getValue(), requestedItems);
        String jsonInputData = objectMapper.writeValueAsString(orderInputData);

        Order order = getOrder(orderId, clientId, appleId, orangeId);
        BDDMockito.given(receiveOrderUseCaseService.receiveOrder(any(ReceiveOrderCommand.class))).willReturn(order);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(jsonInputData))
                        .andDo(print())
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        ArgumentCaptor<ReceiveOrderCommand> orderCaptor = ArgumentCaptor.forClass(ReceiveOrderCommand.class);
        verify(receiveOrderUseCaseService, atMostOnce()).receiveOrder(orderCaptor.capture());
        assertThat(orderCaptor.getValue().getClientId()).isEqualTo(clientId);
    }

    @DisplayName("Argument Not Valid")
    @Test
    void receiveOrderFailInvalidArgument() throws Exception {
        Order.OrderId orderId = new Order.OrderId(1L);
        Order.ClientId clientId = new Order.ClientId(null);
        Item.ItemId appleId = new Item.ItemId(10L);
        Item.ItemId orangeId = new Item.ItemId(11L);
        List<RequestedItem> requestedItems = getRequestedItems(appleId, orangeId);
        OrderInputData orderInputData = new OrderInputData(clientId.getValue(), requestedItems);
        String jsonInputData = objectMapper.writeValueAsString(orderInputData);

        Order order = getOrder(orderId, clientId, appleId, orangeId);
        BDDMockito.given(receiveOrderUseCaseService.receiveOrder(any(ReceiveOrderCommand.class))).willReturn(order);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(jsonInputData))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    private Order getOrder(Order.OrderId orderId, Order.ClientId clientId, Item.ItemId... itemIds) {
        List<Detail> details = new ArrayList<>();
        for (Item.ItemId id: itemIds) {
            details.add(
                    defaultDetail()
                            .withOrderId(orderId)
                            .withItemId(id)
                            .withQuantity(10)
                            .withCost(10).build()
            );
        }
        return defaultOrder()
                .withOrderId(orderId)
                .withClientId(clientId)
                .withOrderDetail(new OrderDetail(details)).build();
    }

    private List<RequestedItem> getRequestedItems(Item.ItemId... itemIds){
        List<RequestedItem> requestedItems = new ArrayList<>();
        for (Item.ItemId id: itemIds) {
            requestedItems.add( new RequestedItem(id.getValue(), 10));
        }
        return requestedItems;
    }
}