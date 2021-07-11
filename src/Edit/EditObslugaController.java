package Edit;

import JavaFX.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditObslugaController implements Initializable{

    private final obslugafxController obsluga_instance = obslugafxController.getInstance();

    private Connection conn;
    private Connect connect;
    private ObservableList<Obsluga> list;


    @FXML
    private Button zapisz;
    @FXML
    private ComboBox editPrac;
    @FXML
    private ComboBox editWarsz;
    @FXML
    private ComboBox editPojazd;
    @FXML
    private DatePicker editDate;
    @FXML
    private TextField editCena;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        obslugafxController obs = new obslugafxController();
        connect = new Connect();

        try {
            String sql = "Select * from obsluga where id_obslugi=" + obs.editID;
            conn = connect.getConnection();
            ResultSet sett = conn.createStatement().executeQuery(sql);

            while(sett.next()){

                String query2 = "SELECT id_pracownika, nazwisko, imie FROM pracownik where id_pracownika = " + sett.getInt("id_pracownika");
                ResultSet res = conn.createStatement().executeQuery(query2);
                while (res.next()) {
                    editPrac.setValue(res.getString("id_pracownika")+ " - "+ res.getString("nazwisko") + " " + res.getString("imie"));
                }

                String query3 = "SELECT id_warsztatu,adres, miasto FROM warsztaty where id_warsztatu = " + sett.getInt("id_warsztatu");
                ResultSet res2 = conn.createStatement().executeQuery(query3);
                while (res2.next()) {
                    editWarsz.setValue(res2.getString("id_warsztatu")+ " - " +res2.getString("adres") + " " + res2.getString("miasto"));
                }

                String query4 = "SELECT pojazdy.id_pojazdu, pojazdy.id_klienta, klient.imie, klient.nazwisko, model, marka FROM pojazdy Inner JOIN klient on pojazdy.id_klienta = klient.id_klienta  where id_pojazdu = " + sett.getInt("id_pojazdu");
                ResultSet res3 = conn.createStatement().executeQuery(query4);
                while (res3.next()) {
                    editPojazd.setValue(res3.getString("id_pojazdu")+" - "+res3.getString("imie") + " " + res3.getString("nazwisko") + " " + res3.getString("model") + " " + res3.getString("marka"));
                }
                editDate.setValue(LocalDate.parse(sett.getString("data_obslugi")));
                editCena.setText(sett.getString("cena_obslugi"));
            }

        }catch(SQLException er){

        }



        try {
            list = FXCollections.observableArrayList();
            String query = "Select * from obsluga";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Obsluga obsluga = new Obsluga();
                obsluga.setId_obslugi(set.getInt("id_obslugi"));

                String query2 = "SELECT nazwisko, imie FROM pracownik where id_pracownika = " + set.getInt("id_pracownika");
                ResultSet res = conn.createStatement().executeQuery(query2);
                while (res.next()) {
                    obsluga.setId_pracownika(res.getString("nazwisko") + " " + res.getString("imie"));
                }

                String query3 = "SELECT adres, miasto FROM warsztaty where id_warsztatu = " + set.getInt("id_warsztatu");
                ResultSet res2 = conn.createStatement().executeQuery(query3);
                while (res2.next()) {
                    obsluga.setId_warsztatu(res2.getString("adres") + " " + res2.getString("miasto"));
                }

                String query4 = "SELECT pojazdy.id_pojazdu, pojazdy.id_klienta, klient.imie, klient.nazwisko, model, marka FROM pojazdy Inner JOIN klient on pojazdy.id_klienta = klient.id_klienta  where id_pojazdu = " + set.getInt("id_pojazdu");
                ResultSet res3 = conn.createStatement().executeQuery(query4);
                while (res3.next()) {
                    obsluga.setId_pojazdu(res3.getString("imie") + " " + res3.getString("nazwisko") + " " + res3.getString("model") + " " + res3.getString("marka"));
                }

                obsluga.setData_obslugi(set.getString("data_obslugi"));
                obsluga.setCena_obslugi(set.getString("cena_obslugi"));
                list.add(obsluga);
            }

        } catch (SQLException err){

        }

        try {
            String query = "SELECT id_pracownika, nazwisko, imie FROM pracownik";
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                editPrac.getItems().addAll(set.getInt("id_pracownika")+ " - " +set.getString("nazwisko")+ " " + set.getString("imie"));
            }
        }catch(SQLException ex){
            Logger.getLogger(pojazdyController.class.getName()).log(Level.SEVERE,null, ex);
        }

        conn = connect.getConnection();
        try {
            String query = "SELECT id_warsztatu, adres, miasto FROM warsztaty";
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                editWarsz.getItems().addAll(set.getInt("id_warsztatu")+ " - " +set.getString("adres")+ " " + set.getString("miasto"));
            }
        }catch(SQLException ex){
            Logger.getLogger(pojazdyController.class.getName()).log(Level.SEVERE,null, ex);
        }

        conn = connect.getConnection();
        try {
            String query = "SELECT id_pojazdu, id_klienta, model, marka FROM pojazdy";
            ResultSet set = conn.createStatement().executeQuery(query);



            while (set.next()) {
                String str = new String();
                String query2 = "SELECT nazwisko, imie FROM klient where id_klienta = " +set.getInt("id_klienta");
                ResultSet res = conn.createStatement().executeQuery(query2);
                while(res.next()) {
                    str = res.getString("nazwisko") + " " + res.getString("imie");
                }

                editPojazd.getItems().addAll(set.getInt("id_pojazdu")+ " - " +str+ " | " + set.getString("model")+ " " + set.getString("marka"));
            }



        }catch(SQLException ex){
            Logger.getLogger(pojazdyController.class.getName()).log(Level.SEVERE,null, ex);
        }

        new AutoCompleteComboBoxListener<>(editPrac);
        new AutoCompleteComboBoxListener<>(editWarsz);
        new AutoCompleteComboBoxListener<>(editPojazd);

    }

    public void editData() throws SQLException{
        obslugafxController obs = new obslugafxController();
        connect = new Connect();

        Pattern p1 = Pattern.compile("\\d+");
        Matcher m1 = p1.matcher((String)editPrac.getValue());
        String str1 = new String();
        while(m1.find()){
            str1 = m1.group();
        }

        Pattern p2 = Pattern.compile("\\d+");
        Matcher m2 = p2.matcher((String)editWarsz.getValue());
        String str2 = new String();
        while(m2.find()){
            str2 = m2.group();
            break;
        }

        Pattern p3 = Pattern.compile("\\d+");
        Matcher m3 = p3.matcher((String)editPojazd.getValue());
        String str3 = new String();
        while(m3.find()){
            str3 = m3.group();
            break;
        }

        String query = "UPDATE obsluga set id_pracownika="+str1+", id_warsztatu="+str2+", id_pojazdu="+str3+", data_obslugi='"+editDate.getValue()+"', cena_obslugi='"+editCena.getText()+"' where id_obslugi="+obs.editID;

        Statement stat = conn.createStatement();
        stat.executeUpdate(query);

        Stage stage = (Stage) zapisz.getScene().getWindow();
        stage.close();

        obsluga_instance.updateTable();

    }
}
