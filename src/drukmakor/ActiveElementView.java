package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
/**
 * Megjeleníti a vele összekapcsolt, hozzá tartozó típusú modell objektumot.
 * Absztrakt őse az aktív elemeket megjelenítő viewknak. Ki tudja rajzolni az aktív elemet,
 * és a szomszédos pipe-ok pozícióira számokat ír, hogy lehessen látni,
 * melyik indexen melyik pipe van.
 */
public abstract class ActiveElementView extends ElementView {

	/**
	 * visszaadja a viewhoz tartozó modell objektumot
	 */
	protected abstract ActiveElement getModel();
	/**
	 * konstruktor, beállítja az elem pozícióját
	 */
	public ActiveElementView(Coords c) {
		coords = c;
	}

	/**
	 * az aktív elem pozíciója a képernyőn
	 */
	protected Coords coords;
	/**
	 * visszaadja az aktív elem pozícióját
	 */
	@Override public Coords getCoords() {
		return coords;
	}
	/**
	 * visszaadja az idx-edik slot / pipecsatlakozó pozícióját
	 * (egy picit odébb van, az elem körül)
	 */
	protected Coords getCoordsForIdx(int idx) {
		Coords c2 = coords.copy();
		double phi = 2*Math.PI*idx / ActiveElement.MAX_CONNECTIONS;
		c2.x += Math.cos(phi) * 34;
		c2.y += Math.sin(phi) * 34;
		return c2;
	}
	/**
	 * kirajzolja g-re az aktív elemet
	 */
	@Override public void draw(Graphics g) { // színbeállítás a gyerekeknél kötelező // not anymore
		g.setColor(new Color(0, 0, 50));
		for (int i = 0; i < ActiveElement.MAX_CONNECTIONS; ++i) {
			Coords nc = getCoordsForIdx(i);
			if (getModel().getNeighbours()[i] != null)
				getModel().getNeighbours()[i].getView().setCoords(nc);
			g.drawRect(nc.x-4, nc.y-4, 9, 9);
			g.drawString(Integer.toString(i), nc.x-3, nc.y+6);
		}
	}
	
}
