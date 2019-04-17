import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "czytelnik")

public class Czytelnik {
    private String Imie;
    private String Nazwisko;
    private Integer czytelnikId;
    private Set<Wypozyczenia> wypozyczeniaSet = new HashSet<Wypozyczenia>(0);

    public Czytelnik(){super();}
    public Czytelnik(String Imie, String Nazwisko){
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
    }

    @Id
    @GeneratedValue
    @Column(name = "CZYTELNIK_ID", unique = true, nullable = false)
    public Integer getCzytelnikId(){
        return this.czytelnikId;
    }

    public void setCzytelnikId(Integer czytelnikId) {
        this.czytelnikId = czytelnikId;
    }

    @Column(name = "IMIE", unique = false, nullable = false)
    public String getImie(){
        return this.Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    @Column(name = "NAZWISKO", unique = false, nullable = false)
    public String getNazwisko(){
        return this.Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "czytelnik")
    public Set<Wypozyczenia> getWypozyczeniaSet(){
        return this.wypozyczeniaSet;
    }

    public void setWypozyczeniaSet(Set<Wypozyczenia> wypozyczeniaSet) {
        this.wypozyczeniaSet = wypozyczeniaSet;
    }
}
