package drukmakor;

import java.awt.*;
import java.awt.image.BufferedImage;
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

	private final BufferedImage sandImage;

	private Desert() {
		try {
			sandImage = javax.imageio.ImageIO.read(new java.io.File("sand.jpg"));
		} catch (java.io.IOException e) {
			throw new RuntimeException(e);
		}
	}
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
		TexturePaint tp = new TexturePaint(sandImage, new Rectangle(0, 0, sandImage.getWidth(), sandImage.getHeight()));

		/*g.setColor(new Color(140, 78, 7));
		g.fillRect(0, 0, getWidth(), getHeight());*/

		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(tp);
		g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));

		g.setColor(new Color(255/2,180/2,170/2));
		int i = 0;
		for (String s : KeyboardInput.getLayout())
			g.drawString(s, 10, 90 + 20 * i++);
		for (Drawable dr : drawableList)
			dr.draw(g);
	}
}
