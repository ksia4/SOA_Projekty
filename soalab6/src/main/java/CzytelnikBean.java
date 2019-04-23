import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "CzytelnikBean")
@RequestScoped
public class CzytelnikBean {

    private static Czytelnik czytelnik = new Czytelnik();
    private CzytelnikDAO czytelnikDAO = new CzytelnikDAO();

    public CzytelnikBean(){}

    public void setCzytelnik(Czytelnik cz) {
        czytelnik = cz;
    }

    public Czytelnik getCzytelnik() {
        return czytelnik;
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
}
