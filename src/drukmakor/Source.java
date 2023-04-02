package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
/**
 * Innen kell eljuttatni a vizet a ciszternákba a szerelőknek. Csövek kapcsolódnak hozzá.
A szerelők új csöveket is csatlakoztathatnak hozzá. Mindig pumpál vizet a hozzá kapcsolódó
csövekbe.
Tudja melyik csövek kapcsolódnak bele.
 *
 */
public class Source extends ActiveElement {

	public Source(Coords c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	/**
	 *  az összes kimeneti csőbe megpróbál vizet benyomni
	 */
	@Override public void pushWater() {
		for (Pipe p : pipes)
			if (p != null)
				p.addWater();
	}
	
	
	
	

	@Override public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 245));
		super.draw(g);
	}
}
