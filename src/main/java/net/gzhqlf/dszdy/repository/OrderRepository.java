package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.OrdersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Integer> {

    OrdersEntity findByOrderId(String orderId);

    Page<OrdersEntity> findByOrderId(String orderId, Pageable pageable);

    Page<OrdersEntity> findByOrderUserId(Integer orderUserId, Pageable pageable);

    Page<OrdersEntity> findByOrderUserIdIn(List<Integer> id, Pageable pageable);

    @Transactional
    @Query(value = "select count(*) from orders", nativeQuery = true)
    int getCounts();
}
