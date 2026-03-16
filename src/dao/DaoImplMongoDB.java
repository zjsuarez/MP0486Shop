package dao;

import java.util.ArrayList;


import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao{

	MongoCollection<Document> collection;
	MongoCollection<Document> users;
	MongoDatabase mongoDatabase;
	ObjectId id;
	
	
	public DaoImplMongoDB() {
		connect();	
	}
	
	
	@Override
	public void connect() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		mongoDatabase = mongoClient.getDatabase("Shop");
		collection = mongoDatabase.getCollection("Shop");
		users = mongoDatabase.getCollection("users");
	}
	
	private int getNextProductId() {
		// Get the maximum ID from all products
		int maxId = 0;
		for (Document document : collection.find()) {
			Integer id = document.getInteger("id");
			if (id != null && id > maxId) {
				maxId = id;
			}
		}
		return maxId + 1;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> productList = new ArrayList<>();
		
		for (Document document : collection.find()) {
			Product product = new Product();
			product.setId(document.getInteger("id"));
			product.setName(document.getString("name"));
			product.setPublicPrice(document.getDouble("price"));
			product.setAvailable(document.getBoolean("available"));
			product.setStock(document.getInteger("stock"));
			
			productList.add(product);
		}
		
		return productList;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> productlist) {
		try {
			// TO TEST ERROR
			// throw new Exception("error");
			
			
			MongoCollection<Document> historicalCollection = mongoDatabase.getCollection("historical_inventory");
		
			java.time.ZonedDateTime now = java.time.ZonedDateTime.now(java.time.ZoneId.of("UTC"));
			java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
			String createdAt = now.format(formatter);

			for (Product product : productlist) {
				Document wholesalerPriceDoc = new Document();
				wholesalerPriceDoc.append("value", product.getWholesalerPrice());
				wholesalerPriceDoc.append("currency", "€");
				
				Document document = new Document();
				document.append("id", product.getId());
				document.append("name", product.getName());
				document.append("wholesalerprice", wholesalerPriceDoc);
				document.append("available", product.isAvailable());
				document.append("stock", product.getStock());
				document.append("created_at", createdAt);
				
				historicalCollection.insertOne(document);
			}
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		Employee employee = null;
		Document document = users.find(eq("employeeid", employeeId)).first();
		if (document != null) {
			String storedPassword = document.getString("password");
			if (storedPassword.equals(password)) {
				employee = new Employee();
				employee.setEmployeeId(employeeId);
				employee.setName(document.getString("name"));
				
			}
		}
		return employee;
	}

	@Override
	public void addProduct(Product product) {
		try {
			// Generate auto-incrementing ID
			int nextId = getNextProductId();
			product.setId(nextId);
			
			Document document = new Document();
			document.append("id", nextId);
			document.append("name", product.getName());
			document.append("price", product.getPublicPrice());
			document.append("available", product.isAvailable());
			document.append("stock", product.getStock());
			
			collection.insertOne(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateProduct(Product product) {
		try {
			Document newValues = new Document();
			newValues.append("name", product.getName());
			newValues.append("price", product.getPublicPrice());
			newValues.append("available", product.isAvailable());
			newValues.append("stock", product.getStock());
			
			Document updateDoc = new Document("$set", newValues);
			UpdateResult result = collection.updateOne(eq("id", product.getId()), updateDoc);
			
			if (result.getModifiedCount() == 0) {
				System.out.println("Product with id " + product.getId() + " not found or not updated");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(int id) {
		try {
			DeleteResult result = collection.deleteOne(eq("id", id));
			
			if (result.getDeletedCount() == 0) {
				System.out.println("Product with id " + id + " not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
