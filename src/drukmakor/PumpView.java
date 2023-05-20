package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Megjeleníti a vele összekapcsolt, hozzá tartozó típusú modell objektumot.
 * Ehhez el kell őt tárolnia, és kérdezgetnie a rajzolás alatt.
 */
public class PumpView extends ActiveElementView {
	/**
	 * a viewhoz tartozó modell objektum
	 */
	private Pump model;
	/**
	 * visszaadja a viewhoz tartozó modell objektumot
	 */
	@Override
	protected Pump getModel() {
		return model;
	}
	/**
	 * konstruktor, beállítja a model attribútum értékét, és a pozíciót
	 */
	public PumpView(Pump m, Coords c) {
		super(c);
		model = m;
	}
	/**
	 * kirajzolja g-re a pumpot
	 */
	@Override public void draw(Graphics g) {
		Coords nc = getCoordsForIdx(model.getInPipeIdx());
		g.setColor(new Color(0, 230, 0)); // zöldből a pirosba pumpál
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		nc = getCoordsForIdx(model.getOutPipeIdx());
		g.setColor(new Color(230, 0, 0));
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		g.setColor(new Color(model.getIsBroken()? 200 : 50, 0, 0));
		super.draw(g);
		if (!model.getHasWater())
			return;
		g.setColor(new Color(100, 100, 245));
		g.fillOval(coords.x-2, coords.y+6, 5, 12);
		
	}
	/**
	 * setter
	 */
	public void setCoords(Coords c) {
		coords = c.copy();
	}
}
