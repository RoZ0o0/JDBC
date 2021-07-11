package JavaFX;

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

public class obslugafxController implements Initializable {

    public static obslugafxController instance;

    @FXML
    private TableView<Obsluga> tableObsluga;
    @FXML
    private TableColumn<Obsluga, Integer> colId_obs;
    @FXML
    private TableColumn<Obsluga, String> colPrac;
    @FXML
    private TableColumn<Obsluga, String> colWarszt;
    @FXML
    private TableColumn<Obsluga, String> colPojazd;
    @FXML
    private TableColumn<Obsluga, String> colDataObs;
    @FXML
    private TableColumn<Obsluga, String> colCenaObs;


    @FXML
    private AnchorPane anchorObsluga;

    private Connection conn;
    private ObservableList<Obsluga> list;
    private Connect connect;

    public static int editID;

    @FXML
    public ComboBox cmbPrac;
    @FXML
    public ComboBox cmbWarszt;
    @FXML
    public ComboBox cmbPojazd;


    public DatePicker data_obs;
    public TextField cena_obs;

    public obslugafxController(){
        instance = this;
    }

    public static obslugafxController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect = new Connect();
        populateTableView();
        tableObsluga.setEditable(true);
        new AutoCompleteComboBoxListener(cmbPrac);
        new AutoCompleteComboBoxListener(cmbWarszt);
        new AutoCompleteComboBoxListener(cmbPojazd);

    }

    public void populateTableView(){
        try {
            list = FXCollections.observableArrayList();
            String query = "Select * from obsluga";

            conn = connect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {

                Obsluga obsluga = new Obsluga();
                obsluga.setId_obslugi(set.getInt("id_obslugi"));

                String query2 = "SELECT nazwisko, imie FROM pracownik where id_pracownika = " +set.getInt("id_pracownika");
                ResultSet res = conn.createStatement().executeQuery(query2);
                while(res.next()) {
                    obsluga.setId_pracownika(res.getString("nazwisko") + " " + res.getString("imie"));
                }

                String query3 = "SELECT adres, miasto FROM warsztaty where id_warsztatu = " +set.getInt("id_warsztatu");
                ResultSet res2 = conn.createStatement().executeQuery(query3);
                while(res2.next()) {
                    obsluga.setId_warsztatu(res2.getString("adres") + " " + res2.getString("miasto"));
                }

                String query4 = "SELECT pojazdy.id_pojazdu, pojazdy.id_klienta, klient.imie, klient.nazwisko, model, marka FROM pojazdy Inner JOIN klient on pojazdy.id_klienta = klient.id_klienta  where id_pojazdu = " +set.getInt("id_pojazdu");
                ResultSet res3 = conn.createStatement().executeQuery(query4);
                while(res3.next()) {
                    obsluga.setId_pojazdu(res3.getString("imie")+ " " + res3.getString("nazwisko") + " " + res3.getString("model") + " " + res3.getString("marka"));
                }

                obsluga.setData_obslugi(set.getString("data_obslugi"));
                obsluga.setCena_obslugi(set.getString("cena_obslugi"));
                list.add(obsluga);
            }

            colId_obs.setCellValueFactory(new PropertyValueFactory<>("id_obslugi"));
            colPrac.setCellValueFactory(new PropertyValueFactory<>("id_pracownika"));
            colWarszt.setCellValueFactory(new PropertyValueFactory<>("id_warsztatu"));
            colPojazd.setCellValueFactory(new PropertyValueFactory<>("id_pojazdu"));
            colDataObs.setCellValueFactory(new PropertyValueFactory<>("data_obslugi"));
            colCenaObs.setCellValueFactory(new PropertyValueFactory<>("cena_obslugi"));

            tableObsluga.setItems(list);


        }catch(SQLException a){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
        }

        conn = connect.getConnection();
        try {
            String query = "SELECT id_pracownika, nazwisko, imie FROM pracownik";
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                cmbPrac.getItems().addAll(set.getInt("id_pracownika")+ " - " +set.getString("nazwisko")+ " " + set.getString("imie"));
            }
        }catch(SQLException ex){
            Logger.getLogger(pojazdyController.class.getName()).log(Level.SEVERE,null, ex);
        }

        conn = connect.getConnection();
        try {
            String query = "SELECT id_warsztatu, adres, miasto FROM warsztaty";
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                cmbWarszt.getItems().addAll(set.getInt("id_warsztatu")+ " - " +set.getString("adres")+ " " + set.getString("miasto"));
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

                cmbPojazd.getItems().addAll(set.getInt("id_pojazdu")+ " - " +str+ " | " + set.getString("model")+ " " + set.getString("marka"));
            }



        }catch(SQLException ex){
            Logger.getLogger(pojazdyController.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    public void updateTable(){

        if(list!=null) {
            list.clear();
        }
        if(conn != null) {
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

                colId_obs.setCellValueFactory(new PropertyValueFactory<>("id_obslugi"));
                colPrac.setCellValueFactory(new PropertyValueFactory<>("id_pracownika"));
                colWarszt.setCellValueFactory(new PropertyValueFactory<>("id_warsztatu"));
                colPojazd.setCellValueFactory(new PropertyValueFactory<>("id_pojazdu"));
                colDataObs.setCellValueFactory(new PropertyValueFactory<>("data_obslugi"));
                colCenaObs.setCellValueFactory(new PropertyValueFactory<>("cena_obslugi"));

                tableObsluga.setItems(list);


            } catch (SQLException a) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }
        }
    }

    public void buttonFind(){


        conn = connect.getConnection();
        if(cmbPrac.getSelectionModel().isEmpty() && cmbWarszt.getSelectionModel().isEmpty() && cmbPojazd.getSelectionModel().isEmpty() && data_obs.getValue() == null && cena_obs.getText().isEmpty()) {

            Stage stage = (Stage) anchorObsluga.getScene().getWindow();

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
                String query = "Select * from obsluga";

                conn = connect.getConnection();
                ResultSet set = conn.createStatement().executeQuery(query);

                while (set.next()) {


                    Obsluga obsluga = new Obsluga();
                    obsluga.setId_obslugi(set.getInt("id_obslugi"));

                    String query2 = "SELECT nazwisko, imie FROM pracownik where id_pracownika = " +set.getInt("id_pracownika");
                    ResultSet res = conn.createStatement().executeQuery(query2);
                    while(res.next()) {
                        obsluga.setId_pracownika(res.getString("nazwisko") + " " + res.getString("imie"));
                    }

                    String query3 = "SELECT adres, miasto FROM warsztaty where id_warsztatu = " +set.getInt("id_warsztatu");
                    ResultSet res2 = conn.createStatement().executeQuery(query3);
                    while(res2.next()) {
                        obsluga.setId_warsztatu(res2.getString("adres") + " " + res2.getString("miasto"));
                    }

                    String query4 = "SELECT id_pojazdu, model, marka FROM pojazdy where id_pojazdu = " +set.getInt("id_pojazdu");
                    ResultSet res3 = conn.createStatement().executeQuery(query4);
                    while(res3.next()) {
                        obsluga.setId_pojazdu(res3.getInt("id_pojazdu") + " - " + res3.getString("model") + " " + res3.getString("marka"));
                    }

                    obsluga.setData_obslugi(set.getString("data_obslugi"));
                    obsluga.setCena_obslugi(set.getString("cena_obslugi"));
                    list.add(obsluga);
                }

                colId_obs.setCellValueFactory(new PropertyValueFactory<>("id_obslugi"));
                colPrac.setCellValueFactory(new PropertyValueFactory<>("id_pracownika"));
                colWarszt.setCellValueFactory(new PropertyValueFactory<>("id_warsztatu"));
                colPojazd.setCellValueFactory(new PropertyValueFactory<>("id_pojazdu"));
                colDataObs.setCellValueFactory(new PropertyValueFactory<>("data_obslugi"));
                colCenaObs.setCellValueFactory(new PropertyValueFactory<>("cena_obslugi"));

                if(data_obs.getValue() != null){
                    list.removeIf(obsluga -> !obsluga.getData_obslugi().equals(data_obs.getValue().toString()));
                }

                if(cena_obs.getText() != ""){
                    list.removeIf(obsluga -> !obsluga.getCena_obslugi().equals(cena_obs.getText()));
                }

                tableObsluga.setItems(list);


            }catch(SQLException a){
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }

        }
    }

    public void buttonClear(){
        updateTable();
        cmbPrac.setValue("Wybierz");
        cmbWarszt.setValue("Wybierz");
        cmbPojazd.setValue("Wybierz");
        data_obs.setValue(null);
        cena_obs.clear();
    }

    public void buttonDelete() {

        if(tableObsluga.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) anchorObsluga.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else {


            try {
                String sql = "DELETE FROM obsluga where id_obslugi=" + tableObsluga.getSelectionModel().getSelectedItem().getId_obslugi();
                Statement stat = conn.createStatement();
                stat.executeUpdate(sql);

            } catch (SQLException a) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, a);
            }
            updateTable();
            cmbPrac.setValue("Wybierz");
            cmbWarszt.setValue("Wybierz");
            cmbPojazd.setValue("Wybierz");
            data_obs.setValue(null);
            cena_obs.clear();
        }
    }

    public void buttonEdit() throws IOException{

        if(tableObsluga.getSelectionModel().getSelectedItems().isEmpty()){
            Stage stage = (Stage) anchorObsluga.getScene().getWindow();

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie wybrano rekordu");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();
        }else{
            editID = tableObsluga.getSelectionModel().getSelectedItem().getId_obslugi();
            Parent root = FXMLLoader.load(getClass().getResource("../Edit/EditObsluga.fxml"));
            Stage dialog = new Stage();
            dialog.setTitle("Kreator");
            dialog.setResizable(false);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(Main.STAGE);
            dialog.setScene(new Scene(root, 600, 400));
            dialog.show();
        }
    }

    public void buttonObsluga() throws SQLException {
        conn = connect.getConnection();
        if(cmbPrac.getSelectionModel().isEmpty() || cmbWarszt.getSelectionModel().isEmpty() || cmbPojazd.getSelectionModel().isEmpty() || data_obs.getValue() == null || cena_obs.getText().isEmpty()) {

            Stage stage = (Stage) anchorObsluga.getScene().getWindow();

            stage.setResizable(false);

            Alert.AlertType type = Alert.AlertType.INFORMATION;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);

            alert.getDialogPane().setContentText("Nie podano wszystkich informacji!");
            alert.getDialogPane().setHeaderText("Błąd!!");
            alert.showAndWait();

        }else{
            Pattern p1 = Pattern.compile("\\d+");
            Matcher m1 = p1.matcher((String)cmbPrac.getValue());
            String str1 = new String();
            while(m1.find()){
                str1 = m1.group();
            }

            Pattern p2 = Pattern.compile("\\d+");
            Matcher m2 = p2.matcher((String)cmbWarszt.getValue());
            String str2 = new String();
            while(m2.find()){
                str2 = m2.group();
                break;
            }

            Pattern p3 = Pattern.compile("\\d+");
            Matcher m3 = p3.matcher((String)cmbPojazd.getValue());
            String str3 = new String();
            while(m3.find()){
                str3 = m3.group();
                break;
            }

            String sql = "INSERT INTO obsluga(id_pracownika, id_warsztatu, id_pojazdu, data_obslugi, cena_obslugi) VALUES ('" + str1 + "', '" + str2 + "', '" + str3 + "', '" + data_obs.getValue()+"', '" + cena_obs.getText() +"')";
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);

            updateTable();
            cmbPrac.setValue("Wybierz");
            cmbWarszt.setValue("Wybierz");
            cmbPojazd.setValue("Wybierz");
            data_obs.setValue(null);
            cena_obs.clear();

        }
    }

    @FXML
    public void menuKlient() throws IOException {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("sample.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableObsluga.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuPracownik() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("pracownik.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableObsluga.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuWarsztaty() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("warsztaty.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableObsluga.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }

    @FXML
    public void menuPojazdy() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("pojazdy.fxml"));
        Stage app_stage = new Stage();
        app_stage.setResizable(false);
        tableObsluga.getScene().getWindow().hide();
        app_stage.setScene(new Scene(root, 1100, 600));
        app_stage.show();
    }
}