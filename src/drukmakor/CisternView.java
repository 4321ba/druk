package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class CisternView extends ActiveElementView {
	Cistern cistern;
	public CisternView(Cistern c, Coords co) {
		super(c, co);
		cistern = c;
	}
	
	@Override public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.drawString(Integer.toString(cistern.getWaterLevel()), getCoords().x-10, getCoords().y-10);
		g.setColor(new Color(0, 245, 0));
		super.draw(g);
	}
}
