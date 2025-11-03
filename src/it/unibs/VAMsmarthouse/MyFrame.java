package it.unibs.VAMsmarthouse;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RadialGradientPaint;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MyFrame {
	public JFrame frame;
	public JPanel mainPanel;
	private JScrollPane scrollSensori, scrollElettro;
	private JPanel leftPanelS;
	private JPanel leftPanelE;
	private boolean storicoSVisibile = false;
	private boolean storicoEVisibile = false;;
	public CardLayout cardLayout;
	private javax.swing.Timer timerSensoriPanel, timerElettroPanel;
	private Centralina centralina = new Centralina();

//Sfondo bellino
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
		frame.add(mainPanel); // aggiunge il pannello che gestisce le card alla finestra

		mainPanel.add(createPanel("Sensori", centralina.getSensori(), centralina::startAllS, centralina::stopAllS,
				s -> s.getType(), s -> s.start(), s -> s.stop(), () -> centralina.statoSensori()), "sensori");

		mainPanel.add(createPanel("Elettrodomestici", centralina.getElettrodomestici(), centralina::startAllE,
				centralina::stopAllE, e -> e.getType(), e -> e.start(), e -> e.stop(),
				() -> centralina.statoElettrodomestici()), "elettrodomestici");
	}

	protected void viewPanelS() {
		cardLayout.show(mainPanel, "sensori"); // "sensori" è il nome della card creata con creaPannello
	}

	protected void viewPanelE() {
		cardLayout.show(mainPanel, "elettrodomestici"); // nome della card corrispondente
	}

