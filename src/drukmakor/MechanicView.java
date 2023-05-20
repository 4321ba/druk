package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Megjeleníti a vele összekapcsolt, hozzá tartozó típusú modell objektumot.
 * Ehhez el kell őt tárolnia, és kérdezgetnie a rajzolás alatt.
 */
public class MechanicView extends CharacterView {
	/**
	 * a viewhoz tartozó modell objektum
	 */
	private Mechanic model;
	/**
	 * konstruktor, beállítja a model attribútum értékét
	 */
	public MechanicView(Mechanic m) {
		model = m;
	}
	/**
	 * visszaadja a viewhoz tartozó modell objektumot
	 */
	@Override
	protected Mechanic getModel() {
		return model;
	}
	/**
	 * kirajzolja g-re a mechanicot
	 */
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
}
