package it.unibs.VAMsmarthouse;

import java.util.ArrayList;

public class Centralina {
	private ArrayList<Sensore<?>> sensori = new ArrayList<>();
	private ArrayList<Elettrodomestico> elettrodomestici = new ArrayList<>();
	protected SensoreTEsterna te = new SensoreTEsterna("se");
	protected SensoreTInterna t0 = new SensoreTInterna("t-0", te);
	protected SensoreTInterna t1 = new SensoreTInterna("t-1", te);
	protected SensoreMovimento m = new SensoreMovimento("m");
	protected SensoreQualità q = new SensoreQualità("q");
	protected Asciugatrice a = new Asciugatrice("a");
	protected Lavatrice lvt = new Lavatrice("lvt");
	protected Lavastoviglie lvs = new Lavastoviglie("lvs");
	protected Robottino r = new Robottino("r");

//prende tutti i valori e li manda in stampa. gestisce i sensori
	public Centralina() {
		addSensore(te);
		addSensore(t0);
		addSensore(t1);
		addSensore(m);
		addSensore(q);

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

	public String statoSensori() {
		StringBuilder sb = new StringBuilder();
		for (Sensore<?> s : sensori) {
			if (s.running) {
				sb.append(s.toString()).append("\n"); // include anche t0 e t1
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

	public String statoElettrodomestici() {
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
