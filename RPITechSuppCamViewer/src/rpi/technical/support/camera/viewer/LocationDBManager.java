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
package rpi.technical.support.camera.viewer;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class to manage the database of locations and the equipment/issues
 * specific to each location
 * @author Jamie Gilbertson
 */
public class LocationDBManager {
    // JDBC driver name and database URL
    static final String JDBCDriver = "org.apache.derby.jdbc.EmbeddedDriver";
    static final String DBURL = "jdbc:derby://localhost:1527/RemoteLocationDB";
    
    //DB username and password
    static final String username = "root";
    static final String password = "admin";
    
    /**
     * Method to add a new issue to the database
     * @param location the location that the issue is relevant to
     * @param issue the issue to be entered
     * @throws Exception 
     */
    public static void addIssue(String location, String issue) throws Exception{
        Connection connection = null;
        Statement sqlStmt = null;
        //sanitize input
        String locString = location.replace("'", "''");
        String issueString = issue.replace("'", "''");
        //try to add the issue into the DB
        Class.forName(JDBCDriver);
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "INSERT INTO ISSUES (LOCATION, ISSUE) " + " VALUES ( '" + locString + "', '" + issueString + "')";
        sqlStmt.executeUpdate(sql);
        //close connection
        connection.close();
    }
    
    /**
     * Method to add a new piece of equipment to the database
     * @param location the location the equipment is located at
     * @param equip the name of the equipment to be entered
     * @throws Exception 
     */
    public static void addEquipment(String location, String equip)throws Exception{
        Connection connection = null;
        Statement sqlStmt = null;
        //sanitize input
        String locString = location.replace("'", "''");
        String equipString = equip.replace("'", "''");
        //try to add the equipment into the DB
        Class.forName(JDBCDriver);
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "INSERT INTO EQUIPMENT (LOCATION, EQUIPMENT) " + " VALUES ( '" + locString + "', '" + equipString + "')";
        sqlStmt.executeUpdate(sql);
        //close connection
        connection.close();
    }
    
    /**
     * Method to remove an issue from a specific location
     * @param issue the issue to be remove
     * @param location the location to remove the issue from
     * @throws Exception 
     */
    public static void removeIssue(String issue, String location) throws Exception{
        //used to remove an issue from the database
        Connection connection = null;
        Statement sqlStmt = null;
        //sanitize input
        String locString = location.replace("'", "''");
        String issueString = issue.replace("'", "''");
        //try to remove the entry from the DB
        Class.forName(JDBCDriver);
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "DELETE FROM ISSUES WHERE ISSUE='" + issueString + "' AND LOCATION='" + locString + "'";
        sqlStmt.executeUpdate(sql);
        //close connection
        connection.close();
    }
    
    /**
     * Method to remove a piece of equipment from a specific location
     * @param equip the equipment to be removed
     * @param location the location to remove the issue from
     * @throws Exception 
     */
    public static void removeEquipment(String equip, String location) throws Exception{
        //used to remove a piece of a equipment from the database
        Connection connection = null;
        Statement sqlStmt = null;
        //sanitize input
        String equipString = equip.replace("'", "''");
        String locString = location.replace("'", "''");
        //try to remove the entry from the DB
        Class.forName(JDBCDriver);
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "DELETE FROM EQUIPMENT WHERE EQUIPMENT='" + equipString + "' AND LOCATION='" + locString + "'";
        sqlStmt.executeUpdate(sql);
        //close connection
        connection.close();
    }

    /**
     * Method that returns the list of all issues at a specified location
     * @param location the location to retrieve issues for
     * @return ArrayList of Strings of all the issues at a location
     * @throws Exception 
     */
    public static ArrayList<String> getIssues(String location) throws Exception {
        //used to return list of all previous issues at this location
        Connection connection = null;
        Statement sqlStmt = null;
        ArrayList<String> issues = new ArrayList<>();
        //sanitize input
        String locString = location.replace("'", "''");
        Class.forName(JDBCDriver);
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM ISSUES WHERE LOCATION='" + locString + "'";
        ResultSet dbResult = sqlStmt.executeQuery(sql);
        //check if the ResultSet has any rows and create return list if it does
        if(dbResult.isBeforeFirst()){
            while(dbResult.next() != false){
                issues.add(dbResult.getString(2));
            }
        }
        //close the connection
        connection.close();
        return issues;
    }
    
    /**
     * Method that returns the list of all equipment at a specified location
     * @param location the location to retrieve equipment for
     * @return ArrayList of Strings of all the equipment at a location
     * @throws Exception 
     */
    public static ArrayList<String> getEquipment(String location) throws Exception {
        //used to return list of all equipment registered at this location
        Connection connection = null;
        Statement sqlStmt = null;
        ArrayList<String> equipment = new ArrayList<>();
        //sanitize input
        String locString = location.replace("'", "''");
        Class.forName(JDBCDriver);
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM EQUIPMENT WHERE LOCATION='" + locString + "'";
        ResultSet dbResult = sqlStmt.executeQuery(sql);
        //check if the ResultSet has any rows and create return list if it does
        if(dbResult.isBeforeFirst()){
            while(dbResult.next() != false){
                equipment.add(dbResult.getString(2));
            }
        }
        //close connection
        connection.close();
        return equipment;
    }
}
