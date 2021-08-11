package com.service.orders.order.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "discount_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_detail_id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "item_id")
    private Long itemId;

    private Integer quantity;

    @Column(name = "cost_discounted")
    private Integer costDiscounted;
}
