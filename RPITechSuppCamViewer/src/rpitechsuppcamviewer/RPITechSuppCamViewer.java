/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpitechsuppcamviewer;

import RPITechSuppCamServer.RPITechSuppServerService;
import RPITechSuppCamServer.RPITechSuppServer;

import java.util.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
/**
 *
 * @author Jamie Gilbertson
 */
public class RPITechSuppCamViewer {

    /**
     * @param args the command line arguments
     */
    private static RPITechSuppServerService service;
    private static RPITechSuppServer port;
    private static TechSuppGUI GUI;
    private static ArrayList<RPICam> camList  = new ArrayList<>();
    private static boolean listOnlineOnly = true;
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        service = new RPITechSuppCamServer.RPITechSuppServerService();
        port = service.getRPITechSuppServerPort();
        GUI = new TechSuppGUI();
        GUI.setVisible(true);
        refresh();
        
    }
    
    public static void refresh(){
        //call getOnlineList() from web service refresh pi list
        if(listOnlineOnly){
            //get the list of online pis
            String temp = port.getOnlineList();
            if(!temp.equals("")){
                //if the onlineList has entries, split it and send the list to the GUI
                String[] tempArr = temp.split(";");
                String[] tempArr1;
                
                camList.clear();
                //populate local list
                for(int i = 0; i < tempArr.length; i++){
                    camList.add(new RPICam(tempArr[i], 1));
                }
                //update GUI pi list
                tempArr1 = new String[camList.size()];
                for(int i = 0; i < camList.size(); i++){
                    tempArr1[i] = camList.get(i).toDisplayString();                
                }
                GUI.updatePiList(tempArr1);
                GUI.enableConnect();
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
                    tempArr1[i] = camList.get(i).toDisplayString();                
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
    
    public static void openCam(int index){
        //open instance of the default browser to the address of the pi camera requested
        if(listOnlineOnly){
            if(Desktop.isDesktopSupported())
            {
                try{
                    Desktop.getDesktop().browse(new URI(camList.get(index).getIPAddress()));
                }
                catch(URISyntaxException | IOException e){}
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Camera Offline","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void copyIP(int index){
        //copy ip address of pi to clipboard, usually for SSH
        StringSelection stringSelection = new StringSelection (camList.get(index).getIPAddress());
        Clipboard board = Toolkit.getDefaultToolkit ().getSystemClipboard ();
        board.setContents (stringSelection, null);   
    }
    
    public static void setListOnlineOnly(boolean set){
        //to be accessed from GUI class
        listOnlineOnly = set;
        refresh();
    }
}
