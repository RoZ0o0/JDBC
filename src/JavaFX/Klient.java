package JavaFX;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Klient {
    private SimpleIntegerProperty id_klienta = new SimpleIntegerProperty();
    private SimpleStringProperty nazwisko = new SimpleStringProperty();
    private SimpleStringProperty imie = new SimpleStringProperty();
    private SimpleStringProperty miasto = new SimpleStringProperty();
    private SimpleStringProperty adres_nr_domu = new SimpleStringProperty();

    public int getId_klienta() {
        return id_klienta.get();
    }

    public SimpleIntegerProperty id_klientaProperty() {
        return id_klienta;
    }

    public void setId_klienta(int id_klienta) {
        this.id_klienta.set(id_klienta);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public SimpleStringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getImie() {
        return imie.get();
    }

    public SimpleStringProperty imieProperty() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getMiasto() {
        return miasto.get();
    }

    public SimpleStringProperty miastoProperty() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto.set(miasto);
    }

    public String getAdres_nr_domu() {
        return adres_nr_domu.get();
    }

    public SimpleStringProperty adres_nr_domuProperty() {
        return adres_nr_domu;
    }

    public void setAdres_nr_domu(String adres_nr_domu) {
        this.adres_nr_domu.set(adres_nr_domu);
    }
}
