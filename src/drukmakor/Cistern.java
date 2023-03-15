package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

public class Cistern extends ActiveElement {

	public Cistern(Coords c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	int waterLevel = 0;
	boolean danglingPipe = false;
	@Override void randomEvent() {
		danglingPipe = true;
	}

	@Override void pullWater() {
		for (Pipe p : pipes)
			if (p.drainWater()) {
				PointCounter.get().addMechanicPoint();
				++waterLevel;
			}
	}
	

	@Override void pushWater() {
			if (danglingPipe && waterLevel > 0) {
				PointCounter.get().addSaboteurPoint();
				PointCounter.get().subtractMechanicPoint();
				--waterLevel;
			}
	}

	@Override boolean pickUpPump() {
		return true;
	}
	@Override Pipe pickUpDanglingPipe() {
		if (!danglingPipe)
			return null;
		danglingPipe = false;
		try {
			Pipe pi = new Pipe(this, null);
			Main.registernewpipe(pi);
			return pi;
		} catch (IOException e) {
			return null; // nem sikerült a csövet létrehozni, az aktív elemek nem fogadták be pl max csőszámot meghaladja TODO ilyenkor danglingPipeot visszaállíthatjuk falsera??
		}
	}

	@Override public void draw(Graphics g) {
		if (danglingPipe) {
			g.setColor(new Color(0, 0, 0));
			g.drawLine(c.x, c.y, c.x-10, c.y-10);
		}
		g.setColor(new Color(255, 255, 255));
		g.drawString(Integer.toString(waterLevel), c.x-10, c.y-10);
		g.setColor(new Color(0, 245, 0));
		super.draw(g);
	}
}
