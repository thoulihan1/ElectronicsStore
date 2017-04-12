package groupid.dao;

import groupid.PersistenceUtil;
import groupid.model.Manufacturer;
import groupid.model.StockItem;
import groupid.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static groupid.dao.UserDAO.emf;

/**
 * Created by Thomas on 3/13/17.
 */

public class StockItemDAO {

    public static void addStockItem(StockItem m){
        PersistenceUtil.persist(m);
    }

    public static void updateStockItem(StockItem StockItem){
        PersistenceUtil.merge(StockItem);
    }

    public static void removeStockItem(StockItem m){
        PersistenceUtil.remove(m);
    }

    public static StockItem getStockItemById(int id){
        EntityManager em = emf.createEntityManager();
        StockItem m = (StockItem) em.createNamedQuery("StockItem.getById").setParameter("id", id).getSingleResult();
        return m;
    }

    public static List<StockItem> getStockItemByManufacturer(Manufacturer m){
        EntityManager em = emf.createEntityManager();
        List<StockItem> stockItems = (List<StockItem>) em.createNamedQuery("StockItem.getByManufacturer").setParameter("manufacturer", m).getResultList();
        return stockItems;
    }

    public static List<StockItem> getStockItemByCategory(String cat){
        EntityManager em = emf.createEntityManager();
        List<StockItem> stockItems = (List<StockItem>) em.createNamedQuery("StockItem.getByCategory").setParameter("category", cat).getResultList();
        return stockItems;
    }

    public static List<StockItem> getAllStockItems(){
        EntityManager em = emf.createEntityManager();
        List<StockItem> m = (List<StockItem>) em.createNamedQuery("StockItem.getAll").getResultList();
        return m;
    }
}
