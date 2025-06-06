package ar.com.manager.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Datos del producto para operaciones de creación, actualización y consulta")
public class ProductDto {
    private static final long serialVersionUID = 1L;
    @Schema(
            description = "ID único del producto. Se genera automáticamente al crear un producto",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Schema(
            description = "Número/código de identificación del producto",
            example = "PROD-001",
            required = true,
            minLength = 3,
            maxLength = 20
    )
    @NotBlank(message = "El número de producto es obligatorio")
    @Size(min = 3, max = 20, message = "El número de producto debe tener entre 3 y 20 caracteres")
    private String number;

    @Schema(
            description = "Nombre del producto",
            example = "Laptop HP EliteBook",
            required = true,
            minLength = 3,
            maxLength = 100
    )
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre del producto debe tener entre 3 y 100 caracteres")
    private String name;

    @Schema(
            description = "Cantidad disponible en inventario",
            example = "50",
            minimum = "0",
            required = true
    )
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @Schema(
            description = "Código de barras del producto",
            example = "123456789012",
            required = true,
            minLength = 8,
            maxLength = 20
    )
    @NotBlank(message = "El código de barras del producto es obligatorio")
    @Size(min = 8, max = 20, message = "El código de barras debe tener entre 8 y 20 caracteres")
    private String barCode;

    @Schema(
            description = "Precio del producto",
            example = "1299.99",
            minimum = "0.01",
            required = true
    )
    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Double price;

    @Schema(
            description = "Descripción detallada del producto",
            example = "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
            maxLength = 500
    )
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String description;

    @Schema(
            description = "Categoría del producto",
            example = "Electrónicos",
            required = true,
            minLength = 3,
            maxLength = 50
    )
    @NotBlank(message = "La categoría es obligatoria")
    @Size(min = 3, max = 50, message = "La categoría debe tener entre 3 y 50 caracteres")
    private String category;

    @Schema(
            description = "URL de la imagen del producto",
            example = "https://example.com/images/product.jpg",
            maxLength = 255
    )
    @Size(max = 255, message = "La URL de la imagen no puede exceder los 255 caracteres")
    private String image;

    @Schema(
            description = "ID del usuario propietario/creador del producto. Obligatorio para creación",
            example = "1",
            required = true
    )
    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer userId;

    @Schema(
            description = "ID del proveedor del producto. Opcional",
            example = "1"
    )
    private Integer supplierId;

    @Schema(
            description = "Indica si el producto ha sido eliminado lógicamente del sistema",
            example = "false",
            accessMode = Schema.AccessMode.READ_ONLY,
            defaultValue = "false"
    )
    private boolean deleted;

    public ProductDto() {
    }

    public ProductDto(Integer id, String number, String name, int stock, String barCode, Double price, String description, String category, String image, Integer userId, Integer supplierId) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.stock = stock;
        this.barCode = barCode;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.userId = userId;
        this.supplierId = supplierId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
