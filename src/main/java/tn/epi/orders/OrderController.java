package tn.epi.orders;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final Map<Long, Order> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @GetMapping
    public Collection<Order> list() {
        return store.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        return Optional.ofNullable(store.get(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) {
        long id = sequence.incrementAndGet();
        Order order = new Order(id, request.getProduct(), request.getQuantity());
        store.put(id, order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}