/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
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
    
    
    public static void main(String[] args) {
        // TODO code application logic here
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
    
    public static boolean login(String password){
        //to be called by GUI before enabling any controls
        //add entered password to the HTTP request headers to authenticate at server
        Map<String, Object> reqContext = ((BindingProvider)port).getRequestContext();
        //reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, service.getWSDLDocumentLocation());
        String temp = password;
        Map<String, List<String>> header = new HashMap<>();
        header.put("Password", Collections.singletonList(temp));
        reqContext.put(MessageContext.HTTP_REQUEST_HEADERS, header);
        
        return port.login();
    }
    
    public static void startGUI(){
        GUI = new TechSuppGUI();
        GUI.setVisible(true);
        refresh();
        loginForm.setVisible(false);
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
    
    public static void callDevice(int index){
        if(listOnlineOnly){
            try{
            //start new call
            call = new TechCall(camList.get(index).getIPAddress());
            }
            catch(LineUnavailableException e){
                JOptionPane.showMessageDialog(null,"Error with Audio: " + e,"Alert",JOptionPane.WARNING_MESSAGE);
            }
            catch(SocketException e){
                JOptionPane.showMessageDialog(null,"Error establishing connection: " + e,"Alert",JOptionPane.WARNING_MESSAGE);
            }
            call.startCall();
            GUI.setCaller(camList.get(index).getLocation(), camList.get(index).getIPAddress());
        }
        else{
            JOptionPane.showMessageDialog(null,"Camera Offline","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void endCall(){
        //Validate in GUI to ensure this cannot be called without a preceding call to callDevice()
        call.stopCall();        
    }
    
    public static void informCallTimedOut(){
        GUI.disableCallInterface();
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
    
    public static void updateLocationPanel(int index){
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
    
    public static void addIssueToDB(String issue){
        String location = camList.get(GUI.getSelectedLocationIndex()).getLocation();
        try{
            LocationDBManager.addIssue(location, issue);
            updateLocationPanel(GUI.getSelectedLocationIndex());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Database Error " + e,"Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void addEquipmentToDB(String equip){
        String location = camList.get(GUI.getSelectedLocationIndex()).getLocation();
        try{
            LocationDBManager.addEquipment(location, equip);
            updateLocationPanel(GUI.getSelectedLocationIndex());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Database Error " + e,"Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void removeIssueFromDB(String issue){
        try{
            LocationDBManager.removeIssue(issue, camList.get(GUI.getSelectedLocationIndex()).getLocation());
            updateLocationPanel(GUI.getSelectedLocationIndex());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Database Error " + e,"Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void removeEquipmentFromDB(String equip){
        try{
            LocationDBManager.removeEquipment(equip, camList.get(GUI.getSelectedLocationIndex()).getLocation());
            updateLocationPanel(GUI.getSelectedLocationIndex());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Database Error " + e,"Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void manualRetirePi(int index){
        port.retire(camList.get(index).getLocation());
        refresh();
    }
    
    //deprecated as it causes list to be repainted in GUI, which causes current selection to be reset
    /*
    public static class Refresher implements Runnable{
        
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
    */
}
