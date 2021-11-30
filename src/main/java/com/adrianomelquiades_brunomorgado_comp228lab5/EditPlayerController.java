package com.adrianomelquiades_brunomorgado_comp228lab5;

import javafx.application.Platform;
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


public class EditPlayerController implements Initializable{

    @FXML
    private Label idLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField provinceTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button confirmButtonOnAction;
    @FXML
    private Button cancelButtonOnAction;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populatePlayerFields();

    }

    //Populate fields with data from the Logged in Player
    public void populatePlayerFields(){

            idLabel.setText(Integer.toString(LoginController.player.getId()));
            firstNameTextField.setText(LoginController.player.getFirst_name());
            lastNameTextField.setText(LoginController.player.getLast_name());
            addressTextField.setText(LoginController.player.getAddress());
            postalCodeTextField.setText(LoginController.player.getPostalCode());
            provinceTextField.setText(LoginController.player.getProvince());
            phoneTextField.setText(Long.toString(LoginController.player.getPhoneNumber()));

    }

    //Confirm changes to user
    public void confirmButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        //Verify if all fields are completed
        if (idLabel.getText() != null && firstNameTextField.getText() != null && lastNameTextField.getText() != null
                && addressTextField.getText() != null && postalCodeTextField.getText() != null
                && provinceTextField.getText() != null && phoneTextField.getText() != null) {

            updatePlayer();

            Stage stage = (Stage) confirmButtonOnAction.getScene().getWindow();
            stage.close();

            LoginController.createDisplayGames();

        } else {
            messageLabel.setText("Please fill in all values.");
        }

    }

    //Cancel the Edit action
    public void cancelButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) cancelButtonOnAction.getScene().getWindow();
        stage.close();

        LoginController.createDisplayGames();

    }

    public void updatePlayer() throws SQLException, ClassNotFoundException {

        //Get Values from label and TextFields
        int newID = Integer.parseInt(idLabel.getText());
        int id = LoginController.player.getId();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String province = provinceTextField.getText();
        long phone = Long.parseLong(phoneTextField.getText());


        try {
            //Update Player's data into the table
            DBUtil.updateDataIntoPlayer(newID, firstName, lastName, address, postalCode, province, phone, id);
            messageLabel.setText("User has been updated!");

            //Update the Logged in Player's data
            updateLoginPlayerData();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }

    }

    //Update the Logged in Player
    public void updateLoginPlayerData() throws SQLException, ClassNotFoundException {

        Player newPlayer = null;

        String getPlayer = "SELECT * FROM player WHERE player_id ='" + Integer.parseInt(idLabel.getText()) + "'";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsPlayer = DBUtil.dbExecuteQuery(getPlayer);

            newPlayer = DBUtil.getPlayerFromResultSet(rsPlayer);

            LoginController.player = newPlayer;


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("While searching a player an error occurred: " + e);
            //Return exception
            throw e;
        }

        if (DBUtil.statement != null) {
            //close statement
            DBUtil.statement.close();
            System.out.println("Statement closed");
        }


    }



}
