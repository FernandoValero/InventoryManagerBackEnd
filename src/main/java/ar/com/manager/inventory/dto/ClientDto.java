package ar.com.manager.inventory.dto;

import ar.com.manager.inventory.entity.Sale;

import java.util.List;

public class ClientDto {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String firstName;
    private String lastName;
    private String dni;
    private List<SaleDto> sales;
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
