/**
 * Se encarga de pintar las bolas.
 * 
 * @author Alejandro Goicoechea Román
 *
 */
public class Pintor implements Runnable {
	private final Board tablero;

	public Pintor(Board tablero) {
		this.tablero = tablero;
	}

	@Override
	public void run() {
		while (true) {
			try {
				tablero.repaint();
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				break;
			}
		}
	}
}
