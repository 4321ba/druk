package drukmakor;
/**
 * Egész (int) koordináta-pár tárolása.
 */
public class Coords {
	/**
	 * x koordináta
	 */
	public int x;
	/**
	 * y koordináta
	 */
	public int y;
	/**
	 * konstruktor, beállítja az adattagokat
	 */
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * lemásolja az objektumot, és visszaadja a másolatot
	 * @return másolat
	 */
	public Coords copy() {
		return new Coords(x,y);
	}
}
