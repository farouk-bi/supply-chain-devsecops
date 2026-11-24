package tn.epi.orders;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Donnees recues du client. La validation est la premiere defense contre les injections :
 * seuls les lettres, chiffres, espaces, points et tirets sont acceptes dans le nom du produit.
 */
public record OrderRequest(
        @NotBlank @Size(max = 100) @Pattern(regexp = "^[\\p{L}0-9 .-]+$") String product,
        @Min(1) @Max(100) int quantity) {
}
