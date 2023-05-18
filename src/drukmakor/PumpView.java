package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class PumpView extends ActiveElementView {
	Pump pump;
	public PumpView(Pump p, Coords co) {
		super(p, co);
		pump = p;
	}
	@Override public void draw(Graphics g) {
		Coords nc = getCoordsForIdx(pump.getInPipeIdx());
		g.setColor(new Color(0, 230, 0));//zöldből a pirosba pumpál
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		nc = getCoordsForIdx(pump.getOutPipeIdx());
		g.setColor(new Color(230, 0, 0));
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		g.setColor(new Color(pump.getIsBroken()? 200 : 50, 0, 0));
		super.draw(g);
		if (!pump.getHasWater())
			return;
		Coords c = getCoords();
		g.setColor(new Color(100, 100, 245));
		g.fillOval(c.x-2, c.y+6, 5, 12);
		
	}
	public void setCoords(Coords c) {
		coords = c.copy();
	}
}
