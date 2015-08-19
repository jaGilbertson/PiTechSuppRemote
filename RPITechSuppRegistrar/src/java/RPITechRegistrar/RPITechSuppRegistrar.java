/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPITechRegistrar;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.xml.ws.*;
import javax.xml.ws.handler.*;
import javax.servlet.http.*;
import java.util.*;
/**
 *
 * @author Jamie Gilbertson
 */


@WebService
public class RPITechSuppRegistrar{
    private ArrayList<RPICam> onlinePis = new ArrayList<>(); //stores all online devices
    private HashMap<Integer, String> callSwitcher = new HashMap<>(); //stores IP adresses of clients initiating voice calls against ID of device to be called
    private int IDCounter = 1;
    private int timeoutParam = 1; //timeout in seconds
    private WebServiceContext context; //to be used to get IP from ping requests
    
    
    @WebMethod
    public int register(String location){
        //Check if pi is already registered at that location. Return 0 if pi already registered. Return ID if pi successfully registered. IDs start from 1 and will never be 0.
        if(DBManager.getPi(location).equals("")){
            DBManager.registerPi(IDCounter, location);
            IDCounter++;
            return IDCounter - 1;
        }
        else
        return 0;
    }
    
    @WebMethod
    public int retire(String location){
        //Check if pi is registered at that location. If true, remove pi from database and online list. IDs are not recycled. Return 1 if successfullyt removed, 0 if pi doesn't exist.
         if(!DBManager.getPi(location).equals("")){
            DBManager.retirePi(location);
            return 1;
        }
        else
             return 0;
    }
    
    @WebMethod
    public void pingAlive(int ID, String IP){
        //TODO
        //RPIs call this method to ping to say they are online. They should do this as often as the timeout parameter. RPIs are incapable of pinging if they aren't registered.
        boolean pingSet = false;
        //retreive IP address from message context
        MessageContext pingContext = context.getMessageContext();
        HttpServletRequest pingRequest = (HttpServletRequest)pingContext.get(MessageContext.SERVLET_REQUEST);
        //iterate through pis already online and update the most recent ping if match is found
        for(int i = 0; i < onlinePis.size(); i++){
            if(onlinePis.get(i).getID() == ID){
                //set the online information for the remote Pi
                onlinePis.get(i).setLastPing(new Date());
                onlinePis.get(i).setIPAddress(pingRequest.getRemoteAddr());
                pingSet = true;
                break;
            }
        }
        //if match was not found, grab pi from database and add it to onlinePis list with a new ping time
        //it should be impossible for a pi to ping the server if it has not registered
        if(!pingSet){
            onlinePis.add(new RPICam(DBManager.getPi(ID), timeoutParam, new Date(), pingRequest.getRemoteAddr())); 
        }
    }
    
    @WebMethod
    public String getOnlineList(){
        //return a String of all the online RPIs and their class data
        return convertListToString();
    }
    
    @WebMethod
    public String getRegisteredPiList(){
        //return a String of all registered RPIs and their class data as stored in the DB
        return DBManager.getAll();
    }
    
    @WebMethod
    private void intitiateCall(String camString, String IP){
        //to be called by a client wishing to start a voice chat with a device
        RPICam temp = new RPICam(camString, 1);
        MessageContext pingContext = context.getMessageContext();
        HttpServletRequest pingRequest = (HttpServletRequest)pingContext.get(MessageContext.SERVLET_REQUEST);
        //get the IP Address of the client wishing to initiate a call, and place that in callSwitcher against an ID for devices to check
        callSwitcher.put(temp.getID(), pingRequest.getRemoteAddr());
    }
    
    @WebMethod
    private String getCalls(int ID){
        //to be called by devices during their ping loop to check if there are any calls being made to them
        if(callSwitcher.get(ID) != null){
         return callSwitcher.get(ID);   
        }
        return "";
    }
    
    private String convertListToString(){
        String tempString = "";
        //check if there are any online RPIs in the list
        if(onlinePis.size() > 0){
            //loop through the list of online pis and add each entry to a string to be returned
            for(int i =0; i < onlinePis.size(); i++){
                //prune any pis that are outside the timeout parameter
                if(!onlinePis.get(i).isPingWithinParam()){
                    onlinePis.remove(i);
                }
                //list is returned as a string with entries separated by ;
                tempString = tempString + ";" + onlinePis.get(i).toString();
            }
        }
        //"" will be returned if there are no online RPIs, this should be validated client-side
        return tempString;
    }
}
