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
package rpitechsuppcamviewer;

import CamRegistrar.RPITechSuppRegistrarService;
import CamRegistrar.RPITechSuppRegistrar;

import java.util.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import javax.sound.sampled.LineUnavailableException;
import java.net.SocketException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
/**
 * Class that serves as the main controller for the Camera Viewer client
 * to be used by technician users
 * @author Jamie Gilbertson
 */
public class RPITechSuppCamViewer {

    /**
     * @param args the command line arguments
     */
    private static RPITechSuppRegistrarService service;
    private static RPITechSuppRegistrar port;
    private static TechSuppGUI GUI;
    private static LoginForm loginForm;
    private static ArrayList<RPICam> camList  = new ArrayList<>();
    private static boolean listOnlineOnly = true;
    private static TechCall call;
    //deprecated
    //private static Thread refreshThread;
    
    /**
     * Main method that starts the Webservice object and opens the login form.
     * @param args not used
     */
    public static void main(String[] args) {
        try{
        service = new CamRegistrar.RPITechSuppRegistrarService();
        port = service.getRPITechSuppRegistrarPort();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error connecting to server: " + e, "Registrar Offline", JOptionPane.INFORMATION_MESSAGE);
        }
        loginForm = new LoginForm();
        loginForm.setVisible(true);
    }
    
    /**
     * Method that sends a password passed to it to the server to be confirmed.
     * @param password
     * @return 
     */
    public static boolean login(String password){
        //to be called by GUI before enabling any controls
        //add entered password to the HTTP request headers to authenticate at server
        Map<String, Object> reqContext = ((BindingProvider)port).getRequestContext();
        String temp = password;
        Map<String, List<String>> header = new HashMap<>();
        header.put("Password", Collections.singletonList(temp));
        reqContext.put(MessageContext.HTTP_REQUEST_HEADERS, header);
        
        return port.login();
    }
    
    /**
     * Method to create and start the main GUI
     */
    public static void startGUI(){
        GUI = new TechSuppGUI();
        GUI.setVisible(true);
        refresh();
        loginForm.setVisible(false);
    }
    
    /**
     * Method to refresh the local list of camera devices based on whether the list
     * is set to view only online devices, or to view all registered devices. This 
     * method also refreshes the list view in the main GUI.
     */
    public static void refresh(){
        //call getOnlineList() from web service refresh pi list
        if(listOnlineOnly){
            //get the list of online pis
            String temp = port.getOnlineList();
            if(!temp.equals("")){
                //if the onlineList has entries, split it and send the list to the GUI
                String[] tempArr = temp.split(";");
                String[] tempArr1;
                //clear the local list ready to be populated
                camList.clear();
                //populate local list
                for(int i = 0; i < tempArr.length; i++){
                    if(!tempArr[i].equals("")){
                        camList.add(new RPICam(tempArr[i], 1));
                    }
                }
                //update GUI pi list
                tempArr1 = new String[camList.size()];
                for(int i = 0; i < camList.size(); i++){
                    tempArr1[i] = camList.get(i).toDisplayString();                
                }
                GUI.updatePiList(tempArr1);
                GUI.disableConnect();
            }
            else{
                //if the onlineList has no entries, just give the GUI an array saying there are no active cameras
                String[] tempArr = {"No active cameras"};
                GUI.updatePiList(tempArr);
                GUI.disableConnect();
            }
        }
        else{
            //get the list of all registered pis
            String temp = port.getRegisteredPiList();
            if(!temp.equals("")){
                //if the registeredPiList has entries, split it and send the list to the GUI
                String[] tempArr = temp.split(";");
                String[] tempArr1;
                camList.clear();
                //populate local list
                for(int i = 0; i < tempArr.length; i++){
                    camList.add(new RPICam(tempArr[i], 0));
                }
                //update GUI pi list
                tempArr1 = new String[camList.size()];
                for(int i = 0; i < camList.size(); i++){
                    tempArr1[i] = camList.get(i).getLocation();                
                }
                GUI.updatePiList(tempArr1);
            }
            else{
                //if the onlineList has no entries, just give the GUI an array saying there are no active cameras
                String[] tempArr = {"No registered devices"};
                GUI.updatePiList(tempArr);
            }
        }
    }
    
