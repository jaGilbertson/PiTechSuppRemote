/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPITechRegistrar;

/**
 *
 * @author Jamie Gilbertson
 */
import java.util.*;

public class RPICam {
    //If more members are added then RPICam constructor that accepts string and toString() need to be updated
    private int ID;
    private String location;
    private String IPAddress;
    private Date lastPing;
    private int timeoutParam;

    public RPICam(int ID, String location, String IPAddress, int timeout) {
        this.ID = ID;
        this.location = location;
        this.IPAddress = IPAddress;
        this.lastPing = new Date();
        this.timeoutParam = timeout;
    }
    
    public RPICam(int ID, String location, int timeout) {
        this.ID = ID;
        this.location = location;
        this.IPAddress = "";
        this.lastPing = new Date();
        this.timeoutParam = timeout;
    }
    
    public RPICam(String constructString, int timeout, Date newDate, String address){
        //to be used to create RPICam instances from DB entries when receiving ping
        String[] components = constructString.split("%");
        this.ID = Integer.parseInt(components[0]);
        this.location = components[1];
        this.timeoutParam = timeout;
        this.lastPing = newDate;
        this.IPAddress = address;
    }
    
    public RPICam(String constructString, int code){
        //to be used client-side (may be removed). Code = 1 indicates online pi with IP address, Code = 0 indicates offline pi with no IP address
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
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    } 

    public Date getLastPing() {
        return lastPing;
    }

    public void setLastPing(Date lastPing) {
        this.lastPing = lastPing;
    }

    public int getTimeoutParam() {
        return timeoutParam;
    }

    public void setTimeoutParam(int timeoutParam) {
        this.timeoutParam = timeoutParam;
    }
    
    public String toString(){
        //IF THIS METHOD IS CHANGED THEN ALSO CHANGE RPICam CONSTRUCTOR THAT ACCEPTS 1 STRING
        //NOT to be called on offline RPIs
        return ID + "%" + location + "%" + IPAddress;
    }
    
    public String toDisplayString(){
        //creates display string for GUI
        return location + " - " + IPAddress;
    }
    
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
