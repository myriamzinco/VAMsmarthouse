package it.unibs.VAMsmarthouse;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RadialGradientPaint;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MyFrame {
	public JFrame frame;
	public JPanel mainPanel;
	public CardLayout cardLayout;

	private static class Sfondo extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			int w = getWidth();
			int h = getHeight();

			float cx = w / 2f;
			float cy = h / 2f;
			float radius = Math.max(w, h) / 2f;
			Color colorStart = new Color(50, 150, 200);
			Color colorEnd = new Color(10, 10, 20);
			Color[] colors = { colorStart, colorEnd };
			float[] dist = { 0f, 1f };

			RadialGradientPaint rgp = new RadialGradientPaint(cx, cy, radius, dist, colors);
			g2d.setPaint(rgp);
			g2d.fillRect(0, 0, w, h);
			g2d.dispose();
		}
	}

	public MyFrame() { // è il costruttore
		initialize();
	}

	private void initialize() { // è quello che dovrei mettere nel costruttore ma faccio così perchè è più
								// ordinato
		frame = new JFrame("VAM Smart House"); // creo la cornice per davvero
		frame.setBounds(100, 100, 640, 480); // prende in input anche la posizione della finestra (x, y, w, h)
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // è la x per chiudere
		frame.setLayout(new BorderLayout()); // specifico il layout per la finestra

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout); // questo è il panel per gestire le card
		mainPanel.add(menuPanel(), "menu"); // prima card
		mainPanel.add(sensoriPanel(), "sensori"); // seconda card

		frame.add(mainPanel); // aggiunge il pannello che gestisce le card alla finestra
	}

	protected JPanel menuPanel() {
		JPanel menuPanel = new Sfondo(); // pannello con sfondo personalizzato
		menuPanel.setLayout(new GridBagLayout()); // layout a griglia flessibile
		GridBagConstraints gbc = new GridBagConstraints();

		// questo centra il bottone
		gbc.insets = new Insets(20, 0, 20, 0);
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.CENTER; // centra orizzontalmente

		// Etichetta
		JLabel casaLabel = new JLabel("VAM Smart House", SwingConstants.CENTER);
		casaLabel.setForeground(Color.WHITE);
		casaLabel.setFont(new Font("Arial", Font.BOLD, 35));
		casaLabel.setOpaque(false);

		gbc.gridy = 0; // prima riga
		menuPanel.add(casaLabel, gbc);

		JButton btnStart = new JButton("Avvio");
		btnStart.addActionListener(_ -> start());
		btnStart.setBackground(Color.WHITE);
		btnStart.setForeground(Color.BLACK);
		btnStart.setFocusPainted(false);
		btnStart.setFont(new Font("Arial", Font.BOLD, 20));
		btnStart.setOpaque(true);
		btnStart.setBorderPainted(false);
		btnStart.setPreferredSize(new Dimension(150, 60));

		gbc.gridy = 1; // seconda riga
		menuPanel.add(btnStart, gbc);

		return menuPanel;
	}

	protected JPanel sensoriPanel() {
		JPanel sensoriPanel = new Sfondo();
		sensoriPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(20, 0, 20, 0);
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		JLabel sensoriLabel = new JLabel("Sensori", SwingConstants.CENTER);
		sensoriLabel.setForeground(Color.WHITE);
		sensoriLabel.setFont(new Font("Arial", Font.BOLD, 35));
		sensoriLabel.setOpaque(false);
		gbc.gridy = 0;
		sensoriPanel.add(sensoriLabel, gbc);

		JTextArea jtx = new JTextArea(5, 20);
		jtx.setEditable(false);
		gbc.gridy = 1;
		sensoriPanel.add(jtx, gbc);

		JButton btnTemperatura = new JButton("Sensore tempreaturs");
		gbc.gridy = 2;
		sensoriPanel.add(btnTemperatura, gbc);
//centralina va spostato in classe centraliina, ma comunque qua va creato l'oggetto centralina. Poi fai qui un ciclo for per creare i bottoni
		Centralina centralina = new Centralina();
		javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
			jtx.setText(centralina.statoSensori());
		});
		timer.start();

		return sensoriPanel;
	}

	protected void start() {
		cardLayout.show(mainPanel, "sensori");

	}
//LISTA DELLE COSE DA FARE: 1 FARE LO STORICO (con append) e devi mettere lo storico, 2 AGGIUNGERE I BOTTONI DI TUTTO, 3 FARE LO START AND STOP PER POTER DECIDERE QUANDO FAR P

}
