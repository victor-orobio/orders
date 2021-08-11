package com.service.orders.order.adapter.out.persistence.entity;

import com.service.orders.order.domain.DiscountDetail;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.ItemDetail;
import com.service.orders.order.domain.Order;

public class DiscountDetailDataMapper {
    public static  DiscountDetailEntity mapToEntity(DiscountDetail discountDetail){
        return new DiscountDetailEntity(
                discountDetail.getId().getValue(),
                discountDetail.getOrderId().getValue(),
                discountDetail.getItemId().getValue(),
                discountDetail.getQuantity(),
                discountDetail.getCostDiscounted()
        );
    }

    public static  DiscountDetailEntity mapToEntityNoId(DiscountDetail discountDetail){
        return new DiscountDetailEntity(
                null,
                discountDetail.getOrderId().getValue(),
                discountDetail.getItemId().getValue(),
                discountDetail.getQuantity(),
                discountDetail.getCostDiscounted()
        );
    }

    public static DiscountDetail mapToBusiness(DiscountDetailEntity discountDetailEntity){
        return new DiscountDetail(
                new DiscountDetail.DetailId(discountDetailEntity.getId()),
                new Order.OrderId(discountDetailEntity.getOrderId()),
                new Item.ItemId(discountDetailEntity.getItemId()),
                discountDetailEntity.getQuantity(),
                discountDetailEntity.getCostDiscounted()
        );
    }
}
