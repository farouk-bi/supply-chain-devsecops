package tn.epi.orders;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrderRequest {

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^[\\p{L}0-9 .-]+$")
    private String product;

    @Min(1)
    @Max(100)
    private int quantity;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}