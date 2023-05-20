package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
/**
 * a dolgok képernyőre való rajzolását végzi egy jpanelre
 */
public class Desert extends JPanel {
	/**
	 * szingleton, szóval privát konstruktor
	 */
	private Desert() {}
	/**
	 * az egyetlen példány
	 */
	private static Desert desert = new Desert();
	/**
	 * az egyetlen példány megszerzése
	 */
	public static Desert get() { return desert; }
	/**
	 * no comment
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * a kirajzolandó objektumok listája
	 */
	private List<Drawable> drawableList = new ArrayList<Drawable>();
	/**
	 * hozzáadja d-t a kirajzolandó objektumok listájához
	 * @param d
	 */
	public synchronized void addDrawable(Drawable d) {
		drawableList.add(d);
	}
	/**
	 * törli a kirajzolandó objektumok listáját,
	 * de a pointcounter mindig rajta kell legyen
	 */
	public synchronized void clearDrawable() {
		drawableList.clear();
		addDrawable(PointCounter.get());
	}

	/**
	 * kirajzol mindent, külön a help szöveget (billentyűkiosztás),
	 * majd az összes drawableList-beli objektumot
	 */
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
