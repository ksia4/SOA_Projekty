import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ksiazka")
public class Ksiazka {
    private Integer ksiazkaId;
    private String tytul;
    //private Integer autorID;
    private Boolean czyDostepna;
    private Autor autor;
    private Set<Wypozyczenia> wypozyczeniaSet = new HashSet<Wypozyczenia>(0);

    public Ksiazka() {this.czyDostepna = true;}

    public Ksiazka(String tytul, Autor autor){
        this.tytul = tytul;
        this.autor = autor;
        //this.autorID = autorID;
        this.czyDostepna = true;
    }

    @Id
    @GeneratedValue
    @Column(name = "KSIAZKA_ID", unique = true, nullable = false)
    public Integer getKsiazkaId(){
        return this.ksiazkaId;
    }

    public void setKsiazkaId(Integer ksiazkaId) {
        this.ksiazkaId = ksiazkaId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTOR_ID", nullable = false)
    public Autor getAutor(){
        return this.autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Column(name = "TYTUL", unique = false, nullable = false)
    public String getTytul(){
        return this.tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    /*@Column(name = "AUTOR_ID", unique = false, nullable = false)
    public Integer getAutorID(){
        return this.autorID;
    }

    public void setAutorID(Integer autorID) {
        this.autorID = autorID;
    }*/

    @Column(name = "STATUS", unique = false, nullable = false)
    public Boolean getCzyDostepna(){
        return this.czyDostepna;
    }

    public void setCzyDostepna(Boolean czyDostepna) {
        this.czyDostepna = czyDostepna;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ksiazka")
    public Set<Wypozyczenia> getWypozyczeniaSet(){
        return this.wypozyczeniaSet;
    }

    public void setWypozyczeniaSet(Set<Wypozyczenia> wypozyczeniaSet) {
        this.wypozyczeniaSet = wypozyczeniaSet;
    }
}
