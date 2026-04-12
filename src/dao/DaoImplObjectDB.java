package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.Employee;
import model.Product;

public class DaoImplObjectDB implements Dao {

    private static final String DB_PATH = "objects" + File.separator + "users.odb";
    private EntityManagerFactory emf;

    @Override
    public void connect() {
        try {
            File objectsDir = new File(System.getProperty("user.dir") + File.separator + "objects");
            if (!objectsDir.exists()) {
                objectsDir.mkdirs();
            }

            if (emf == null || !emf.isOpen()) {
                emf = javax.persistence.Persistence.createEntityManagerFactory("objectdb:" + DB_PATH);
            }

            ensureSeedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private void ensureSeedUser() {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(e) FROM Employee e", Long.class);
            Long count = countQuery.getSingleResult();

            if (count == null || count == 0L) {
                Employee seed = new Employee();
                seed.setEmployeeId(123);
                seed.setPassword("test");
                em.persist(seed);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Employee> query = em.createQuery(
                    "SELECT e FROM Employee e WHERE e.employeeId = :employeeId AND e.password = :password",
                    Employee.class);
            query.setParameter("employeeId", employeeId);
            query.setParameter("password", password);

            List<Employee> result = query.getResultList();
            if (result.isEmpty()) {
                return null;
            }

            Employee source = result.get(0);
            Employee auth = new Employee();
            auth.setEmployeeId(source.getEmployeeId());
            auth.setPassword(source.getPassword());
            return auth;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public ArrayList<Product> getInventory() {
        return new ArrayList<>();
    }

    @Override
    public boolean writeInventory(ArrayList<Product> productlist) {
        return false;
    }

    @Override
    public void addProduct(Product product) {
        // Out of scope for ObjectDB login DAO
    }

    @Override
    public void updateProduct(Product product) {
        // Out of scope for ObjectDB login DAO
    }

    @Override
    public void deleteProduct(int id) {
        // Out of scope for ObjectDB login DAO
    }
}
