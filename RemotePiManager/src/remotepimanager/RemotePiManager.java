/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remotepimanager;

import CamRegistrar.RPITechSuppRegistrarService;
import CamRegistrar.RPITechSuppRegistrar;
/**
 *
 * @author Jamie Gilbertson
 */
public class RemotePiManager {
    private static RPITechSuppRegistrarService service;
    private static RPITechSuppRegistrar port;
    private static int ID = 2;
    private static String location = "Cardiff";
    private static Thread pinger;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
        service = new CamRegistrar.RPITechSuppRegistrarService();
        port = service.getRPITechSuppRegistrarPort();
        port.register(location);
        }
        catch(Exception e){
            System.out.println("Error connecting to server: " + e);
            System.exit(0);
        }
        pinger = new Thread(new pingThread());
        pinger.start();
    }
    
    public static void pingServer(){
        port.pingAlive(ID);
    }
    
    public static class pingThread implements Runnable{
        public void run(){
            while(true){
                System.out.println("Pinging server");
                pingServer();
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