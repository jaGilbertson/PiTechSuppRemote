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

/**
 * Class to act as an object representation of a physical remote camera device
 * @author Jamie Gilbertson
 */
import java.util.Date;

public class RPICam {
    //If more members are added then RPICam constructor that accepts string and toString() need to be updated
    private int ID;
    private String location;
    private String IPAddress;
    private Date lastPing;
    private int timeoutParam;

    /**
     * Constructor to create new instance of RPICam for an online device
     * @param ID the ID of the device
     * @param location the Location of the device
     * @param IPAddress the IPAddress of the device
     * @param timeout the time (in milliseconds) that can pass since last ping
     * before the device is considered to have timed out
     */
    public RPICam(int ID, String location, String IPAddress, int timeout) {
        this.ID = ID;
        this.location = location;
        this.IPAddress = IPAddress;
        this.lastPing = new Date();
        this.timeoutParam = timeout;
    }
    
    /**
     * Constructor to create new instance of RPICam without a defined IP Address,
     * usually for an offline device
     * @param ID the ID of the device
     * @param location the Location of the device
     * @param timeout the time (in milliseconds) that can pass since last ping
     * before the device is considered to have timed out
     */
    public RPICam(int ID, String location, int timeout) {
        this.ID = ID;
        this.location = location;
        this.IPAddress = "";
        this.lastPing = new Date();
        this.timeoutParam = timeout;
    }
    
    /**
     * Constructor to create new instance of RPICam where the ID and Location are
     * both contained within a single String and separated by a '%' character
     * @param constructString the String to parse containing the ID and Location
     * @param timeout the time (in milliseconds) that can pass since last ping
     * before the device is considered to have timed out
     * @param newDate the time of the most recent ping from the device
     * @param address the IP Address of the device
     */
    public RPICam(String constructString, int timeout, Date newDate, String address){
        //to be used to create RPICam instances from DB entries when receiving ping
        String[] components = constructString.split("%");
        this.ID = Integer.parseInt(components[0]);
        this.location = components[1];
        this.timeoutParam = timeout;
        this.lastPing = newDate;
        this.IPAddress = address;
    }
    
    /**
     * Constructor to create a new instance of RPICam using a String that contains
     * the ID, the Location, and potentially the IPAddress if the device is online,
     * all separated by a '%' symbol.
     * Also takes a code to indicate whether the device is online (and therefore if 
     * the constructString contains an IPAddress parameter).
     * @param constructString the String that contains details to parse
     * @param code integer, 1 indicates online device that has an IP Address, 0 indicated offline with no IP Adress
     */
    public RPICam(String constructString, int code){
        //to be used client-side. Code = 1 indicates online pi with IP address, Code = 0 indicates offline pi with no IP address
        if(code == 1){
        String[] components = constructString.split("%");
        ID = Integer.parseInt(components[0]);
        location = components[1];
        IPAddress = components[2];
        }
        if(code == 0){
            String[] components = constructString.split("%");
            ID = Integer.parseInt(components[0]);
            location = components[1];
            IPAddress = "";
        }
    }
    
    /**
     * Method to get the ID of the device
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Method to set the ID of the device
     * @param ID the ID to be set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Method to get the location of the device
     * @return the Location of the device
     */
    public String getLocation() {
        return location;
    }

    /** 
     * Method to set the location of the device
     * @param location the Location to be set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Method to get the IP Address of the device
     * @return the IP Address of the device
     */
    public String getIPAddress() {
        return IPAddress;
    }

    /**
     * Method to set the IP Address of the device 
     * @param IPAddress the IP Address to be set
     */
    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    } 

    /**
     * Method to get the time of the most recent ping from the device
     * @return the time of the most recent ping from the device
     */
    public Date getLastPing() {
        return lastPing;
    }

    /**
     * Method to set the time of the most recent ping from the device
     * @param lastPing the time to be set
     */
    public void setLastPing(Date lastPing) {
        this.lastPing = lastPing;
    }

    /**
     * Method to get the Timeout parameter set for this device
     * @return the timeout parameter in milliseconds
     */
    public int getTimeoutParam() {
        return timeoutParam;
    }

    /**
     * Method to set the Timeout parameter for this device
     * @param timeoutParam the timeout desired in milliseconds
     */
    public void setTimeoutParam(int timeoutParam) {
        this.timeoutParam = timeoutParam;
    }
    
    /**
     * Method to convert the RPICam object into a String representation
     * @return a String containing ID, Location, and IP Address separated by a '%' character
     */
    public String toString(){
        //IF THIS METHOD IS CHANGED THEN ALSO CHANGE RPICam CONSTRUCTOR THAT ACCEPTS 1 STRING
        //NOT to be called on offline RPIs
        return ID + "%" + location + "%" + IPAddress;
    }
    
    /**
     * Method to convert the RPICam object into a String representation that is
     * to be displayed on a UI
     * @return a String containing the Location and IP Address separated by " - "
     */
    public String toDisplayString(){
        //creates display string for GUI
        return location + " - " + IPAddress;
    }
    
    /**
     * Method to check if the device has timed out
     * @return true if the device has NOT timed out, false if the device HAS timed out
     */
    public boolean isPingWithinParam(){
        //checks if the most recent ping was within the period of timeoutParm in seconds of when this method is called
        Date tempDate = new Date();
        if(tempDate.getTime() - lastPing.getTime() <= timeoutParam*1000){
            return true;
        }
        else{
            return false;
        }    
    }
}
