package drukmakor;

/**
 * Belőle áll a pálya, minden egyes elem belőle származik. Számontartja, hogy van-e benne víz,
tud karaktert elfogadni, illetve értesíteni lehet, hogy egy ellépett róla. Továbbá definiál
különböző leszármazott-specifikus függvényeket is, amikkel különböző akciókat lehet rajta
végrehajtani.
 *
 */
public abstract class Element {
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
		Pr.fv(this, "fix");
		return Pr.ret(false);
	}
	/**
	 * az elem reakcióját írja elő a rajta álló szabotőr “piercePipe”
akciójára, alapértelmezetten sikertelen, leszármazottaknak célszerű felüldefiniálni
	 * @return
	 */
	public boolean piercePipe() {
		Pr.fv(this, "piercePipe");
		return Pr.ret(false);
	}
	/**
	 * egy karakter rá szeretne lépni from elem
irányából, alapértelmezetten sikertelen, leszármazottaknak célszerű felüldefiniálni
	 * @param from
	 * @return
	 */
	public boolean acceptCharacter(Element from) {
		Pr.fv(this, "acceptCharacter", from);
		return Pr.ret(false);
	}
	/**
	 * egy karakter lelépett erről az elemről
	 */
	public void characterExited() {
		Pr.fv(this, "characterExited");
		Pr.ret();
	}
	/**
	 * leteszi p cső végét erre az elemre, az idxedik
helyre (lista elem), p-t is megkéri, hogy frissítse magát, -1-es index esetén bárhova
csatlakoztatja
	 * @param p
	 * @param idx
	 * @return
	 */
	public boolean connectPipe(Pipe p, int idx) {
		Pr.fv(this, "connectPipe", p, idx);
		return Pr.ret(false);
	}
	/**
	 * amennyiben sikerül az adott pozícióból pipe-ot
lecsatolni, azt visszaadja, különben null-t, a pipe-ot is megkéri, hogy frissítse magát
	 * @param idx
	 * @return
	 */
	public Pipe disconnectPipe(int idx) {
		Pr.fv(this, "disconnectPipe", idx);
		return Pr.ret((Pipe)null);
	}
	/**
	 * beállítja a kimeneti és a bemeneti
cső indexet, amennyiben a leszármazott megvalósítja
	 * @param inPipeIdx
	 * @param outPipeIdx
	 * @return
	 */
	public boolean alterPump(int inPipeIdx, int outPipeIdx) {
		Pr.fv(this, "alterPump", inPipeIdx, outPipeIdx);
		return Pr.ret(false);
	}
	/**
	 * felveszi az indexedik helyen levő lelógó
csövet erről az elemről, nullt ad vissza, ha sikertelen
	 * @param idx
	 * @return
	 */
	public Pipe pickUpDanglingPipe(int idx) {
		Pr.fv(this, "pickUpDanglingPipe", idx);
		return Pr.ret((Pipe)null);
	}
	/**
	 * felvesz egy pumpát erről az elemről
	 * @return
	 */
	public Pump pickUpPump() {
		Pr.fv(this, "pickUpPump");
		return Pr.ret((Pump)null);
	}
	/**
	 * letesz egy pumpát, amennyiben ez az adott elemen
lehetséges. Visszaadja a sikerességet.
	 * @param p
	 * @return
	 */
	public boolean placePump(Pump p) {
		Pr.fv(this, "placePump", p);
		return Pr.ret(false);
	}
	
	
}
