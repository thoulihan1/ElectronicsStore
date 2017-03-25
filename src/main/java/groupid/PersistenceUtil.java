package groupid;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Thomas on 10/25/16.
 */

public class PersistenceUtil implements Serializable{

    private static final long serialVersionUID = 1L;

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("electronicsStore");

    public static void persist(Object obj){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        em.close();
    }

    public static void remove(Object obj){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Object mergedEntity = em.merge(obj);
        em.remove(mergedEntity);
        em.getTransaction().commit();
        em.close();
    }

    public static Object merge(Object obj){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        obj = em.merge(obj);
        em.getTransaction().commit();
        em.close();
        return obj;
    }

    public static EntityManager createEM(){
        return emf.createEntityManager();
    }
}
