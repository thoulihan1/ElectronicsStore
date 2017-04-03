package groupid.dao;

import groupid.PersistenceUtil;
import groupid.model.OrderHistory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 */
public class OrderHistoryDAO {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("electronicsStore");

    public static void addOrderHistory(OrderHistory o){
        PersistenceUtil.persist(o);
    }

    public static void updateOrderHistory(OrderHistory o){
        PersistenceUtil.merge(o);
    }

    public static void removeOrderHistory(OrderHistory o){
        PersistenceUtil.remove(o);
    }

    public static OrderHistory getOrderHistoryById(int id){
        EntityManager em = emf.createEntityManager();
        OrderHistory m = (OrderHistory) em.createNamedQuery("OrderHistory.getById").setParameter("id", id).getSingleResult();
        return m;
    }

    public static List<OrderHistory> getAllOrderHistories(){
        EntityManager em = emf.createEntityManager();
        List<OrderHistory> m = (List<OrderHistory>) em.createNamedQuery("OrderHistory.getAll").getResultList();
        return m;
    }
}
