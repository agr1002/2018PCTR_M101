import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase billar que corresponde al juego de billar
 * 
 * TODO Transform the code to be used safely in a concurrent context.
 * 
 * @author Carlos López Nozal - Alejandro Goicoechea Román
 */
@SuppressWarnings("serial")
public class Billiards extends JFrame {

	public static int Width = 800;
	public static int Height = 600;

	private JButton b_start, b_stop;

	private Board board;

	private ExecutorService ejecutor;
	// Update with number of group label. See practice statement. Nos pedía 6 bolas.
	private final int N_BALL = 6;
	private Ball[] balls;
	protected Thread[] threads;
	
	public Billiards() {

		board = new Board();
		board.setForeground(new Color(0, 128, 0));
		board.setBackground(new Color(0, 128, 0));

		initBalls();

		b_start = new JButton("Empezar");
		b_start.addActionListener(new StartListener());
		b_stop = new JButton("Parar");
		b_stop.addActionListener(new StopListener());

		JPanel p_Botton = new JPanel();
		p_Botton.setLayout(new FlowLayout());
		p_Botton.add(b_start);
		p_Botton.add(b_stop);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(p_Botton, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setTitle("Práctica programación concurrente objetos móviles independientes");
		setResizable(false);
		setVisible(true);
	}

	private void initBalls() {
		balls = new Ball[N_BALL];
		for (int i = 0; i < N_BALL; i++) {
			balls[i] = new Ball();
		}
		board.setBalls(balls);
		// Init balls
	}

	protected Thread makeThread(final Ball b) {
		Runnable runloop = new Runnable() {
			public void run() {
				try {
					for (;;) {
						b.move();
						Thread.sleep(100); // 100msec arbitrario
					}
				} catch (InterruptedException e) {
					return;
				}
			}
		};
		return new Thread(runloop);
	}

	private class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (threads == null) {
				balls = new Ball[N_BALL];
				for(int i = 0; i < N_BALL; ++i) {
					balls[i] = new Ball();
				}
				threads = new Thread[N_BALL];
				for (int i = 0; i < N_BALL; ++i) {
					threads[i] = makeThread(balls[i]);
					threads[i].start();
				}
			}
			// executor newFiexedThreadPool, si son null, instanciar nuevo Thread con el nº
			// de bolas, hace un for hasta el nº de bolas y dentro instanciar por cada
			// número
			// TODO Code is executed when start button is pushed

		}
	}

	private class StopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (threads != null) {
				for (int i = 0; i < threads.length; ++i) {
					threads[i].interrupt();
				}
				threads = null;
			}
			// TODO Code is executed when stop button is pushed

		}
	}

	public static void main(String[] args) {
		new Billiards();
	}
}