package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class PointCounter implements Drawable {
	private PointCounter() {}
	private static PointCounter thePC = new PointCounter();
	public static PointCounter get() {
		return thePC;
	}
	private int mechanicPoints = 0;
	private int saboteurPoints = 0;

	void addSaboteurPoint() {
		++saboteurPoints;
	}
	void addMechanicPoint() {
		++mechanicPoints;
	}
	void subtractMechanicPoint() {
		--mechanicPoints;
	}
	public void clear() {
		mechanicPoints = 0;
		saboteurPoints = 0;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(240,240,240));
		g.drawString("Mechanic points: " + mechanicPoints, 10, 70);
		g.drawString("Saboteur points: " + saboteurPoints, 10, 90);
	}
}
