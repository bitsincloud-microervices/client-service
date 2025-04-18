package br.com.bitsincloud.crdereservice.controller;

import br.com.bitsincloud.crdereservice.entity.Order;
import br.com.bitsincloud.crdereservice.repository.OrderRepository;
import br.com.bitsincloud.crdereservice.service.OrderEventPublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository repository;
    private final OrderEventPublisher publisher;

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody Order order) {
        Order orderSaved = repository.save(order);
        publisher.publish(orderSaved);
        return ResponseEntity.ok(orderSaved);
    }

    @GetMapping
    public List<Order> list() {
        return repository.findAll();
    }
}
