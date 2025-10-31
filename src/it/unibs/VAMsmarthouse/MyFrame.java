package it.unibs.VAMsmarthouse;

import javax.swing.JFrame;

public class MyFrame {
//interfaccia grafica legge i dati e visualizza a schermo
	public JFrame frame;

	public static void main(String[] args) {

		MyFrame window = new MyFrame();
		window.frame.setVisible(true);

	}

	public MyFrame() {
		initialize();
	}

	private void initialize() {

	}
}
