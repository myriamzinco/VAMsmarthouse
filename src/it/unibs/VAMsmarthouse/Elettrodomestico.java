package it.unibs.VAMsmarthouse;

public abstract class Elettrodomestico implements Runnable {
	protected boolean running;
	protected long time;
	protected String id;
	protected String type;
	protected StatoElettrodomestico stato;

	public Elettrodomestico(String id, long time, String type) {
		this.id = id;
		this.time = time;
		this.type = type;
		this.stato = StatoElettrodomestico.SPENTO;

	}

	protected Sensore<?> sensore;

	public String getType() {
		return type;
	}

	public String getID() {
		return id;
	}

	public StatoElettrodomestico getStato() {
		return stato;
	}

	protected abstract StatoElettrodomestico generaStato();

	public void setSensore(Sensore<?> s) {
		this.sensore = s;
	}

	public Sensore<?> getSensore() {
		return sensore;
	}

	@Override
	public void run() {
		running = true;
		stato = StatoElettrodomestico.RUNNING;
		while (running) {
			stato = generaStato();

			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				running = false;
			}
		}
	}

	public void start() {
		if (!running) {
			running = true;
			new Thread(this).start();
		}
	}

	public void stop() {
		running = false;
		if (sensore != null) {
			sensore.stop();
		}
	}

	@Override
	public String toString() {
		return String.format("[%s] [%s] ID=%s, Stato=%s", Sensore.timeStamp(), type, id, stato != null ? stato : StatoElettrodomestico.SPENTO);
	}

}
