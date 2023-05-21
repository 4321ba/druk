package drukmakor;

import java.awt.*;

/**
 * A karaktereket megjelenítő view osztályok közös őse. Számon tartja,
 * hogy kit irányít éppen a billentyűzet, és ezt jelzi a rajzolásban is.
 */
public abstract class CharacterView implements Drawable {
	/**
	 * az ő modelljét irányítja-e éppen a billentyűzet
	 */
	private boolean soros = false;
	/**
	 * setter
	 */
	public void setSoros(boolean b) { soros = b; }
	/**
	 * visszaadja a viewhoz tartozó modell objektumot
	 */
	protected abstract Character getModel();
	/**
	 * kirajzolja, ami közös a karakterekben
	 */
	@Override public void draw(Graphics g) { // leszármazott állít színt!!
		Coords c = getModel().getCurrentPosition().getView().getCoords();
		g.drawRect(c.x-5, c.y-15, 10, 20);
		if (soros) {
			g.setColor(new Color(186,27,27));
			g.fillOval(c.x - 6, c.y - 27, 12, 12);
		}
	}
}
