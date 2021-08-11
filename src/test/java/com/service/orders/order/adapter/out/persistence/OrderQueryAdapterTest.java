package com.service.orders.order.adapter.out.persistence;

import com.service.orders.order.adapter.out.persistence.entity.*;
import com.service.orders.order.application.service.ReceiveOrderService;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Offer;
import com.service.orders.order.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Testing Order Query Adapter")
@ExtendWith(MockitoExtension.class)
class OrderQueryAdapterTest {

    @InjectMocks
    private OrderQueryAdapter adapter;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemDetailRepository itemDetailRepository;

    @Mock
    private DiscountDetailRepository discountDetailRepository;

    @BeforeEach
    public void setup() {
        this.adapter = new OrderQueryAdapter(itemRepository, offerRepository, orderRepository, itemDetailRepository, discountDetailRepository);
    }

    @Test
    void findAllItems() {
        List<ItemEntity> entityList = Arrays.asList(
                new ItemEntity(10L, "Apple", 60),
                new ItemEntity(11L, "Orange", 25)
        );
        BDDMockito.given(itemRepository.findAll()).willReturn(entityList);
        Map<Long, Item> items = adapter.findAllItems();
        assertThat(items).isNotNull();
        assertThat(items.size()).isEqualTo(entityList.size());
        assertThat(items.containsKey(11L)).isTrue();
    }

    @Test
    void findAllOffers() {
        List<OfferEntity> entityList = Arrays.asList(
                new OfferEntity(1L,10L,2,1),
                new OfferEntity(2L,11L,3,2)
        );
        BDDMockito.given(offerRepository.findAll()).willReturn(entityList);
        Map<Long, Offer> offers = adapter.findAllOffers();
        assertThat(offers).isNotNull();
        assertThat(offers.size()).isEqualTo(entityList.size());
        assertThat(offers.containsKey(11L)).isTrue();
    }

    @Test
    void findById() {
        Optional<OrderEntity> orderEntity = Optional.of(new OrderEntity(1L,2L, LocalDateTime.now()));
        List<ItemDetailEntity> itemDetailEntities = Arrays.asList(new ItemDetailEntity(3L, 4L, 5L, 6, 7));
        List<DiscountDetailEntity> discountDetailEntities = Arrays.asList(new DiscountDetailEntity(8L, 9L, 10L, 11, 12));
        BDDMockito.given(orderRepository.findById(any(Long.class))).willReturn(orderEntity);
        BDDMockito.given(itemDetailRepository.findByOrderId(any(Long.class))).willReturn(itemDetailEntities);
        BDDMockito.given(discountDetailRepository.findByOrderId(any(Long.class))).willReturn(discountDetailEntities);
        Optional<Order> order = adapter.findById(1L);
        assertThat(order).isNotNull();
        assertThat(order.isPresent()).isTrue();
    }

    @Test
    void findAll() {
        List<OrderEntity> orderEntities = Arrays.asList(new OrderEntity(1L,2L, LocalDateTime.now()));
        List<ItemDetailEntity> itemDetailEntities = Arrays.asList(new ItemDetailEntity(3L, 4L, 5L, 6, 7));
        List<DiscountDetailEntity> discountDetailEntities = Arrays.asList(new DiscountDetailEntity(8L, 9L, 10L, 11, 12));
        BDDMockito.given(orderRepository.findAll()).willReturn(orderEntities);
        BDDMockito.given(itemDetailRepository.findAllByOrderIdIn(any(List.class))).willReturn(itemDetailEntities);
        BDDMockito.given(discountDetailRepository.findAllByOrderIdIn(any(List.class))).willReturn(discountDetailEntities);
        List<Order> orders = adapter.findAll();
        assertThat(orders).isNotNull();
        assertThat(orders).isNotEmpty();
    }
}