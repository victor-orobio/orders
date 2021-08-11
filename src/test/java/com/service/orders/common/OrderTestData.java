package com.service.orders.common;

import com.service.orders.order.domain.*;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class OrderTestData {
    public static DetailBuilder defaultDetail(){
        return new DetailBuilder()
                .withDetailId(new ItemDetail.DetailId(41L))
                .withOrderId(new Order.OrderId(42L))
                .withItemId(new Item.ItemId(43L))
                .withCost(1)
                .withQuantity(10);
    }

    public static class DetailBuilder{
        private ItemDetail.DetailId id;
        private Order.OrderId orderId;
        private Item.ItemId itemId;
        private Integer quantity;
        private Integer cost;

        public DetailBuilder withDetailId(ItemDetail.DetailId id){
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

        public ItemDetail build(){
            return new ItemDetail(
                    this.id,
                    this.orderId,
                    this.itemId,
                    this.quantity,
                    this.cost
            );
        }

    }

    public static DiscountBuilder defaultDiscount(){
        return new DiscountBuilder()
                .withDetailId(new DiscountDetail.DetailId(41L))
                .withOrderId(new Order.OrderId(42L))
                .withItemId(new Item.ItemId(43L))
                .withCostDiscounted(1)
                .withQuantity(1);
    }

    public static class DiscountBuilder{
        private DiscountDetail.DetailId id;
        private Order.OrderId orderId;
        private Item.ItemId itemId;
        private Integer quantity;
        private Integer costDiscounted;

        public DiscountBuilder withDetailId(DiscountDetail.DetailId id){
            this.id = id;
            return this;
        }

        public DiscountBuilder withOrderId(Order.OrderId orderId){
            this.orderId = orderId;
            return this;
        }

        public DiscountBuilder withItemId(Item.ItemId itemId){
            this.itemId = itemId;
            return this;
        }

        public DiscountBuilder withQuantity(Integer quantity){
            this.quantity = quantity;
            return this;
        }

        public DiscountBuilder withCostDiscounted(Integer costDiscounted){
            this.costDiscounted = costDiscounted;
            return this;
        }

        public DiscountDetail build(){
            return new DiscountDetail(
                    this.id,
                    this.orderId,
                    this.itemId,
                    this.quantity,
                    this.costDiscounted
            );
        }

    }

    public static OrderBuilder defaultOrder(){
        return new OrderBuilder()
                .withOrderId(new Order.OrderId(51L))
                .withClientId(new Order.ClientId(90L))
                .withTimestamp(LocalDateTime.now())
                .withOrderDetail(new OrderedItems(
                        defaultDetail()
                                .withDetailId(new ItemDetail.DetailId(61L))
                                .withItemId(new Item.ItemId(10L)).withCost(60).build(),
                        defaultDetail()
                                .withDetailId(new ItemDetail.DetailId(71L))
                                .withItemId(new Item.ItemId(11L)).withCost(25).build()))
                .withOfferedDiscounts(new OfferedDiscounts(
                        defaultDiscount().withItemId(new Item.ItemId(10L))
                                .withQuantity(5).withCostDiscounted(60).build(),
                        defaultDiscount().withItemId(new Item.ItemId(11L))
                                .withQuantity(3).withCostDiscounted(25).build()));
    }

    public static class OrderBuilder{
        private Order.OrderId id;
        private OrderedItems orderedItems;
        private  OfferedDiscounts offeredDiscounts;
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

        public OrderBuilder withOrderDetail(OrderedItems orderedItems){
            this.orderedItems = orderedItems;
            return this;
        }

        public OrderBuilder withOfferedDiscounts(OfferedDiscounts offeredDiscounts){
            this.offeredDiscounts = offeredDiscounts;
            return this;
        }

        public Order build(){
            return new Order(
                    this.id,
                    this.clientId,
                    this.timestamp,
                    this.orderedItems,
                    this.offeredDiscounts
            );
        }

    }


    public static Map<Long, Item> listItems() {
        Map<Long, Item> items = new HashMap<>();
        items.put(10L, new Item(new Item.ItemId(10L), "Apple", 60));
        items.put(11L, new Item(new Item.ItemId(11L), "Orange", 25));
        return items;
    }

    public static Map<Long, Offer> listOffers() {
        Map<Long, Offer> offers = new HashMap<>();
        offers.put(10L, new Offer(new Item.ItemId(10L), 2, 1));
        offers.put(11L, new Offer(new Item.ItemId(11L), 3, 2));
        return offers;
    }
}
