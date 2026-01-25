package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Employee;
import model.Product;
import model.ProductHistory;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class DaoImplHibernate implements Dao {

	
	SessionFactory sessionFactory;
	
	public DaoImplHibernate() {
        try {
            Configuration config = new Configuration().configure();
            sessionFactory = config.buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error creating SessionFactory");
            e.printStackTrace();
        }
    }
	
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Product> getInventory() {
		connect(); 
        
        ArrayList<Product> res = new ArrayList<>();
        
		try (Session session = sessionFactory.openSession()) {
		            
            session.beginTransaction();


            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> cr = cb.createQuery(Product.class);
            Root<Product> root = cr.from(Product.class);
            cr.select(root);


            List<Product> resultados = session.createQuery(cr).getResultList();
            res.addAll(resultados);

            session.getTransaction().commit();
		
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
        return res;
		
	}

	@Override
	public boolean writeInventory(ArrayList<Product> productlist) {

	    try (Session session = sessionFactory.openSession()) {
	        session.beginTransaction();
	        for (Product product : productlist) {
	            ProductHistory history = new ProductHistory(product);
	            session.save(history);
	        }
	        session.getTransaction().commit();
	        return true;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProduct(Product product) {
		try (Session session = sessionFactory.openSession()) {
	        session.beginTransaction();
	        

	        session.save(product);
	        
	        session.getTransaction().commit();
	    }

	}

	@Override
	public void updateProduct(Product product) {
		try (Session session = sessionFactory.openSession()) {
	        session.beginTransaction();
	        

	        session.update(product);
	        
	        session.getTransaction().commit();
	    }
	}

	@Override
	public void deleteProduct(int id) {
		try (Session session = sessionFactory.openSession()) {
	        session.beginTransaction();
	        

	        Product product = session.get(Product.class, id);
	        

	        if (product != null) {
	            session.delete(product);
	        }
	        
	        session.getTransaction().commit();
	    }
	}

}
