package com.service.orders.order.adapter.out.persistence;

import com.service.orders.order.adapter.out.persistence.entity.ItemEntity;
import com.service.orders.order.adapter.out.persistence.entity.OfferEntity;
import com.service.orders.order.application.service.ReceiveOrderService;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Offer;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing receive order use case")
@ExtendWith(MockitoExtension.class)
class OrderQueryAdapterTest {

    @InjectMocks
    private OrderQueryAdapter adapter;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OfferRepository offerRepository;

    @BeforeEach
    public void setup() {
        this.adapter = new OrderQueryAdapter(itemRepository,offerRepository);
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
}