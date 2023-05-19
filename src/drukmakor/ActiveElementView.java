package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public abstract class ActiveElementView extends ElementView {

	protected abstract ActiveElement getModel();
	public ActiveElementView(Coords c) {
		coords = c;
	}

	protected Coords coords;
	
	protected Coords getCoordsForIdx(int idx) {
		Coords c2 = coords.copy();
		double phi = 2*Math.PI*idx / ActiveElement.MAX_CONNECTIONS;
		c2.x += Math.cos(phi) * 30;
		c2.y += Math.sin(phi) * 30;
		return c2;
	}
	/*
	private Coords getCoordsForPipe(Pipe p) {
		for (int i = 0; i < ActiveElement.MAX_CONNECTIONS; ++i)
			if (activeElement.getPipes()[i] == p)
				return getCoordsForIdx(i);
		throw new RuntimeException("nincs is benne a pipe");
	}*/
	
	@Override public void draw(Graphics g) {//színbeállítás a gyerekeknél kötelező
		g.fillOval(coords.x-6, coords.y-6, 12, 12);
		g.setColor(new Color(0, 0, 50));
		for (int i = 0; i < ActiveElement.MAX_CONNECTIONS; ++i) {
			Coords nc = getCoordsForIdx(i);
			if (getModel().getNeighbours()[i] != null)
				getModel().getNeighbours()[i].getView().setCoords(nc);
			g.drawRect(nc.x-4, nc.y-4, 9, 9);
			g.drawString(Integer.toString(i), nc.x-3, nc.y+6);
		}
	}
	
	@Override public Coords getCoords() {
		return coords;
	}
}
