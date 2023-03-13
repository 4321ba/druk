package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

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
			if (p.drainWater())
				PointCounter.thePC.addMechanicPoint();
	}
	
	

	@Override public void draw(Graphics g) {
		g.setColor(new Color(0, 245, 0));
		super.draw(g);
	}
}
