package ar.com.manager.inventory.dto;

import ar.com.manager.inventory.entity.Sale;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
@Schema(description = "Datos del cliente para operaciones de creación, actualización y consulta")

public class ClientDto {

    private static final long serialVersionUID = 1L;
    @Schema(
            description = "ID único del cliente. Se genera automáticamente al crear un cliente",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Schema(
            description = "Nombre del cliente",
            example = "Erick",
            required = true,
            minLength = 2,
            maxLength = 50
    )
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstName;

    @Schema(
            description = "Apellido del cliente",
            example = "Lopez",
            required = true,
            minLength = 2,
            maxLength = 50
    )
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;

    @Schema(
            description = "Documento Nacional de Identidad del cliente. Debe ser único en el sistema",
            example = "12345678",
            required = true,
            pattern = "^[0-9]{8,12}$"
    )
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8,12}$", message = "El DNI debe contener entre 8 y 12 dígitos")
    private String dni;

    @Schema(
            description = "Lista de ventas asociadas al cliente. Se incluye solo en consultas, no en creación o actualización",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SaleDto> sales;

    @Schema(
            description = "Indica si el cliente ha sido eliminado lógicamente del sistema",
            example = "false",
            accessMode = Schema.AccessMode.READ_ONLY,
            defaultValue = "false"
    )
    private boolean deleted;

    public ClientDto() {
    }

    public ClientDto(Integer id, String firstName, String lastName, String dni, List<SaleDto> sales) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.sales = sales;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public List<SaleDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleDto> sales) {
        this.sales = sales;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void addSale(SaleDto sale) {
        sales.add(sale);
    }
}
