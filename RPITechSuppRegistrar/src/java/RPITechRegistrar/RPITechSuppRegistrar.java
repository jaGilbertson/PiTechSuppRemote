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

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.xml.ws.*;
import javax.xml.ws.handler.*;
import javax.servlet.http.*;
import java.util.*;
import javax.annotation.Resource;
/**
 *
 * @author Jamie Gilbertson
 */


@WebService
public class RPITechSuppRegistrar{
    private final ArrayList<RPICam> onlinePis = new ArrayList<>(); //stores all online devices
    private final HashMap<Integer, String> callSwitcher = new HashMap<>(); //stores IP addresses of clients initiating voice calls against ID of device to be called
    private final int timeoutParam = 1; //timeout in seconds
    private final static String serverPassword = "wvnuser";
    
    @Resource
    private WebServiceContext context; //to be used to get IP from ping requests
   
    
    @WebMethod
    public boolean login(){
        return checkPass();
    }
    
    private boolean checkPass(){
        MessageContext authContext = context.getMessageContext();
        Map headers = (Map) authContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        List pw = (List) headers.get("Password");
        String pass = "";
        
        if(pw != null){
            pass = pw.get(0).toString();
        }
        
        if(serverPassword.equals(pass))
            return true;
        else
            return false;
    }
    
    @WebMethod
    public int register(String location){
        //Check if pi is already registered at that location. Return 0 if pi already registered. Return ID if pi successfully registered. IDs start from 1 and will never be 0.
        if(DBManager.getPi(location).equals("")){
            DBManager.registerPi(DBManager.getNewID(), location);
            return DBManager.getNewID() - 1;
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
    public void pingAlive(int ID){
        //RPIs call this method to ping to say they are online. They should do this as often as the timeout parameter. RPIs are incapable of pinging if they aren't registered.
        boolean pingSet = false;
        //retreive IP address from message context
        MessageContext pingContext = context.getMessageContext();
        HttpServletRequest pingRequest = (HttpServletRequest)pingContext.get(MessageContext.SERVLET_REQUEST);

        //iterate through pis already online and update the most recent ping if match is found
        synchronized(onlinePis){
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
    }
    
    @WebMethod
    public String getOnlineList(){
        if(checkPass()){
            //return a String of all the online RPIs and their class data
            return convertListToString();
        }
        else return "1%PASSWORDNOTFOUNDD%PASSWORDNOTFOUND";
    }
    
    @WebMethod
    public String getRegisteredPiList(){
        if(checkPass()){
            //return a String of all registered RPIs and their class data as stored in the DB
            return DBManager.getAll();
        }
        else return "1%PASSWORDNOTFOUND%PASSWORDNOTFOUND";
    }
    
    @WebMethod
    private void intitiateCall(String camString){
        if(checkPass()){
            //method not used in project, but left in for future expandability
            //to be called by a client wishing to start a voice chat with a device
            RPICam temp = new RPICam(camString, 1);
            MessageContext pingContext = context.getMessageContext();
            HttpServletRequest pingRequest = (HttpServletRequest)pingContext.get(MessageContext.SERVLET_REQUEST);
            //get the IP Address of the client wishing to initiate a call, and place that in callSwitcher against an ID for devices to check
            callSwitcher.put(temp.getID(), pingRequest.getRemoteAddr());
        }        
    }
    
    @WebMethod
    private String getCalls(int ID){
        //method not used in project, but left in for future expandability
        //to be called by devices during their ping loop to check if there are any calls being made to them
        if(callSwitcher.get(ID) != null){
         return callSwitcher.get(ID);
        }
        return "";
    }
    
    private String convertListToString(){
        String tempString = "";
        //check if there are any online RPIs in the list
        synchronized(onlinePis){
            if(onlinePis.size() > 0){
                //loop through the list of online pis and prune pis that are offline
                for(int i =0; i < onlinePis.size(); i++){
                    //prune any pis that are outside the timeout parameter
                    if(!onlinePis.get(i).isPingWithinParam()){
                        onlinePis.remove(i);
                    }
                }
            }
            if(onlinePis.size() > 0){
                //loop through the list of online pis and add each entry to a string to be returned
                for(int i =0; i < onlinePis.size(); i++){
                    //list is returned as a string with entries separated by ;
                    tempString = tempString + ";" + onlinePis.get(i).toString();
                }
            }
            //"" will be returned if there are no online RPIs, this should be validated client-side
            return tempString;
        }
    }
}
