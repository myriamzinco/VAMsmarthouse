package it.unibs.VAMsmarthouse;

import java.util.ArrayList;

public class Centralina {
//prende tutti i valori e li manda in stampa.gestisce i sensori
	public Centralina() {
		SensoreTEsterna te = new SensoreTEsterna("se");
		SensoreTInterna t0 = new SensoreTInterna("t-0", te);
		SensoreTInterna t1 = new SensoreTInterna("t-1", te);
		SensoreQualità q = new SensoreQualità("q");
		SensoreMovimento m = new SensoreMovimento("m");

		addSensore(te);
		addSensore(t0);
		addSensore(t1);
		addSensore(q);
		addSensore(m);

	}

	private ArrayList<Sensore<?>> sensori = new ArrayList<>();
	private ArrayList<Elettrodomestico> elettrodomestici = new ArrayList<>();

	public void addSensore(Sensore<?> s) {
		sensori.add(s);

	}

	public ArrayList<Sensore<?>> getSensori() {
		return sensori;
	}

	public String statoSensori() { // prende tutti i sensori attivi e con sb costurisce ciò che vuole mostrare inn
									// interfaccia grafica
		StringBuilder sb = new StringBuilder();
		for (Sensore s : sensori) {
			if (s.running) {
				sb.append(s.toString()).append("\n");
			}
		}

		return sb.toString();
	}

	public void addElettrodomestico(Elettrodomestico e) {
		elettrodomestici.add(e);
	}

	public ArrayList<Elettrodomestico> getElettrodomestici() {
		return elettrodomestici;
	}

	public String statoElettrodomestici() { // prende tutti i sensori attivi e con sb costurisce ciò che vuole mostrare
											// inn interfaccia grafica
		StringBuilder sb = new StringBuilder();
		for (Elettrodomestico e : elettrodomestici) {
			if (e.running) {
				sb.append(e.toString()).append("\n");
			}
		}

		return sb.toString();
	}

	public void stopAll() {
		for (Sensore<?> s : sensori) {
			s.stop();
		}
		for (Elettrodomestico e : elettrodomestici) {
			e.stop();
		}
	}

}
