package com.service.orders.common;

import com.service.orders.order.domain.Detail;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Order;
import com.service.orders.order.domain.OrderDetail;


import java.time.LocalDateTime;


public class OrderTestData {
    public static DetailBuilder defaultDetail(){
        return new DetailBuilder()
                .withDetailId(new Detail.DetailId(41L))
                .withOrderId(new Order.OrderId(42L))
                .withItemId(new Item.ItemId(43L))
                .withCost(1)
                .withQuantity(1);
    }

    public static class DetailBuilder{
        private Detail.DetailId id;
        private Order.OrderId orderId;
        private Item.ItemId itemId;
        private Integer quantity;
        private Integer cost;

        public DetailBuilder withDetailId(Detail.DetailId id){
            this.id = id;
            return this;
        }

        public DetailBuilder withOrderId(Order.OrderId orderId){
            this.orderId = orderId;
            return this;
        }

        public DetailBuilder withItemId(Item.ItemId itemId){
            this.itemId = itemId;
            return this;
        }

        public DetailBuilder withQuantity(Integer quantity){
            this.quantity = quantity;
            return this;
        }

        public DetailBuilder withCost(Integer cost){
            this.cost = cost;
            return this;
        }

        public Detail build(){
            return new Detail(
                    this.id,
                    this.orderId,
                    this.itemId,
                    this.quantity,
                    this.cost
            );
        }

    }
    public static OrderBuilder defaultOrder(){
        return new OrderBuilder()
                .withOrderId(new Order.OrderId(51L))
                .withClientId(new Order.ClientId(45L))
                .withTimestamp(LocalDateTime.now())
                .withOrderDetail(new OrderDetail(
                        defaultDetail()
                                .withDetailId(new Detail.DetailId(61L))
                                .withItemId(new Item.ItemId(81L)).build(),
                        defaultDetail()
                                .withDetailId(new Detail.DetailId(71L))
                                .withItemId(new Item.ItemId(91L)).build()));
    }

    public static class OrderBuilder{
        private Order.OrderId id;
        private OrderDetail orderDetail;
        private Order.ClientId clientId;
        private LocalDateTime timestamp;


        public OrderBuilder withOrderId(Order.OrderId orderId){
            this.id = orderId;
            return this;
        }

        public OrderBuilder withClientId(Order.ClientId clientId){
            this.clientId = clientId;
            return this;
        }

        public OrderBuilder withTimestamp(LocalDateTime timestamp){
            this.timestamp = timestamp;
            return this;
        }

        public OrderBuilder withOrderDetail(OrderDetail orderDetail){
            this.orderDetail = orderDetail;
            return this;
        }

        public Order build(){
            return new Order(
                    this.id,
                    this.clientId,
                    this.timestamp,
                    this.orderDetail
            );
        }

    }
}
