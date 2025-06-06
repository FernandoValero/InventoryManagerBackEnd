package ar.com.manager.inventory.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="number")
    private String number;

    @Column(name="name")
    private String name;

    @Column(name="stock")
    private Integer stock;

    @Column(name="barCode")
    private String barCode;

    @Column(name="price")
    private Double price;

    @Column(name="description")
    private String description;

    @Column(name="category")
    private String category;

    @Column(name = "image")
    private String image;

    @Column(name = "deleted")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public Product() {
    }

    public Product(Integer id, String number, String name, Integer stock, String barCode, Double price, String description, String category, String image, boolean deleted, User user, Supplier supplier) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.stock = stock;
        this.barCode = barCode;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.deleted = deleted;
        this.user = user;
        this.supplier = supplier;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
