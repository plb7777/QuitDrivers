package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import app.ui.MainWindow;

public class Main {

	public static void main(String[] args) {
		System.setProperty("sun.java2d.d3d", "false");
		System.setProperty("sun.java2d.opengl", "false");
		System.setProperty("sun.java2d.xrender", "false");
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
		MainWindow app = new MainWindow();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				app.setVisible(true);
			}
		});
	}

}
