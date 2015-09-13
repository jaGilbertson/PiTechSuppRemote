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
package remotepimanager;

import CamRegistrar.RPITechSuppRegistrarService;
import CamRegistrar.RPITechSuppRegistrar;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
/**
 * RemotePiManager
 * Class to act as the main program to manage remote devices,
 * each device should be running an instance of this class
 * 
 * @author Jamie Gilbertson
 */
public class RemotePiManager {
    private static RPITechSuppRegistrarService service;
    private static RPITechSuppRegistrar port;
    private static int ID;
    private static String location;
    private static Thread pinger;
    private static FileReader reader;
    private static BufferedReader lineReader;
    private static String confPath = "/home/pi/RemotePiManager/details.conf";
    private static TechCall callHandler;
    /**
     * Does not use any passed arguments.
     * Looks for details.conf in assigned directory and reads it.
     * If details.conf does not exist, main creates it and exits.
     * The location the device is located must be manually entered
     * into the first line of home/pi/RemotePiManager/details.conf
     * If details.conf exists, but is empty, main prints error
     * message and exits.
     * If details.conf exists and has a location entered to the
     * first line, main will attempt to register itself.
     * When registering, if a 0 ID is returned, an error message is
     * printed into details.conf
     * If a realy ID is returned, this is written to the second line
     * of details.conf and a server-pinging loop and an instance of 
     * TechCall is started
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //establish connection with server
        service = new CamRegistrar.RPITechSuppRegistrarService();
        port = service.getRPITechSuppRegistrarPort();
        //for RemotePiManager to run, the user must enter a location into details.conf which should be kept in the same location as RemotePiManager.jar
        
        //try to read the configuration file
        try{
            reader = new FileReader(confPath);
            lineReader = new BufferedReader(reader);
        }
        catch(IOException e){
            //if file does not exist, create it an exit program
            File file = new File(confPath);
            try{
                file.createNewFile();
            }
            catch(IOException io){
                //if file creation fails, simply exit program
                System.out.println("Failed to create details.conf");
                System.exit(0);
            }
            System.exit(0);
        }
        try{
            //try to read the location entered by user, if none has been entered, simply exit program
            location = lineReader.readLine();
            if(location.equals("")){
                System.out.println("No location entered into details.conf");
                System.exit(0);
            }
        }
        catch(Exception e){
            System.out.println("error reading details.conf");
            System.exit(0);
        }
        try{
            //try to read the ID of the PI, if this has not been set, register location with the server and save the ID returned
            ID = Integer.parseInt(lineReader.readLine());
        }
        catch(Exception e){
            ID = port.register(location);
            //if the location is already registered, the server will return 0 and the program should write this to line 3 of details.conf (which is never read by the program)
            if(ID != 0){
                //try to write the new ID to the file
                try{
                    //this should clear any error printouts from the file and write all correct details to it
                    PrintWriter writer = new PrintWriter(confPath);
                    writer.print(location + "\n" + ID);
                    writer.close();
                }
                catch(IOException io){
                    System.out.println("error overwriting details.conf");
                    System.exit(0);
                }
            }
            else{
             //if pi is already registered, write this to line 3 of details.conf
                try{
                    //this should clear any error printouts from the file and write all correct details to it
                    PrintWriter writer = new PrintWriter(confPath);
                    writer.print( location + "\n\nThere is already a device registered to this location, please enter a valid location, or retire old device");
                    writer.close();
                }
                catch(IOException io){
                    System.out.println("error writing to details.conf");
                    System.exit(0);
                }
            }
        }

        pinger = new Thread(new PingThread());
        pinger.start();
        
        createAndStartCallHandler();
    }
    
    /**
     * Method to ping the server to inform it that this device
     * is online
     */
    public static void pingServer(){
        try{
            port.pingAlive(ID);
        }
        catch(Exception e){
            System.out.println("Error connecting to server: " + e);
            System.exit(0);
        }
    }
    
    /**
     * Method used to create an instance of TechCall class and
     * start the call handler
     * @see TechCall
     */
    public static void createAndStartCallHandler(){
        try{
            callHandler = new TechCall();
        }
        catch(Exception e){
            System.out.println("Error establishing call support");
            e.printStackTrace(System.out);
            System.exit(0);
        }
        callHandler.startCall();
    }
    /**
    * Thread used to ping the server once every second
    * 
    * @author Jamie Gilbertson
    */
    public static class PingThread implements Runnable{
        /**
     * Runs a loop that calls pingServer() every second
     * @deprecated create new instance of Thread class using
     * this class as constructor argument, then use thread.start
     * @see Thread
     */
        public void run(){
            while(true){
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