import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;
import java.util.List;

public class CzytelnikDAO {

    private EntityManagerFactory factory;
    private EntityManager em;

    public CzytelnikDAO(){
        factory = Persistence.createEntityManagerFactory("soalab");
        em = factory.createEntityManager();
    }

    public void DodajCzytelnika(Czytelnik cz){
        try{
            Integer ID = ZnajdzCzytelnika(cz);
            if(ID == -1){
            em.getTransaction().begin();

            em.persist(cz);
            em.getTransaction().commit(); }
            else{
                cz.setCzytelnikId(ID);
            }
        }
        catch (Exception e){
            System.err.println("Błąd przy dodawaniu czytelnika: " + e);
        }
    }

    public Integer ZnajdzCzytelnika(Czytelnik cz){
        try{
            em.getTransaction().begin();
            Query q = em.createQuery("from Czytelnik where imie = :imie and nazwisko = :nazwisko");
            q.setParameter("imie", cz.getImie());
            q.setParameter("nazwisko", cz.getNazwisko());
            List<Czytelnik> list = q.getResultList();
            em.getTransaction().commit();
            if(list.size() == 0){
                return -1;
            }
            else{
                return list.get(0).getCzytelnikId();
            }

        }
        catch (Exception e){
            System.err.println("Błąd przy pobieraniu czytelnika");
        }
        return -1;
    }
}
