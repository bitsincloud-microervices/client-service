package br.com.bitsincloud.crdereservice.repository;

import br.com.bitsincloud.crdereservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
