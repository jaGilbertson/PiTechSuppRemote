/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remotepimanager;

import RPITechSuppCamServer.RPITechSuppServerService;
import RPITechSuppCamServer.RPITechSuppServer;
/**
 *
 * @author Jamie Gilbertson
 */
public class RemotePiManager {
    private static RPITechSuppServerService service;
    private static RPITechSuppServer port;
    private static int ID = 2;
    private static String location = "Cardiff";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        service = new RPITechSuppCamServer.RPITechSuppServerService();
        port = service.getRPITechSuppServerPort();
        port.register(location);
        while(true){
            try{
            pingServer();
            Thread.sleep(20*1000);
            System.out.println("Pinging server");
            }
            catch(Exception e){}
        }
    }
    
    public static void pingServer(){
        port.pingAlive(ID);
    }
}
