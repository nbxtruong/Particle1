import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class ParticleFrame extends JFrame {

	protected Thread[] threads; // null when not running

	protected int size = 400;

	protected ParticleComponent component = new ParticleComponent(size);

	public ParticleFrame() {
		setContentPane(component);
	}

	public static void main(String[] args) {
		final ParticleFrame frame = new ParticleFrame();
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				int c = arg0.getKeyChar();
				if (c == 's') {
					if (frame.threads == null) {
						frame.start();
					} else {
						frame.stop();
					}
				}
			}
		});
		frame.setFocusable(true);
		frame.requestFocus();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	protected Thread makeThread(final Particle p) { // utility
		Runnable runloop = new Runnable() {
			public void run() {
				try {
					for (;;) {
						p.move();
						component.repaint();
						Thread.sleep(100); // 100msec is arbitrary
					}
				} catch (InterruptedException e) {
					return;
				}
			}
		};
		return new Thread(runloop);
	}

	public synchronized void start() {
		int n = 10; // just for demo

		if (threads == null) { // bypass if already started
			Particle[] particles = new Particle[n];
			for (int i = 0; i < n; ++i)
				particles[i] = new Particle(size / 2, size / 2);
			component.setParticles(particles);

			threads = new Thread[n];
			for (int i = 0; i < n; ++i) {
				threads[i] = makeThread(particles[i]);
				threads[i].start();
			}
		}
	}

	public synchronized void stop() {
		if (threads != null) { // bypass if already stopped
			for (int i = 0; i < threads.length; ++i)
				threads[i].interrupt();
			threads = null;
		}
	}
}
