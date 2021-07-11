package JavaFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
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

public class Controller implements Initializable {

    public static Controller instance;

    @FXML
    private TableView<Klient> tableKlient;
    @FXML
    private TableColumn<Klient, Integer> colId_kl;
    @FXML
    private TableColumn<Klient, String> colNazwisko;
    @FXML
    private TableColumn<Klient, String> colImie;
    @FXML
    private TableColumn<Klient, String> colMiasto;
    @FXML
    private TableColumn<Klient, String> colAdres;

    @FXML
    private AnchorPane myAnchorPane;

    public static int editID;

    private Connection conn;
    private ObservableList<Klient> list;
    private Connect connect;

    public TextField nazwisko;
    public TextField imie;
    public TextField miasto;
    public TextField ulica;

    public Controller(){
        instance = this;
    }

    public static Controller getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connect = new Connect();
        populateTableView();

    }

    public void populateTableView(){
        try {
            list = FXCollections.observableArrayList();
            String query = "Select * from klient";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Klient klient = new Klient();
                klient.setId_klienta(set.getInt("id_klienta"));
                klient.setNazwisko(set.getString("nazwisko"));
                klient.setImie(set.getString("imie"));
                klient.setMiasto(set.getString("miasto"));
                klient.setAdres_nr_domu(set.getString("ulica_nr_domu"));
                list.add(klient);
            }

            colId_kl.setCellValueFactory(new PropertyValueFactory<>("id_klienta"));
            colNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
            colImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
            colMiasto.setCellValueFactory(new PropertyValueFactory<>("miasto"));
            colAdres.setCellValueFactory(new PropertyValueFactory<>("adres_nr_domu"));

            tableKlient.setItems(list);

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
            String query = "Select * from klient";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Klient klient = new Klient();
                klient.setId_klienta(set.getInt("id_klienta"));
                klient.setNazwisko(set.getString("nazwisko"));
                klient.setImie(set.getString("imie"));
                klient.setMiasto(set.getString("miasto"));
                klient.setAdres_nr_domu(set.getString("ulica_nr_domu"));
                list.add(klient);
            }

            colId_kl.setCellValueFactory(new PropertyValueFactory<>("id_klienta"));
            colNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
            colImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
            colMiasto.setCellValueFactory(new PropertyValueFactory<>("miasto"));
            colAdres.setCellValueFactory(new PropertyValueFactory<>("adres_nr_domu"));

            tableKlient.setItems(list);


        }catch(SQLException a){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
        }

    }

    public void buttonFind(){


        conn = connect.getConnection();
        if(nazwisko.getText().isEmpty() && imie.getText().isEmpty() && miasto.getText().isEmpty() && ulica.getText().isEmpty()) {

            Stage stage = (Stage) myAnchorPane.getScene().getWindow();

            AlertType type = AlertType.INFORMATION;
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
                String query = "Select * from klient";

                conn = connect.getConnection();
                ResultSet set = conn.createStatement().executeQuery(query);

                while (set.next()) {

                        Klient klient = new Klient();
                        klient.setId_klienta(set.getInt("id_klienta"));
                        klient.setNazwisko(set.getString("nazwisko"));
                        klient.setImie(set.getString("imie"));
                        klient.setMiasto(set.getString("miasto"));
                        klient.setAdres_nr_domu(set.getString("ulica_nr_domu"));
                        list.add(klient);
                }


                colId_kl.setCellValueFactory(new PropertyValueFactory<>("id_klienta"));
                colNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
                colImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
                colMiasto.setCellValueFactory(new PropertyValueFactory<>("miasto"));
                colAdres.setCellValueFactory(new PropertyValueFactory<>("adres_nr_domu"));

                if(nazwisko.getText() != ""){
                    list.removeIf(klient -> !klient.getNazwisko().equals(nazwisko.getText()));
                }

                if(imie.getText() != ""){
                    list.removeIf(klient -> !klient.getImie().equals(imie.getText()));
                }

                if(miasto.getText() != ""){
                    list.removeIf(klient -> !klient.getMiasto().equals(miasto.getText()));
                }

                if(ulica.getText() != ""){
                    list.removeIf(klient -> !klient.getAdres_nr_domu().equals(ulica.getText()));
                }

                tableKlient.setItems(list);


            }catch(SQLException a){
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }

        }
    }

    public void buttonClear(){
        updateTable();
        nazwisko.clear();
        imie.clear();
        miasto.clear();
        ulica.clear();
    }

    public void buttonDelete() {


        if(tableKlient.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) myAnchorPane.getScene().getWindow();

            AlertType type = AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }

        else {
            try {
                String sql = "DELETE FROM klient where id_klienta=" + tableKlient.getSelectionModel().getSelectedItem().getId_klienta();
                Statement stat = conn.createStatement();
                stat.executeUpdate(sql);

            } catch (SQLException a) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }
            updateTable();
            nazwisko.clear();
            imie.clear();
            miasto.clear();
            ulica.clear();
        }
    }

    public void buttonEdit() throws IOException{

        if(tableKlient.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) myAnchorPane.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else{
            editID = tableKlient.getSelectionModel().getSelectedItem().getId_klienta();
            Parent root = FXMLLoader.load(getClass().getResource("../Edit/EditKlient.fxml"));
            Stage dialog = new Stage();
            dialog.setTitle("Kreator");
            dialog.setResizable(false);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(Main.STAGE);
            dialog.setScene(new Scene(root, 600, 400));
            dialog.show();
        }
    }

    public void button() throws SQLException {


        conn = connect.getConnection();
        if(nazwisko.getText().isEmpty() || imie.getText().isEmpty() || miasto.getText().isEmpty() || ulica.getText().isEmpty()) {

            Stage stage = (Stage) myAnchorPane.getScene().getWindow();

            AlertType type = AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie podano wszystkich informacji!");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();

        }else{

            String sql = "INSERT INTO klient(nazwisko, imie, miasto, ulica_nr_domu) VALUES('" + nazwisko.getText() + "', '" + imie.getText() + "', '" + miasto.getText() + "', '" + ulica.getText() + "')";
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);

            updateTable();
            nazwisko.clear();
            imie.clear();
            miasto.clear();
            ulica.clear();
        }

    }

    @FXML
    public void menuObsluga() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("obslugafx.fxml"));
            Stage app_stage = new Stage();
            app_stage.setResizable(false);
            tableKlient.getScene().getWindow().hide();
            app_stage.setScene(new Scene(root, 1100, 600));
            app_stage.show();
    }

    @FXML
    public void menuPracownik() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("pracownik.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableKlient.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuWarsztaty() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("warsztaty.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableKlient.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuPojazdy() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("pojazdy.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableKlient.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }
}
