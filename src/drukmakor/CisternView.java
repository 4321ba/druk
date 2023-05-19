package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class CisternView extends ActiveElementView {
	Cistern model;
	public CisternView(Cistern m, Coords c) {
		super(c);
		model = m;
	}
	
	@Override public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.drawString(Integer.toString(model.getWaterLevel()), getCoords().x-10, getCoords().y-10);
		g.setColor(new Color(0, 245, 0));
		super.draw(g);
	}
	@Override
	protected Cistern getModel() {
		return model;
	}
}
