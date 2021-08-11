package com.service.orders.order.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "offer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long id;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "offered_quantity")
    private Integer offeredQuantity;

    @Column(name = "base_quantity")
    private Integer baseQuantity;
}