//Pannello menù
	protected JPanel menuPanel() {
		JPanel menuPanel = new Sfondo();
		menuPanel.setLayout(new GridBagLayout());
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

		JButton btnViewS = new JButton("Visualizza sensori");
		btnViewS.addActionListener(_ -> viewPanelS());
		btnViewS.setBackground(Color.WHITE);
		btnViewS.setForeground(Color.BLACK);
		btnViewS.setFocusPainted(false);
		btnViewS.setFont(new Font("Arial", Font.BOLD, 16));
		btnViewS.setOpaque(true);
		btnViewS.setBorderPainted(false);
		btnViewS.setPreferredSize(new Dimension(250, 60));

		JButton btnViewE = new JButton("Visualizza elettrodomestici");
		btnViewE.addActionListener(_ -> viewPanelE());
		btnViewE.setBackground(Color.WHITE);
		btnViewE.setForeground(Color.BLACK);
		btnViewE.setFocusPainted(false);
		btnViewE.setFont(new Font("Arial", Font.BOLD, 16));
		btnViewE.setOpaque(true);
		btnViewE.setBorderPainted(false);
		btnViewE.setPreferredSize(new Dimension(250, 60));

		JButton btnStartAllS = new JButton("Avvia tutti i sensori");
		btnStartAllS.addActionListener(_ -> {
			centralina.startAllS();
			storicoSVisibile = true;
			scrollSensori.setVisible(true);
			cardLayout.show(mainPanel, "sensori");
			if (timerSensoriPanel != null && !timerSensoriPanel.isRunning()) {
				timerSensoriPanel.start();
			}
			for (Component comp : leftPanelS.getComponents()) {
				if (comp instanceof JButton btn) {
					String text = btn.getText();
					if (text.startsWith("Avvia")) {
						String type = text.replace("Avvia ", "");
						btn.setText(type + " Stop ");
					}
				}
			}
		});
		btnStartAllS.setBackground(Color.WHITE);
		btnStartAllS.setForeground(Color.BLACK);
		btnStartAllS.setFocusPainted(false);
		btnStartAllS.setFont(new Font("Arial", Font.BOLD, 16));
		btnStartAllS.setOpaque(true);
		btnStartAllS.setBorderPainted(false);
		btnStartAllS.setPreferredSize(new Dimension(250, 60));

		JButton btnStartAllE = new JButton("Controllo elettrodomestici");
		btnStartAllE.addActionListener(_ -> {
			centralina.startAllE();
			storicoEVisibile = true;
			scrollElettro.setVisible(true);
			cardLayout.show(mainPanel, "elettrodomestici");

			if (timerElettroPanel != null) {
				if (!timerElettroPanel.isRunning()) {
					timerElettroPanel.start();
				}
			}
			for (Component comp : leftPanelE.getComponents()) {
				if (comp instanceof JButton btn) {
					String text = btn.getText();
					if (text.startsWith("Avvia")) {
						String type = text.replace("Avvia ", "");
						btn.setText(type + " Stop ");
					}
				}
			}
		});
		btnStartAllE.setBackground(Color.WHITE);
		btnStartAllE.setForeground(Color.BLACK);
		btnStartAllE.setFocusPainted(false);
		btnStartAllE.setFont(new Font("Arial", Font.BOLD, 16));
		btnStartAllE.setOpaque(true);
		btnStartAllE.setBorderPainted(false);
		btnStartAllE.setPreferredSize(new Dimension(250, 60));

		JPanel BtnPanel = new JPanel(new GridLayout(2, 2, 20, 20));
		BtnPanel.setPreferredSize(new Dimension(550, 160));
		BtnPanel.setOpaque(false);
		BtnPanel.add(btnViewS);
		BtnPanel.add(btnViewE);
		BtnPanel.add(btnStartAllS);
		BtnPanel.add(btnStartAllE);

		gbc.gridy = 1; // seconda riga
		menuPanel.add(BtnPanel, gbc);

		return menuPanel;
	}

	private <T> JPanel createPanel(String titolo, java.util.List<T> elementi, Runnable startAll, Runnable stopAll,
			java.util.function.Function<T, String> getType, java.util.function.Consumer<T> startElemento,
			java.util.function.Consumer<T> stopElemento, java.util.function.Supplier<String> statoSupplier) {

		JPanel panel = new Sfondo();
		panel.setLayout(new BorderLayout());

		// --- Area storico ---
		JTextArea storico = new JTextArea(25, 40);
		storico.setEditable(false);
		JScrollPane scroll = new JScrollPane(storico);
		scroll.setPreferredSize(new Dimension(400, 500));
		scroll.setVisible(false);
		panel.add(scroll, BorderLayout.EAST);

		// --- Pannello centrale con i bottoni ---
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.anchor = GridBagConstraints.CENTER;

		JLabel titleLabel = new JLabel(titolo, SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
		gbc.gridy = 0;
		centerPanel.add(titleLabel, gbc);

		int y = 1;
		for (T elemento : elementi) {
			JButton btn = new JButton("Avvia " + getType.apply(elemento));
			btn.setPreferredSize(new Dimension(250, 40));
			btn.setBackground(Color.WHITE);
			btn.setFont(new Font("Arial", Font.BOLD, 16));

			btn.addActionListener(_ -> {
				if (btn.getText().startsWith("Avvia")) {
					startElemento.accept(elemento);
					btn.setText(getType.apply(elemento) + " Stop");
					storico.append(getType.apply(elemento) + " Avviato\n");
					scroll.setVisible(true);
					if (titolo.equals("Sensori")) {
						if (timerSensoriPanel != null && !timerSensoriPanel.isRunning())
							timerSensoriPanel.start();
					} else if (titolo.equals("Elettrodomestici")) {
						if (timerElettroPanel != null && !timerElettroPanel.isRunning())
							timerElettroPanel.start();
					}
				} else {
					stopElemento.accept(elemento);
					btn.setText("Avvia " + getType.apply(elemento));
					storico.append(getType.apply(elemento) + " Fermato\n");
				}
			});

			gbc.gridy = y++;
			centerPanel.add(btn, gbc);
		}

		panel.add(centerPanel, BorderLayout.CENTER);

		// --- Pannello inferiore con Indietro e Stop All ---
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setOpaque(false);

		JButton back = new JButton("Indietro");
		back.addActionListener(_ -> cardLayout.show(mainPanel, "menu"));
		bottomPanel.add(back, BorderLayout.WEST);

		JButton stop = new JButton("Stop tutti");
		stop.addActionListener(_ -> {
			stopAll.run();
			storico.append("Tutti fermati\n");
		});
		bottomPanel.add(stop, BorderLayout.EAST);

		panel.add(bottomPanel, BorderLayout.SOUTH);

		// --- Timer aggiornamento storico ---
		javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
			String stato = statoSupplier.get();
			if (!stato.isEmpty()) {
				storico.append(stato + "\n");
			}
		});
		timer.stop();
		if (titolo.equals("Sensori")) {
			scrollSensori = scroll;
			leftPanelS = centerPanel;
			timerSensoriPanel = timer;
		} else if (titolo.equals("Elettrodomestici")) {
			scrollElettro = scroll;
			leftPanelE = centerPanel;
			timerElettroPanel = timer;
		}

		return panel;
	}

}
