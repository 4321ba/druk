package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

public class Cistern extends ActiveElement {

	public Cistern(Coords c) {
		super(c);
	}
	int waterLevel = 0;
	@Override void randomEvent() {
		try {
			new Pipe(this, null);
		} catch (IOException e) {} // már pl nincs több hely a ciszternán
	}

	@Override void pullWater() {
		for (Pipe p : pipes)
			if (p != null && p.drainWater()) {
				PointCounter.get().addMechanicPoint();
				++waterLevel;
			}
	}
	

	@Override void pushWater() {
		for (Pipe p : pipes) {
			if (p != null && waterLevel > 0) {
				if (p.wasteWater()) {
					PointCounter.get().subtractMechanicPoint();
					--waterLevel;
				}
			}
		}
	}

	@Override Pump pickUpPump() {
		return new Pump(getCoords().copy());
	}

	@Override public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.drawString(Integer.toString(waterLevel), c.x-10, c.y-10);
		g.setColor(new Color(0, 245, 0));
		super.draw(g);
	}
}
