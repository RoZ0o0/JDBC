package JavaFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pojazdyController implements Initializable {

    public static pojazdyController instance;

    @FXML
    private TableView<Pojazdy> tablePojazdy;
    @FXML
    private TableColumn<Pojazdy, Integer> colID_poj;
    @FXML
    private TableColumn<Pojazdy, String> colID_kli;
    @FXML
    private TableColumn<Pojazdy, String> colModel;
    @FXML
    private TableColumn<Pojazdy, String> colMarka;
    @FXML
    private TableColumn<Pojazdy, String> colRocznik;

    @FXML
    private AnchorPane anchorPojazdy;

    public static int editID;

    @FXML
    private ComboBox cmbklient;

    private Connection conn;
    private ObservableList<Pojazdy> list;
    private Connect connect;


    public TextField Model;
    public TextField Marka;
    public TextField Rocznik;

    public pojazdyController(){
        instance = this;
    }

    public static pojazdyController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        connect = new Connect();
        populateTableView();

        new AutoCompleteComboBoxListener<>(cmbklient);
    }

    public void populateTableView(){
        try {
            list = FXCollections.observableArrayList();
            String query = "Select * from pojazdy";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Pojazdy pojazdy = new Pojazdy();
                pojazdy.setId_pojazdu(set.getInt("id_pojazdu"));
                String query2 = "SELECT nazwisko, imie FROM klient where id_klienta = " +set.getInt("id_klienta");
                ResultSet res = conn.createStatement().executeQuery(query2);
                while(res.next()) {
                    pojazdy.setId_klienta(res.getString("nazwisko") + " " + res.getString("imie"));
                }
                pojazdy.setModel(set.getString("model"));
                pojazdy.setMarka(set.getString("marka"));
                pojazdy.setRocznik(set.getString("rocznik"));
                list.add(pojazdy);
            }

            colID_poj.setCellValueFactory(new PropertyValueFactory<>("id_pojazdu"));
            colID_kli.setCellValueFactory(new PropertyValueFactory<>("id_klienta"));
            colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
            colMarka.setCellValueFactory(new PropertyValueFactory<>("marka"));
            colRocznik.setCellValueFactory(new PropertyValueFactory<>("rocznik"));

            tablePojazdy.setItems(list);

        }catch(SQLException a){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
        }

        conn = connect.getConnection();
        try {
            String query = "SELECT id_klienta, nazwisko, imie FROM klient";
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                cmbklient.getItems().addAll(set.getInt("id_klienta")+ " - " +set.getString("nazwisko")+ " " + set.getString("imie"));
            }
        }catch(SQLException ex){
            Logger.getLogger(pojazdyController.class.getName()).log(Level.SEVERE,null, ex);
        }

    }

    public void updateTable(){
        if(list!=null) {
            list.clear();
        }

        try {
            list = FXCollections.observableArrayList();
            String query = "Select * from pojazdy";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Pojazdy pojazdy = new Pojazdy();
                pojazdy.setId_pojazdu(set.getInt("id_pojazdu"));
                String query2 = "SELECT nazwisko, imie FROM klient where id_klienta = " +set.getInt("id_klienta");
                ResultSet res = conn.createStatement().executeQuery(query2);
                while(res.next()) {
                    pojazdy.setId_klienta(res.getString("nazwisko") + " " + res.getString("imie"));
                }
                pojazdy.setModel(set.getString("model"));
                pojazdy.setMarka(set.getString("marka"));
                pojazdy.setRocznik(set.getString("rocznik"));
                list.add(pojazdy);
            }

            colID_poj.setCellValueFactory(new PropertyValueFactory<>("id_pojazdu"));
            colID_kli.setCellValueFactory(new PropertyValueFactory<>("id_klienta"));
            colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
            colMarka.setCellValueFactory(new PropertyValueFactory<>("marka"));
            colRocznik.setCellValueFactory(new PropertyValueFactory<>("rocznik"));



            tablePojazdy.setItems(list);

        }catch(SQLException a){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
        }
    }

    public void buttonFind(){


        conn = connect.getConnection();
        if(cmbklient.getSelectionModel().isEmpty() && Model.getText().isEmpty() && Marka.getText().isEmpty() && Rocznik.getText().isEmpty()) {

            Stage stage = (Stage) anchorPojazdy.getScene().getWindow();

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
                String query = "Select * from pojazdy";

                conn = connect.getConnection();
                ResultSet set = conn.createStatement().executeQuery(query);

                while (set.next()) {

                    Pojazdy pojazdy = new Pojazdy();
                    pojazdy.setId_pojazdu(set.getInt("id_pojazdu"));
                    String query2 = "SELECT nazwisko, imie FROM klient where id_klienta = " +set.getInt("id_klienta");
                    ResultSet res = conn.createStatement().executeQuery(query2);
                    while(res.next()) {
                        pojazdy.setId_klienta(res.getString("nazwisko") + " " + res.getString("imie"));
                    }
                    pojazdy.setModel(set.getString("model"));
                    pojazdy.setMarka(set.getString("marka"));
                    pojazdy.setRocznik(set.getString("rocznik"));
                    list.add(pojazdy);
                }

                colID_poj.setCellValueFactory(new PropertyValueFactory<>("id_pojazdu"));
                colID_kli.setCellValueFactory(new PropertyValueFactory<>("id_klienta"));
                colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
                colMarka.setCellValueFactory(new PropertyValueFactory<>("marka"));
                colRocznik.setCellValueFactory(new PropertyValueFactory<>("rocznik"));


                if(Model.getText() != ""){
                    list.removeIf(pojazdy -> !pojazdy.getModel().equals(Model.getText()));
                }

                if(Marka.getText() != ""){
                    list.removeIf(pojazdy -> !pojazdy.getMarka().equals(Marka.getText()));
                }

                if(Rocznik.getText() != ""){
                    list.removeIf(pojazdy -> !pojazdy.getRocznik().equals(Rocznik.getText()));
                }

                tablePojazdy.setItems(list);


            }catch(SQLException a){
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }

        }
    }

    public void buttonClear(){
        updateTable();
        cmbklient.setValue("Wybierz");
        Model.clear();
        Marka.clear();
        Rocznik.clear();
    }

    public void buttonDelete() {

        if(tablePojazdy.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) anchorPojazdy.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else {


            try {
                String sql = "DELETE FROM pojazdy where id_pojazdu=" + tablePojazdy.getSelectionModel().getSelectedItem().getId_pojazdu();
                Statement stat = conn.createStatement();
                stat.executeUpdate(sql);

            } catch (SQLException a) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }
            updateTable();
            cmbklient.setValue("Wybierz");
            Model.clear();
            Marka.clear();
            Rocznik.clear();
        }
    }

    public void buttonEdit() throws IOException{

        if(tablePojazdy.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) anchorPojazdy.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else{
            editID = tablePojazdy.getSelectionModel().getSelectedItem().getId_pojazdu();
            Parent root = FXMLLoader.load(getClass().getResource("../Edit/EditPojazdy.fxml"));
            Stage dialog = new Stage();
            dialog.setTitle("Kreator");
            dialog.setResizable(false);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(Main.STAGE);
            dialog.setScene(new Scene(root, 600, 400));
            dialog.show();
        }
    }

    public void buttonPojazdy() throws SQLException{
        conn = connect.getConnection();
        if(cmbklient.getSelectionModel().isEmpty() || Model.getText().isEmpty() || Marka.getText().isEmpty() || Rocznik.getText().isEmpty()) {

            Stage stage = (Stage) anchorPojazdy.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie podano wszystkich informacji!");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();

        }else{
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher((String)cmbklient.getValue());
            String str = new String();
            while(m.find()){
                str = m.group();
            }
            String sql = "INSERT INTO pojazdy(id_klienta, model, marka, rocznik) VALUES ('" + str + "', '" + Model.getText() + "', '" + Marka.getText() + "', '" + Rocznik.getText()+"')";
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);

            updateTable();
            cmbklient.setValue("Wybierz");
            Model.clear();
            Marka.clear();
            Rocznik.clear();

        }
    }

    @FXML
    public void menuObsluga() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("obslugafx.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tablePojazdy.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuPracownik() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("pracownik.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tablePojazdy.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuWarsztaty() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("warsztaty.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tablePojazdy.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuKlient() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tablePojazdy.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

}