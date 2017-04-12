package groupid.dao;

import groupid.PersistenceUtil;
import groupid.model.Manufacturer;
import groupid.model.Cart;
import groupid.model.Review;
import groupid.model.StockItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Thomas on 3/13/17.
 */
public class ReviewDAO {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("electronicsStore");

    public static void addReview(Review m){
        PersistenceUtil.persist(m);
    }

    public static void updateReview(Review Cart){
        PersistenceUtil.merge(Cart);
    }

    public static void removeReview(Review m){
        PersistenceUtil.remove(m);
    }

    public static Review getReviewById(String id){
        EntityManager em = emf.createEntityManager();
        Review m = (Review) em.createNamedQuery("Review.getById").setParameter("id", id).getSingleResult();
        return m;
    }

    public static List<Review> getReviewsbyStockItem(StockItem stockItem){
        EntityManager em = emf.createEntityManager();
        List<Review> m = (List<Review>) em.createNamedQuery("Review.getByStockItem").setParameter("stockItem", stockItem).getResultList();
        return m;
    }
}
