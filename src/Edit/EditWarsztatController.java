package Edit;

import JavaFX.Connect;
import JavaFX.Controller;
import JavaFX.warsztatyController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EditWarsztatController implements Initializable{

    private final warsztatyController warsztaty_instance = warsztatyController.getInstance();

    private Connection conn;
    private Connect connect;

    @FXML
    private Button zapisz;
    @FXML
    private TextField adres;
    @FXML
    private TextField miasto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warsztatyController obs = new warsztatyController();
        connect = new Connect();

        try {
            String sql = "Select * from warsztaty where id_warsztatu=" + obs.editID;
            conn = connect.getConnection();
            ResultSet sett = conn.createStatement().executeQuery(sql);

            while(sett.next()){
                adres.setText(sett.getString("adres"));
                miasto.setText(sett.getString("miasto"));
            }

        }catch(SQLException er){

        }
    }

    public void editData() throws SQLException{
        warsztatyController obs = new warsztatyController();

        String query = "UPDATE warsztaty set adres='"+adres.getText()+"', miasto='"+miasto.getText()+"' where id_warsztatu="+obs.editID;

        Statement stat = conn.createStatement();
        stat.executeUpdate(query);

        Stage stage = (Stage) zapisz.getScene().getWindow();
        stage.close();

        warsztaty_instance.updateTable();

    }
}
