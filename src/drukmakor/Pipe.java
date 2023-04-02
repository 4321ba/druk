package drukmakor;

import java.security.InvalidParameterException;
/**
 * Ezen az objektumon keresztül folyik a víz. Csövek kötik össze a pumpákat és a pumpákat
ilyen elem köti a forrásokhoz/ciszternákhoz. A pumpák és ciszternák szívják belőle ki a vizet,
illetve a pumpák és a források nyomják bele azt. Egyszerre csak egy játékos állhat rajta.
Tudja, hogy melyik pumpákat/ciszternát/forrást köti össze, illetve hogy folyik-e rajta keresztül
víz.
 *
 */
public class Pipe extends Element {
	/**
	 * a cső egyik vége ide van csatlakoztatva, nem lehet null
	 */
	private ActiveElement end1;
	/**
	 * a cső másik vége ide van csatlakoztatva, null, ha játékos fogja, vagy dangling
	 */
	private ActiveElement end2;
	/**
	 * eltárolja, hogy áll-e rajta játékos
	 */
	private boolean isOccupied = false;
	/**
	 * eltárolja, hogy ki van-e lyukaszva
	 */
	private boolean isPierced = false;
	/**
	 * eltárolja, hogy egy szerelő éppen hordozza-e
	 */
	private boolean isCarried = false;
	/**
	 * megjavítja a csövet, ha ki van lyukasztva
	 */
	@Override public boolean fix() {
		Pr.fv(this, "fix");
		boolean prevIsBroken = isPierced;
		isPierced = false;
		return Pr.ret(prevIsBroken);
	}
	/**
	 * kilyukasztja a csövet, ha eddig nem volt; a benne levő
víz is kifolyik, ha volt benne
	 */
	@Override public boolean piercePipe() {
		Pr.fv(this, "piercePipe");
		boolean prevIsBroken = isPierced;
		isPierced = true;
		if (hasWater) {
			hasWater = false;
		}
		return Pr.ret(!prevIsBroken);
	}
	
