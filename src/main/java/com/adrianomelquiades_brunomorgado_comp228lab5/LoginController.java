package com.adrianomelquiades_brunomorgado_comp228lab5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButtonOnAction;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField playerIDTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button registrationButtonOnAction;

    //Global Variables
    public static Player player = null;
    public CachedRowSetImpl crs = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("Images/Centennial_College.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        //Create Tables if not existent during program's initialization
        try {
            DBUtil.createTablePlayer();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            DBUtil.createTableGame();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            DBUtil.createTablePlayerAndGame();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //Initialize login validation
    public void loginButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (firstNameTextField.getText().isBlank() == false && lastNameTextField.getText().isBlank() == false
                && playerIDTextField.getText().isBlank() == false) {

            validateLogin();


        } else {
            loginMessageLabel.setText("Please enter First Name  and Last Name");
        }
    }


    //Create Player object and validate Login
    public void validateLogin() throws SQLException, ClassNotFoundException {

        String verifyLogin = "SELECT * FROM player WHERE first_name ='" + firstNameTextField.getText() + "' AND last_name ='" + lastNameTextField.getText() + "'"
                + " AND player_id ='" + Integer.parseInt(playerIDTextField.getText()) + "'";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsPlayer = DBUtil.dbExecuteQuery(verifyLogin);

            player = DBUtil.getPlayerFromResultSet(rsPlayer);

            if (player != null) {
                if (firstNameTextField.getText().equals(player.getFirst_name())
                        && lastNameTextField.getText().equals(player.getLast_name())) {

                    createDisplayGames();
                    System.out.println(player.toString());

                }
            } else {
                loginMessageLabel.setText("Player not found. Please Register.");
            }

        } catch (SQLException e) {
            System.out.println("While searching an player an error occurred: " + e);
            //Return exception
            throw e;
        }

        if (DBUtil.statement != null) {
            //close statement
            DBUtil.statement.close();
            System.out.println("Statement closed");
        }
        //close connection:
        DBUtil.dbDisconnect();

    }

    //Get player's first name to display on Games View
    public static String getPlayerFirstName() {

        if (player == null) {
            return "";
        } else {
            return player.getFirst_name();
        }

    }

    //Initialize Registration form
    public void registrationButtonOnAction(ActionEvent event) {

        createPlayerForm();

    }

    //Cancel registration and close view
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    //Build and display Registration form
    public void createPlayerForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(OnlineGames.class.getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //Build and Display Games View
    public static void createDisplayGames() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(OnlineGames.class.getResource("games-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
