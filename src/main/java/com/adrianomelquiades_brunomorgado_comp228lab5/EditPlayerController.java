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
    private TextField idTextField;
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

    public void populatePlayerFields(){

//        if(LoginController.player != null){
            idTextField.setText(Integer.toString(LoginController.player.getId()));
            firstNameTextField.setText(LoginController.player.getFirst_name());
            lastNameTextField.setText(LoginController.player.getLast_name());
            addressTextField.setText(LoginController.player.getAddress());
            postalCodeTextField.setText(LoginController.player.getPostalCode());
            provinceTextField.setText(LoginController.player.getProvince());
            phoneTextField.setText(Long.toString(LoginController.player.getPhoneNumber()));
//        }else{
//            idTextField.setText(Integer.toString(LoginController.player.getId()));
//            firstNameTextField.setText(LoginController.player.getFirst_name());
//            lastNameTextField.setText(LoginController.player.getLast_name());
//            addressTextField.setText(LoginController.player.getAddress());
//            postalCodeTextField.setText(LoginController.player.getPostalCode());
//            provinceTextField.setText(LoginController.player.getProvince());
//            phoneTextField.setText(Long.toString(LoginController.player.getPhoneNumber()));
//        }

    }

    public void confirmButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (idTextField.getText() != null && firstNameTextField.getText() != null && lastNameTextField.getText() != null
                && addressTextField.getText() != null && postalCodeTextField.getText() != null
                && provinceTextField.getText() != null && phoneTextField.getText() != null) {

            updateUser();

            Stage stage = (Stage) confirmButtonOnAction.getScene().getWindow();
            stage.close();

            LoginController.createDisplayGames();

        } else {
            messageLabel.setText("Please fill in all values.");
        }

    }

    public void cancelButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) cancelButtonOnAction.getScene().getWindow();
        stage.close();

        LoginController.createDisplayGames();

    }

    public void updateUser() throws SQLException, ClassNotFoundException {


        int newID = Integer.parseInt(idTextField.getText());
        int id = LoginController.player.getId();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String province = provinceTextField.getText();
        long phone = Long.parseLong(phoneTextField.getText());


        try {

            DBUtil.updateDataIntoPlayer(newID, firstName, lastName, address, postalCode, province, phone, id);
            messageLabel.setText("User has been updated!");
//                LoginController.createDisplayGames();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }

    }



}
