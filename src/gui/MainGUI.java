package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;
import stbisearch.*;

/**
 *
 * @author Cilvia
 */
public class MainGUI {
	public static DocumentProcess dp;
	public static QueryProcess qp;
	static Indexing guiIndexing;
	static Query guiQuerySetting;
	static Searching guiSearching;
	static MainMenu guiMainMenu;
	static MainFrame mainFrame;
	
	public MainGUI(){
		
	}
	
	public void init(){
		dp = new DocumentProcess();
		qp = new QueryProcess();
		guiIndexing = new Indexing();
		guiQuerySetting = new Query();
		guiSearching = new Searching();
		guiMainMenu = new MainMenu();
		mainFrame = new MainFrame();
		
		
		mainFrame.setContentPane(guiMainMenu);
		mainFrame.setVisible(true);
		mainFrame.setTitle("DoquSearch");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
	}
	
	public void showIndexingPanel(){
		mainFrame.setContentPane(guiIndexing);
		mainFrame.setTitle("DoquSearch - Indexing");
		mainFrame.revalidate();
	}
	
	public void showQuerySettingPanel(){
		mainFrame.setContentPane(guiQuerySetting);
		mainFrame.setTitle("DoquSearch - Query Setting");
		mainFrame.revalidate();
	}
	
	public void showSearchingPanel(){
		mainFrame.setContentPane(guiSearching);
		mainFrame.setTitle("DoquSearch - Searching");
		mainFrame.revalidate();
	}
	
	public void showMainMenuPanel(){
		mainFrame.setContentPane(guiMainMenu);
		mainFrame.setTitle("DoquSearch");
		mainFrame.revalidate();
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
//			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					javax.swing.UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
        //</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainGUI gc = new MainGUI();
				gc.init();
			}
		});
	}
}
