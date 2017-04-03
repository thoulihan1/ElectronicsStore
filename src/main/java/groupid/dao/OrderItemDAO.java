package groupid.dao;

import groupid.PersistenceUtil;
import groupid.model.OrderItem;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Thomas on 3/13/17.
 */
public class OrderItemDAO {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("electronicsStore");

    public static void addOrderItem(OrderItem m){
        PersistenceUtil.persist(m);
    }

    public static void updateOrderItem(OrderItem OrderItem){
        PersistenceUtil.merge(OrderItem);
    }

    public static void removeOrderItem(OrderItem m){
        PersistenceUtil.remove(m);
    }

}
