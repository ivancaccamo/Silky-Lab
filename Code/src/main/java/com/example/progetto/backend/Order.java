package com.example.ecommerce.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import java.util.List;
 @Entity
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User user;
    private LocalDateTime date;
    private String status;  // "IN LAVORAZIONE", "SPEDITO", "CONSEGNATO", ...
    private List<CartItem> items;

    public double getTotalPrice() {
        if (items == null) return 0;
        return items.stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();
    }

    // ... get/set
}
