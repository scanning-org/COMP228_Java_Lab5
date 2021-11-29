package com.adrianomelquiades_brunomorgado_comp228lab5;

import com.sun.rowset.CachedRowSetImpl;
import javafx.fxml.FXML;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;


public class DBUtil {

    //Global variables
    public static Connection conn = null;
    public static Statement statement = null;

    //Establish a connection to the database
    public static void connectToDatabase() throws SQLException {

        String dbURL = "jdbc:oracle:thin:@localhost:1521:orcl";
        String username = "BRUNOMORGADO";
        String password = "password";

        //Connect to database
        try{
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Database is connected");
            statement = conn.createStatement();
        }catch(SQLException e){
            System.out.println("Database is NOT connected");
            System.out.println(e.getMessage());
        }

    }

    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database is Closed");
            }
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    //Check if table exists
    public static boolean tableExist(Connection conn, String tableName) throws SQLException{
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

    public static void createTableGame() throws SQLException {
        //Open database connection
        connectToDatabase();

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

        if (statement != null) {
            //Close statement
            statement.close();
        }
        //Close connection:
        dbDisconnect();

    }

    public static void createTablePlayer() throws SQLException {
        //Open database connection
        connectToDatabase();

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

        if (statement != null) {
            //Close statement
            statement.close();
        }
        //Close connection:
        dbDisconnect();

    }

    public static void createTablePlayerAndGame() throws SQLException {
        //Open database connection
        connectToDatabase();

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
            String sql = "CREATE TABLE player_and_game(player_game_id integer GENERATED ALWAYS AS(player_id || '' || game_id), game_id integer, player_id integer," +
                    "playing_date DATE, score INTEGER DEFAULT 0, CONSTRAINT player_game_ID_pk PRIMARY KEY (player_game_id))";
            statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("Table PlayerAndGame created");

//            //Add Composite primary key
//            String constraint = "ALTER TABLE player_and_game ADD CONSTRAINT gameID#_playerID#_pk" +
//                    " PRIMARY KEY (game_id, player_id)";
//            statement = conn.createStatement();
//            statement.execute(constraint);

            //Add other constraints
            String constraints = "ALTER TABLE player_and_game MODIFY (game_id CONSTRAINT gameID_nn NOT NULL," +
                    "player_id CONSTRAINT playerID_nn NOT NULL, playing_date CONSTRAINT playingDate_nn NOT NULL)";
            statement = conn.createStatement();
            statement.execute(constraints);
        }

        if (statement != null) {
            //Close statement
            statement.close();
        }
        //Close connection:
        dbDisconnect();

    }

    public static void insertDataIntoPlayer(int id, String fName, String lName, String address, String postalCode,
                                            String province, long phoneNumber) throws SQLException {
        //Open connection
        connectToDatabase();

        try {
            String insertFields = "INSERT INTO player VALUES ('";
            String insertValues = id + "','" + fName + "','" + lName + "','" + address + "','" + postalCode +
                    "','" + province + "','" + phoneNumber + "')";
            String insertToRegister = insertFields + insertValues;
            statement.execute(insertToRegister);
            System.out.println("User Created");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        //Close Connection
        if (statement != null) {
            //Close statement
            statement.close();
        }
        //close connection
        dbDisconnect();
    }

    public static void updateDataIntoPlayer(int newID, String fName, String lName, String address, String postalCode,
                                            String province, long phoneNumber, int id) throws SQLException {
        //Open connection
        connectToDatabase();

        try {
            String insertFields = "Update player SET ";
            String insertValues = "player_id = " +  "'" + newID + "'," + "first_name = " + "'"
                    + fName + "'," + "last_name = " + "'" + lName + "'," + "address = " + "'" + address + "',"
                    + "postal_code = " + "'"+ postalCode + "'," + "province = " + "'" + province + "',"
                    + "phone_number = " + "'" + phoneNumber + "'";
            String where = "WHERE player_id = " + "'" + id + "'";
            String insertToUpdate = insertFields + insertValues + where;
            statement.execute(insertToUpdate);
            System.out.println("User Updated");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        //Close Connection
        if (statement != null) {
            //Close statement
            statement.close();
        }
        //close connection
        dbDisconnect();
    }

    //Insert data into GAME table
    public static void insertDataIntoGame(int game_id, String game_title) throws SQLException{
        //open database connection:
        connectToDatabase();

        try {
            String sql = "INSERT INTO game VALUES " + "(" + game_id + ", " + "'" + game_title + "'" + ")";
            statement.execute(sql);
            System.out.println("Game Inserted");
        } catch (Exception e) {
            System.out.println("Something went wrong. Data was not inserted");
            System.out.println(e.getMessage());
        }
        if (statement != null) {
            //Close statement
            statement.close();
        }
        //close connection:
        dbDisconnect();
    }

    //Insert data into PLAYER_AND_GAME table
    public static void insertDataIntoPlayerAndGame(int game_id, int player_id, String playing_date,  int score) throws SQLException{
        //open database connection:
        connectToDatabase();

        try {
            String sql = "INSERT INTO player_and_game (game_id, player_id, playing_date, score)" +
                    " VALUES " + "(" + game_id + ", " + player_id + ", TO_DATE(" + "'" + playing_date + "', 'yyyy-mm-dd'), " + score + ")";
            statement.execute(sql);
            System.out.println("Player_And_Game Inserted");
        } catch (Exception e) {
            System.out.println("Something went wrong. Data was not inserted");
            System.out.println(e.getMessage());
        }
        if (statement != null) {
            //Close statement
            statement.close();
        }
        //close connection:
        dbDisconnect();
    }

    //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
        try {
            //Connect to DB (Establish Oracle Connection)
            connectToDatabase();
            System.out.println("Select statement: " + queryStmt + "\n");

            //Create statement
            stmt = conn.createStatement();

            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);

            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet

            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return crs;
    }

    //Use ResultSet from DB as parameter and print to the console a player's attributes.
    public static Player getPlayerFromResultSet(ResultSet rs) throws SQLException
    {
        Player player = null;

        if (rs.next()) {
            player = new Player();
            player.setId(rs.getInt("PLAYER_ID"));
            player.setFirst_name(rs.getString("FIRST_NAME"));
            player.setLast_name(rs.getString("LAST_NAME"));
            player.setAddress(rs.getString("ADDRESS"));
            player.setPostalCode(rs.getString("POSTAL_CODE"));
            player.setProvince(rs.getString("PROVINCE"));
            player.setPhoneNumber(rs.getLong("PHONE_NUMBER"));

        }

        return player;
    }




}
