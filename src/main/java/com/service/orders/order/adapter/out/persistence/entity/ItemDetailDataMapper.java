package com.service.orders.order.adapter.out.persistence.entity;

import com.service.orders.order.domain.DiscountDetail;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.ItemDetail;
import com.service.orders.order.domain.Order;

public class ItemDetailDataMapper {
    public static ItemDetailEntity mapToEntity(ItemDetail itemDetail){
        return new ItemDetailEntity(
                itemDetail.getId().getValue(),
                itemDetail.getOrderId().getValue(),
                itemDetail.getItemId().getValue(),
                itemDetail.getQuantity(),
                itemDetail.getCost()
        );
    }

    public static ItemDetailEntity mapToEntityNoId(ItemDetail itemDetail){
        return new ItemDetailEntity(
                null,
                itemDetail.getOrderId().getValue(),
                itemDetail.getItemId().getValue(),
                itemDetail.getQuantity(),
                itemDetail.getCost()
        );
    }

    public static ItemDetailEntity mapToEntityNoId(ItemDetail itemDetail, Long orderId){
        return new ItemDetailEntity(
                null,
                orderId,
                itemDetail.getItemId().getValue(),
                itemDetail.getQuantity(),
                itemDetail.getCost()
        );
    }

    public static ItemDetail mapToBusiness(ItemDetailEntity itemDetailEntity){
        return new ItemDetail(
                new ItemDetail.DetailId(itemDetailEntity.getId()),
                new Order.OrderId(itemDetailEntity.getOrderId()),
                new Item.ItemId(itemDetailEntity.getItemId()),
                itemDetailEntity.getQuantity(),
                itemDetailEntity.getCost()
        );
    }
}
