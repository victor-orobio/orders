package com.service.orders.order.adapter.out.persistence;

import com.service.orders.order.adapter.out.persistence.entity.DiscountDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountDetailRepository extends JpaRepository<DiscountDetailEntity, Long> {
    List<DiscountDetailEntity> findByOrderId(Long id);
    List<DiscountDetailEntity> findAllByOrderIdIn(List<Long> orders);

}
