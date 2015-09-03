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
package rpitechsuppcamviewer;

import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author Mecha
 */
public class TechSuppGUI extends javax.swing.JFrame {

    /**
     * Creates new form TechSuppGUI
     */
    //create the models to be used to populate lists
    DefaultListModel piModel = new DefaultListModel();
    DefaultListModel equipModel = new DefaultListModel();
    DefaultListModel issueModel = new DefaultListModel();
    
    AddIssueForm issueAdder = new AddIssueForm();
    AddEquipmentForm equipAdder = new AddEquipmentForm();
    
    public TechSuppGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        RPIList = new javax.swing.JList();
        viewComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        equipList = new javax.swing.JList();
        eqAtThisLocLbl = new javax.swing.JLabel();
        locationLbl = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        issueList = new javax.swing.JList();
        pastIssueLbl = new javax.swing.JLabel();
        addEquipBtn = new javax.swing.JButton();
        removeEquipBtn = new javax.swing.JButton();
        addIssueBtn = new javax.swing.JButton();
        removeIssueBtn = new javax.swing.JButton();
        openCamBtn = new javax.swing.JButton();
        copyBtn = new javax.swing.JButton();
        listRefresh = new javax.swing.JButton();
        callBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        progressLbl = new javax.swing.JLabel();
        locLbl = new javax.swing.JLabel();
        addLbl = new javax.swing.JLabel();
        endCallBtn = new javax.swing.JButton();
        retirePiBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Remote Support Viewer");
        setMaximumSize(new java.awt.Dimension(1196, 596));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        RPIList.setModel(piModel);
        RPIList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        RPIList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                RPIListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(RPIList);

        viewComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Online", "All Registered" }));
        viewComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("View Mode:");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        equipList.setModel(equipModel);
        equipList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        equipList.setAutoscrolls(false);
        equipList.setEnabled(false);
        jScrollPane2.setViewportView(equipList);

        eqAtThisLocLbl.setText("Equipment at this location:");
        eqAtThisLocLbl.setEnabled(false);

        locationLbl.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        locationLbl.setText("No Location Selected");
        locationLbl.setEnabled(false);

        issueList.setModel(issueModel);
        issueList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        issueList.setAutoscrolls(false);
        issueList.setEnabled(false);
        issueList.setMaximumSize(new java.awt.Dimension(490, 0));
        jScrollPane3.setViewportView(issueList);

        pastIssueLbl.setText("Past Issues:");
        pastIssueLbl.setEnabled(false);

