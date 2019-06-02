import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// IT SHOULD BE DELETED, UNUSED CLASS
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

    public void Wypozycz(Czytelnik czytelnik, Ksiazka ksiazka){
        Wypozyczenia wypozyczenia = new Wypozyczenia(ksiazka, czytelnik, new Date());
        Wyszukiwarka.zmienStan(ksiazka.getKsiazkaId(), ksiazka.getCzyDostepna());
        try{
            em.getTransaction().begin();
            em.persist(wypozyczenia);
            em.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("Błąd przy wypozyczaniu: " + e);
        }
    }

    public void Oddaj(Wypozyczenia w){
        Wyszukiwarka.zmienStan(w.getKsiazka().getKsiazkaId(), w.getKsiazka().getCzyDostepna());
        try{
            em.getTransaction().begin();
            Query q = em.createQuery("update Wypozyczenia set dataZwrotu = :datazwrot where wypozyczeniaID = :ID");
            q.setParameter("datazwrot", new Date());
            q.setParameter("ID", w.getWypozyczeniaID());
            q.executeUpdate();
            em.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("Błąd przy wypozyczaniu: " + e);
        }
    }

    public List<Wypozyczenia> ListaWypozyczonych(Czytelnik cz){
        List<Wypozyczenia> list = new ArrayList<Wypozyczenia>();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("from Wypozyczenia where czytelnik.czytelnikId = :czID");
            q.setParameter("czID", cz.getCzytelnikId());
            list = q.getResultList();
            em.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("Błąd przy pobieraniu wypozyczen");
        }

        return list;
    }
}
