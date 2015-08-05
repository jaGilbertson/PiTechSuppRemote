/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPITechServer;

import java.sql.*;
/**
 *
 * @author Jamie Gilbertson
 */
public class DBManager {
    // JDBC driver name and database URL
    static final String JDBCDriver = "org.apache.derby.jdbc.EmbeddedDriver";
    static final String DBURL = "jdbc:derby://localhost:1527/RegisteredRPIDB";

    //DB username and password
    static final String username = "root";
    static final String password = "admin";
    
    public static void registerPi( int ID, String location){
        Connection connection = null;
        Statement sqlStmt = null;
        try{
            //try to add the new pi into the DB
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement();
            String sql = "INSERT INTO REGISTEREDPIS (ID, LOCATION) " + " VALUES ( '" + ID + "', '" + location + "')";
            sqlStmt.executeUpdate(sql);
        } 
        catch (SQLException se) {
            se.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public static void retirePi(int ID) {
        Connection connection = null;
        Statement sqlStmt = null;
        try {
            //try to remove the pi from the DB (existence of entry should have already been checked)
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement();
            String sql = "DELETE FROM REGISTEREDPIS WHERE ID=" + ID;
            sqlStmt.executeUpdate(sql);
        } 
        catch (SQLException se) {
            se.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public static void retirePi(String location) {
        Connection connection = null;
        Statement sqlStmt = null;
        try {
            //try to remove the pi from the DB (existence of entry should have already been checked)
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement();
            String sql = "DELETE FROM REGISTEREDPIS WHERE LOCATION=" + location;
            sqlStmt.executeUpdate(sql);
        } 
        catch (SQLException se) {
            se.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public static String getPi(String location) {
        String returnString = "";
        Connection connection = null;
        Statement sqlStmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement();
            String sql = "SELECT 1 FROM REGISTEREDPIS WHERE LOCATION='" + location + "'";
            ResultSet dbResult = sqlStmt.executeQuery(sql);
            //check if the ResultSet has any rows and create return string if it does
            if(dbResult.isBeforeFirst()){
                returnString = dbResult.getString(1) + ":" + dbResult.getString(2);
            }
            dbResult.close();
        } 
        catch (SQLException se) {
            se.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return returnString;
    }
    
    public static String getPi(int ID) {
        String returnString = "";
        Connection connection = null;
        Statement sqlStmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement();
            String sql = "SELECT 1 FROM REGISTEREDPIS WHERE ID='" + ID + "'";
            ResultSet dbResult = sqlStmt.executeQuery(sql);
            //check if the ResultSet has any rows and create return string if it does
            if(dbResult.isBeforeFirst()){
                returnString = dbResult.getString(1) + ":" + dbResult.getString(2);
            }
            dbResult.close();
        } 
        catch (SQLException se) {
            se.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return returnString;
    }
    
    public static String getAll(){
        String returnString = "";
        Connection connection = null;
        Statement sqlStmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement();
            String sql = "SELECT * FROM REGISTEREDPIS";
            ResultSet dbResult = sqlStmt.executeQuery(sql);
            //check if the ResultSet has any rows and create return string if it does
            if(dbResult.isBeforeFirst()){
                returnString = dbResult.getString(1) + ":" + dbResult.getString(2);
            }
            while(dbResult.next() != false){
                returnString = returnString + ";" + dbResult.getString(1) + ":" + dbResult.getString(2);
            }
            dbResult.close();
        } 
        catch (SQLException se) {
            se.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try{
                if(connection != null) {
                    connection.close();
                }
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return returnString;
    }
}
