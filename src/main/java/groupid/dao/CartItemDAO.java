package groupid.dao;

import groupid.PersistenceUtil;
import groupid.model.Cart;
import groupid.model.Manufacturer;
import groupid.model.CartItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static groupid.dao.UserDAO.emf;

/**
 * Created by Thomas on 3/13/17.
 */
public class CartItemDAO {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("electronicsStore");

    public static void addCartItem(CartItem m){
        PersistenceUtil.persist(m);
    }

    public static void updateCartItem(CartItem CartItem){
        PersistenceUtil.merge(CartItem);
    }

    public static void removeCartItem(CartItem m){
        PersistenceUtil.remove(m);
    }

    public static CartItem getCartItemById(int id){
        EntityManager em = emf.createEntityManager();
        CartItem m = (CartItem) em.createNamedQuery("CartItem.getById").setParameter("id", id).getSingleResult();
        return m;
    }

    public static List<CartItem> getCartItemsByCart(Cart cart){
        EntityManager em = emf.createEntityManager();
        List<CartItem> m = (List<CartItem>) em.createNamedQuery("CartItem.getByCart").setParameter("cart", cart).getResultList();
        return m;
    }

}
