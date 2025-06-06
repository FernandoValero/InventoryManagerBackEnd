package ar.com.manager.inventory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Datos del proveedor para operaciones de creación, actualización y consulta")
public class SupplierDto {
    private static final long serialVersionUID = 1L;
    @Schema(
            description = "ID único del proveedor. Se genera automáticamente al crear un proveedor",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Schema(
            description = "Nombre del proveedor",
            example = "Juan",
            required = true,
            minLength = 2,
            maxLength = 50
    )
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstName;

    @Schema(
            description = "Apellido del proveedor",
            example = "Pérez",
            required = true,
            minLength = 2,
            maxLength = 50
    )
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;

    @Schema(
            description = "Número de teléfono del proveedor",
            example = "+51987654321",
            required = true,
            pattern = "^\\+?[0-9\\s]{9,15}$"
    )
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9\\s]{7,15}$", message = "El teléfono debe tener entre 7 y 15 dígitos")
    private String phoneNumber;

    @Schema(
            description = "Correo electrónico del proveedor",
            example = "juan.perez@example.com",
            required = true,
            pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"
    )
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    @Schema(
            description = "Nombre de la empresa del proveedor (opcional)",
            example = "TecnoSuministros S.A.",
            maxLength = 100
    )
    @Size(max = 40, message = "El nombre de la empresa no puede exceder los 40 caracteres")
    private String company;

    @Schema(
            description = "Lista de productos asociados al proveedor. Solo se muestra en consultas",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductDto> products;

    @Schema(
            description = "Indica si el proveedor ha sido eliminado lógicamente del sistema",
            example = "false",
            accessMode = Schema.AccessMode.READ_ONLY,
            defaultValue = "false"
    )
    private boolean deleted;

    public SupplierDto() {
    }

    public SupplierDto(Integer id, String firstName, String lastName, String phoneNumber, String email, String company, List<ProductDto> products) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.company = company;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public void addProduct(ProductDto product) {
        this.products.add(product);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
