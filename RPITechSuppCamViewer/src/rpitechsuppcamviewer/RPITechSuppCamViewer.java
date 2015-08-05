/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpitechsuppcamviewer;
/**
 *
 * @author Jamie Gilbertson
 */
public class RPITechSuppCamViewer {

    /**
     * @param args the command line arguments
     */
    static TechSuppGUI GUI;
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        GUI = new TechSuppGUI();
        GUI.setVisible(true);
        
    }
    
    public void forceRefresh(){
        //manually call getOnlineList() from web service to force instant refresh
    }
    
}
