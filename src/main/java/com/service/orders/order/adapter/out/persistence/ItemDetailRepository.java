package com.service.orders.order.adapter.out.persistence;

import com.service.orders.order.adapter.out.persistence.entity.ItemDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDetailRepository extends JpaRepository<ItemDetailEntity, Long> {
    List<ItemDetailEntity> findByOrderId(Long id);
    List<ItemDetailEntity> findAllByOrderIdIn(List<Long> orders);
}
