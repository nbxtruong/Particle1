import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

/** From Doug Lea */

public class ParticleComponent extends JComponent {

	private Particle[] particles = new Particle[0];

	ParticleComponent(int size) {
		setPreferredSize(new Dimension(size, size));
	}

	// Intended to be called by applet
	public synchronized void setParticles(Particle[] ps) {
		if (ps == null)
			throw new IllegalArgumentException("Cannot set null");

		particles = ps;
	}

	protected synchronized Particle[] getParticles() {
		return particles;
	}

	public void paintComponent(Graphics g) { // override JComponent.paint
		Particle[] ps = getParticles();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
		for (Particle p : ps)
			p.draw(g);
	}
}