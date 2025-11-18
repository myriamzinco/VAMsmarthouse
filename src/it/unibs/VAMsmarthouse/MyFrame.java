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
	public CardLayout cardLayout;

	private JScrollPane scrollSensori, scrollElettro;
	private JPanel leftPanelS;
	private JPanel leftPanelE;
	private JButton closeStoricoS;
	private JButton closeStoricoE;
	private javax.swing.Timer timerSensoriPanel, timerElettroPanel;
	private Centralina centralina = new Centralina();

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

	public MyFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("VAM Smart House");
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		mainPanel.add(menuPanel(), "menu");

		mainPanel.add(createPanel("Sensori", centralina.getSensori(), centralina::startAllS, centralina::stopAllS,
				s -> s.getType(), s -> s.start(), s -> s.stop(), () -> centralina.statoSensori()), "Sensori");

		mainPanel.add(createPanel("Elettrodomestici", centralina.getElettrodomestici(), centralina::startAllE,
				centralina::stopAllE, e -> e.getType(), e -> e.start(), e -> e.stop(),
				() -> centralina.statoElettrodomestici()), "Elettrodomestici");

		frame.add(mainPanel);
	}

	protected void viewPanelS() {
		cardLayout.show(mainPanel, "Sensori");
	}

	protected void viewPanelE() {
		cardLayout.show(mainPanel, "Elettrodomestici");
	}

	protected JPanel menuPanel() {
		JPanel menuPanel = new Sfondo();
		menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(20, 0, 20, 0);
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		JLabel casaLabel = new JLabel("VAM Smart House", SwingConstants.CENTER);
		casaLabel.setForeground(Color.WHITE);
		casaLabel.setFont(new Font("Arial", Font.BOLD, 35));
		casaLabel.setOpaque(false);
		gbc.gridy = 0;
		menuPanel.add(casaLabel, gbc);

		JButton btnViewS = new JButton("Visualizza sensori");
		btnViewS.addActionListener(_ -> viewPanelS());
		btnViewS.setOpaque(false);
		btnViewS.setForeground(Color.BLACK);
		btnViewS.setFocusPainted(false);
		btnViewS.setFont(new Font("Arial", Font.BOLD, 16));
		btnViewS.setOpaque(true);
		btnViewS.setBorderPainted(false);
		btnViewS.setPreferredSize(new Dimension(250, 60));

		JButton btnViewE = new JButton("Visualizza elettrodomestici");
		btnViewE.addActionListener(_ -> viewPanelE());
		btnViewE.setOpaque(false);
		btnViewE.setForeground(Color.BLACK);
		btnViewE.setFocusPainted(false);
		btnViewE.setFont(new Font("Arial", Font.BOLD, 16));
		btnViewE.setOpaque(true);
		btnViewE.setBorderPainted(false);
		btnViewE.setPreferredSize(new Dimension(250, 60));

		JButton btnStartAllS = new JButton("Avvia tutti i sensori");
		btnStartAllS.addActionListener(_ -> {
			centralina.startAllS();
			scrollSensori.setVisible(true);
			closeStoricoS.setVisible(true);
			cardLayout.show(mainPanel, "Sensori");
			if (timerSensoriPanel != null && !timerSensoriPanel.isRunning()) {
				timerSensoriPanel.start();
			}
			for (Component comp : leftPanelS.getComponents()) {
				if (comp instanceof JButton btn) {

					btn.setText(btn.getText() + " Stop");
					btn.putClientProperty("running", true);
				}
			}
		});
		btnStartAllS.setOpaque(false);
		btnStartAllS.setForeground(Color.BLACK);
		btnStartAllS.setFocusPainted(false);
		btnStartAllS.setFont(new Font("Arial", Font.BOLD, 16));
		btnStartAllS.setOpaque(true);
		btnStartAllS.setBorderPainted(false);
		btnStartAllS.setPreferredSize(new Dimension(250, 60));

		JButton btnStartAllE = new JButton("Controllo elettrodomestici");
		btnStartAllE.addActionListener(_ -> {
			centralina.startAllE();
			scrollElettro.setVisible(true);
			closeStoricoE.setVisible(true);

			cardLayout.show(mainPanel, "Elettrodomestici");

			if (timerElettroPanel != null) {
				if (!timerElettroPanel.isRunning()) {
					timerElettroPanel.start();
				}

				for (Component comp : leftPanelE.getComponents()) {
					if (comp instanceof JButton btn) {
						btn.setText(btn.getText() + " Stop");
						btn.putClientProperty("running", true);
					}
				}
			}
		});
		btnStartAllE.setOpaque(false);
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
		gbc.gridy = 1;
		menuPanel.add(BtnPanel, gbc);

		return menuPanel;
	}

	private <T> JPanel createPanel(String titolo, java.util.List<T> elementi, Runnable startAll, Runnable stopAll,
			java.util.function.Function<T, String> getType, java.util.function.Consumer<T> startElemento,
			java.util.function.Consumer<T> stopElemento, java.util.function.Supplier<String> statoSupplier) {

		JPanel panel = new Sfondo();
		panel.setLayout(new BorderLayout());

		JTextArea storico = new JTextArea(25, 40);
		storico.setEditable(false);
		storico.setLineWrap(true);
		storico.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(storico);
		scroll.setPreferredSize(new Dimension(400, 500));
		scroll.setVisible(false);

		JButton closeStorico = new JButton("X");
		closeStorico.setFont(new Font("Arial", Font.BOLD, 14));
		closeStorico.setOpaque(true);
		closeStorico.setBackground(Color.WHITE);
		closeStorico.setForeground(Color.BLACK);
		closeStorico.setFocusPainted(false);
		closeStorico.setBorderPainted(false);
		closeStorico.setPreferredSize(new Dimension(50, 40));
		closeStorico.setVisible(false);
		closeStorico.addActionListener(_ -> {

			scroll.setVisible(false);
			closeStorico.setVisible(false);
		});
		if (titolo.equals("Sensori")) {
			closeStoricoS = closeStorico;
		} else {
			closeStoricoE = closeStorico;
		}

		JPanel barraExit = new JPanel(new BorderLayout());
		barraExit.setOpaque(true);
		barraExit.setBackground(Color.WHITE);
		barraExit.add(closeStorico, BorderLayout.EAST);

		JPanel storicoPanel = new JPanel(new BorderLayout());
		storicoPanel.add(barraExit, BorderLayout.NORTH);
		storicoPanel.add(scroll, BorderLayout.CENTER);
		panel.add(storicoPanel, BorderLayout.EAST);

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
			if (titolo.equals("Sensori") && (elemento instanceof SensoreTInterna)) {
				continue; // salta t0 e t1 perchè devo creare il bottone a parte per metterli insieme
			}
			JButton btn = new JButton(getType.apply(elemento));
			btn.setPreferredSize(new Dimension(250, 40));
			btn.setFont(new Font("Arial", Font.BOLD, 16));
			btn.putClientProperty("running", false); // proprietà running
			btn.addActionListener(_ -> {
				boolean running = (boolean) btn.getClientProperty("running");
				if (!running) {
					startElemento.accept(elemento);
					btn.setText(getType.apply(elemento) + " Stop");
					btn.putClientProperty("running", true);
					storico.append(getType.apply(elemento) + " Avviato\n");
					scroll.setVisible(true);
					closeStorico.setVisible(true);
					if (titolo.equals("Sensori")) {
						if (timerSensoriPanel != null && !timerSensoriPanel.isRunning())
							timerSensoriPanel.start();
					} else if (titolo.equals("Elettrodomestici")) {
						if (timerElettroPanel != null && !timerElettroPanel.isRunning())
							timerElettroPanel.start();
					}
				} else {
					stopElemento.accept(elemento);
					btn.setText(getType.apply(elemento));
					btn.putClientProperty("running", false);
					storico.append(getType.apply(elemento) + " Fermato\n");
				}
			});

			gbc.gridy = y++;
			centerPanel.add(btn, gbc);
		}

		if (titolo.equals("Sensori")) {
			SensoreTInterna t0 = centralina.t0;
			SensoreTInterna t1 = centralina.t1;
			JButton btnTempInterna = new JButton("Temperature Interne");

			btnTempInterna.setPreferredSize(new Dimension(250, 40));
			btnTempInterna.setOpaque(false);
			btnTempInterna.setFont(new Font("Arial", Font.BOLD, 16));
			btnTempInterna.putClientProperty("running", false);
			gbc.gridy = y++;
			centerPanel.add(btnTempInterna, gbc);
			btnTempInterna.addActionListener(_ -> {
				if (!btnTempInterna.getText().endsWith("Stop")) {
					t0.start();
					t1.start();
					btnTempInterna.setText("Temperature Interne Stop");
					btnTempInterna.putClientProperty("running", true);
					storico.append(String.format("Temperature interne avviate\n"));
					scroll.setVisible(true);
					closeStorico.setVisible(true);
					if (timerSensoriPanel != null && !timerSensoriPanel.isRunning())
						timerSensoriPanel.start();
				} else {
					t0.stop();
					t1.stop();
					btnTempInterna.setText("Temperature Interne");
					btnTempInterna.putClientProperty("running", false);
					storico.append(String.format("Temperature interne fermate\n"));
				}
			});
		}

		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setOpaque(false);

		JButton back = new JButton("Indietro");
		back.addActionListener(_ -> cardLayout.show(mainPanel, "menu"));
		bottomPanel.add(back, BorderLayout.WEST);

		JButton stop = new JButton("Stop tutti");
		stop.addActionListener(_ -> {
			stopAll.run();
			storico.append("Tutti fermati\n");
			for (Component comp : centerPanel.getComponents()) {
				if (comp instanceof JButton btn) {
					String text = btn.getText();
					if (text.endsWith(" Stop")) {
						btn.setText(text.substring(0, text.length() - 5));
						btn.putClientProperty("running", false);

					}
				}
			}
		});
		bottomPanel.add(stop, BorderLayout.EAST);

		panel.add(bottomPanel, BorderLayout.SOUTH);

		// Timer storico
		javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
			String stato = statoSupplier.get();
			if (!stato.isEmpty()) {
				storico.append(stato + "\n");
			}
		});
		timer.stop();

		// Associa i componenti (scroll, pannello pulsanti e timer) al tipo di pannello
		// creato per poterli poi gestire dal menu principale
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