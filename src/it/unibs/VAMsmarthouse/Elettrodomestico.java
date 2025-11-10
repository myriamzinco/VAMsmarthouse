package it.unibs.VAMsmarthouse;

public abstract class Elettrodomestico implements Runnable {
	protected boolean running;
	protected long time;
	protected String id;
	protected String type;
	protected StatoElettrodomestico stato;

//Costruttore
	public Elettrodomestico(String id, long time, String type) {
		this.id = id;
		this.time = time;
		this.type = type;
		this.stato = StatoElettrodomestico.SPENTO;

	}

//generiamo un sensore interno all'elettrodomestico
	protected Sensore<?> sensore;

//getter 
	public String getType() {
		return type;
	}

	public String getID() {
		return id;
	}

	public StatoElettrodomestico getStato() {
		return stato;
	}

//Metodo astratto che genera lo stato dell'elettrodomestico, verrà overridato in ogni sottoclasse
	protected abstract StatoElettrodomestico generaStato();

//Setter e getter del sensore 
	public void setSensore(Sensore<?> s) {
		this.sensore = s;
	}

	public Sensore<?> getSensore() {
		return sensore;
	}

//Metodo che fa partire il thread
	@Override
	public void run() {
		running = true;
		while (running) {
			stato = generaStato();

			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				running = false;
			}
		}
	}

//metodo che fa partire il sensore 
	public void start() {
		if (!running) {
			running = true;
			new Thread(this).start();
		}
	}

//stoppa il sensore
	public void stop() {
		running = false;
		if (sensore != null) {
			sensore.stop();
		}
	}

	@Override
	public String toString() {
		return String.format("[%s] [%s] ID=%s, Stato=%s", Sensore.timeStamp(), type, id,
				stato != null ? stato : StatoElettrodomestico.SPENTO);
	}

}
