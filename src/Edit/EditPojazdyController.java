package Edit;

import JavaFX.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditPojazdyController implements Initializable{

    private final pojazdyController pojazdy_instance = pojazdyController.getInstance();

    private Connection conn;
    private Connect connect;
    private ObservableList<Obsluga> list;

    @FXML
    private Button zapisz;
    @FXML
    private ComboBox editKlient;
    @FXML
    private TextField model;
    @FXML
    private TextField marka;
    @FXML
    private TextField rocznik;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pojazdyController obs = new pojazdyController();
        connect = new Connect();

        try {
            String sql = "Select * from pojazdy where id_pojazdu=" + obs.editID;
            conn = connect.getConnection();
            ResultSet sett = conn.createStatement().executeQuery(sql);

            while(sett.next()){
                String query2 = "SELECT id_klienta, nazwisko, imie FROM klient where id_klienta = " + sett.getInt("id_klienta");
                ResultSet res = conn.createStatement().executeQuery(query2);
                while (res.next()) {
                    editKlient.setValue(res.getString("id_klienta")+ " - "+ res.getString("nazwisko") + " " + res.getString("imie"));
                }
                model.setText(sett.getString("model"));
                marka.setText(sett.getString("marka"));
                rocznik.setText(sett.getString("rocznik"));
            }

        }catch(SQLException er){

        }

        try {
            list = FXCollections.observableArrayList();
            String query = "Select * from pojazdy";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Pojazdy pojazdy = new Pojazdy();

                String query2 = "SELECT nazwisko, imie FROM klient where id_klienta = " + set.getInt("id_klienta");
                ResultSet res = conn.createStatement().executeQuery(query2);
                while (res.next()) {
                    pojazdy.setId_klienta(res.getString("nazwisko") + " " + res.getString("imie"));
                }
            }
        }catch(SQLException srr){

        }

        try {
            String query = "SELECT id_klienta, nazwisko, imie FROM klient";
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                editKlient.getItems().addAll(set.getInt("id_klienta")+ " - " +set.getString("nazwisko")+ " " + set.getString("imie"));
            }
        }catch(SQLException ex){
            Logger.getLogger(pojazdyController.class.getName()).log(Level.SEVERE,null, ex);
        }

        new AutoCompleteComboBoxListener<>(editKlient);
    }

    public void editData() throws SQLException{
        pojazdyController obs = new pojazdyController();
        connect = new Connect();

        Pattern p1 = Pattern.compile("\\d+");
        Matcher m1 = p1.matcher((String)editKlient.getValue());
        String str1 = new String();
        while(m1.find()){
            str1 = m1.group();
        }

        String query = "UPDATE pojazdy set id_klienta="+str1+", model='"+model.getText()+"', marka='"+marka.getText()+"', rocznik='"+rocznik.getText()+"' where id_pojazdu="+obs.editID;

        Statement stat = conn.createStatement();
        stat.executeUpdate(query);

        Stage stage = (Stage) zapisz.getScene().getWindow();
        stage.close();

        pojazdy_instance.updateTable();
    }
}
