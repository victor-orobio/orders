package com.service.orders.order.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Offer {

    @Getter
    private Item.ItemId id;

    @Getter
    @NonNull
    private Integer offeredQuantity;

    @Getter
    @NonNull
    private Integer baseQuantity;

    public Integer calculateDiscountQuantity(Integer orderedQuantity){
        int promotions = orderedQuantity / this.offeredQuantity;
        int givenUnitsPerPromotion = offeredQuantity - baseQuantity;
        return promotions * givenUnitsPerPromotion;
    }

}
