/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPITechServer;

import javax.jws.WebService;
import javax.jws.WebMethod;
import java.util.*;
/**
 *
 * @author Jamie Gilbertson
 */
@WebService
public class RPITechSuppServer{
    private ArrayList<RPICam> onlinePis;
    private int IDCounter = 1;
    private int timeoutParam = 30; //timeout in seconds
    
    
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
    public void pingAlive(int ID){
        //TODO
        //RPIs call this method to ping to say they are online. They should do this as often as the timeout parameter. RPIs are incapable of pinging if they aren't registered.
        boolean pingSet = false;
        //iterate through pis already online and update the most recent ping if match is found
        for(int i = 0; i < onlinePis.size(); i++){
            if(onlinePis.get(i).getID() == ID){
                onlinePis.get(i).setLastPing(new Date());
                pingSet = true;
                break;
            }
        }
        //if match was not found, grab pi from database and add it to onlinePis list with a new ping time
        if(!pingSet){
            onlinePis.add(new RPICam(DBManager.getPi(ID), timeoutParam, new Date())); 
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
