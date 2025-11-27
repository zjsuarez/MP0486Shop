package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {
	Connection connection;

	@Override
	public void connect() {
		// Define connection parameters
		String url = "jdbc:mysql://localhost:3306/shop";
		String user = "root";
		String pass = "";
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		Employee employee = null;
		String query = "select * from employee where employeeId= ? and password = ? ";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
    		ps.setInt(1,employeeId);
    	  	ps.setString(2,password);
    	  	//System.out.println(ps.toString());
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));
            	}
            }
        } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
    	return employee;
	}
	
	public ArrayList<Product> getInventory(){
		String query = "select * from inventory";
		ArrayList<Product> products = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement(); // Creamos un objeto Statement
		    ResultSet rs = stmt.executeQuery(query);
		    
		    while(rs.next()) {
		    	boolean availablefield = rs.getInt("available") == 1;

		    	Product product = new Product(rs.getInt("id"),rs.getString("name"),new Amount(rs.getDouble("wholesalerPrice")),availablefield,rs.getInt("stock"));
		    	products.add(product);
		    }
		    
		} catch(Exception e) {
			
		}
		return products;
	}
	
	public boolean writeInventory(ArrayList<Product> productlist) {
		String query = "insert into historical_inventory(id_product,name,wholesalerPrice,available,stock) values(?,?,?,?,?)";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
			int rows = 0;
			
			
			
			for(Product product: productlist) {
				int availablefield = product.isAvailable() ? 1 : 0;
				ps.setInt(1,product.getId());
	    		ps.setString(2,product.getName());
	    	  	ps.setDouble(3,product.getWholesalerPrice().getValue());
	    	  	ps.setInt(4,availablefield);
	    	  	ps.setInt(5, product.getStock());
	    	  	if(ps.executeUpdate()>0) {
	    	  		rows++;
	    	  	}
			}
			return rows>0;
		
        } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
			return false;
		}
	}


	public boolean addProduct(Product product) {
		String query = "insert into inventory(name,wholesalerPrice,available,stock) values(?,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
			int availablefield = product.isAvailable() ? 1 : 0;
			
    		ps.setString(1,product.getName());
    	  	ps.setDouble(2,product.getWholesalerPrice().getValue());
    	  	ps.setInt(3,availablefield);
    	  	ps.setInt(4, product.getStock());
    	  	int rows = ps.executeUpdate();              

            return rows > 0;
            
        } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
			return false;
		}
	}


	public boolean updateProduct(Product product) {
		String query = "UPDATE inventory "
	             + "SET name = ?, "
	             + "wholesalerPrice = ?, "
	             + "available = ?, "
	             + "stock = ? "
	             + "WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
    		ps.setString(1,product.getName());
    		ps.setDouble(2,product.getWholesalerPrice().getValue());
    		int availablefield = product.isAvailable() ? 1 : 0;
    		ps.setInt(3,availablefield);
    		ps.setInt(4,product.getStock());
    		ps.setInt(5, product.getId());
    		int rows = ps.executeUpdate();              

            return rows > 0;
        } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean deleteProduct(int id) {
		String query = "delete from inventory where id = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
    		ps.setInt(1,id);
    		int rows = ps.executeUpdate();              

            return rows > 0;
        } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
			return false;
		}
	}
	

	
	public Integer getProductIdByName(String name) {
	    String query = "SELECT id FROM inventory WHERE name = ?";
	    
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, name);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("id");
	            } else {
	                return null; // No encontrado
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
