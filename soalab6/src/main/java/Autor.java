import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autor")
public class Autor {
    private String Imie;
    private String Nazwisko;
    private Integer autorID;
    private Set<Ksiazka> ksiazkaSet = new HashSet<Ksiazka>(0);
    private String Nazwa;

    public Autor(){super();}
    public Autor(String Imie, String Nazwisko){
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        this.Nazwa = this.Imie + " " + this.Nazwisko;
    }

    public String getNazwa() {
        this.Nazwa = this.Imie + " " + this.Nazwisko;
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    @Id
    @GeneratedValue
    @Column(name = "AUTOR_ID", unique = true, nullable = false)
    public Integer getAutorID(){
        return this.autorID;
    }

    public void setAutorID(Integer autorID) {
        this.autorID = autorID;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "autor")
    public Set<Ksiazka> getKsiazkaSet(){
        return this.ksiazkaSet;
    }

    public void setKsiazkaSet(Set<Ksiazka> ksiazkaSet) {
        this.ksiazkaSet = ksiazkaSet;
    }

    public boolean equals(Object other) {
        return other instanceof Autor && (getNazwa() != null) ? Nazwa.equals(((Autor) other).Nazwa) : (other == this);
    }

    public int hashCode(){
        return getNazwa() != null ? this.getClass().hashCode() + Nazwa.hashCode() : super.hashCode();
    }
}
