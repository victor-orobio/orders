package com.service.orders.order.adapter.out.persistence;

import com.service.orders.order.adapter.out.persistence.entity.*;
import com.service.orders.order.domain.Order;
import com.service.orders.order.domain.OrderedItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.service.orders.common.OrderTestData.defaultOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Testing Order Estate Manager Adapter")
@ExtendWith(MockitoExtension.class)
class OrderEstateManagerAdapterTest {

    @InjectMocks
    private OrderEstateManagerAdapter adapter;

    @Mock
    private  OrderRepository orderRepository;

    @Mock
    private  ItemDetailRepository itemDetailRepository;

    @Mock
    private  DiscountDetailRepository discountDetailRepository;

    @BeforeEach
    public void setup(){
        this.adapter = new OrderEstateManagerAdapter(orderRepository, itemDetailRepository, discountDetailRepository);
    }

    @Test
    void successfullySaveWithDiscounts() {
        Order order = defaultOrder().withOrderId(new Order.OrderId(1L)).build();
        List<ItemDetailEntity> itemDetailEntities = order.getOrderedItems().getDetails().stream()
                .map(ItemDetailDataMapper::mapToEntity).collect(Collectors.toList());
        List<DiscountDetailEntity> discountDetailEntities = order.getOfferedDiscounts().getDetails().stream()
                .map(DiscountDetailDataMapper::mapToEntity).collect(Collectors.toList());
        OrderEntity orderEntity = new OrderEntity(
                order.getId().getValue(),
                order.getClientId().getValue(),
                order.getTimestamp()
        );
        BDDMockito.given(orderRepository.save(any(OrderEntity.class))).willReturn(orderEntity);
        BDDMockito.given(itemDetailRepository.saveAll(any(List.class))).willReturn(itemDetailEntities);
        BDDMockito.given(discountDetailRepository.saveAll(any(List.class))).willReturn(discountDetailEntities);
        Order returnedOrder = adapter.save(order);
        assertThat(returnedOrder).isNotNull();
        assertThat(returnedOrder.getOfferedDiscounts()).isNotNull();
        assertThat(returnedOrder.getOfferedDiscounts().getDetails()).isNotEmpty();
    }

    @Test
    void successfullySaveWithoutDiscounts() {
        Order order = defaultOrder().withOrderId(new Order.OrderId(1L)).build();
        List<ItemDetailEntity> itemDetailEntities = order.getOrderedItems().getDetails().stream()
                .map(ItemDetailDataMapper::mapToEntity).collect(Collectors.toList());
        List<DiscountDetailEntity> discountDetailEntities = order.getOfferedDiscounts().getDetails().stream()
                .map(DiscountDetailDataMapper::mapToEntity).collect(Collectors.toList());
        OrderEntity orderEntity = new OrderEntity(
                order.getId().getValue(),
                order.getClientId().getValue(),
                order.getTimestamp()
        );
        BDDMockito.given(orderRepository.save(any(OrderEntity.class))).willReturn(orderEntity);
        BDDMockito.given(itemDetailRepository.saveAll(any(List.class))).willReturn(itemDetailEntities);
        BDDMockito.given(discountDetailRepository.saveAll(any(List.class))).willReturn(new ArrayList<DiscountDetailEntity>());
        Order returnedOrder = adapter.save(order);
        assertThat(returnedOrder).isNotNull();
        assertThat(returnedOrder.getOfferedDiscounts()).isNotNull();
        assertThat(returnedOrder.getOfferedDiscounts().getDetails()).isEmpty();
    }
}