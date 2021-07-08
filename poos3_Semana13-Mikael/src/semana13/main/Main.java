package semana13.main;

import javax.swing.SwingUtilities;

import semana13.graphic.WindowGraphic;

public class Main {
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(WindowGraphic::new);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
