package dao;

import java.util.ArrayList;

import model.Employee;
import model.Product;

public interface Dao {
	
	default public void connect() {
	}

	default public void disconnect() {
	}
	
	public ArrayList<Product> getInventory();
	
	public boolean writeInventory(ArrayList<Product> productlist);

	default public Employee getEmployee(int employeeId, String password) {
		return null;
	}
}
