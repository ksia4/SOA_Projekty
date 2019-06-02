import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
// IT SHOULD BE DELETED, UNUSED CLASS
@ManagedBean(name = "CzytelnikBean")
@RequestScoped
public class CzytelnikBean {

    private static Czytelnik czytelnik = new Czytelnik();
    private CzytelnikDAO czytelnikDAO = new CzytelnikDAO();
    private List<Wypozyczenia> wypozyczeniaList = new ArrayList<Wypozyczenia>();

    public CzytelnikBean(){}

    public void setCzytelnik(Czytelnik cz) {
        czytelnik = cz;
    }

    public Czytelnik getCzytelnik() {
        return czytelnik;
    }

    public void setWypozyczeniaList(List<Wypozyczenia> wypozyczeniaList) {
        this.wypozyczeniaList = wypozyczeniaList;
    }

    public List<Wypozyczenia> getWypozyczeniaList() {
        wypozyczeniaList = czytelnikDAO.ListaWypozyczonych(czytelnik);
        return wypozyczeniaList;
    }

    public String loguj(){

        czytelnikDAO.DodajCzytelnika(czytelnik);
        return NapisyBean.setToWypozycz();
    }

    public String wypozycz(Ksiazka ksiazka){
        System.out.println("Czytelnik: " + czytelnik.getImie() + " o ID = " + czytelnik.getCzytelnikId());
        czytelnikDAO.Wypozycz(czytelnik, ksiazka);
        return "wypozycz";
    }

    public String oddaj(Wypozyczenia wypozyczenia){
        czytelnikDAO.Oddaj(wypozyczenia);
        return "wypozyczenia";
    }
}
