package tn.epi.orders;

/**
 * Une commande enregistree.
 */
public record Order(Long id, String product, int quantity) {
}
