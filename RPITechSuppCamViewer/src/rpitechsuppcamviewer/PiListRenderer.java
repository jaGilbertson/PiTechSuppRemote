/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpitechsuppcamviewer;

import javax.swing.*;
import java.awt.Component;
/**
 *
 * @author Jamie Gilbertson
 */
class PiListRenderer extends JLabel implements ListCellRenderer {


  public PiListRenderer() {
    setOpaque(true);
    setIconTextGap(12);
  }

  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    //RPICam pi = (RPICam) value;
    setText((String) value);//pi.toDisplayString());
    String temp = (String) value;
    if(!temp.equals("No registered devices") && !temp.equals("No active cameras")){
       setIcon(new ImageIcon("resources/offline.png")); 
    }
    if (isSelected) {
            this.setForeground(list.getSelectionForeground());
            this.setBackground(list.getSelectionBackground());
        } else {
            this.setForeground(list.getForeground());
            this.setBackground(list.getBackground());
        }
    return this;
  }
}