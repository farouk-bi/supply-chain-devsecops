package tn.epi.orders;

/**
 * Une commande enregistree (immuable : aucun setter).
 */
public class Order {

    private final Long id;
    private final String product;
    private final int quantity;

    public Order(Long id, String product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}