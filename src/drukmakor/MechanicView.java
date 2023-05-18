package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class MechanicView extends CharacterView {
	Mechanic mechanic;
	public MechanicView(Mechanic m) {
		super(m);
		mechanic = m;
	}
	@Override public void draw(Graphics g) {
		Pump hp = mechanic.getHoldingPump();
		Pipe hpi = mechanic.getHoldingPipe();
		Coords cpc = mechanic.getCurrentPosition().getView().getCoords();
		if (hp!=null)
			hp.getView().setCoords(new Coords(cpc.x+6, cpc.y-14));
		if (hpi!=null)
			hpi.getView().setCoords(new Coords(cpc.x, cpc.y-10));
		g.setColor(new Color(255,255,255));
		super.draw(g);
	}
}
