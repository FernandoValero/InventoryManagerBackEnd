package ar.com.manager.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Detalle de los productos vendidos en una venta")
public class SaleDetailDto {
    private static final long serialVersionUID = 1L;
    @Schema(
            description = "ID único del detalle de venta. Se genera automáticamente",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Schema(
            description = "Cantidad del producto vendido",
            example = "2",
            required = true,
            minimum = "1"
    )
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int amount;

    @Schema(
            description = "Producto vendido. Solo se necesita proporcionar el ID del producto",
            required = true,
            example = "{\"id\": 1}"
    )
    @NotNull(message = "El producto es obligatorio")
    private ProductDto product;

    @Schema(
            description = "Indica si el detalle ha sido eliminado lógicamente",
            example = "false",
            accessMode = Schema.AccessMode.READ_ONLY,
            defaultValue = "false"
    )
    private boolean deleted;

    public SaleDetailDto() {
    }

    public SaleDetailDto(Integer id, int amount, ProductDto product) {
        this.id = id;
        this.amount = amount;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
