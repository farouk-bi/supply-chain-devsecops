package tn.epi.orders;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * API REST de gestion de commandes.
 * Stockage en memoire : moins de dependances, donc moins de surface d'attaque et de CVE.
 */
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
        Order order = new Order(id, request.product(), request.quantity());
        store.put(id, order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
