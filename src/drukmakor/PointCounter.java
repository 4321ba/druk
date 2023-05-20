package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

/**
 * számolja a pontokat
 */
public class PointCounter implements Drawable {
	/**
	 * szingleton, szóval privát konstruktor
	 */
	private PointCounter() {}
	/**
	 * az egyetlen példány
	 */
	private static PointCounter thePC = new PointCounter();
	/**
	 * az egyetlen példány megszerzése
	 */
	public static PointCounter get() {
		return thePC;
	}
	/**
	 * a mechanicok pontjai
	 */
	private int mechanicPoints = 0;
	/**
	 * a szabotőrök pontjai
	 */
	private int saboteurPoints = 0;
	/**
	 * hozzáad egyet a szabotőrök pontjaihoz
	 */
	public void addSaboteurPoint() {
		++saboteurPoints;
	}
	/**
	 * hozzáad egyet a mechanicok pontjaihoz
	 */
	public void addMechanicPoint() {
		++mechanicPoints;
	}
	/**
	 * kivon egyet a mechanicok pontjaiból
	 */
	public void subtractMechanicPoint() {
		--mechanicPoints;
	}
	/**
	 * nullázza a pontokat
	 */
	public void clear() {
		mechanicPoints = 0;
		saboteurPoints = 0;
	}
	/**
	 * kirajzolja g-re a pontokat
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(240,240,240));
		g.drawString("Mechanic points: " + mechanicPoints, 10, 30);
		g.drawString("Saboteur points: " + saboteurPoints, 10, 50);
	}
}
