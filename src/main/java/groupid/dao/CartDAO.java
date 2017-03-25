package groupid.dao;

import groupid.PersistenceUtil;
import groupid.model.Manufacturer;
import groupid.model.Cart;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Thomas on 3/13/17.
 */
public class CartDAO {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("electronicsStore");

    public static void addCart(Cart m){
        PersistenceUtil.persist(m);
    }

    public static void updateCart(Cart Cart){
        PersistenceUtil.merge(Cart);
    }

    public static void removeCart(Cart m){
        PersistenceUtil.remove(m);
    }

    public static Cart getCartById(String id){
        EntityManager em = emf.createEntityManager();
        Cart m = (Cart) em.createNamedQuery("Cart.getById").setParameter("id", id).getSingleResult();
        return m;
    }
}
