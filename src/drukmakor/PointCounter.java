package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class PointCounter implements Drawable {
	private PointCounter() {}
	static PointCounter thePC = new PointCounter();
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
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(240,240,240));
		g.drawString("Mechanic points: " + mechanicPoints, 10, 70);
		g.drawString("Saboteur points: " + saboteurPoints, 10, 90);
	}
}
