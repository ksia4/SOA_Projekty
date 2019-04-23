import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "FilterBean")
@RequestScoped


public class FilterBean {

    public FilterBean(){}

    public static void FilterTylkoDostepne(){
        System.out.println("Filtrowanie ksiazek dostepnych");
        List<Ksiazka> tmp = new ArrayList<Ksiazka>();
        System.out.println(DodajKsiazke.getKsiazkaList().size());
        for(Ksiazka k : DodajKsiazke.getKsiazkaList()){
            if(k.getCzyDostepna()) {
                tmp.add(k);
                System.out.println("Filtrowanie ksiazek dostepnych");
                System.out.println(k.getCzyDostepna().toString());
                System.out.println(k.getTytul());
            }
        }
        DodajKsiazke.setFiltrowanaksiazkaList(tmp);
    }

    public static void FilterWszystkiePozycjeZBazy(){
        List<Ksiazka> tmp = DodajKsiazke.getKsiazkaList();
        DodajKsiazke.setFiltrowanaksiazkaList(tmp);
    }
}
