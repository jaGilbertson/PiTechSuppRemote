/*
 * Copyright (C) 2015 Mecha
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
package rpitechsuppcamviewer;

import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Jamie Gilbertson
 */
public class LocationDBManager {
    // JDBC driver name and database URL
    static final String JDBCDriver = "org.apache.derby.jdbc.EmbeddedDriver";
    static final String DBURL = "jdbc:derby://localhost:1527/RemoteLocationDB";
    
    //DB username and password
    static final String username = "root";
    static final String password = "admin";
    
    public static void addIssue(String location, String issue) throws Exception{
        Connection connection = null;
        Statement sqlStmt = null;

        //try to add the issue into the DB
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "INSERT INTO ISSUES (LOCATION, ISSUE) " + " VALUES ( '" + location + "', '" + issue + "')";
        sqlStmt.executeUpdate(sql);
        //close connection
        connection.close();
    }
    
    public static void addEquipment(String location, String equip)throws Exception{
        Connection connection = null;
        Statement sqlStmt = null;

        //try to add the equipment into the DB
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "INSERT INTO EQUIPMENT (LOCATION, EQUIPMENT) " + " VALUES ( '" + location + "', '" + equip + "')";
        sqlStmt.executeUpdate(sql);
        //close connection
        connection.close();
    }
    
    public static void removeIssue(String issue, String location) throws Exception{
        //used to remove an issue from the database
        Connection connection = null;
        Statement sqlStmt = null;

        //try to remove the entry from the DB
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "DELETE FROM ISSUES WHERE ISSUE='" + issue + "' AND LOCATION='" + location + "'";
        sqlStmt.executeUpdate(sql);
        //close connection
        connection.close();
    }
    
    public static void removeEquipment(String equip, String location) throws Exception{
        //used to remove a piece of a equipment from the database
        Connection connection = null;
        Statement sqlStmt = null;

        //try to remove the entry from the DB
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "DELETE FROM EQUIPMENT WHERE EQUIPMENT='" + equip + "' AND LOCATION='" + location + "'";
        sqlStmt.executeUpdate(sql);
        //close connection
        connection.close();
    }

    public static ArrayList<String> getIssues(String location) throws Exception {
        //used to return list of all previous issues at this location
        Connection connection = null;
        Statement sqlStmt = null;
        ArrayList<String> issues = new ArrayList<>();

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "SELECT * FROM ISSUES WHERE LOCATION='" + location + "'";
        ResultSet dbResult = sqlStmt.executeQuery(sql);
        //check if the ResultSet has any rows and create return string if it does
        if(dbResult.isBeforeFirst()){
            while(dbResult.next() != false){
                issues.add(dbResult.getString(2));
            }
        }
        //close the connection
        connection.close();
        return issues;
    }
    
    public static ArrayList<String> getEquipment(String location) throws Exception {
        //used to return list of all equipment registered at this location
        Connection connection = null;
        Statement sqlStmt = null;
        ArrayList<String> equipment = new ArrayList<>();

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        connection = DriverManager.getConnection(DBURL, username, password);
        sqlStmt = connection.createStatement();
        String sql = "SELECT * FROM EQUIPMENT WHERE LOCATION='" + location + "'";
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
