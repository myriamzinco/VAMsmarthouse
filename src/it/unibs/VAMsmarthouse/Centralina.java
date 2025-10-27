package it.unibs.VAMsmarthouse;

import java.util.ArrayList;

public class Centralina {
//prende tutti i valori e li manda in stampa.gestisce i sensori

	private ArrayList<Sensore<?>> sensori = new ArrayList<>();
	private ArrayList<Elettrodomestico> elettrodomestici = new ArrayList<>();

	public void aggiungiSensore(Sensore<?> s) {
		sensori.add(s);
		new Thread(s).start();

	}

	public ArrayList<Sensore<?>> getSensori() {
		return sensori;
	}

	public void aggiungiElettrodomestico(Elettrodomestico e) {
		elettrodomestici.add(e);
		new Thread(e).start();
	}

	public ArrayList<Elettrodomestico> getElettrodomestico() {
		return elettrodomestici;
	}
}
