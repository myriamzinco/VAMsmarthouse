package it.unibs.VAMsmarthouse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class Sensore<T> implements Runnable { // nel caso usi abstract uso implements

	protected boolean running;
	protected Date timestamp;
	protected long time;
	protected String id;
	protected String type;
	protected T value;

	public Sensore(String id, long time, String type) {
		this.id = id;
		this.time = time;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getID() {
		return id;
	}

	public T getValue() {
		return value;
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			value = generaValore();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				running = false;
			}
		}
	}

//aggiungere anche timeStamp lettura valori sia in classe sensore sia in elettrodomesito. Poi il metodo genera valore dovrà anche salvarmi l'orario perchè nell'interfaccia va stampato. L'ora va formattata in modo che mi stampi l'ora, i minutin e i secondi e va aggiunta nel toString
	protected abstract T generaValore(); // indica la specializzazione del sensore

	public static String timeStamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return LocalDateTime.now().format(formatter);
	}

	public void start() {
		if (!running) {
			running = true;
			new Thread(this).start();
		}
	}

	public void stop() {
		running = false;
	}

	@Override
	public String toString() {
		return String.format("[%s]  [%s] ID=%s, Value=%.2f °C", Sensore.timeStamp(), type, id, value);
	}

}
