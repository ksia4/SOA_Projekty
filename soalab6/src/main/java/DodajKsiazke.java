import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "DodajKsiazke")
@RequestScoped
public class DodajKsiazke {
    static private Ksiazka ksiazka;
    static private List<Ksiazka> ksiazkaList = new ArrayList<Ksiazka>();
    static private List<Ksiazka> filtrowanaksiazkaList = new ArrayList<Ksiazka>();
    private List<Autor> autorList = new ArrayList<Autor>();
    private List<SelectItem> selectItems;
    private Autor autor;
    private Wyszukiwarka wyszukiwarka;
    public DodajKsiazke(){
        ksiazka = new Ksiazka();
        wyszukiwarka = new Wyszukiwarka();
        autor = new Autor("","");
        ksiazkaList = wyszukiwarka.znajdziKsiazki();
        autorList = wyszukiwarka.znajdzAutorow();
        filtrowanaksiazkaList = ksiazkaList;
        fillSelectItems();
    }

    public String Dodaj(){
        System.out.println("Funkcja Dodaj");
        this.ksiazka.setAutor(this.autor);
        System.out.println(ksiazka.getAutor().getNazwisko());
        wyszukiwarka.dodajKsiazke(this.ksiazka);
        NapisyBean.setToIndex();
        return "index";
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    private void fillSelectItems(){
        selectItems = new ArrayList<SelectItem>();
        autorList = wyszukiwarka.znajdzAutorow();
        for(Autor a : autorList){
            selectItems.add(new SelectItem(a, a.getNazwa()));
            System.out.println("Dodano do selectItems");
        }
    }
    public Ksiazka getKsiazka() {
        return ksiazka;
    }

    public void setKsiazka(Ksiazka ksiazka) {
        this.ksiazka = ksiazka;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public static void setKsiazkaList(List<Ksiazka> ksiazkaList) {
        DodajKsiazke.ksiazkaList = ksiazkaList;
    }

    public static List<Ksiazka> getKsiazkaList() {
        return ksiazkaList;
    }

    public List<Autor> getAutorList() {
        return autorList;
    }

    public void setAutorList(List<Autor> autorList) {
        this.autorList = autorList;
    }

    public static void setFiltrowanaksiazkaList(List<Ksiazka> filtrowanaksiazkaList) {
        DodajKsiazke.filtrowanaksiazkaList = filtrowanaksiazkaList;
    }

    public List<Ksiazka> getFiltrowanaksiazkaList() {

        return wyszukiwarka.getActualksiazkaList();
    }

    public String deleteKsiazke(Ksiazka k){
        System.out.println("Bede usuwac ksiazke");
        wyszukiwarka.usun(k);
        return "index";
    }

    public String editKsiazke(Ksiazka k){
        ksiazka = k;
        ksiazka.setAutor(k.getAutor());
        autor = k.getAutor();
        return "edytuj";
    }

    public String Update(){
        wyszukiwarka.update(ksiazka);
        return "index";
    }
}