        addEquipBtn.setText("Add Equipment");
        addEquipBtn.setEnabled(false);
        addEquipBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEquipBtnActionPerformed(evt);
            }
        });

        removeEquipBtn.setText("Remove Equipment");
        removeEquipBtn.setEnabled(false);
        removeEquipBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEquipBtnActionPerformed(evt);
            }
        });

        addIssueBtn.setText("Add Issue");
        addIssueBtn.setEnabled(false);
        addIssueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIssueBtnActionPerformed(evt);
            }
        });

        removeIssueBtn.setText("Remove Issue");
        removeIssueBtn.setEnabled(false);
        removeIssueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeIssueBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(eqAtThisLocLbl)
                                .addGap(73, 73, 73)
                                .addComponent(pastIssueLbl))
                            .addComponent(locationLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(removeEquipBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addEquipBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addIssueBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(removeIssueBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(locationLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eqAtThisLocLbl)
                    .addComponent(pastIssueLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addEquipBtn)
                    .addComponent(addIssueBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeEquipBtn)
                    .addComponent(removeIssueBtn))
                .addContainerGap())
        );

        openCamBtn.setText("Open Camera");
        openCamBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openCamBtnActionPerformed(evt);
            }
        });

        copyBtn.setText("Copy IP Address");
        copyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyBtnActionPerformed(evt);
            }
        });

        listRefresh.setText("Refresh");
        listRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listRefreshActionPerformed(evt);
            }
        });

        callBtn.setText("Start Call");
        callBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callBtnActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setEnabled(false);

        progressLbl.setText("Call in progress:-");
        progressLbl.setEnabled(false);

        endCallBtn.setText("End call");
        endCallBtn.setEnabled(false);
        endCallBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endCallBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(endCallBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(progressLbl)
                            .addComponent(locLbl)
                            .addComponent(addLbl))
                        .addGap(0, 62, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(locLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(endCallBtn)
                .addContainerGap())
        );

        retirePiBtn.setText("Retire Device");
        retirePiBtn.setEnabled(false);
        retirePiBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retirePiBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(callBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(listRefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(openCamBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(copyBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                    .addComponent(retirePiBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(18, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(viewComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(openCamBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(callBtn)
                                .addGap(8, 8, 8)
                                .addComponent(copyBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(listRefresh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(retirePiBtn))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void viewComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewComboBoxActionPerformed
            disableLocViewer();
        if(viewComboBox.getSelectedIndex() == 1){
            piModel.clear();
            RPITechSuppCamViewer.setListOnlineOnly(false);
            disableConnect();
            RPIList.repaint();
            retirePiBtn.setEnabled(true);
        }
        else{
            piModel.clear();
            RPITechSuppCamViewer.setListOnlineOnly(true);
            retirePiBtn.setEnabled(false);
        }
    }//GEN-LAST:event_viewComboBoxActionPerformed

    private void listRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listRefreshActionPerformed
        RPITechSuppCamViewer.refresh();
    }//GEN-LAST:event_listRefreshActionPerformed

    private void openCamBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openCamBtnActionPerformed
        if(RPIList.getSelectedIndex() >= 0){
            RPITechSuppCamViewer.openCam(RPIList.getSelectedIndex());
        }
    }//GEN-LAST:event_openCamBtnActionPerformed

    private void copyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyBtnActionPerformed
        if(RPIList.getSelectedIndex() >= 0){
            RPITechSuppCamViewer.copyIP(RPIList.getSelectedIndex());
        }
    }//GEN-LAST:event_copyBtnActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void callBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callBtnActionPerformed
        callBtn.setEnabled(false);
        endCallBtn.setEnabled(false);
        RPITechSuppCamViewer.callDevice(RPIList.getSelectedIndex());
    }//GEN-LAST:event_callBtnActionPerformed

    private void endCallBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endCallBtnActionPerformed
        RPITechSuppCamViewer.endCall();
        endCallBtn.setEnabled(false);
    }//GEN-LAST:event_endCallBtnActionPerformed

    private void RPIListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_RPIListValueChanged
        //tell RPITechSuppCamViewer class to update location panel with relevant information
        if(RPIList.getSelectedIndex() > -1){
            RPITechSuppCamViewer.updateLocationPanel(RPIList.getSelectedIndex());
        }
    }//GEN-LAST:event_RPIListValueChanged

    private void addEquipBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEquipBtnActionPerformed
        equipAdder.setVisible(true);
    }//GEN-LAST:event_addEquipBtnActionPerformed

    private void addIssueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIssueBtnActionPerformed
        issueAdder.setVisible(true);
    }//GEN-LAST:event_addIssueBtnActionPerformed

    private void removeEquipBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeEquipBtnActionPerformed
        RPITechSuppCamViewer.removeEquipmentFromDB(equipModel.get(equipList.getSelectedIndex()).toString());
    }//GEN-LAST:event_removeEquipBtnActionPerformed

    private void removeIssueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeIssueBtnActionPerformed
        //HTML was added to render the issue correctly in the JList, it needs to be removed before being used to search the database
        RPITechSuppCamViewer.removeIssueFromDB(issueModel.get(issueList.getSelectedIndex()).toString().replace("<br>", "\n").replace("<HTML>",""));
    }//GEN-LAST:event_removeIssueBtnActionPerformed

    private void retirePiBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retirePiBtnActionPerformed
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to retire this device?", "Please Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(response == JOptionPane.YES_OPTION)
            RPITechSuppCamViewer.manualRetirePi(RPIList.getSelectedIndex());
    }//GEN-LAST:event_retirePiBtnActionPerformed

    public void setCaller(String location, String address){
        locationLbl.setText(location);
        addLbl.setText(address);
        endCallBtn.setEnabled(true);
        progressLbl.setEnabled(false);
    }
    
    public void disableCallInterface(){
        endCallBtn.setEnabled(false);
        locLbl.setText("");
        addLbl.setText("");
        callBtn.setEnabled(true);
        progressLbl.setEnabled(false);
    }
        
    public void updatePiList(String[] listString){
        //update the RPIList with an updated list
        piModel.clear();
        for (String listString1 : listString) {
            piModel.addElement(listString1);
        }
        RPIList.repaint();
    }
    
    public void disableConnect(){
        progressLbl.setEnabled(true);
        callBtn.setEnabled(false);
        openCamBtn.setEnabled(false);
        copyBtn.setEnabled(false);
    }
    
    public void enableConnect(){
        progressLbl.setEnabled(false);
        callBtn.setEnabled(true);
        openCamBtn.setEnabled(true);
        copyBtn.setEnabled(true);
    }
    
    public void enableLocViewer(){
        locationLbl.setEnabled(true);
        eqAtThisLocLbl.setEnabled(true);
        pastIssueLbl.setEnabled(true);
        equipList.setEnabled(true);
        issueList.setEnabled(true);
        addEquipBtn.setEnabled(true);
        removeEquipBtn.setEnabled(true);
        addIssueBtn.setEnabled(true);
        removeIssueBtn.setEnabled(true);        
    }
    
    public void disableLocViewer(){
        locationLbl.setEnabled(false);
        eqAtThisLocLbl.setEnabled(false);
        pastIssueLbl.setEnabled(false);
        equipList.setEnabled(false);
        issueList.setEnabled(false);
        addEquipBtn.setEnabled(false);
        removeEquipBtn.setEnabled(false);
        addIssueBtn.setEnabled(false);
        removeIssueBtn.setEnabled(false);        
    }
    
    public void updateLocationDetails(ArrayList<String> issues, ArrayList<String> equipment, String location){
        //update the issue model and repaint the list
        issueModel.clear();
        for (int i = 0; i < issues.size(); i++) {
            //replace newline characters with HTML breaks to allow proper formatting in JList
            issueModel.addElement("<HTML>" + issues.get(i).replace("\n", "<br>"));
        }
        issueList.repaint();
        //update the equipment model and repaint the list
        equipModel.clear();
        for (int i = 0; i < equipment.size(); i++) {
            equipModel.addElement(equipment.get(i));
        }
        equipList.repaint();
        //update the location name
        locationLbl.setText(location);
        //enable the panel
        enableLocViewer();
    }
    
    public int getSelectedLocationIndex(){
        return RPIList.getSelectedIndex();        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TechSuppGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TechSuppGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TechSuppGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TechSuppGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TechSuppGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList RPIList;
    private javax.swing.JButton addEquipBtn;
    private javax.swing.JButton addIssueBtn;
    private javax.swing.JLabel addLbl;
    private javax.swing.JButton callBtn;
    private javax.swing.JButton copyBtn;
    private javax.swing.JButton endCallBtn;
    private javax.swing.JLabel eqAtThisLocLbl;
    private javax.swing.JList equipList;
    private javax.swing.JList issueList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton listRefresh;
    private javax.swing.JLabel locLbl;
    private javax.swing.JLabel locationLbl;
    private javax.swing.JButton openCamBtn;
    private javax.swing.JLabel pastIssueLbl;
    private javax.swing.JLabel progressLbl;
    private javax.swing.JButton removeEquipBtn;
    private javax.swing.JButton removeIssueBtn;
    private javax.swing.JButton retirePiBtn;
    private javax.swing.JComboBox viewComboBox;
    // End of variables declaration//GEN-END:variables
}
