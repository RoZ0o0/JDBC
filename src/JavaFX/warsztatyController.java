package JavaFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class warsztatyController implements Initializable{

    public static warsztatyController instance;

    @FXML
    private TableView<Warsztaty> tableWarsztaty;
    @FXML
    private TableColumn<Warsztaty, Integer> colID_warszt;
    @FXML
    private TableColumn<Warsztaty, String> colAdres;
    @FXML
    private TableColumn<Warsztaty, String> colMiasto;

    @FXML
    private AnchorPane anchorWarsztaty;

    public static int editID;

    private Connection conn;
    private ObservableList<Warsztaty> list;
    private Connect connect;

    public TextField adres_warsz;
    public TextField miasto_warsz;

    public warsztatyController(){
        instance = this;
    }

    public static warsztatyController getInstance() {
        return instance;
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        connect = new Connect();
        populateTableView();
    }

    private void populateTableView() {
        try {
            list = FXCollections.observableArrayList();
            String query = "Select * from warsztaty";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Warsztaty warsztaty = new Warsztaty();
                warsztaty.setId_warsztatu(set.getInt("id_warsztatu"));
                warsztaty.setAdres(set.getString("adres"));
                warsztaty.setMiasto(set.getString("miasto"));
                list.add(warsztaty);
            }

            colID_warszt.setCellValueFactory(new PropertyValueFactory<>("id_warsztatu"));
            colAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
            colMiasto.setCellValueFactory(new PropertyValueFactory<>("miasto"));

            tableWarsztaty.setItems(list);

        }catch(SQLException a){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
        }
    }

    public void updateTable(){
        if(list!=null) {
            list.clear();
        }

        try {
            list = FXCollections.observableArrayList();
            String query = "Select * from warsztaty";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Warsztaty warsztaty = new Warsztaty();
                warsztaty.setId_warsztatu(set.getInt("id_warsztatu"));
                warsztaty.setAdres(set.getString("adres"));
                warsztaty.setMiasto(set.getString("miasto"));
                list.add(warsztaty);
            }

            colID_warszt.setCellValueFactory(new PropertyValueFactory<>("id_warsztatu"));
            colAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
            colMiasto.setCellValueFactory(new PropertyValueFactory<>("miasto"));

            tableWarsztaty.setItems(list);

        }catch(SQLException a){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
        }
    }

    public void buttonFind(){


        conn = connect.getConnection();
        if(adres_warsz.getText().isEmpty() && miasto_warsz.getText().isEmpty()) {

            Stage stage = (Stage) anchorWarsztaty.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie podano wszystkich informacji!");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();

        }else{


            list.clear();

            try {
                list = FXCollections.observableArrayList();
                String query = "Select * from warsztaty";

                conn = connect.getConnection();
                ResultSet set = conn.createStatement().executeQuery(query);

                while (set.next()) {

                    Warsztaty warsztaty = new Warsztaty();
                    warsztaty.setId_warsztatu(set.getInt("id_warsztatu"));
                    warsztaty.setAdres(set.getString("adres"));
                    warsztaty.setMiasto(set.getString("miasto"));
                    list.add(warsztaty);
                }

                colID_warszt.setCellValueFactory(new PropertyValueFactory<>("id_warsztatu"));
                colAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
                colMiasto.setCellValueFactory(new PropertyValueFactory<>("miasto"));

                if(adres_warsz.getText() != ""){
                    list.removeIf(warsztaty -> !warsztaty.getAdres().equals(adres_warsz.getText()));
                }

                if(miasto_warsz.getText() != ""){
                    list.removeIf(warsztaty -> !warsztaty.getMiasto().equals(miasto_warsz.getText()));
                }


                tableWarsztaty.setItems(list);


            }catch(SQLException a){
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }

        }
    }

    public void buttonClear(){
        updateTable();

        adres_warsz.clear();
        miasto_warsz.clear();
    }

    public void buttonDelete() {

        if(tableWarsztaty.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) anchorWarsztaty.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else {


            try {
                String sql = "DELETE FROM warsztaty where id_warsztatu=" + tableWarsztaty.getSelectionModel().getSelectedItem().getId_warsztatu();
                Statement stat = conn.createStatement();
                stat.executeUpdate(sql);

            } catch (SQLException a) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }
            updateTable();

            adres_warsz.clear();
            miasto_warsz.clear();
        }
    }

    public void buttonEdit() throws IOException{

        if(tableWarsztaty.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) anchorWarsztaty.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else{
            editID = tableWarsztaty.getSelectionModel().getSelectedItem().getId_warsztatu();
            Parent root = FXMLLoader.load(getClass().getResource("../Edit/EditWarsztat.fxml"));
            Stage dialog = new Stage();
            dialog.setTitle("Kreator");
            dialog.setResizable(false);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(Main.STAGE);
            dialog.setScene(new Scene(root, 600, 400));
            dialog.show();
        }
    }

    public void buttonWarsztaty() throws SQLException{

        conn = connect.getConnection();
        if(adres_warsz.getText().isEmpty() || miasto_warsz.getText().isEmpty()) {

            Stage stage = (Stage) anchorWarsztaty.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie podano wszystkich informacji!");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else{
            String sql = "INSERT INTO warsztaty(adres, miasto) VALUES('" + adres_warsz.getText() + "', '" + miasto_warsz.getText() +"')";
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);

            updateTable();
            adres_warsz.clear();
            miasto_warsz.clear();
        }
    }

    @FXML
    public void menuObsluga() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("obslugafx.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableWarsztaty.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuPojazdy() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("pojazdy.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableWarsztaty.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuPracownik() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("pracownik.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableWarsztaty.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuKlient() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableWarsztaty.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }
}