	/**
	 * konstruktor: létrehozza a csövet ae1
és ae2 között. Ae1-et és ae2-t is frissíti, hozzáadja magát a listájukhoz. Ae2 lehet null,
ekkor csak egy vége van a csőnek, a másik vége a játékosnál lesz. Ha a csatlakoztatás
nem sikerül, kivételt dob
	 * @param ae1
	 * @param ae2
	 */
	public Pipe(ActiveElement ae1, ActiveElement ae2) throws InvalidParameterException {    // mi történik ha teli ciszernához veszünk fel új csövet??? Exception amit a ciszternának el kell kapni

		Pr.fv(this, "Pipe", ae1, ae2);
		boolean res1 = ae1.connectPipe(this, -1);
		if (!res1) {
			throw new InvalidParameterException("Failed to connect to Pump!");
		}
		if (ae2 != null) {
			boolean res2 = ae2.connectPipe(this, -1);
			if (!res2) {
				ae1.disconnectPipe(this);//konzisztens állapotba visszaállítani a varázslatot
				throw new InvalidParameterException("Failed to connect to Pump!");
			}
		}
		Pr.ret();
	}
	/**
	 * lecsatlakoztatja az ae véget, amennyiben
	nem áll a csövön senki. Ha állnak rajta, akkor nem lehet lecsatlakoztatni. A hívás
	feltétele, hogy a cső egyik vége ae legyen
	 * @param ae
	 * @return
	 */
	public boolean disconnectFrom(ActiveElement ae) {// ha rajta állnak akkor ne engedje a felvételt
		Pr.fv(this, "disconnectFrom", ae);
		if (isOccupied)
			return Pr.ret(false);
		if (end1 == null || end2 == null)
			return Pr.ret(false);
		assert(end1 != end2);
		if (ae != end1 && ae != end2)
			throw new RuntimeException("A cső egyik vége sem ae!");
		if (ae == end1)
			end1 = end2;
		end2 = null;
		isCarried = true;
		return Pr.ret(true);
	}
	/**
	 * rácsatlakoztatja ae-t a szabad végére. A hívás
feltétele, hogy legyen a csőnek szabad vége. Hurokélt nem enged meg
	 * @param ae
	 * @return
	 */
	public boolean connectTo(ActiveElement ae) {//ctorból hívva még mindkettő null lesz!
		Pr.fv(this, "connectTo", ae);
		if (end2 != null)
			throw new RuntimeException("Mindkét végén foglalt csőhöz történő csatlakoztatás!");
		if (end1 == ae)
			return Pr.ret(false);
		isCarried = false;
		end2 = ae;
		if (end1 == null) {//ha ctorból van hívva és mindkettő null
			end1 = end2;
			end2 = null;
		}
		return Pr.ret(true);
	}
	/**
	 * visszaadja önmagát, ha ez egy dangling pipe (azaz egyik
vége null és nem isCarried), egyébként nullt ad vissza.
	 * @return
	 */
	public Pipe pickUpDangling() {
		Pr.fv(this, "pickUpDangling");
		if ((end1 == null || end2 == null) && !isCarried) {//ha egyik vége null de nem viszik: ekkor dangling
			isCarried = true;
			return Pr.ret(this);
		}
		return Pr.ret((Pipe)null);
	}
	/**
	 *  rá enged lépni egy karaktert, ha
nem foglalt, illetve nem viszik éppen
	 */
	@Override public boolean acceptCharacter(Element from) {
		Pr.fv(this, "acceptCharacter", from);
		if (isOccupied || end2 == null)//ha fel van véve az egyik fele, akkor lehessen-e rálépni? perpillanat ha rajta áll valaki, akkor nem lehet felvenni, tehát szimmetriai okokból ne lehessen rálépni
			return Pr.ret(false);
		if (from != end1 && from != end2 && from != null)
			return Pr.ret(false);
		isOccupied = true;
		return Pr.ret(true);
	}
	/**
	 * lelépett róla egy karakter. Ismét szabad
	 */
	@Override public void characterExited() {
		Pr.fv(this, "characterExited");
		isOccupied = false;
		Pr.ret();
	}
	/**
	 * Kap vizet, visszaadja, hogy elfogadta-e
	 * @return
	 */
	public boolean addWater() {
		Pr.fv(this, "addWater");
		if (hasWater)
			return Pr.ret(false);
		if (isPierced) {
			return Pr.ret(true);
		}
		hasWater = true;
		return Pr.ret(true);
	}
	/**
	 * Szívnak belőle vizet, visszaadja, hogy volt-e benne
	 * @return
	 */
	public boolean drainWater() {
		Pr.fv(this, "drainWater");
		if (!hasWater)
			return Pr.ret(false);
		hasWater = false;
		return Pr.ret(true);
	}
	/**
	 * Amennyiben a cső egyik vége szabadon van, elfolyatja a kapott vizet.
	 * Ha mindkét vége foglalt, akkor a nyomás miatt nem csinál semmit.
	 * @return
	 */
	public boolean wasteWater() {
		Pr.fv(this, "wasteWater");
		if ((end1 == null || end2 == null) && !isCarried) {// dangling: azaz az egyik vége null, és nem szállítják
			return Pr.ret(true);
		}
		return Pr.ret(false);
	}
	/**
	 * leteszi p pumpát középre, magát
átcsatlakoztatja hozzá, és létrehoz még egy csövet az új pumpa, és a másik vég között.
	 */
	@Override public boolean placePump(Pump p) {
		Pr.fv(this, "placePump", p);
		assert(end1!=null&&end2!=null);
		boolean previsocc = isOccupied;//azért kell, mert occupied csövet nem lehet felszedni
		isOccupied = false;
		ActiveElement prevend1 = end1;
		end1.disconnectPipe(this);
		new Pipe(prevend1, p);
		boolean res2 = p.connectPipe(this, -1);
		assert(res2);
		isOccupied = previsocc;
		return Pr.ret(true);
	}

}
