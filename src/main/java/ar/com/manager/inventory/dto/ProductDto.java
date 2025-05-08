package ar.com.manager.inventory.dto;

public class ProductDto {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String number;
    private String name;
    private int stock;
    private String barCode;
    private Double price;
    private String description;
    private String category;
    private String image;
    private Integer userId;
    private Integer supplierId;

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
}
