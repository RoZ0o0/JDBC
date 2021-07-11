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

public class EditKlientController implements Initializable{

    private final Controller klient_instance = Controller.getInstance();

    private Connection conn;
    private Connect connect;

    @FXML
    private Button zapisz;
    @FXML
    private TextField nazwisko;
    @FXML
    private TextField imie;
    @FXML
    private TextField miasto;
    @FXML
    private TextField adres;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Controller obs = new Controller();
        connect = new Connect();

        try {
            String sql = "Select * from klient where id_klienta=" + obs.editID;
            conn = connect.getConnection();
            ResultSet sett = conn.createStatement().executeQuery(sql);

            while(sett.next()){
                nazwisko.setText(sett.getString("nazwisko"));
                imie.setText(sett.getString("imie"));
                miasto.setText(sett.getString("miasto"));
                adres.setText(sett.getString("ulica_nr_domu"));
            }

        }catch(SQLException er){

        }
    }

    public void editData() throws SQLException{
        Controller obs = new Controller();

        String query = "UPDATE klient set nazwisko='"+nazwisko.getText()+"', imie='"+imie.getText()+"', miasto='"+miasto.getText()+"', ulica_nr_domu='"+adres.getText()+"' where id_klienta="+obs.editID;

        Statement stat = conn.createStatement();
        stat.executeUpdate(query);

        Stage stage = (Stage) zapisz.getScene().getWindow();
        stage.close();

        klient_instance.updateTable();

    }

}
