package JavaFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Pracownik {

    private SimpleIntegerProperty id_pracownika = new SimpleIntegerProperty();
    private SimpleStringProperty nazwisko = new SimpleStringProperty();
    private SimpleStringProperty imie = new SimpleStringProperty();
    private SimpleStringProperty data_zatr = new SimpleStringProperty();
    private SimpleIntegerProperty wynagr = new SimpleIntegerProperty();

    public int getId_pracownika() {
        return id_pracownika.get();
    }

    public SimpleIntegerProperty id_pracownikaProperty() {
        return id_pracownika;
    }

    public void setId_pracownika(int id_pracownika) {
        this.id_pracownika.set(id_pracownika);
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

    public String getData_zatr() {
        return data_zatr.get();
    }

    public SimpleStringProperty data_zatrProperty() {
        return data_zatr;
    }

    public void setData_zatr(String data_zatr) {
        this.data_zatr.set(data_zatr);
    }

    public int getWynagr() {
        return wynagr.get();
    }

    public SimpleIntegerProperty wynagrProperty() {
        return wynagr;
    }

    public void setWynagr(int wynagr) {
        this.wynagr.set(wynagr);
    }
}
