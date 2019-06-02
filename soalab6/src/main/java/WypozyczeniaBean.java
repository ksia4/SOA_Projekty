import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
// IT SHOULD BE DELETED, UNUSED CLASS
@ManagedBean(name = "WypozyczeniaBean")
@RequestScoped
public class WypozyczeniaBean {
    private CzytelnikDAO czytelnikDAO;
    private List<Wypozyczenia> wypozyczeniaList;
    public WypozyczeniaBean(){
        //wypozyczeniaList = czytelnikDAO.ListaWypozyczonych(CzytelnikBean.czytelnik);
    }

    public void setWypozyczeniaList(List<Wypozyczenia> wypozyczeniaList) {
        this.wypozyczeniaList = wypozyczeniaList;
    }

    public List<Wypozyczenia> getWypozyczeniaList() {
        return wypozyczeniaList;
    }


}
