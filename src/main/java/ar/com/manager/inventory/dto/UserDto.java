package ar.com.manager.inventory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UserDto {
    private static final long serialVersionUID = 1L;
    @Schema(
            description = "ID único del usuario. Se genera automáticamente al crear un usuario",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Schema(
            description = "Nombre del usuario",
            example = "Carlos",
            required = true,
            minLength = 2,
            maxLength = 50
    )
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstName;

    @Schema(
            description = "Apellido del usuario",
            example = "Gómez",
            required = true,
            minLength = 2,
            maxLength = 50
    )
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;

    @Schema(
            description = "Nombre de usuario (debe ser único en el sistema)",
            example = "cgomez2023",
            required = true,
            minLength = 4,
            maxLength = 20
    )
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 20, message = "El nombre de usuario debe tener entre 4 y 20 caracteres")
    private String userName;

    @Schema(
            description = "Contraseña del usuario",
            example = "password123",
            required = true,
            minLength = 8,
            maxLength = 100
    )
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    private String password;

    @Schema(
            description = "Número de teléfono del usuario",
            example = "+51987654321",
            required = true,
            pattern = "^\\+?[0-9\\s]{9,15}$"
    )
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9\\s]{7,15}$", message = "El teléfono debe tener entre 7 y 15 dígitos")
    private String phoneNumber;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "c.gomez@example.com",
            required = true,
            pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"
    )
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    @Schema(
            description = "Tipo de usuario (ej. ADMIN, USER, MANAGER)",
            example = "USER",
            required = true,
            allowableValues = {"ADMIN", "USER", "MANAGER"}
    )
    @NotBlank(message = "El tipo de usuario es obligatorio")
    private String type;

    @Schema(
            description = "Indica si el usuario está habilitado en el sistema. Por defecto true",
            example = "true",
            accessMode = Schema.AccessMode.READ_ONLY,
            defaultValue = "true"
    )
    private boolean enabled;

    @Schema(
            description = "Lista de ventas asociadas al usuario. Solo se incluye en consultas",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SaleDto> sales;

    @Schema(
            description = "Indica si el usuario ha sido eliminado lógicamente del sistema",
            example = "false",
            accessMode = Schema.AccessMode.READ_ONLY,
            defaultValue = "false"
    )
    private boolean deleted;

    public UserDto() {
    }

    public UserDto(Integer id, boolean enabled, String type, String email, String phoneNumber, String userName, String password, String lastName, String firstName, List<SaleDto> sales) {
        this.id = id;
        this.enabled = enabled;
        this.type = type;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<SaleDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleDto> sales) {
        this.sales = sales;
    }

    public void addSale(SaleDto sale) {
        this.sales.add(sale);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
