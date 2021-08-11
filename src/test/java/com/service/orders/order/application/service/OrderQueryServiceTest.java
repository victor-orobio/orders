package com.service.orders.order.application.service;

import com.service.orders.order.adapter.out.persistence.OrderQueryAdapter;
import com.service.orders.order.domain.Order;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.service.orders.common.OrderTestData.defaultOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@DisplayName("Testing query order service")
@ExtendWith(MockitoExtension.class)
class OrderQueryServiceTest {

    @InjectMocks
    private OrderQueryService service;

    @Mock
    private OrderQueryAdapter queryAdapter;

    @BeforeEach
    public void setup() {
        this.service = new OrderQueryService(queryAdapter);
    }

    @Test
    void successFindById() {
        BDDMockito.given(queryAdapter.findById(any(Long.class))).willReturn(Optional.of(defaultOrder().build()));
        Order order = service.findById(1L);
        assertThat(order).isNotNull();
        verify(queryAdapter, atLeastOnce()).findById(any(Long.class));
    }

    @Test
    void exceptionFindById() {
        BDDMockito.given(queryAdapter.findById(any(Long.class))).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Element not found");
    }

    @Test
    void findAll() {
        List<Order> orders = Arrays.asList(defaultOrder().build());
        BDDMockito.given(queryAdapter.findAll()).willReturn(orders);
        List<Order> returnedOrders = service.findAll();
        assertThat(returnedOrders).isNotNull();
        verify(queryAdapter, atLeastOnce()).findAll();
    }
}