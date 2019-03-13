/**
 * Clase que permite el movimiento de la bola.
 * 
 * @author Alejandro Goicoechea Román
 *
 */
public class Movimiento implements Runnable {
	private final Ball bola;

	public Movimiento(Ball bola) {
		this.bola = bola;
	}

	@Override
	public void run() {
		while (true) {
			bola.move();
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				break;
			}
		}
	}
}
