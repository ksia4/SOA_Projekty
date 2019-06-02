import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

// IT SHOULD BE DELETED, UNUSED CLASS
@ManagedBean(name = "NapisyBean")
@RequestScoped

public class NapisyBean {
    static private String actualH1;
    static private String actualTitle;
    static private String actualButton;
    private Boolean zmiana;

    public NapisyBean(){
        actualH1 = "Witaj w Bibliotece!";
        actualTitle = "Biblioteka";
        actualButton = "Wypożycz książkę";
        zmiana = true;
    }

    public static String getActualH1() {
        return actualH1;
    }

    public static void setActualH1(String actualH1) {
        NapisyBean.actualH1 = actualH1;
    }

    public static String getActualTitle() {
        return actualTitle;
    }

    public static void setActualTitle(String actualTitle) {
        NapisyBean.actualTitle = actualTitle;
    }

    public static void setActualButton(String actualButton) {
        NapisyBean.actualButton = actualButton;
    }

    public static String getActualButton() {
        return actualButton;
    }

    public static String setToKatalog(){
        FilterBean.FilterWszystkiePozycjeZBazy();
        setActualTitle("Katalog");
        setActualH1("Spis książek");
        setActualButton("Wypożycz książkę");
        return "katalog";
    }

    public static String setToDodajKsiazke(){

        setActualTitle("Dodawanie Ksiazki");
        setActualH1("Wprowadz nową książkę");
        return "dodaj";
    }

    public static String setToIndex(){
        setActualTitle("Biblioteka");
        setActualH1("Witaj w Bibliotece!");
        System.out.println("Index");
        return "index";
    }

    public static String setToWypozycz(){
        setActualTitle("Wypozyczalnia");
        setActualH1("Spis Dostępnych Książek");
        setActualButton("Zobacz pełen spis książek");
        FilterBean.FilterTylkoDostepne();
        System.out.println(getActualH1());
        System.out.println("Filtrowanie ksiazek dostepnych");
        return "wypozycz";
    }

    public static String setToZaloguj(){
        setActualTitle("Logowanie");
        setActualH1("Podaj swoje dane");
        setActualButton("zaloguj");
        return "logowanie";
    }

    public String changePage(){
        System.out.println("Zmiana!" + zmiana.toString());
        if(this.zmiana){
            zmiana = false;
            System.out.println("Zmiana!" + zmiana.toString());
            return setToWypozycz();
        }
        else {
            zmiana = true;
            return setToKatalog();
        }
    }

    public static String setTowypozyczenia(){

        return "wypozyczenia";
    }


}
