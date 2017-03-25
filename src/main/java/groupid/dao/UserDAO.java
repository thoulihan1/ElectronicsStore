package groupid.dao;

import groupid.PersistenceUtil;
import groupid.model.Manufacturer;
import groupid.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    public static User getUserByUsernameAndPassword(String username, String password){
        EntityManager em = emf.createEntityManager();
        User m = (User) em.createNamedQuery("User.getByUsernameAndPassword").setParameter("name", username).setParameter("password", password).getSingleResult();
        return m;
    }

    public static User getUserById(int id){
        EntityManager em = emf.createEntityManager();
        User m = (User) em.createNamedQuery("User.getById").setParameter("id", id).getSingleResult();
        return m;
    }
}
