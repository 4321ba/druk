package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
/**
 * Belőle áll a pálya, minden egyes elem belőle származik. Számontartja, hogy van-e benne víz,
tud karaktert elfogadni, illetve értesíteni lehet, hogy egy ellépett róla. Továbbá definiál
különböző leszármazott-specifikus függvényeket is, amikkel különböző akciókat lehet rajta
végrehajtani.
 *
 */
public abstract class Element implements Drawable {
	/**
	 * van-e benne víz
	 */
	protected boolean hasWater = false;
	/**
	 * az elem reakcióját írja elő a rajta álló szerelő “fix” akciójára,
alapértelmezetten sikertelen, leszármazottaknak célszerű felüldefiniálni
	 * @return
	 */
	public boolean fix() {
		return false;
	}
	/**
	 * az elem reakcióját írja elő a rajta álló szabotőr “piercePipe”
akciójára, alapértelmezetten sikertelen, leszármazottaknak célszerű felüldefiniálni
	 * @return
	 */
	public boolean piercePipe() {
		return false;//for pipe
	}
	/**
	 * egy karakter rá szeretne lépni from elem
irányából, alapértelmezetten sikertelen, leszármazottaknak célszerű felüldefiniálni
	 * @param from
	 * @return
	 */
	public boolean acceptCharacter(Element from) {
		return false;
	}
	/**
	 * egy karakter lelépett erről az elemről
	 */
	public void characterExited() {}
	/**
	 * leteszi p cső végét erre az elemre, az idxedik
helyre (lista elem), p-t is megkéri, hogy frissítse magát, -1-es index esetén bárhova
csatlakoztatja
	 * @param p
	 * @param idx
	 * @return
	 */
	public boolean connectPipe(Pipe p, int idx) {
		return false;
	}
	/**
	 * amennyiben sikerül az adott pozícióból pipe-ot
lecsatolni, azt visszaadja, különben null-t, a pipe-ot is megkéri, hogy frissítse magát
	 * @param idx
	 * @return
	 */
	public Pipe disconnectPipe(int idx) {
		return null;
	}
	/**
	 * beállítja a kimeneti és a bemeneti
cső indexet, amennyiben a leszármazott megvalósítja
	 * @param inPipeIdx
	 * @param outPipeIdx
	 * @return
	 */
	public boolean alterPump(int inPipeIdx, int outPipeIdx) {
		return false;
	}
	/**
	 * felveszi az indexedik helyen levő lelógó
csövet erről az elemről, nullt ad vissza, ha sikertelen
	 * @param idx
	 * @return
	 */
	public Pipe pickUpDanglingPipe(int idx) {
		return null;
	}
	/**
	 * felvesz egy pumpát erről az elemről
	 * @return
	 */
	public Pump pickUpPump() {
		return null;
	}
	/**
	 * letesz egy pumpát, amennyiben ez az adott elemen
lehetséges. Visszaadja a sikerességet.
	 * @param p
	 * @return
	 */
	public boolean placePump(Pump p) {
		return false;
	}
	
	
	
	
	abstract Coords getCoords();
	
	Button b;
	
	@Override
	public void draw(Graphics g) {
		b.c=getCoords();
		b.draw(g);
		if (!hasWater)
			return;
		Coords c = getCoords();
		g.setColor(new Color(100, 100, 245));
		g.fillOval(c.x-2, c.y+6, 5, 12);
	}
}
