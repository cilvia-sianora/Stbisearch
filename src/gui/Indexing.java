/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author ASUS
 */
public class Indexing extends javax.swing.JPanel {
	MainGUI mg;
	
    /**
     * Creates new form Indexing
     */
    public Indexing() {
        initComponents();
		mg = new MainGUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        uploadDoc = new javax.swing.JButton();
        usingStem = new javax.swing.JRadioButton();
        noStem = new javax.swing.JRadioButton();
        augmentedTF = new javax.swing.JRadioButton();
        binaryTF = new javax.swing.JRadioButton();
        logaritmicTF = new javax.swing.JRadioButton();
        rawTF = new javax.swing.JRadioButton();
        noTF = new javax.swing.JRadioButton();
        noNorm = new javax.swing.JRadioButton();
        filepathlabel = new javax.swing.JLabel();
        usingNorm = new javax.swing.JRadioButton();
        Stopword = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        noIDF = new javax.swing.JRadioButton();
        filepathlabel2 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        indexing = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        usingIDF = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Stemming");

        uploadDoc.setText("Document");
        uploadDoc.setActionCommand("documentUpload");
        uploadDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadDocActionPerformed(evt);
            }
        });

        usingStem.setText("using Stemming");

        noStem.setText("no Stemming");
        noStem.setToolTipText("");
        noStem.setActionCommand("");

        augmentedTF.setText("augmented TF");
        augmentedTF.setToolTipText("");
        augmentedTF.setActionCommand("");
        augmentedTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                augmentedTFActionPerformed(evt);
            }
        });

        binaryTF.setText("binary TF");
        binaryTF.setToolTipText("");
        binaryTF.setActionCommand("");
        binaryTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                binaryTFActionPerformed(evt);
            }
        });

        logaritmicTF.setText("logaritmic TF");
        logaritmicTF.setToolTipText("");
        logaritmicTF.setActionCommand("");
        logaritmicTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logaritmicTFActionPerformed(evt);
            }
        });

        rawTF.setText("raw TF");
        rawTF.setToolTipText("");
        rawTF.setActionCommand("");
        rawTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rawTFActionPerformed(evt);
            }
        });

        noTF.setText("no TF");
        noTF.setToolTipText("");
        noTF.setActionCommand("");
        noTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noTFActionPerformed(evt);
            }
        });

        noNorm.setText("no Normalization");
        noNorm.setToolTipText("");
        noNorm.setActionCommand("");
        noNorm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noNormActionPerformed(evt);
            }
        });

        filepathlabel.setText("File : ");

        usingNorm.setText("using Normalization");
        usingNorm.setToolTipText("");
        usingNorm.setActionCommand("");
        usingNorm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usingNormActionPerformed(evt);
            }
        });

        Stopword.setText("Stop Word");
        Stopword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopwordActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Indexing");

        noIDF.setText("no IDF");
        noIDF.setToolTipText("");
        noIDF.setActionCommand("");
        noIDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noIDFActionPerformed(evt);
            }
        });

        filepathlabel2.setText("File : ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("TF");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Normalization");

        indexing.setText("Indexing");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("IDF");

        usingIDF.setText("using IDF");
        usingIDF.setToolTipText("");
        usingIDF.setActionCommand("");
        usingIDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usingIDFActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton1.setText("<");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rawTF)
                            .addComponent(logaritmicTF)
                            .addComponent(binaryTF)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(uploadDoc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(filepathlabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Stopword)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(filepathlabel2))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(augmentedTF)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(indexing))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(noTF))
                                    .addGap(73, 73, 73)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(noIDF)
                                        .addComponent(usingIDF))
                                    .addGap(23, 23, 23)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(noNorm)
                                        .addComponent(usingNorm)
                                        .addComponent(jLabel4))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(usingStem)
                                        .addComponent(noStem))))))
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(3, 3, 3)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uploadDoc)
                    .addComponent(filepathlabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Stopword)
                    .addComponent(filepathlabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(noTF)
                    .addComponent(noIDF)
                    .addComponent(noNorm)
                    .addComponent(noStem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usingStem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rawTF)
                        .addComponent(usingIDF)
                        .addComponent(usingNorm)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logaritmicTF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(binaryTF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(augmentedTF)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(indexing)
                        .addGap(25, 25, 25))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void uploadDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadDocActionPerformed
        JFileChooser fc = new JFileChooser();
        String sb ="";
        String fullPath = "";
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int retval = fc.showOpenDialog(null);
        if(retval == JFileChooser.APPROVE_OPTION){
            File selectedFile = fc.getSelectedFile();
            sb += selectedFile.getName();
            fullPath += selectedFile.getAbsoluteFile();
            this.filepathlabel.setText("File : "+ fullPath);
        }
    }//GEN-LAST:event_uploadDocActionPerformed

    private void augmentedTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_augmentedTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_augmentedTFActionPerformed

    private void binaryTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_binaryTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_binaryTFActionPerformed

    private void logaritmicTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logaritmicTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logaritmicTFActionPerformed

    private void rawTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rawTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rawTFActionPerformed

    private void noTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noTFActionPerformed

    private void noNormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noNormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noNormActionPerformed

    private void usingNormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usingNormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usingNormActionPerformed

    private void StopwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopwordActionPerformed
        JFileChooser fc = new JFileChooser();
        String sb ="";
        String fullPath = "";
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int retval = fc.showOpenDialog(null);
        if(retval == JFileChooser.APPROVE_OPTION){
            File selectedFile = fc.getSelectedFile();
            sb += selectedFile.getName();
            fullPath += selectedFile.getAbsoluteFile();
            this.filepathlabel2.setText("File : "+ fullPath);
        }
    }//GEN-LAST:event_StopwordActionPerformed

    private void noIDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noIDFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noIDFActionPerformed

    private void usingIDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usingIDFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usingIDFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Stopword;
    private javax.swing.JRadioButton augmentedTF;
    private javax.swing.JRadioButton binaryTF;
    private javax.swing.JLabel filepathlabel;
    private javax.swing.JLabel filepathlabel2;
    private javax.swing.JButton indexing;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JRadioButton logaritmicTF;
    private javax.swing.JRadioButton noIDF;
    private javax.swing.JRadioButton noNorm;
    private javax.swing.JRadioButton noStem;
    private javax.swing.JRadioButton noTF;
    private javax.swing.JRadioButton rawTF;
    private javax.swing.JButton uploadDoc;
    private javax.swing.JRadioButton usingIDF;
    private javax.swing.JRadioButton usingNorm;
    private javax.swing.JRadioButton usingStem;
    // End of variables declaration//GEN-END:variables
}
