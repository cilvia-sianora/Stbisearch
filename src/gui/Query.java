/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class Query extends javax.swing.JPanel {
	MainGUI mg;
	
    /**
     * Creates new form Query
     */
    public Query() {
        initComponents();
        topn.setEditable(false);
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

        tf = new javax.swing.ButtonGroup();
        idf = new javax.swing.ButtonGroup();
        normal = new javax.swing.ButtonGroup();
        stem = new javax.swing.ButtonGroup();
        firstret = new javax.swing.ButtonGroup();
        samedoc = new javax.swing.ButtonGroup();
        queryexpand = new javax.swing.ButtonGroup();
        indexing = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        noStem = new javax.swing.JRadioButton();
        noNorm = new javax.swing.JRadioButton();
        usingNorm = new javax.swing.JRadioButton();
        usingStem = new javax.swing.JRadioButton();
        noIDF = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        usingIDF = new javax.swing.JRadioButton();
        binaryTF = new javax.swing.JRadioButton();
        logaritmicTF = new javax.swing.JRadioButton();
        rawTF = new javax.swing.JRadioButton();
        noTF = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        augmentedTF = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        rocchio = new javax.swing.JRadioButton();
        idereguler = new javax.swing.JRadioButton();
        dechi = new javax.swing.JRadioButton();
        pseudo = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        truesamedoc = new javax.swing.JRadioButton();
        falsesamedoc = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        truequeryexp = new javax.swing.JRadioButton();
        falsequeryexp = new javax.swing.JRadioButton();
        topn = new javax.swing.JTextField();

        indexing.setText("OK");
        indexing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indexingActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Stemming");

        stem.add(noStem);
        noStem.setText("no Stemming");
        noStem.setToolTipText("");
        noStem.setActionCommand("");

        normal.add(noNorm);
        noNorm.setText("no Normalization");
        noNorm.setToolTipText("");
        noNorm.setActionCommand("");
        noNorm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noNormActionPerformed(evt);
            }
        });

        normal.add(usingNorm);
        usingNorm.setText("using Normalization");
        usingNorm.setToolTipText("");
        usingNorm.setActionCommand("");
        usingNorm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usingNormActionPerformed(evt);
            }
        });

        stem.add(usingStem);
        usingStem.setText("using Stemming");

        idf.add(noIDF);
        noIDF.setText("no IDF");
        noIDF.setToolTipText("");
        noIDF.setActionCommand("");
        noIDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noIDFActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Normalization");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("TF");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Setting Query");

        idf.add(usingIDF);
        usingIDF.setText("using IDF");
        usingIDF.setToolTipText("");
        usingIDF.setActionCommand("");
        usingIDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usingIDFActionPerformed(evt);
            }
        });

        tf.add(binaryTF);
        binaryTF.setText("binary TF");
        binaryTF.setToolTipText("");
        binaryTF.setActionCommand("");
        binaryTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                binaryTFActionPerformed(evt);
            }
        });

        tf.add(logaritmicTF);
        logaritmicTF.setText("logaritmic TF");
        logaritmicTF.setToolTipText("");
        logaritmicTF.setActionCommand("");
        logaritmicTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logaritmicTFActionPerformed(evt);
            }
        });

        tf.add(rawTF);
        rawTF.setText("raw TF");
        rawTF.setToolTipText("");
        rawTF.setActionCommand("");
        rawTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rawTFActionPerformed(evt);
            }
        });

        tf.add(noTF);
        noTF.setText("no TF");
        noTF.setToolTipText("");
        noTF.setActionCommand("");
        noTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noTFActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("IDF");

        tf.add(augmentedTF);
        augmentedTF.setText("augmented TF");
        augmentedTF.setToolTipText("");
        augmentedTF.setActionCommand("");
        augmentedTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                augmentedTFActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton1.setText("<");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Algoritma First Retrival");

        firstret.add(rocchio);
        rocchio.setText("Rocchio");

        firstret.add(idereguler);
        idereguler.setText("Ide Reguler");
        idereguler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ideregulerActionPerformed(evt);
            }
        });

        firstret.add(dechi);
        dechi.setText("Dec Hi");
        dechi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dechiActionPerformed(evt);
            }
        });

        firstret.add(pseudo);
        pseudo.setText("Pseudo");
        pseudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pseudoActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Use Same Document Collection");

        samedoc.add(truesamedoc);
        truesamedoc.setText("True");

        samedoc.add(falsesamedoc);
        falsesamedoc.setText("False");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Use Query Expansion");

        queryexpand.add(truequeryexp);
        truequeryexp.setText("True");

        queryexpand.add(falsequeryexp);
        falsequeryexp.setText("False");

        topn.setText("topn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rawTF)
                            .addComponent(logaritmicTF)
                            .addComponent(binaryTF)
                            .addComponent(augmentedTF)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(noTF))
                                .addGap(73, 73, 73)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(noIDF)
                                    .addComponent(usingIDF, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(noNorm)
                                    .addComponent(usingNorm)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(usingStem)
                                    .addComponent(noStem)))
                            .addComponent(jLabel1)
                            .addComponent(indexing, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rocchio)
                                        .addGap(45, 45, 45)
                                        .addComponent(idereguler)
                                        .addGap(18, 18, 18)
                                        .addComponent(dechi))
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(truesamedoc)
                                            .addGap(18, 18, 18)
                                            .addComponent(falsesamedoc))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(truequeryexp)
                                            .addGap(18, 18, 18)
                                            .addComponent(falsequeryexp)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(pseudo)
                                        .addGap(18, 18, 18)
                                        .addComponent(topn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1)))
                                .addGap(34, 34, 34)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addComponent(augmentedTF)
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rocchio)
                    .addComponent(idereguler)
                    .addComponent(dechi)
                    .addComponent(pseudo)
                    .addComponent(topn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(truesamedoc)
                    .addComponent(falsesamedoc)
                    .addComponent(jLabel13))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(truequeryexp)
                        .addComponent(falsequeryexp))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel14)))
                .addGap(37, 37, 37)
                .addComponent(indexing)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void noNormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noNormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noNormActionPerformed

    private void usingNormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usingNormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usingNormActionPerformed

    private void noIDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noIDFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noIDFActionPerformed

    private void usingIDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usingIDFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usingIDFActionPerformed

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

    private void augmentedTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_augmentedTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_augmentedTFActionPerformed

    private void indexingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indexingActionPerformed
        String alg="";
        String tf = "";
        boolean idf = false;
        boolean samedoc = false;
        boolean queryexp = false;
        boolean norm = false;
        boolean stem = false;
        int topN = -1 ;
        
        if(noTF.isSelected()){
            tf="no";
        }
        else if (rawTF.isSelected()){
            tf="raw";
        }
        else if (logaritmicTF.isSelected()){
            tf="log";
        }
        else if (binaryTF.isSelected()){
            tf="binary";
        }
        else if (augmentedTF.isSelected()){
            tf="aug";
        }
        
        if(noIDF.isSelected()){
            idf=false;
        }
        else if (usingIDF.isSelected()){
            idf=true;
        }
        
        if(noNorm.isSelected()){
            norm=false;
        }
        else if (usingNorm.isSelected()){
            norm=true;
        }
        
        if(noStem.isSelected()){
            stem=false;
        }
        else if (usingStem.isSelected()){
            stem=true;
        }
        
        if(rocchio.isSelected()){
            alg = "rocchio";
        }
        else if (pseudo.isSelected()){
            alg = "pseudo";
            topN = Integer.parseInt(topn.getText());
        }
        else if (idereguler.isSelected()){
            alg = "idereguler";
        }
        else if (dechi.isSelected()){
            alg = "dechi";
        }
        
        if(truesamedoc.isSelected()){
            samedoc = true;
        }
        else if(falsesamedoc.isSelected()){
            samedoc = false;
        }
        
        if(truequeryexp.isSelected()){
            queryexp = true;
        }
        else if(falsequeryexp.isSelected()){
            queryexp = false;
        }
		
		mg.qp.setQuerySetting(tf, idf, norm, stem);
		JOptionPane.showMessageDialog(this, "Setting done");
    }//GEN-LAST:event_indexingActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        mg.showSearchingPanel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ideregulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ideregulerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ideregulerActionPerformed

    private void dechiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dechiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dechiActionPerformed

    private void pseudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pseudoActionPerformed
        topn.setEditable(true);
    }//GEN-LAST:event_pseudoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton augmentedTF;
    private javax.swing.JRadioButton binaryTF;
    private javax.swing.JRadioButton dechi;
    private javax.swing.JRadioButton falsequeryexp;
    private javax.swing.JRadioButton falsesamedoc;
    private javax.swing.ButtonGroup firstret;
    private javax.swing.JRadioButton idereguler;
    private javax.swing.ButtonGroup idf;
    private javax.swing.JButton indexing;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JRadioButton logaritmicTF;
    private javax.swing.JRadioButton noIDF;
    private javax.swing.JRadioButton noNorm;
    private javax.swing.JRadioButton noStem;
    private javax.swing.JRadioButton noTF;
    private javax.swing.ButtonGroup normal;
    private javax.swing.JRadioButton pseudo;
    private javax.swing.ButtonGroup queryexpand;
    private javax.swing.JRadioButton rawTF;
    private javax.swing.JRadioButton rocchio;
    private javax.swing.ButtonGroup samedoc;
    private javax.swing.ButtonGroup stem;
    private javax.swing.ButtonGroup tf;
    private javax.swing.JTextField topn;
    private javax.swing.JRadioButton truequeryexp;
    private javax.swing.JRadioButton truesamedoc;
    private javax.swing.JRadioButton usingIDF;
    private javax.swing.JRadioButton usingNorm;
    private javax.swing.JRadioButton usingStem;
    // End of variables declaration//GEN-END:variables
}
