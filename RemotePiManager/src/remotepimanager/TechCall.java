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

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.SocketException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.LineUnavailableException;

    
/**
 *
 * @author Jamie Gilbertson
 */
public class TechCall{

    
    private int bufSize = 256; //size of buffers
    private int port = 4071; //port used to receive packets
    private int timeout = 10000; //time in milliseconds before socket.receive() will timeout;
    
    private RecordingThread recordSend;
    private PlaybackThread receivePlayback;
    private Thread recordSendThread; //thread to record sound as byte and send via UDP port
    private Thread receivePlaybackThread; //thread to receive bytes and play it back as sound
    private String IPAddress; //string representation address of counterpart
    private InetAddress INPAddress; //actually used address of counterpart
    private DatagramSocket socket; //socket to receive UDP packets
    private byte[] recBuf; //buffer to received bytes
    private byte[] sendBuf; //buffer for bytes to send
    
    private TargetDataLine targetLine;
    private SourceDataLine sourceLine;
    private AudioFormat format = new AudioFormat(8000, 16, 1, true, true); //sample rate 8kHz, sample size 16 bits, 1 channel, signed true, big Endian true
    
    public TechCall() throws SocketException, LineUnavailableException {
        //to be used by remote device to create new instance of TechCall that will wait to receive a packet before returning a call
        IPAddress = "";
        recordSend = new RecordingThread();
        receivePlayback = new PlaybackThread();
        recordSendThread = new Thread(recordSend);
        receivePlaybackThread = new Thread(receivePlayback);

        socket = new DatagramSocket(port);
        socket.setSoTimeout(timeout);

        setupAudio();

    }
    
    public TechCall(String address) throws SocketException, LineUnavailableException {
        //to be used by client to create new instance of TechCall to start a new call with a remote device
        IPAddress = address;
        recordSendThread = new Thread(new RecordingThread());
        receivePlaybackThread = new Thread(new PlaybackThread());

        socket = new DatagramSocket(port);

        setupAudio();
    }
    
    private void setupAudio() throws LineUnavailableException{
        //sets up the targetLine to use for capturing audio
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        targetLine = (TargetDataLine) AudioSystem.getLine(info);
        targetLine.open(format);        
        sourceLine.open(format);
    }
    
    public void startCall(){
        if(IPAddress.equals("")){
            //if instance has been created without an address, the thread will wait until it receives a packet to initiate the call
            //create byte array and DatagramPacket object to receive packets
            recBuf = new byte[bufSize];
            DatagramPacket recPack = new DatagramPacket(recBuf, bufSize);
            boolean received = false;
            while(!received){
                try{
                    socket.receive(recPack); //thread will wait here until a packet is received
                    received = true;
                }
                catch(SocketTimeoutException e){
                    //do nothing, just don't break the loop
                }
                catch(IOException e){
                    //TODO handle this error
                }
            }
            INPAddress = recPack.getAddress();
        }
        else{
            //if instance has been created with a given address, get that address and send an empty packet to start a call
            try{
            INPAddress = InetAddress.getByName(IPAddress);
            sendBuf = new byte[bufSize];
            DatagramPacket sendPack = new DatagramPacket(sendBuf, bufSize, INPAddress, port);
            socket.send(sendPack);
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        
        //start Receiving and Sending threads
        recordSendThread.start();
        receivePlaybackThread.start();
    }
    
    public void stopCall(){
        //ask Receiving and Sending threads to stop gracefully
        receivePlayback.stopThread();
        recordSend.stopThread();
    }
    
    public class RecordingThread implements Runnable{
        private volatile boolean shouldStop = false;
        
        public void run(){
            //start targetLine so that it delivers audio from the system
            targetLine.start();
            while(!shouldStop){
                sendBuf = new byte[bufSize];
                //write audio input to buffer array and send as UDP packet
                targetLine.read(sendBuf, 0, bufSize);
                DatagramPacket sendPack = new DatagramPacket(sendBuf, bufSize, INPAddress, port);
                try{
                socket.send(sendPack);
                }
                catch(IOException e){
                    System.out.println(e);
                }
            }
            //flush and end targetLine
            targetLine.stop();
            targetLine.flush();
            targetLine.close();
        }
        
        public void stopThread(){
            shouldStop = true;
        }
    }
    
    public class PlaybackThread implements Runnable{
        private volatile boolean shouldStop = false;
        
        public void run(){
            //start sourceLine to playback audio written to it
            sourceLine.start();
            while(!shouldStop){
                //create byte array and DatagramPacket object to receive packets
                recBuf = new byte[bufSize];
                DatagramPacket recPack = new DatagramPacket(recBuf, bufSize);
                //receive packets
                try{
                    socket.receive(recPack); //thread will wait here until a packet is received
                }
                catch(SocketTimeoutException e){
                    stopCall(); //call has timed out, so end all threads
                    startCall(); //restart process to wait for new calls
                    break;
                }
                catch(IOException e){
                }
                //TODO playback received bytes
                sourceLine.write(recBuf, 0, bufSize);
            }
            //flush and end sourceLine
            sourceLine.stop();
            sourceLine.flush();
            sourceLine.close();
        }
        
        public void stopThread(){
            shouldStop = true;
        }
    }
}
