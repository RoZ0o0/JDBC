package JavaFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Pojazdy {

    private SimpleIntegerProperty id_pojazdu = new SimpleIntegerProperty();
    private SimpleStringProperty id_klienta = new SimpleStringProperty();
    private SimpleStringProperty model = new SimpleStringProperty();
    private SimpleStringProperty marka = new SimpleStringProperty();
    private SimpleStringProperty rocznik = new SimpleStringProperty();

    public int getId_pojazdu() {
        return id_pojazdu.get();
    }

    public SimpleIntegerProperty id_pojazduProperty() {
        return id_pojazdu;
    }

    public void setId_pojazdu(int id_pojazdu) {
        this.id_pojazdu.set(id_pojazdu);
    }

    public String getId_klienta() {
        return id_klienta.get();
    }

    public SimpleStringProperty id_klientaProperty() {
        return id_klienta;
    }

    public void setId_klienta(String id_klienta) {
        this.id_klienta.set(id_klienta);
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getMarka() {
        return marka.get();
    }

    public SimpleStringProperty markaProperty() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka.set(marka);
    }

    public String getRocznik() {
        return rocznik.get();
    }

    public SimpleStringProperty rocznikProperty() {
        return rocznik;
    }

    public void setRocznik(String rocznik) {
        this.rocznik.set(rocznik);
    }
}
