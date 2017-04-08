package groupid.dao;

import groupid.PersistenceUtil;
import groupid.model.Admin;
import groupid.model.Customer;
import groupid.model.Manufacturer;
import groupid.model.User;
import groupid.observer.Observer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 */
public class UserDAO {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("electronicsStore");

    public static void addUser(User m){
        PersistenceUtil.persist(m);
    }

    public static void updateUser(User User){
        PersistenceUtil.merge(User);
    }

    public static void removeUser(User m){
        PersistenceUtil.remove(m);
    }

    public static Customer getCustomerById(int id){
        EntityManager em = emf.createEntityManager();
        Customer user = (Customer) em.createNamedQuery("Customer.getById").setParameter("id", id).getSingleResult();
        return user;
    }

    public static User getAdminByEmailAndPassword(String email, String password){
        EntityManager em = emf.createEntityManager();
        User user = (Admin) em.createNamedQuery("Admin.getByEmailAndPassword").setParameter("email", email).setParameter("password", password).getSingleResult();
        user.setType("Admin");
        return user;
    }

    public static User getCustomerByEmailAndPassword(String email, String password){
        EntityManager em = emf.createEntityManager();
        User user = (Customer) em.createNamedQuery("Customer.getByEmailAndPassword").setParameter("email", email).setParameter("password", password).getSingleResult();
        user.setType("Customer");
        return user;
    }

    public static List<Observer> getCustomerWhoAreSubscribed(){
        EntityManager em = emf.createEntityManager();
        List<Observer> users = (List<Observer>) em.createNamedQuery("Customer.getBySubscribed").setParameter("isSubscribed", true).getResultList();
        return users;
    }

    public static List<Customer> getAllCustomers(){
        EntityManager em = emf.createEntityManager();
        List<Customer> users = (List<Customer>) em.createNamedQuery("Customer.getAll").getResultList();
        return users;
    }
}
