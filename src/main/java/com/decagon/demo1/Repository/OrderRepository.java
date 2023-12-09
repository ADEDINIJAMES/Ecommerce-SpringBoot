package com.decagon.demo1.Repository;

import com.decagon.demo1.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Long> {

}
