package it.unibs.VAMsmarthouse;

import java.util.ArrayList;

public class Centralina {
//generiamo le arraylist che contengono i sensori e elettrodomestici con sensori
	private ArrayList<Sensore<?>> sensori = new ArrayList<>();
	private ArrayList<Elettrodomestico> elettrodomestici = new ArrayList<>();
//generiamo tutit i sensori
	protected SensoreTEsterna te = new SensoreTEsterna("se");
	protected SensoreTInterna t0 = new SensoreTInterna("t-0", te);
	protected SensoreTInterna t1 = new SensoreTInterna("t-1", te);
	protected SensoreMovimento m = new SensoreMovimento("m");
	protected SensoreQualità q = new SensoreQualità("q");
	protected Asciugatrice a = new Asciugatrice("a");
	protected Lavatrice lvt = new Lavatrice("lvt");
	protected Lavastoviglie lvs = new Lavastoviglie("lvs");
	protected Robottino r = new Robottino("r");

//prende tutti i valori e li manda in stampa.gestisce i sensori
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

//prende sensori e li mette nell'arraylist
	public void addSensore(Sensore<?> s) {
		sensori.add(s);

	}

//getter
	public ArrayList<Sensore<?>> getSensori() {
		return sensori;
	}

	public String statoSensori() { // prende tutti i sensori attivi e con string builder costurisce la stringa da
									// mostrare in interfaccia grafica
		StringBuilder sb = new StringBuilder();
		for (Sensore<?> s : sensori) {
			if (s.running) {
				sb.append(s.toString()).append("\n"); // include anche t0 e t1
			}
		}
		return sb.toString();
	}

//aggiunge l'elettrodomestico all'arraylist
	public void addElettrodomestico(Elettrodomestico e) {
		elettrodomestici.add(e);
	}

//getter 
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

//Controlla se almeno uno dei sensori è on
	public Boolean sensoriOn() {
		for (Sensore<?> s : sensori) {
			if (s.running)
				return true;
		}
		return false;
	}

//controlla se almeno uno dei sensori interni agli elettrodomestici è on
	public Boolean elettOn() {
		for (Elettrodomestico e : elettrodomestici) {
			if (e.running)
				return true;
			if (e.getSensore() != null && e.getSensore().running)
				return true;
		}
		return false;
	}

//fa partire tutti i sensori
	public void startAllS() {
		for (Sensore<?> s : sensori) {
			if (!s.running) {
				s.start();
			}
		}
	}

//fa partire tutti i sensori degli elettrodomestici 
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

//ferma tutti i sensori (ovvero ferma la generazione di valori a video)
	public void stopAllS() {
		for (Sensore<?> s : sensori) {
			s.stop();
		}
	}

//ferma tutti i sensori degli elettrodomestici (ovvero ferma la generazione di valori a video)
	public void stopAllE() {
		for (Elettrodomestico e : elettrodomestici) {
			e.stop();
		}

	}

}
