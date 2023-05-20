package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Megjeleníti a vele összekapcsolt, hozzá tartozó típusú modell objektumot.
 * Ehhez el kell őt tárolnia, és kérdezgetnie a rajzolás alatt.
 */
public class SaboteurView extends CharacterView {
	/**
	 * a viewhoz tartozó modell objektum
	 */
	private Saboteur model;
	/**
	 * konstruktor, beállítja a model attribútum értékét
	 */
	public SaboteurView(Saboteur m) {
		model = m;
	}
	/**
	 * visszaadja a viewhoz tartozó modell objektumot
	 */
	@Override
	protected Saboteur getModel() {
		return model;
	}
	/**
	 * kirajzolja g-re a saboteurt
	 */
	@Override public void draw(Graphics g) {
		g.setColor(new Color(255,128,128));
		super.draw(g);
	}
}
