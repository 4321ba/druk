package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Megjeleníti a vele összekapcsolt, hozzá tartozó típusú modell objektumot.
 * Ehhez el kell őt tárolnia, és kérdezgetnie a rajzolás alatt.
 */
public class PipeView extends ElementView {
	/**
	 * a viewhoz tartozó modell objektum
	 */
	private Pipe model;
	/**
	 * konstruktor, beállítja a model attribútum értékét
	 */
	public PipeView(Pipe m) {
		model = m;
		coords[0] = new Coords(0, 0);
		coords[1] = new Coords(0, 0);
	}
	/**
	 * a két végpont pozíciója
	 */
	private Coords[] coords = new Coords[2];
	/**
	 * setter
	 */
	public void setCoords(Coords c) {
		coords[1] = coords[0];
		coords[0] = c.copy();
		if (coords[1].x == 0 && coords[1].y == 0)
			coords[1] = c.copy();
	}
	/**
	 * visszaadja a két végpont pozíciójának átlagát
	 */
	@Override
	public Coords getCoords() {
		Coords e1 = coords[0];
		Coords e2 = coords[1];
		return new Coords((e1.x+e2.x)/2, (e1.y+e2.y)/2);
	}

	/**
	 * kirajzolja g-re a pipe-ot
	 */
	@Override public void draw(Graphics g) {
		g.setColor(new Color(model.getIsPierced() ? 200 : 50 , model.getIsSticky() ? 200:0, model.getIsSlippery() ? 255:0));
		Coords e1 = coords[0];
		Coords e2 = coords[0].x == coords[1].x && coords[0].y == coords[1].y ? new Coords(e1.x-10, e1.y-10) : coords[1];
		g.drawLine(e1.x, e1.y, e2.x, e2.y);
		if (!model.getHasWater())
			return;
		g.setColor(new Color(100, 100, 245));
		g.fillOval(getCoords().x-2, getCoords().y+6, 5, 12);
	}
}
