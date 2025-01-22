package com.mindhub.orderMicroservice.repositories;

import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(Status status);
    List<OrderEntity> findByUserId(Long userId);
}
