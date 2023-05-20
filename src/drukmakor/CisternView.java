package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Megjeleníti a vele összekapcsolt, hozzá tartozó típusú modell objektumot.
 * Ehhez el kell őt tárolnia, és kérdezgetnie a rajzolás alatt.
 */
public class CisternView extends ActiveElementView {
	/**
	 * a viewhoz tartozó modell objektum
	 */
	private Cistern model;
	/**
	 * visszaadja a viewhoz tartozó modell objektumot
	 */
	@Override
	protected Cistern getModel() {
		return model;
	}
	/**
	 * konstruktor, beállítja a model attribútum értékét, és a pozíciót
	 */
	public CisternView(Cistern m, Coords c) {
		super(c);
		model = m;
	}

	/**
	 * kirajzolja g-re a cisternt
	 */
	@Override public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.drawString(Integer.toString(model.getWaterLevel()), getCoords().x-10, getCoords().y-10);
		g.setColor(new Color(0, 245, 0));
		super.draw(g);
	}
}
