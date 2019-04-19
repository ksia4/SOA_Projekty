import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "CzytelnikBean")
@RequestScoped
public class CzytelnikBean {

    private Czytelnik czytelnik = new Czytelnik();
    private CzytelnikDAO czytelnikDAO = new CzytelnikDAO();

    public CzytelnikBean(){}

    public void setCzytelnik(Czytelnik czytelnik) {
        this.czytelnik = czytelnik;
    }

    public Czytelnik getCzytelnik() {
        return czytelnik;
    }

    public String loguj(){

        czytelnikDAO.DodajCzytelnika(this.czytelnik);
        return "wypozycz";
    }
}
