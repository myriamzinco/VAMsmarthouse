package it.unibs.VAMsmarthouse;

import java.util.Random;

public class SensoreQualità extends Sensore<Double> {
	private boolean inizializzato;
	private final Random random = new Random();
	private double umidita; /*
							 * private o public questi due? Private per l'incapsulamento, servono solo in
							 * questa classe
							 */
	private double co2; // cercando su internet dovrebbero essere ppm

	public SensoreQualità(String id) {
		super(id, 500, "Qualità dell'aria");
	}

	@Override
	protected Double generaValore() {
		if (inizializzato == false) {
			umidita = 20 + random.nextDouble() * 55;
			co2 = 400 + random.nextDouble() * 1000;
			inizializzato = true;
		} else if (inizializzato == true) {
			double variazioneU = (-0.5 + random.nextDouble()) * 2;
			umidita += variazioneU;
			double variazioneCO2 = (-0.5 + random.nextDouble()) * 30;
			co2 += variazioneCO2;
		}

		if (umidita < 25)
			umidita = 25; // manteniamo dei valori realistici in modo tale che non generi robe strane
		if (umidita > 75)
			umidita = 75;
		if (co2 < 350)
			co2 = 350;
		if (co2 > 2000)
			co2 = 2000;

		return co2;

		/*
		 * qui abbiamo chiesto a chat come poter avere una base dell'umidita per
		 * riuscire ad avere co2 coerente; aggiungiamo una piccola variazione in co2 e
		 * in umidita in modo tale che sia coerente con la logica degli altri sensori
		 * (vedi metodo genera valore in temperatura interna) e coerente con la realtà
		 */

	}

	public double getUmidita() {
		return umidita;
	}

	public double getCO2() {
		return co2;
	}

	@Override
	public String toString()
	/*
	 * quello che hai fatto qui con il controllo degli if va bene, ma non puoi
	 * metterlo nel genera valore. Il genera valore genera solo co2 e lorestituisce,
	 * è il to String che stampa, e facendo l'override del toString tupuoi stampare
	 * quello che vuoi
	 */
	{
		StringBuilder messaggio = new StringBuilder();/*
														 * introduciamo questa variabile così non devi stampare ogni
														 * volta il messaggio, ma metti il riferimento nel to string
														 * così che ti stampi il valore che assume quando entra negli if
														 */
		boolean warning = false;
		messaggio.append(String.format("[Sensore %s] Umidità: %.1f%% | CO₂: %.0f ppm → ", id, umidita, co2));
		if (co2 > 1500) {
			warning = true;
			messaggio.append("CO2 alta, fortemente consigliato aprire le finestre");
		} else if (co2 > 1000) {
			warning = true;
			messaggio.append("Consigliabile aprire le finestre");
		}

		if (umidita < 30) {
			warning = true;
			messaggio.append("Aria troppo secca");
		} else if (umidita > 70) {
			warning = true;
			messaggio.append("Aria troppo umida");
		}

		if (warning == false) {
			messaggio.append("Qualità dell'aria ottimale!");
		}
		return messaggio.toString();

	}

}
