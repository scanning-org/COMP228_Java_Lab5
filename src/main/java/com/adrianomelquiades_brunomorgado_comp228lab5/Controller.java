package com.adrianomelquiades_brunomorgado_comp228lab5;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import java.sql.*;

public class Controller implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    //Global variables
    Connection conn = null;
    Statement statement = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        //Initialize connection to the database
        try {
            connectToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Create Game table if it does not exist
        try {
            createTableGame();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Create Player Table if it does not exist
        try {
            createTablePlayer();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Create PlayerAndGame Table if it does not exist
        try {
            createTablePlayerAndGame();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Establish a connection to the database
    public void connectToDatabase() throws SQLException{

        String dbURL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
        String username = "COMP214M21_001_P_4";
        String password = "password";


        //Connect to database
        try{
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Database is connected");
        }catch(SQLException e){
            System.out.println("Database is NOT connected");
            System.out.println(e.getMessage());
        }

    }

    public boolean tableExist(Connection conn, String tableName) throws SQLException{
        boolean tExists = false;

        try{
            ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
            while(rs.next()){
                String tName = rs.getString("TABLE_NAME");
                if(tName != null && tName.equals(tableName)){
                    tExists = true;
                    break;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return tExists;
    }

    //Create table

    public void createTableGame() throws SQLException {
       boolean tExists = false;
       try{
          tExists = tableExist(conn, "GAME");
       }catch(SQLException e){
           System.out.println(e.getMessage());
       }

       if(tExists){
           System.out.println("Table already created");
       }
       else{
           //Create table and set the Primary Key
           String sql = "CREATE TABLE game(g_id integer, game_title VARCHAR(50), CONSTRAINT game_gID_pk PRIMARY KEY (g_id))";
           statement = conn.createStatement();
           statement.execute(sql);
           System.out.println("Table Game created");

           //Add constraints
           String constraint = "ALTER TABLE game MODIFY game_title CONSTRAINT game_gametitle_nn NOT NULL";
           statement = conn.createStatement();
           statement.execute(constraint);

       }

    }

    public void createTablePlayer() throws SQLException {
        boolean tExists = false;
        try{
            tExists = tableExist(conn, "PLAYER");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        if(tExists){
            System.out.println("Table already created");
        }
        else{
            String sql = "CREATE TABLE player(player_id integer, first_name VARCHAR(50), last_name VARCHAR(50)," +
                    "address VARCHAR(100), postal_code VARCHAR(7), province VARCHAR(2), phone_number NUMBER(10)," +
                    " CONSTRAINT player_playerID_pk PRIMARY KEY (player_id))";
            statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("Table Player created");

            //Add constraints
            String constraint = "ALTER TABLE player MODIFY (first_name CONSTRAINT player_firstname_nn NOT NULL," +
                    "last_name CONSTRAINT player_lastname_nn NOT NULL, phone_number CONSTRAINT player_phonenumber_nn NOT NULL)";
            statement = conn.createStatement();
            statement.execute(constraint);
        }

    }

    public void createTablePlayerAndGame() throws SQLException {
        boolean tExists = false;
        try{
            tExists = tableExist(conn, "PLAYER_AND_GAME");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        if(tExists){
            System.out.println("Table already created");
        }
        else{
            String sql = "CREATE TABLE player_and_game(player_game_id INTEGER, game_id INTEGER, player_id INTEGER," +
                    "playing_date DATE, score INTEGER DEFAULT 0)";
            statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("Table PlayerAndGame created");

            //Add Composite primary key
            String constraint = "ALTER TABLE player_and_game ADD CONSTRAINT gameID#_playerID#_pk" +
                    " PRIMARY KEY (game_id, player_id)";
            statement = conn.createStatement();
            statement.execute(constraint);

            //Add other constraints
            String constraints = "ALTER TABLE player_and_game MODIFY (game_id CONSTRAINT gameID_nn NOT NULL," +
                    "player_id CONSTRAINT playerID_nn NOT NULL, playing_date CONSTRAINT playingDate_nn NOT NULL)";
            statement = conn.createStatement();
            statement.execute(constraints);
        }

    }

}