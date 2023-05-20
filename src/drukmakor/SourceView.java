package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Megjeleníti a vele összekapcsolt, hozzá tartozó típusú modell objektumot.
 * Ehhez el kell őt tárolnia, és kérdezgetnie a rajzolás alatt.
 */
public class SourceView extends ActiveElementView {
	/**
	 * a viewhoz tartozó modell objektum
	 */
	private Source model;
	/**
	 * visszaadja a viewhoz tartozó modell objektumot
	 */
	@Override
	protected Source getModel() {
		return model;
	}
	/**
	 * konstruktor, beállítja a model attribútum értékét, és a pozíciót
	 */
	public SourceView(Source m, Coords c) {
		super(c);
		model = m;
	}
	/**
	 * kirajzolja g-re a source-t
	 */
	@Override public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 245));
		super.draw(g);
	}
}
