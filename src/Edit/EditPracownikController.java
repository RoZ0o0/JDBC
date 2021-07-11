package Edit;

import JavaFX.Connect;
import JavaFX.Controller;
import JavaFX.pracownikController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditPracownikController implements Initializable{

    private final pracownikController pracownik_instance = pracownikController.getInstance();

    private Connection conn;
    private Connect connect;

    @FXML
    private Button zapisz;
    @FXML
    private TextField nazwisko;
    @FXML
    private TextField imie;
    @FXML
    private TextField wynagr;
    @FXML
    private DatePicker data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pracownikController obs = new pracownikController();
        connect = new Connect();

        try {
            String sql = "Select * from pracownik where id_pracownika=" + obs.editID;
            conn = connect.getConnection();
            ResultSet sett = conn.createStatement().executeQuery(sql);

            while(sett.next()){
                nazwisko.setText(sett.getString("nazwisko"));
                imie.setText(sett.getString("imie"));
                wynagr.setText(sett.getString("wynagrodzenie"));
                data.setValue(LocalDate.parse(sett.getString("data_zatrudnienia")));
            }

        }catch(SQLException er){

        }
    }

    public void editData() throws SQLException{
        pracownikController obs = new pracownikController();

        int wyn = Integer.parseInt(wynagr.getText());

        String query = "UPDATE pracownik set nazwisko='"+nazwisko.getText()+"', imie='"+imie.getText()+"', wynagrodzenie='"+wyn+"', data_zatrudnienia='"+data.getValue()+"' where id_pracownika="+obs.editID;

        Statement stat = conn.createStatement();
        stat.executeUpdate(query);

        Stage stage = (Stage) zapisz.getScene().getWindow();
        stage.close();

        pracownik_instance.updateTable();

    }
}
