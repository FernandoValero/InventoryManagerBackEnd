package ar.com.manager.inventory.dto;

public class SaleDetailDto {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private int amount;
    private ProductDto product;
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
