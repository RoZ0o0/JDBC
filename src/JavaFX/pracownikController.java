package JavaFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class pracownikController implements Initializable {

    public static pracownikController instance;

    @FXML
    private TableView<Pracownik> tablePracownik;
    @FXML
    private TableColumn<Pracownik, String> colID_prac;
    @FXML
    private TableColumn<Pracownik, String> colNazwisko;
    @FXML
    private TableColumn<Pracownik, String> colImie;
    @FXML
    private TableColumn<Pracownik, String> colDataZat;
    @FXML
    private TableColumn<Pracownik, Integer> colWynag;

    @FXML
    private AnchorPane anchorPracownik;

    public static int editID;

    private Connection conn;
    private ObservableList<Pracownik> list;
    private Connect connect;

    public TextField nazwisko_prac;
    public TextField imie_prac;
    public DatePicker data_zatru;
    public TextField wynagr;

    public pracownikController(){
        instance = this;
    }

    public static pracownikController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        connect = new Connect();
        populateTableView();
    }

    public void populateTableView(){
        try {
            list = FXCollections.observableArrayList();
            String query = "Select * from pracownik";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Pracownik pracownik = new Pracownik();
                pracownik.setId_pracownika(set.getInt("id_pracownika"));
                pracownik.setNazwisko(set.getString("nazwisko"));
                pracownik.setImie(set.getString("imie"));
                pracownik.setData_zatr(set.getString("data_zatrudnienia"));
                pracownik.setWynagr(set.getInt("wynagrodzenie"));
                list.add(pracownik);
            }

            colID_prac.setCellValueFactory(new PropertyValueFactory<>("id_pracownika"));
            colNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
            colImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
            colDataZat.setCellValueFactory(new PropertyValueFactory<>("data_zatr"));
            colWynag.setCellValueFactory(new PropertyValueFactory<>("wynagr"));

            tablePracownik.setItems(list);

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
            String query = "Select * from pracownik";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Pracownik pracownik = new Pracownik();
                pracownik.setId_pracownika(set.getInt("id_pracownika"));
                pracownik.setNazwisko(set.getString("nazwisko"));
                pracownik.setImie(set.getString("imie"));
                pracownik.setData_zatr(set.getString("data_zatrudnienia"));
                pracownik.setWynagr(set.getInt("wynagrodzenie"));
                list.add(pracownik);
            }

            colID_prac.setCellValueFactory(new PropertyValueFactory<>("id_pracownika"));
            colNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
            colImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
            colDataZat.setCellValueFactory(new PropertyValueFactory<>("data_zatr"));
            colWynag.setCellValueFactory(new PropertyValueFactory<>("wynagr"));

            tablePracownik.setItems(list);

        }catch(SQLException a){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
        }
    }

    public void buttonFind(){


        conn = connect.getConnection();
        if(nazwisko_prac.getText().isEmpty() && imie_prac.getText().isEmpty() && data_zatru.getValue() == null && wynagr.getText().isEmpty()) {

            Stage stage = (Stage) anchorPracownik.getScene().getWindow();

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
                String query = "Select * from pracownik";

                conn = connect.getConnection();
                ResultSet set = conn.createStatement().executeQuery(query);

                while (set.next()) {

                    Pracownik pracownik = new Pracownik();
                    pracownik.setId_pracownika(set.getInt("id_pracownika"));
                    pracownik.setNazwisko(set.getString("nazwisko"));
                    pracownik.setImie(set.getString("imie"));
                    pracownik.setData_zatr(set.getString("data_zatrudnienia"));
                    pracownik.setWynagr(set.getInt("wynagrodzenie"));
                    list.add(pracownik);
                }

                colID_prac.setCellValueFactory(new PropertyValueFactory<>("id_pracownika"));
                colNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
                colImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
                colDataZat.setCellValueFactory(new PropertyValueFactory<>("data_zatr"));
                colWynag.setCellValueFactory(new PropertyValueFactory<>("wynagr"));

                if(nazwisko_prac.getText() != ""){
                    list.removeIf(pracownik -> !pracownik.getNazwisko().equals(nazwisko_prac.getText()));
                }

                if(imie_prac.getText() != ""){
                    list.removeIf(pracownik -> !pracownik.getImie().equals(imie_prac.getText()));
                }

                if(data_zatru.getValue() != null){
                    list.removeIf(pracownik -> !pracownik.getData_zatr().equals(data_zatru.getValue().toString()));
                }

                if(wynagr.getText() != ""){
                    list.removeIf(pracownik -> pracownik.getWynagr() != Integer.parseInt(wynagr.getText().trim()));
                }

                tablePracownik.setItems(list);


            }catch(SQLException a){
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }

        }
    }

    public void buttonClear(){
        updateTable();
        nazwisko_prac.clear();
        imie_prac.clear();
        data_zatru.setValue(null);
        wynagr.clear();
    }

    public void buttonDelete() {

        if(tablePracownik.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) anchorPracownik.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else {


            try {
                String sql = "DELETE FROM pracownik where id_pracownika=" + tablePracownik.getSelectionModel().getSelectedItem().getId_pracownika();
                Statement stat = conn.createStatement();
                stat.executeUpdate(sql);

            } catch (SQLException a) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }
            updateTable();
            nazwisko_prac.clear();
            imie_prac.clear();
            data_zatru.setValue(null);
            wynagr.clear();
        }
    }

    public void buttonEdit() throws IOException{

        if(tablePracownik.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) anchorPracownik.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else{
            editID = tablePracownik.getSelectionModel().getSelectedItem().getId_pracownika();
            Parent root = FXMLLoader.load(getClass().getResource("../Edit/EditPracownik.fxml"));
            Stage dialog = new Stage();
            dialog.setTitle("Kreator");
            dialog.setResizable(false);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(Main.STAGE);
            dialog.setScene(new Scene(root, 600, 400));
            dialog.show();
        }
    }

    public void buttonPracownik() throws SQLException {
        conn = connect.getConnection();
        if(nazwisko_prac.getText().isEmpty() || imie_prac.getText().isEmpty() || data_zatru.getValue() == null || wynagr.getText().isEmpty()) {

            Stage stage = (Stage) anchorPracownik.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie podano wszystkich informacji!");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();

        }else{
            String sql = "INSERT INTO pracownik(nazwisko, imie, data_zatrudnienia, wynagrodzenie) VALUES ('" + nazwisko_prac.getText() + "', '" + imie_prac.getText() + "', '" + data_zatru.getValue() + "', '" + wynagr.getText()+"')";
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);

            updateTable();
            nazwisko_prac.clear();
            imie_prac.clear();
            data_zatru.setValue(null);
            wynagr.clear();

        }
    }

    @FXML
    public void menuObsluga() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("obslugafx.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tablePracownik.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuPojazdy() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("pojazdy.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tablePracownik.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuWarsztaty() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("warsztaty.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tablePracownik.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuKlient() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tablePracownik.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }
}
