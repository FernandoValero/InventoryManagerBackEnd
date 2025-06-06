package ar.com.manager.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Datos de la venta para operaciones de creación, actualización y consulta")
public class SaleDto {
    private static final long serialVersionUID = 1L;
    @Schema(
            description = "ID único de la venta. Se genera automáticamente al crear una venta",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Schema(
            description = "Fecha y hora de la venta. Se establece automáticamente al crear la venta",
            example = "2023-05-15T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String saleDate;

    @Schema(
            description = "Precio total de la venta. Se calcula automáticamente a partir de los detalles",
            example = "150.75",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private double totalPrice;

    @Schema(
            description = "ID del usuario que realiza la venta. Obligatorio",
            example = "1",
            required = true
    )
    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer userId;

    @Schema(
            description = "ID del cliente asociado a la venta. Opcional",
            example = "1"
    )
    private Integer clientId;

    @Schema(
            description = "Lista de detalles de la venta (productos vendidos). Obligatorio",
            required = true,
            example = "[{\"amount\": 2, \"product\": {\"id\": 1}}]"
    )
    @Size(min = 1, message = "La venta debe contener al menos un detalle")
    @NotEmpty(message = "La venta debe contener al menos un producto")
    private List<SaleDetailDto> saleDetail;

    @Schema(
            description = "Indica si la venta ha sido eliminada lógicamente del sistema",
            example = "false",
            accessMode = Schema.AccessMode.READ_ONLY,
            defaultValue = "false"
    )
    private boolean deleted;

    public SaleDto() {
    }

    public SaleDto(Integer id, String saleDate, double totalPrice, Integer userId, Integer clientId, List<SaleDetailDto> saleDetail) {
        this.id = id;
        this.saleDate = saleDate;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.clientId = clientId;
        this.saleDetail = saleDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public List<SaleDetailDto> getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(List<SaleDetailDto> saleDetail) {
        this.saleDetail = saleDetail;
    }

    public void addSaleDetail(SaleDetailDto saleDetail) {
        this.saleDetail.add(saleDetail);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
