package com.adrianomelquiades_brunomorgado_comp228lab5;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;



    public class RegisterController implements Initializable {

        @FXML
        private ImageView centennialLogoImageView;
        @FXML
        private Button closeButton;
        @FXML
        private Button registerButton;
        @FXML
        private Label registrationMessageLabel;
        @FXML
        private Label failedRegistrationMessageLabel;
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
        private TextField idTextField;


        //Global variables
        public static String playerFirstName = "";

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            File centennialFile = new File("Images/centennial.jpg");
            Image centennialImage = new Image(centennialFile.toURI().toString());
            centennialLogoImageView.setImage(centennialImage);

        }

        //Set action event to the register Button
        public void registerButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
            if (idTextField.getText() != null && firstNameTextField.getText() != null && lastNameTextField.getText() != null
                    && addressTextField.getText() != null && postalCodeTextField.getText() != null
                    && provinceTextField.getText() != null && phoneTextField.getText() != null) {

                registerUser();


            } else {
                registrationMessageLabel.setText("Please fill in all values.");
            }

        }

        //Cancel action and close view
        public void closeButtonOnAction(ActionEvent event) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
//            Platform.exit();
        }

        //Method to Register Users
        public void registerUser() throws SQLException, ClassNotFoundException {

            //Select all TextFields values
            int id = Integer.parseInt(idTextField.getText());
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String province = provinceTextField.getText();
            long phone = Long.parseLong(phoneTextField.getText());

            playerFirstName = firstName;

            //Verify if ID already exists in the Database. In case of negative response, register user.
            try {

                String verifyLogin = "SELECT * FROM player WHERE player_id ='" + Integer.parseInt(idTextField.getText()) + "'";
                //Get ResultSet from dbExecuteQuery method
                ResultSet rsPlayer = DBUtil.dbExecuteQuery(verifyLogin);

                LoginController.player = DBUtil.getPlayerFromResultSet(rsPlayer);

                if(LoginController.player == null){
                    DBUtil.insertDataIntoPlayer(id, firstName, lastName, address, postalCode, province, phone);
                    registrationMessageLabel.setText("User has been registered successfully!");

                    Stage stage = (Stage) registerButton.getScene().getWindow();
                    stage.close();

                }else{
                    failedRegistrationMessageLabel.setText("Player ID #" + LoginController.player.getId() + " Already exists.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }


            try {

                //Execute SELECT statement to confirm that the new user has been created. In case positive, perform login
                String verifyLogin = "SELECT * FROM player WHERE first_name ='" + firstNameTextField.getText() + "' AND last_name ='" + lastNameTextField.getText() + "'"
                        + " AND player_id ='" + Integer.parseInt(idTextField.getText()) + "'";

                //Get ResultSet from dbExecuteQuery method
                ResultSet rsPlayer = DBUtil.dbExecuteQuery(verifyLogin);

                LoginController.player = DBUtil.getPlayerFromResultSet(rsPlayer);

                if (LoginController.player != null) {
                    if (firstNameTextField.getText().equals(LoginController.player.getFirst_name())
                            && lastNameTextField.getText().equals(LoginController.player.getLast_name()) &&
                            Integer.parseInt(idTextField.getText()) == LoginController.player.getId()) {

                        LoginController.createDisplayGames();
                        System.out.println(LoginController.player.toString());

                    }
                } else {
                    registrationMessageLabel.setText("Please try again.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }


        }

    }
