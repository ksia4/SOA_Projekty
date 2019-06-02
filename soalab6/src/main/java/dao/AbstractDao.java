package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class AbstractDao<T> {
    private EntityManagerFactory factory;
    private EntityManager em;

    public AbstractDao(){
        factory = Persistence.createEntityManagerFactory("soalab");
        em = factory.createEntityManager();
    }

    public void save(T t){
        try{
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("Straszliwy blad!!!: " + e); //zmienic
        }
    }

//    abstract T get(int id);
//    abstract List<T> getAll();
//    abstract void update(T t, Object[] params);//tu byl string, experymentujemy
//    abstract void delete(T t);
}