import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

// IT SHOULD BE DELETED, UNUSED CLASS

public class Wyszukiwarka {
    private List<Ksiazka> ksiazkaList = new ArrayList<Ksiazka>();
    private List<Ksiazka> actualksiazkaList = new ArrayList<Ksiazka>();
    private List<Autor> autorList = new ArrayList<Autor>();
    private List<String> autorPrintableList = new ArrayList<String>();
    private Map<String, Autor> autorMap;

    private static EntityManagerFactory factory;
    private static EntityManager em;

    public Wyszukiwarka(){
        factory = Persistence.createEntityManagerFactory("soalab");
        em = factory.createEntityManager();
        znajdziKsiazki();
        znajdzAutorow();
    }

    public void setKsiazkaList(List<Ksiazka> ksiazkaList) {
        this.ksiazkaList = ksiazkaList;
    }

    public List<Ksiazka> getKsiazkaList() {
        return ksiazkaList;
    }

    public List<Ksiazka> znajdziKsiazki(){
        try {
            Query q = em.createQuery("FROM Ksiazka ", Ksiazka.class);
            ksiazkaList = q.getResultList();
        }
        catch (Exception e){
            System.err.println("Błąd pobierania rekordow: " + e);
        }
        return getKsiazkaList();
    }

    public void setAutorList(List<Autor> autorList) {
        this.autorList = autorList;
    }

    public List<String> getAutorPrintableList() {
        return autorPrintableList;
    }

    public void setAutorPrintableList(List<String> autorPrintableList) {
        this.autorPrintableList = autorPrintableList;
    }

    public List<Autor> getAutorList() {
        return autorList;
    }
    public List<Autor> znajdzAutorow(){
        try {
            Query q = em.createQuery("FROM Autor ", Autor.class);
            autorList = q.getResultList();
            /*autorPrintableList = new ArrayList<String>();
            for(Autor a : autorList){
                autorPrintableList.add(a.getImie() + " " + a.getNazwisko());
            }*/

            autorMap = new LinkedHashMap<String, Autor>();
            for(Autor a: autorList){
                autorMap.put(a.getNazwa(), a);
            }
        }
        catch (Exception e){
            System.err.println("Błąd pobierania rekordow: " + e);
        }
        return getAutorList();
    }

    public void dodajKsiazke(Ksiazka ksiazka){
        try{
            em.getTransaction().begin();
            em.persist(ksiazka.getAutor());
            em.persist(ksiazka);
            em.getTransaction().commit();
            znajdziKsiazki();
        }
        catch (Exception e){
            System.err.println("Błąd przy dodawaniu rekordu: " + e);
        }
    }

    public Autor find(String key) {
        System.out.println("Funkcja find");
        Autor a = autorMap.get(key);
        System.out.println(a.getNazwa());
        return autorMap.get(key);
    }


    public void usun(Ksiazka k){
        Query q = em.createQuery("delete Ksiazka ks where ks.id = :idK");
        q.setParameter("idK", k.getKsiazkaId());
        System.out.println("id = " + k.getKsiazkaId());
        em.getTransaction().begin();
        int result = q.executeUpdate();
        em.getTransaction().commit();

    }

    public void update(Ksiazka k){
        Query q = em.createQuery("update Ksiazka set tytul = :ksT where ksiazkaId = :ksID");
        System.out.println("update za 3... 2.. 1..");
        q.setParameter("ksT", k.getTytul());
        System.out.println("tytul" + k.getTytul().toString());
        //q.setParameter("ksA", k.getAutor());
        q.setParameter("ksID", k.getKsiazkaId());
        System.out.println("id = "+ k.getKsiazkaId());
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
        //System.out.println("Aktualizacja ksiazki o id" + k.getKsiazkaId().toString() + "Nowy tytul to: " + k.getTytul());
    }

    public static void zmienStan(Integer ID, Boolean stan){
        Query q;
        if(stan){
            q = em.createQuery("update Ksiazka set czyDostepna = false where ksiazkaId = :ksID");
        }
        else{
            q = em.createQuery("update Ksiazka set czyDostepna = true where ksiazkaId = :ksID");
        }
        q.setParameter("ksID", ID);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
    }

    public void setActualksiazkaList(List<Ksiazka> actualksiazkaList) {
        this.actualksiazkaList = actualksiazkaList;
    }

    public List<Ksiazka> getActualksiazkaList() {
        try {
            Query q = em.createQuery("from Ksiazka where czyDostepna = true ");
            actualksiazkaList = q.getResultList();
        }
        catch (Exception e){
            System.err.println("Błąd pobierania rekordow: " + e);
        }
        return actualksiazkaList;
    }
}
