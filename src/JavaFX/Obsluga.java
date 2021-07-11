package JavaFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Obsluga {
    private SimpleIntegerProperty id_obslugi = new SimpleIntegerProperty();
    private SimpleStringProperty id_pracownika = new SimpleStringProperty();
    private SimpleStringProperty id_warsztatu = new SimpleStringProperty();
    private SimpleStringProperty id_pojazdu = new SimpleStringProperty();
    private SimpleStringProperty data_obslugi = new SimpleStringProperty();
    private SimpleStringProperty cena_obslugi = new SimpleStringProperty();

    public int getId_obslugi() {
        return id_obslugi.get();
    }

    public SimpleIntegerProperty id_obslugiProperty() {
        return id_obslugi;
    }

    public void setId_obslugi(int id_obslugi) {
        this.id_obslugi.set(id_obslugi);
    }

    public String getId_pracownika() {
        return id_pracownika.get();
    }

    public SimpleStringProperty id_pracownikaProperty() {
        return id_pracownika;
    }

    public void setId_pracownika(String id_pracownika) {
        this.id_pracownika.set(id_pracownika);
    }

    public String getId_warsztatu() {
        return id_warsztatu.get();
    }

    public SimpleStringProperty id_warsztatuProperty() {
        return id_warsztatu;
    }

    public void setId_warsztatu(String id_warsztatu) {
        this.id_warsztatu.set(id_warsztatu);
    }

    public String getId_pojazdu() {
        return id_pojazdu.get();
    }

    public SimpleStringProperty id_pojazduProperty() {
        return id_pojazdu;
    }

    public void setId_pojazdu(String id_pojazdu) {
        this.id_pojazdu.set(id_pojazdu);
    }

    public String getData_obslugi() {
        return data_obslugi.get();
    }

    public SimpleStringProperty data_obslugiProperty() {
        return data_obslugi;
    }

    public void setData_obslugi(String data_obslugi) {
        this.data_obslugi.set(data_obslugi);
    }

    public String getCena_obslugi() {
        return cena_obslugi.get();
    }

    public SimpleStringProperty cena_obslugiProperty() {
        return cena_obslugi;
    }

    public void setCena_obslugi(String cena_obslugi) {
        this.cena_obslugi.set(cena_obslugi);
    }
}
