package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
/**
 * A játékos olyan változata, aki tud szabotálni, vagyis elrontani pumpákat, illetve kilyukasztani
csöveket.
Tudja, hogy melyik elemen áll és (csak) azt képes elrontani/átállítani.
 *
 */
public class Saboteur extends Character {
	/**
	 * meghívja az ősosztály konstruktorát
	 * @param cp
	 */
	public Saboteur(Element cp) {
		super(cp);
	}
	/**
	 * kiszúrja az elemet, amin áll (csak akkor lehet sikeres, ha csövön
áll)
	 * @return
	 */
	public boolean piercePipe() {
		return currentPosition.piercePipe();
	}

	
	
	
	@Override public void draw(Graphics g) {
		g.setColor(new Color(255,128,128));
		super.draw(g);
	}
}
