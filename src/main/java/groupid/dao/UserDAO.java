package groupid.dao;

import groupid.PersistenceUtil;
import groupid.model.Admin;
import groupid.model.Customer;
import groupid.model.Manufacturer;
import groupid.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

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


    public static User getUserByEmailAndPassword(String email, String password){
        EntityManager em = emf.createEntityManager();
        User user;
        try{
             user = (Admin) em.createNamedQuery("Admin.getByEmailAndPassword").setParameter("email", email).setParameter("password", password).getSingleResult();
             user.setType("Admin");
             return user;
        } catch(NoResultException e){
            try{
                user = (Customer) em.createNamedQuery("Customer.getByEmailAndPassword").setParameter("email", email).setParameter("password", password).getSingleResult();
                user.setType("Customer");
                return user;
            } catch (NoResultException e1){
                return null;
            }
        }
    }
}