    /**
     * Method to open an instance of the default browser that points to the camera
     * viewing interface of a device indicated by the index passed to it
     * @param index the list index of the device to view
     */
    public static void openCam(int index){
        //open instance of the default browser to the address of the pi camera requested
        if(listOnlineOnly){
            if(Desktop.isDesktopSupported())
            {
                try{
                    Desktop.getDesktop().browse(new URI("http://" + camList.get(index).getIPAddress()));
                }
                catch(URISyntaxException | IOException e){
                    System.out.println(e);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Camera Offline","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Method to start a call to the device indicated by the index password to it.
     * Starts a new instance of TechCall to handle the call.
     * @param index the list index of the device to call
     * @see TechCall
     */
    public static void callDevice(int index){
        if(listOnlineOnly && index > -1){
            try{
            //start new call
            call = new TechCall(camList.get(index).getIPAddress());
            call.startCall();
            GUI.setCaller(camList.get(index).getLocation(), camList.get(index).getIPAddress());
            }
            catch(LineUnavailableException e){
                JOptionPane.showMessageDialog(null,"Error with Audio: " + e,"Alert",JOptionPane.WARNING_MESSAGE);
            }
            catch(SocketException e){
                JOptionPane.showMessageDialog(null,"Error establishing connection: " + e,"Alert",JOptionPane.WARNING_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Camera Offline","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Method to end a call and disable the interface relating to ongoing calls.
     */
    public static void endCall(){
        //Validate in GUI to ensure this cannot be called without a preceding call to callDevice()
        call.stopCall();        
        GUI.disableCallInterface();
    }
    
    /**
     * Method to be called when an ongoing call has timed out, usually called from TechCall.
     * @see TechCall
     */
    public static void informCallTimedOut(){
        GUI.disableCallInterface();
        call.stopCall();
    }
    
    /**
     * Method used to copy the IP Address of the device specified by the index passed to it
     * and place it into the system's clipboard.
     * @param index the list index of the device whose IP Address should be copied
     */
    public static void copyIP(int index){
        //copy ip address of pi to clipboard, usually for SSH
        StringSelection stringSelection = new StringSelection (camList.get(index).getIPAddress());
        Clipboard board = Toolkit.getDefaultToolkit ().getSystemClipboard ();
        board.setContents (stringSelection, null);   
    }
    
    /**
     * Method used to set whether the local list should contain only online devices, or
     * whether it should contain a list of all registered devices.
     * @param set true if the list should contain only online devices, false to contain 
     * all registered devices
     */
    public static void setListOnlineOnly(boolean set){
        //to be accessed from GUI class
        listOnlineOnly = set;
        refresh();
    }
    
    /**
     * Method to update the Location Panel of the GUI with the information for the device
     * indicated by the index passed to it.
     * @param index the list index of the device to retrieve location information for
     */
    public static void updateLocationPanel(int index){
        //used to update the location specific details of a device when a device is selected in the GUI list
        //method can be called when there are no entries in the list, so make sure there are actually devices in the local list before attempting to access it
        if(camList.size() > 0){
            String location = camList.get(index).getLocation();
            try{
                GUI.updateLocationDetails(LocationDBManager.getIssues(location), LocationDBManager.getEquipment(location), location);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,"Database Error " + e,"Alert",JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Method to add an issue to the location database.
     * @param issue the issue to be added
     */
    public static void addIssueToDB(String issue){
        //used to add a new issue to the database
        String location = camList.get(GUI.getSelectedLocationIndex()).getLocation();
        try{
            LocationDBManager.addIssue(location, issue);
            updateLocationPanel(GUI.getSelectedLocationIndex());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Database Error " + e,"Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Method to add a piece of equipment to the location database.
     * @param equip the equipment to be added
     */
    public static void addEquipmentToDB(String equip){
        //used to add a new piece of equipment to the database
        String location = camList.get(GUI.getSelectedLocationIndex()).getLocation();
        try{
            LocationDBManager.addEquipment(location, equip);
            updateLocationPanel(GUI.getSelectedLocationIndex());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Database Error " + e,"Alert",JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Method to remove an issue from a specific location from the database.
     * @param issue issue to remove
     */
    public static void removeIssueFromDB(String issue){
        //used to remove an issue from the database
        try{
            LocationDBManager.removeIssue(issue, camList.get(GUI.getSelectedLocationIndex()).getLocation());
            updateLocationPanel(GUI.getSelectedLocationIndex());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Database Error " + e,"Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Method to remove a piece of equipment from a specific location from the database.
     * @param equip 
     */
    public static void removeEquipmentFromDB(String equip){
        //used to remove a piece of equipment from the database
        try{
            LocationDBManager.removeEquipment(equip, camList.get(GUI.getSelectedLocationIndex()).getLocation());
            updateLocationPanel(GUI.getSelectedLocationIndex());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Database Error " + e,"Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /** 
     * Method to manually remove a pi from the database managed by the server
     * @param index the list index of the device to be removed
     */
    public static void manualRetirePi(int index){
        //used to retire devices
        port.retire(camList.get(index).getLocation());
        refresh();
    }
    
    /**
     * Class to refresh the list of devices automatically
     * @deprecated in favour of manual refreshing due to clearing device selection upon every refresh
     */
    public static class Refresher implements Runnable{
        
        /**
         * Runs a loop that refreshes the local device list every second
         * @deprecated create new instance of Thread class using
         * this class as constructor argument, then use thread.start
         */
        public void run(){
            while(true){
                refresh();
                try{
                    Thread.sleep(1000);
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
        }

    }
}