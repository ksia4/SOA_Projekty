import javax.persistence.*;
import java.util.Date;
// IT SHOULD BE DELETED, UNUSED CLASS
@Entity
@Table(name = "wypozyczenia")
public class Wypozyczenia {
    private Integer wypozyczeniaID;
    private Ksiazka ksiazka;
    //private Integer ksiazkaId;
    private Czytelnik czytelnik;
    //private Integer czytelnikId;
    private Date dataWypozyczenia;
    private Date dataZwrotu;

    public Wypozyczenia(){super();}
    public Wypozyczenia(Ksiazka ksiazka, Czytelnik czytelnik, Date dataWypozyczenia){
        //this.ksiazkaId = ksiazkaId;
        this.ksiazka = ksiazka;
        //this.czytelnikId = czytelnikId;
        this.czytelnik = czytelnik;
        this.dataWypozyczenia = dataWypozyczenia;
        this.dataZwrotu = null;
    }

    @Id
    @GeneratedValue
    @Column(name = "WYPOZYCZENIA_ID", unique = true, nullable = false)
    public Integer getWypozyczeniaID(){
        return this.wypozyczeniaID;
    }

    public void setWypozyczeniaID(Integer wypozyczeniaID) {
        this.wypozyczeniaID = wypozyczeniaID;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KSIAZKA_ID", nullable = false)
    public Ksiazka getKsiazka(){
        return this.ksiazka;
    }

    public void setKsiazka(Ksiazka ksiazka) {
        this.ksiazka = ksiazka;
    }

    /*@Column(name = "KSIAZKA_ID", unique = false, nullable = false)
    public Integer getKsiazkaId(){
        return this.ksiazkaId;
    }

    public void setKsiazkaId(Integer ksiazkaId) {
        this.ksiazkaId = ksiazkaId;
    }*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CZYTELNIK_ID", nullable = false)
    public Czytelnik getCzytelnik(){
        return this.czytelnik;
    }

    public void setCzytelnik(Czytelnik czytelnik) {
        this.czytelnik = czytelnik;
    }

    /*@Column(name = "CZYTELNIK_ID", unique = false, nullable = false)
    public Integer getCzytelnikId(){
        return this.czytelnikId;
    }

    public void setCzytelnikId(Integer czytelnikId) {
        this.czytelnikId = czytelnikId;
    }*/

    @Column(name = "DATA_WYPOZYCZENIA", unique = false, nullable = false)
    public Date getDataWypozyczenia(){
        return this.dataWypozyczenia;
    }

    public void setDataWypozyczenia(Date dataWypozyczenia) {
        this.dataWypozyczenia = dataWypozyczenia;
    }

    @Column(name = "DATA_ZWROTU", unique = false, nullable = true)
    public Date getDataZwrotu(){
        return this.dataZwrotu;
    }

    public void setDataZwrotu(Date dataZwrotu) {
        this.dataZwrotu = dataZwrotu;
    }

    public Boolean czyOddana(){
        if (this.dataZwrotu == null){
            return false;
        }
        return true;
    }
}

