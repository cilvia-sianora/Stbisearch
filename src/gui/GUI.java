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
 * @author Anggi
 */
public class GUI extends javax.swing.JFrame {

	/**
	 * Creates new form GUI
	 */
	public GUI() {
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

        tf = new javax.swing.ButtonGroup();
        idf = new javax.swing.ButtonGroup();
        norm = new javax.swing.ButtonGroup();
        stem = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        noTF = new javax.swing.JRadioButton();
        rawTF = new javax.swing.JRadioButton();
        logaritmicTF = new javax.swing.JRadioButton();
        binaryTF = new javax.swing.JRadioButton();
        augmentedTF = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        usingIDF = new javax.swing.JRadioButton();
        noIDF = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        noNorm = new javax.swing.JRadioButton();
        usingNorm = new javax.swing.JRadioButton();
        uploadDoc = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        noStem = new javax.swing.JRadioButton();
        usingStem = new javax.swing.JRadioButton();
        filepathlabel = new javax.swing.JLabel();
        Stopword = new javax.swing.JButton();
        filepathlabel2 = new javax.swing.JLabel();
        indexing = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Information Retrieval");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("TF");

        tf.add(noTF);
        noTF.setText("no TF");
        noTF.setToolTipText("");
        noTF.setActionCommand("");
        noTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noTFActionPerformed(evt);
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

        tf.add(logaritmicTF);
        logaritmicTF.setText("logaritmic TF");
        logaritmicTF.setToolTipText("");
        logaritmicTF.setActionCommand("");
        logaritmicTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logaritmicTFActionPerformed(evt);
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

        tf.add(augmentedTF);
        augmentedTF.setText("augmented TF");
        augmentedTF.setToolTipText("");
        augmentedTF.setActionCommand("");
        augmentedTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                augmentedTFActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("IDF");

        idf.add(usingIDF);
        usingIDF.setText("using IDF");
        usingIDF.setToolTipText("");
        usingIDF.setActionCommand("");
        usingIDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usingIDFActionPerformed(evt);
            }
        });

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

        norm.add(noNorm);
        noNorm.setText("no Normalization");
        noNorm.setToolTipText("");
        noNorm.setActionCommand("");
        noNorm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noNormActionPerformed(evt);
            }
        });

        norm.add(usingNorm);
        usingNorm.setText("using Normalization");
        usingNorm.setToolTipText("");
        usingNorm.setActionCommand("");
        usingNorm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usingNormActionPerformed(evt);
            }
        });

        uploadDoc.setText("Document");
        uploadDoc.setActionCommand("documentUpload");
        uploadDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadDocActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Stemming");

        stem.add(noStem);
        noStem.setText("no Stemming");
        noStem.setToolTipText("");
        noStem.setActionCommand("");

        stem.add(usingStem);
        usingStem.setText("using Stemming");

        filepathlabel.setText("File : ");

        Stopword.setText("Stop Word");
        Stopword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopwordActionPerformed(evt);
            }
        });

        filepathlabel2.setText("File : ");

        indexing.setText("Indexing");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
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
                                        .addComponent(noStem)))))))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uploadDoc)
                    .addComponent(filepathlabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Stopword)
                    .addComponent(filepathlabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rawTF)
                    .addComponent(usingIDF)
                    .addComponent(usingNorm)
                    .addComponent(usingStem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void noTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noTFActionPerformed

    private void rawTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rawTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rawTFActionPerformed

    private void logaritmicTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logaritmicTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logaritmicTFActionPerformed

    private void binaryTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_binaryTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_binaryTFActionPerformed

    private void augmentedTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_augmentedTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_augmentedTFActionPerformed

    private void usingIDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usingIDFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usingIDFActionPerformed

    private void noIDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noIDFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noIDFActionPerformed

    private void noNormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noNormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noNormActionPerformed

    private void usingNormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usingNormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usingNormActionPerformed

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
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
        //</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GUI().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Stopword;
    private javax.swing.JRadioButton augmentedTF;
    private javax.swing.JRadioButton binaryTF;
    private javax.swing.JLabel filepathlabel;
    private javax.swing.JLabel filepathlabel2;
    private javax.swing.ButtonGroup idf;
    private javax.swing.JButton indexing;
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
    private javax.swing.ButtonGroup norm;
    private javax.swing.JRadioButton rawTF;
    private javax.swing.ButtonGroup stem;
    private javax.swing.ButtonGroup tf;
    private javax.swing.JButton uploadDoc;
    private javax.swing.JRadioButton usingIDF;
    private javax.swing.JRadioButton usingNorm;
    private javax.swing.JRadioButton usingStem;
    // End of variables declaration//GEN-END:variables

}
