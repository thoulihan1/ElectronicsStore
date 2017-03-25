package groupid.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import groupid.PersistenceUtil;
import groupid.model.Manufacturer;
import groupid.model.Manufacturer;

import java.util.List;

import static groupid.dao.UserDAO.emf;


/**
 * Created by Thomas on 3/12/17.
 */
public class ManufacturerDAO {


    public static void addManufacturer(Manufacturer m){
        PersistenceUtil.persist(m);
    }

    public static void updateManufacturer(Manufacturer manufacturer){
        PersistenceUtil.merge(manufacturer);
    }

    public static void removeManufacturer(Manufacturer m){
        PersistenceUtil.remove(m);
    }

    public static Manufacturer getManufacturerById(String id){
        EntityManager em = emf.createEntityManager();
        Manufacturer m = (Manufacturer) em.createNamedQuery("Manufacturer.getById").setParameter("id", Integer.parseInt(id)).getSingleResult();
        return m;
    }


    public static List<Manufacturer> getAllManufacturers(){
        EntityManager em = emf.createEntityManager();
        List<Manufacturer> m = (List<Manufacturer>) em.createNamedQuery("Manufacturer.getAll").getResultList();
        return m;
    }
}