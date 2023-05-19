package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class MechanicView extends CharacterView {
	Mechanic model;
	public MechanicView(Mechanic m) {
		model = m;
	}
	@Override public void draw(Graphics g) {
		Pump hp = model.getHoldingPump();
		Pipe hpi = model.getHoldingPipe();
		Coords cpc = model.getCurrentPosition().getView().getCoords();
		if (hp != null)
			hp.getView().setCoords(new Coords(cpc.x+6, cpc.y-14));
		if (hpi != null)
			hpi.getView().setCoords(new Coords(cpc.x, cpc.y-10));
		g.setColor(new Color(255,255,255));
		super.draw(g);
	}
	@Override
	protected Mechanic getModel() {
		return model;
	}
}
