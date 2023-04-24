package drukmakor;

/**
 * Olyan játékos, aki képes megjavítani a szabotált/elromlott dolgokat, és módosítani a pályát
 * pumpák és csövek elhelyezésével, vagy csövek mozgatásával. Fel tudnak venni új csövet,
 * illetve pumpát a ciszternáknál.
 * Tudja, hogy melyik elemen áll, és (csak) azt képes javítani/átállítani.
 *
 */
public class Mechanic extends Character {
	/**
	 * a cső, aminek a végét tartja. Null, ha nem fog csövet
	 */
	private Pipe holdingPipe = null;
	/**
	 * A pumpa, ami a mechaniknál van, ha null, akkor nincs nála
	 */
	private Pump holdingPump = null;
	/**
	 * meghívja az ősosztály konstruktorát
	 * @param cp
	 */
	public Mechanic(Element cp) {
		super(cp);
	}
	/**
	 * megpróbálja megjavítani az elemet, amin áll
	 * @return
	 */
	public boolean fix() {
		Pr.fv(this, "fix");
		return Pr.ret(currentPosition.fix());
	}
	/**
	 * arról a pumpáról, amin áll, megkísérli lekapcsolni a
	 * paraméterként kapott indexű csatlakozón lévő (lista eleme) Pipe-ot. Amennyiben ez
	 * sikeres, ez lesz a holdingPipe.
	 * @param index
	 * @return
	 */
	public boolean disconnectPipe(int index) {
		Pr.fv(this, "disconnectPipe", index);
		if (holdingPipe != null)
			return Pr.ret(false);
		holdingPipe = currentPosition.disconnectPipe(index);
		return Pr.ret(holdingPipe != null);
	}
	/**
	 * megkísérli a holdingPipe csövet csatlakoztatni ahhoz
	 * az elemhez, amin áll, ahhoz a csatlakozóhoz
	 * @param index
	 * @return
	 */
	public boolean connectPipe(int index) {
		Pr.fv(this, "connectPipe", index);
		if (holdingPipe == null)
			return Pr.ret(false);
		boolean res = currentPosition.connectPipe(holdingPipe, index);
		if (res)
			holdingPipe = null;
		return Pr.ret(res);
	}
	/**
	 * felveszi az indexedik helyen levő lelógó
	 * csövet (a ciszternánál), ha sikerül
	 * @param idx
	 * @return
	 */
	public boolean pickUpDanglingPipe(int idx) {
		Pr.fv(this, "pickUpDanglingPipe", idx);
		if (holdingPipe != null)
			return Pr.ret(false);
		holdingPipe = currentPosition.pickUpDanglingPipe(idx);
		return Pr.ret(holdingPipe != null);
	}
	/**
	 * felvesz egy pumpát (a ciszternánál), ha sikerül ez lesz a
	 * holdingPump
	 * @return
	 */
	public boolean pickUpPump() {
		Pr.fv(this, "pickUpPump");
		if (holdingPump != null)
			return Pr.ret(false);
		holdingPump = currentPosition.pickUpPump();
		return Pr.ret(holdingPump != null);
	}
	/**
	 * megkéri az elemet, amin áll, hogy rakja le a pumpát (ha van nála)
	 * @return
	 */
	public boolean placePump() {
		Pr.fv(this, "placePump");
		if(holdingPump == null)
			return Pr.ret(false);
		boolean res1 = currentPosition.placePump(holdingPump);
		if (!res1)
			return Pr.ret(false);
		boolean res2 = moveTo(holdingPump);
		if (!res2)
			throw new RuntimeException("Nem jól bekötött, nem tudunk átlépni a pumpára!");
		holdingPump = null;
		return Pr.ret(true);
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
