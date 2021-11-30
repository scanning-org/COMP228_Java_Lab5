package com.adrianomelquiades_brunomorgado_comp228lab5;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button listGamesButtonOnAction;
    @FXML
    private TableView gamesTableView;
    @FXML
    private TableView gamesTitlesTableView;
    @FXML
    private TableColumn gameIDTableColumn;
    @FXML
    private TableColumn gameTitleTableColumn;
    @FXML
    private TableColumn playingDateTableColumn;
    @FXML
    private TableColumn scoreTableColumn;

    public Game game = null;
    public PlayerAndGame pAndGame = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File gameBrandFile = new File("Images/centennial-games.jpg");
        Image gameBrandImage = new Image(gameBrandFile.toURI().toString());
        gameBrandImageView.setImage(gameBrandImage);

        String playerName = LoginController.getPlayerFirstName();

        //Display Logged in Player's name on the screen
        if(!playerName.equals("") && !playerName.equals(null)){
            welcomeUserLabel.setText("Welcome, " + playerName);
            System.out.println("login: " + playerName);
        }else{
            welcomeUserLabel.setText("Welcome, " + RegisterController.playerFirstName);
            System.out.println("Registration: " + RegisterController.playerFirstName);
        }



    }

    //Initialize the Edit player View and close the display View
    public void editPlayerButtonOnAction(ActionEvent event){
        createEditPlayer();

        Stage stage = (Stage) editPlayerButtonOnAction.getScene().getWindow();
        stage.close();
    }

    //Call methods to insert Game and PlayerAndGame in their respective tables
    public void insertGameButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        insertNewGame();
        insertNewPLayerAndGame();

    }

    //List all games registered for the current Player
    public void listGamesButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        onDisplayPlayerInformation();
    }

    //Display Edit Player View
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

    //Insert data into Game Table
    public void insertNewGame() throws SQLException, ClassNotFoundException {


        int gameID = Integer.parseInt(gameIDTextField.getText());
        String gameTitle = gameTitleTextField.getText();

        try {

            DBUtil.insertDataIntoGame(gameID, gameTitle);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    //Insert data into PlayerAndGame Table
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


    //Display Game and PlayerAndGame information in the TableView
    public void onDisplayPlayerInformation() throws SQLException, ClassNotFoundException {

        gamesTitlesTableView.getItems().clear();

        ResultSet rsGame = DBUtil.dbExecuteQuery("SELECT g.g_id, g.game_title FROM\n" +
                "game g, player p, player_and_game pag\n" +
                "WHERE p.player_id = " + LoginController.player.getId() + "\n" +
                "AND p.player_id = pag.player_id \n" +
                "AND pag.game_id = g.g_id");

        ObservableList<Game> games  = FXCollections.observableArrayList();

        while(rsGame.next()) {

            Game game = DBUtil.createGameFromResultSet(rsGame);

            games.add(game);
        }
        gameTitleTableColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("game_title"));

        gamesTitlesTableView.setItems(games);

        //clear things before populating new records
        gamesTableView.getItems().clear();

        ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM player_and_game WHERE player_id = '" + LoginController.player.getId() + "'");


        ObservableList<PlayerAndGame> playersAndGames  = FXCollections.observableArrayList();

        while(rs.next()) {

            PlayerAndGame pAndGame = DBUtil.createPlayerAndGameFromResultSet(rs);

            playersAndGames.add(pAndGame);
        }
        gameIDTableColumn.setCellValueFactory(new PropertyValueFactory<PlayerAndGame, Integer>("game_id"));
        playingDateTableColumn.setCellValueFactory(new PropertyValueFactory<PlayerAndGame, String>("playing_date"));
        scoreTableColumn.setCellValueFactory(new PropertyValueFactory<PlayerAndGame, Integer>("score"));

        gamesTableView.setItems(playersAndGames);


    }

    //Exit the Display Games View
    public void exitButtonOnAction(ActionEvent event){

        Stage stage = (Stage) exitButtonOnAction.getScene().getWindow();
        stage.close();


    }



}




