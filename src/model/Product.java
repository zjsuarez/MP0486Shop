package model;

import javax.persistence.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.PostLoad;
@Entity
@Table(name="inventory")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String name;
    
    @Column(name = "price")
    private double publicPrice;
    
    @Transient
    private double wholesalerPrice;
    
    
    private boolean available;
    private int stock;
    private static int totalProducts;
     
    public final static double EXPIRATION_RATE=0.60;
    
    public Product() {
	}
    
	public Product(int id, String name, double wholesalerPrice, boolean available, int stock) {
		super();
		this.id = id;
		this.name = name;
		this.wholesalerPrice = wholesalerPrice;
		this.publicPrice = wholesalerPrice * 2;
		this.available = available;
		this.stock = stock;
		totalProducts++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@PostLoad
    private void calculateWholesalerPrice() {
        this.wholesalerPrice = this.publicPrice / 2;
    }
	

	public double getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(double publicPrice) {
		this.publicPrice = publicPrice;
	}

	public double getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(double wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public static int getTotalProducts() {
		return totalProducts;
	}

	public static void setTotalProducts(int totalProducts) {
		Product.totalProducts = totalProducts;
	}
	
	public void expire() {
		this.publicPrice = (this.getPublicPrice() *EXPIRATION_RATE); ;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", publicPrice=" + publicPrice + ", wholesalerPrice=" + wholesalerPrice
				+ ", available=" + available + ", stock=" + stock + "]";
	}

	
	
	
	
	

    

    
}
