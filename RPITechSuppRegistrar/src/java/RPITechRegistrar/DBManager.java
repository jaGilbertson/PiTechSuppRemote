/*
 * Copyright (C) 2015 Jamie Gilbertson
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package RPITechRegistrar;

import java.sql.*;
/**
 * Class to manage the database of registered devices
 * @author Jamie Gilbertson
 */
public class DBManager {
    // JDBC driver name and database URL
    static final String JDBCDriver = "org.apache.derby.jdbc.EmbeddedDriver";
    static final String DBURL = "jdbc:derby://localhost:1527/RegisteredRPIDB";

    //DB username and password
    static final String username = "root";
    static final String password = "admin";
    
    /** 
     * Method to register a new device. Duplicate entries should already be checked before using
     * this method to add a unique device.
     * @param ID the ID of the device to be added
     * @param location the Location of the device to be added
     */
    public static void registerPi(int ID, String location){
        Connection connection = null;
        Statement sqlStmt = null;
        String locString = location.replace("'", "''");
        try{
            //try to add the new pi into the DB
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement();
            String sql = "INSERT INTO REGISTEREDPIS (ID, LOCATION) " + " VALUES ( " + ID + ", '" + locString + "')";
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
    
    /**
     * Method to search through the database and return an ID integer 1 value higher than the
     * current highest ID in the list.
     * @return returns a valid new ID
     */
    public static int getNewID(){
        //Function to search through database to find the highest ID number and return an ID 1 higher than it, returns 0 if there is an error
        Connection connection = null;
        Statement sqlStmt = null;
        int freeID = 1;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM REGISTEREDPIS";
            ResultSet dbResult = sqlStmt.executeQuery(sql);
            //check if the ResultSet has any rows and create return string if it does
            if(dbResult.isBeforeFirst()){
                while(dbResult.next() != false){
                    if( dbResult.getInt(1) > freeID){
                        freeID = dbResult.getInt(1);
                    }
                }
                return freeID + 1;
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
        return 0;
    }
    
    /**
     * Method to retire an existing device. Existence of this device should be checked before using this method.
     * @param ID the ID of the device to retire
     */
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
    
    /**
     * Method to retire an existing device. Existence of this device should be checked before using this method.
     * @param location the Location of the device to retire
     */
    public static void retirePi(String location) {
        Connection connection = null;
        Statement sqlStmt = null;
        String locString = location.replace("'", "''");
        try {
            //try to remove the pi from the DB (existence of entry should have already been checked)
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement();
            String sql = "DELETE FROM REGISTEREDPIS WHERE LOCATION='" + locString + "'";
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
    
    /**
     * Method to get the details of a device at a specified location
     * @param location the location of the device whose details should be returned
     * @return a string containing the ID and Location of the device separated by a '%' character
     */
    public static String getPi(String location) {
        String returnString = "";
        Connection connection = null;
        Statement sqlStmt = null;
        String locString = location.replace("'", "''");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT ID,LOCATION FROM REGISTEREDPIS WHERE LOCATION='" + locString + "'";
            ResultSet dbResult = sqlStmt.executeQuery(sql);
            //check if the ResultSet has any rows and create return string if it does
            if(dbResult.isBeforeFirst()){
                dbResult.next();
                returnString = dbResult.getString(1) + "%" + dbResult.getString(2);
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
    
    /**
     * Method to get the details of a device with a specified ID
     * @param ID the location of the device whose details should be returned
     * @return a string containing the ID and Location of the device separated by a '%' character
     */
    public static String getPi(int ID) {
        String returnString = "";
        Connection connection = null;
        Statement sqlStmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT ID,LOCATION FROM REGISTEREDPIS WHERE ID=" + ID + "";
            ResultSet dbResult = sqlStmt.executeQuery(sql);
            //check if the ResultSet has any rows and create return string if it does
            if(dbResult.isBeforeFirst()){
                dbResult.next();
                returnString = dbResult.getString(1) + "%" + dbResult.getString(2);
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
    
    /**
     * Method to get the entire list of all registered devices
     * @return a String containing the entire list of devices, where each device is separated by
     * a ';' character and each device's details contained the ID and Location separated by a
     * '%' character
     */
    public static String getAll(){
        String returnString = "";
        Connection connection = null;
        Statement sqlStmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(DBURL, username, password);
            sqlStmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM REGISTEREDPIS";
            ResultSet dbResult = sqlStmt.executeQuery(sql);
            //check if the ResultSet has any rows and create return string if it does
            if(dbResult.isBeforeFirst()){
                while(dbResult.next() != false){
                    returnString = returnString + dbResult.getString(1) + "%" + dbResult.getString(2) + ";";
                }
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
