package model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "historical_inventory")
public class ProductHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "available")
    private boolean available;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "id_product")
    private int idProduct;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "stock")
    private int stock;


    public ProductHistory() {
    }


    public ProductHistory(Product product) {
        this.idProduct = product.getId();
        this.name = product.getName();
        this.price = product.getPublicPrice(); 
        this.stock = product.getStock();
        this.available = product.isAvailable();
        this.createdAt = LocalDateTime.now(); 
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public int getIdProduct() { return idProduct; }
    public void setIdProduct(int idProduct) { this.idProduct = idProduct; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}