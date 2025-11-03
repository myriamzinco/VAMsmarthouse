package it.unibs.VAMsmarthouse;

import java.util.ArrayList;

public class Centralina {
	private ArrayList<Sensore<?>> sensori = new ArrayList<>();
	private ArrayList<Elettrodomestico> elettrodomestici = new ArrayList<>();

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

		Asciugatrice a = new Asciugatrice("a");
		Lavatrice lvt = new Lavatrice("lvt");
		Lavastoviglie lvs = new Lavastoviglie("lvs");
		Robottino r = new Robottino("r");
		addElettrodomestico(a);
		addElettrodomestico(lvt);
		addElettrodomestico(lvs);
		addElettrodomestico(r);

	}

	public void addSensore(Sensore<?> s) {
		sensori.add(s);

	}

	public ArrayList<Sensore<?>> getSensori() {
		return sensori;
	}

	public String statoSensori() { // prende tutti i sensori attivi e con sb costurisce ciò che vuole mostrare inn
									// interfaccia grafica
		StringBuilder sb = new StringBuilder();
		for (Sensore<?> s : sensori) {
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

	public Boolean sensoriOn() {
		for (Sensore<?> s : sensori) {
			if (s.running)
				return true;
		}
		return false;
	}

	public Boolean elettOn() {
		for (Elettrodomestico e : elettrodomestici) {
			if (e.running)
				return true;
			if (e.getSensore() != null && e.getSensore().running)
				return true;
		}
		return false;
	}

	public void startAllS() {
		for (Sensore<?> s : sensori) {
			if (!s.running) {
				s.start();
			}
		}
	}

	public void startAllE() {
		for (Elettrodomestico e : elettrodomestici) {
			if (!e.running) {
				new Thread(e).start();

				Sensore<?> s = e.getSensore();
				if (s != null && !s.running) {
					new Thread(s).start();
				}
			}
		}
	}

	public void stopAllS() {
		for (Sensore<?> s : sensori) {
			s.stop();
		}
	}

	public void stopAllE() {
		for (Elettrodomestico e : elettrodomestici) {
			e.stop();
		}

	}

}
