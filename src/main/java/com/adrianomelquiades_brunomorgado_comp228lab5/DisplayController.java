package com.adrianomelquiades_brunomorgado_comp228lab5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import com.sun.rowset.CachedRowSetImpl;
import javafx.stage.StageStyle;
import org.controlsfx.control.action.Action;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DisplayController implements Initializable {

    @FXML
    private Label welcomeUserLabel;
    @FXML
    private Button editPlayerButtonOnAction;
    @FXML
    private Button exitButtonOnAction;
    @FXML
    private ImageView gameBrandImageView;
    @FXML
    private TextField gameIDTextField;
    @FXML
    private TextField gameTitleTextField;
    @FXML
    private DatePicker playingDateDatePicker;
    @FXML
    private TextField gameScoreTextField;
    @FXML
    private Button insertGameButtonOnAction;
    @FXML
    private Label gameMessageLabel;
    @FXML
    private TableView gamesTableView;
    @FXML
    private TableColumn gameIDTableColumn;
    @FXML
    private TableColumn gameTitleTableColumn;
    @FXML
    private TableColumn playingDateTableColumn;
    @FXML
    private TableColumn scoreTableColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File gameBrandFile = new File("Images/centennial-games.jpg");
        Image gameBrandImage = new Image(gameBrandFile.toURI().toString());
        gameBrandImageView.setImage(gameBrandImage);

        String playerName = LoginController.getPlayerFirstName();

        if(!playerName.equals("") && !playerName.equals(null)){
            welcomeUserLabel.setText("Welcome, " + playerName);
            System.out.println("login: " + playerName);
        }else{
            welcomeUserLabel.setText("Welcome, " + RegisterController.playerFirstName);
            System.out.println("Registration: " + RegisterController.playerFirstName);
        }



    }

    public void editPlayerButtonOnAction(ActionEvent event){
        createEditPlayer();

        Stage stage = (Stage) editPlayerButtonOnAction.getScene().getWindow();
        stage.close();
    }

    public void insertGameButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        insertNewGame();
        insertNewPLayerAndGame();
    }

    public static void createEditPlayer(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(OnlineGames.class.getResource("edit-player-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void insertNewGame() throws SQLException, ClassNotFoundException {


        int gameID = Integer.parseInt(gameIDTextField.getText());
        String gameTitle = gameTitleTextField.getText();

        try {

            DBUtil.insertDataIntoGame(gameID, gameTitle);
//            gameMessageLabel.setText("New Gave has been inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void insertNewPLayerAndGame() throws SQLException, ClassNotFoundException {


        int gameID = Integer.parseInt(gameIDTextField.getText());
        int playerID = LoginController.player.getId();
        String playingDate =  playingDateDatePicker.getValue().toString();
        int score = Integer.parseInt(gameScoreTextField.getText());

        try {

            DBUtil.insertDataIntoPlayerAndGame(gameID, playerID, playingDate, score);
            gameMessageLabel.setText("New Game has been inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void exitButtonOnAction(ActionEvent event){

        Stage stage = (Stage) exitButtonOnAction.getScene().getWindow();
        stage.close();

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(OnlineGames.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }












}




    //Global variables
//    Connection conn = null;
//    Statement statement = null;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle){
//
//        //Initialize connection to the database
//        try {
//            connectToDatabase();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        //Create Game table if it does not exist
//        try {
//            createTableGame();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        //Create Player Table if it does not exist
//        try {
//            createTablePlayer();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        //Create PlayerAndGame Table if it does not exist
//        try {
//            createTablePlayerAndGame();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    //Establish a connection to the database
//    public void connectToDatabase() throws SQLException{
//
//        String dbURL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
//        String username = "COMP214M21_001_P_4";
//        String password = "password";
//
//
//        //Connect to database
//        try{
//            conn = DriverManager.getConnection(dbURL, username, password);
//            System.out.println("Database is connected");
//        }catch(SQLException e){
//            System.out.println("Database is NOT connected");
//            System.out.println(e.getMessage());
//        }
//
//    }
//
//    public boolean tableExist(Connection conn, String tableName) throws SQLException{
//        boolean tExists = false;
//
//        try{
//            ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
//            while(rs.next()){
//                String tName = rs.getString("TABLE_NAME");
//                if(tName != null && tName.equals(tableName)){
//                    tExists = true;
//                    break;
//                }
//            }
//        }catch(SQLException e){
//            System.out.println(e.getMessage());
//        }
//
//        return tExists;
//    }

    //Create table

//    public void createTableGame() throws SQLException {
//       boolean tExists = false;
//       try{
//          tExists = tableExist(conn, "GAME");
//       }catch(SQLException e){
//           System.out.println(e.getMessage());
//       }
//
//       if(tExists){
//           System.out.println("Table already created");
//       }
//       else{
//           //Create table and set the Primary Key
//           String sql = "CREATE TABLE game(g_id integer, game_title VARCHAR(50), CONSTRAINT game_gID_pk PRIMARY KEY (g_id))";
//           statement = conn.createStatement();
//           statement.execute(sql);
//           System.out.println("Table Game created");
//
//           //Add constraints
//           String constraint = "ALTER TABLE game MODIFY game_title CONSTRAINT game_gametitle_nn NOT NULL";
//           statement = conn.createStatement();
//           statement.execute(constraint);
//
//       }
//
//    }
//
//    public void createTablePlayer() throws SQLException {
//        boolean tExists = false;
//        try{
//            tExists = tableExist(conn, "PLAYER");
//        }catch(SQLException e){
//            System.out.println(e.getMessage());
//        }
//
//        if(tExists){
//            System.out.println("Table already created");
//        }
//        else{
//            String sql = "CREATE TABLE player(player_id integer, first_name VARCHAR(50), last_name VARCHAR(50)," +
//                    "address VARCHAR(100), postal_code VARCHAR(7), province VARCHAR(2), phone_number NUMBER(10), password VARCHAR(40)" +
//                    " CONSTRAINT player_playerID_pk PRIMARY KEY (player_id))";
//            statement = conn.createStatement();
//            statement.execute(sql);
//            System.out.println("Table Player created");
//
//            //Add constraints
//            String constraint = "ALTER TABLE player MODIFY (first_name CONSTRAINT player_firstname_nn NOT NULL," +
//                    "last_name CONSTRAINT player_lastname_nn NOT NULL, phone_number CONSTRAINT player_phonenumber_nn NOT NULL," +
//                    "CONSTRAINT player_uk UNIQUE(password)";
//            statement = conn.createStatement();
//            statement.execute(constraint);
//        }
//
//    }
//
//    public void createTablePlayerAndGame() throws SQLException {
//        boolean tExists = false;
//        try{
//            tExists = tableExist(conn, "PLAYER_AND_GAME");
//        }catch(SQLException e){
//            System.out.println(e.getMessage());
//        }
//
//        if(tExists){
//            System.out.println("Table already created");
//        }
//        else{
//            String sql = "CREATE TABLE player_and_game(player_game_id INTEGER, game_id INTEGER, player_id INTEGER," +
//                    "playing_date DATE, score INTEGER DEFAULT 0)";
//            statement = conn.createStatement();
//            statement.execute(sql);
//            System.out.println("Table PlayerAndGame created");
//
//            //Add Composite primary key
//            String constraint = "ALTER TABLE player_and_game ADD CONSTRAINT gameID#_playerID#_pk" +
//                    " PRIMARY KEY (game_id, player_id)";
//            statement = conn.createStatement();
//            statement.execute(constraint);
//
//            //Add other constraints
//            String constraints = "ALTER TABLE player_and_game MODIFY (game_id CONSTRAINT gameID_nn NOT NULL," +
//                    "player_id CONSTRAINT playerID_nn NOT NULL, playing_date CONSTRAINT playingDate_nn NOT NULL)";
//            statement = conn.createStatement();
//            statement.execute(constraints);
//        }
//
//    }

