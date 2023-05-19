package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Desert extends JPanel {

	private static Desert desert = new Desert();
	public static Desert get() { return desert; }
	private Desert() {}
	
	private static final long serialVersionUID = 1L;
	private List<Drawable> drawableList = new ArrayList<Drawable>();
	public synchronized void addDrawable(Drawable d) {
		drawableList.add(d);
	}
	public synchronized void clearDrawable() {
		drawableList.clear();
		addDrawable(PointCounter.get());
	}

	@Override protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
	    g.setColor(new Color(140, 78, 7));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(255,180,170));
		int i = 0;
		for (String s : KeyboardInput.getLayout())
			g.drawString(s, 10, 90 + 20 * i++);
		for (Drawable dr : drawableList)
			dr.draw(g);
	}
}
