package JavaFX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Warsztaty {

    private SimpleIntegerProperty id_warsztatu = new SimpleIntegerProperty();
    private SimpleStringProperty adres = new SimpleStringProperty();
    private SimpleStringProperty miasto = new SimpleStringProperty();

    public int getId_warsztatu() {
        return id_warsztatu.get();
    }

    public SimpleIntegerProperty id_warsztatuProperty() {
        return id_warsztatu;
    }

    public void setId_warsztatu(int id_warsztatu) {
        this.id_warsztatu.set(id_warsztatu);
    }

    public String getAdres() {
        return adres.get();
    }

    public SimpleStringProperty adresProperty() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres.set(adres);
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
}
