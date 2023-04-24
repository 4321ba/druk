package drukmakor;

import java.security.InvalidParameterException;
/**
 * Ezen az objektumon keresztül folyik a víz. Csövek kötik össze a pumpákat és a pumpákat
 * ilyen elem köti a forrásokhoz/ciszternákhoz. A pumpák és ciszternák szívják belőle ki a vizet,
 * illetve a pumpák és a források nyomják bele azt. Egyszerre csak egy játékos állhat rajta.
 * Tudja, hogy melyik pumpákat/ciszternát/forrást köti össze, illetve hogy folyik-e rajta keresztül
 * víz.
 *
 */
public class Pipe extends Element {
	private boolean hasWater;
	/**
	 * eltárolja, hogy áll-e rajta játékos
	 */
	private boolean isOccupied;
	private boolean isPierced;
	private boolean end1Carried;
	private boolean end2Carried;
	private int isSticky;
	private int isSlippery;
	private int notPiercable;
	/**
	 * a cső egyik vége ide van csatlakoztatva, nem lehet null
	 */
	private ActiveElement end1;
	/**
	 * a cső másik vége ide van csatlakoztatva, null, ha játékos fogja, vagy dangling
	 */
	private ActiveElement end2;
	
	@Override public boolean fix() {
		if(isPierced) {
			isPierced = false;
			return true;
		}else {
			return false;
		}
	}
	/**
	 * kilyukasztja a csövet, ha eddig nem volt; a benne levő
	 * víz is kifolyik, ha volt benne
	 */
	@Override public boolean piercePipe() {
		isPierced = true;
		if (hasWater) {
			hasWater = false;
			//50 PONT A GRIFFENDÉLNEK
		}
		return isPierced;
	}
	
	/**
	 * konstruktor: létrehozza a csövet ae1
	 * és ae2 között. Ae1-et és ae2-t is frissíti, hozzáadja magát a listájukhoz. Ae2 lehet null,
	 * ekkor csak egy vége van a csőnek, a másik vége a játékosnál lesz. Ha a csatlakoztatás
	 * nem sikerül, kivételt dob
	 * @param ae1
	 * @param ae2
	 */
	public Pipe(ActiveElement ae1, ActiveElement ae2) throws InvalidParameterException {    // mi történik ha teli ciszernához veszünk fel új csövet??? Exception amit a ciszternának el kell kapni

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
		hasWater = false;
		isOccupied = false;
		isPierced = false;
		end1Carried = false;
		end2Carried = false;
		isSticky = 0;
		isSlippery = 0;
		notPiercable = 0;
		
	}
	/**
	 * lecsatlakoztatja az ae véget, amennyiben
	 * nem áll a csövön senki. Ha állnak rajta, akkor nem lehet lecsatlakoztatni. A hívás
	 * feltétele, hogy a cső egyik vége ae legyen
	 * @param ae
	 * @return
	 */
	public boolean disconnectFrom(ActiveElement ae) {// ha rajta állnak akkor ne engedje a felvételt
		if (isOccupied)
			return false;
		if (end1 == ae ) {
			end1Carried = true;
			end1 = null;
			return true;
		}else if(end2 == ae) {
			end2Carried = true;
			end2 = null;
			return true;
		}else if(ae != end1 && ae != end2) {
			throw new RuntimeException("A cső egyik vége sem ae!");
		}
		return false;
	}
	/**
	 * rácsatlakoztatja ae-t a szabad végére. A hívás
	 * feltétele, hogy legyen a csőnek szabad vége. Hurokélt nem enged meg
	 * @param ae
	 * @return
	 */
	public boolean connectTo(ActiveElement ae) { // ctorból hívva még mindkettő null lesz!
		if (end2 == null && end2Carried) {
			end2 = ae;
			end2Carried = false;
			return true;
		}else if(end1 == null && end1Carried) {
			end1 = ae;
			end1Carried = false;
			return true;
		}else if(end2 == null && !end2Carried) { //construktorból volt
			end2 = ae;
			return true;
		}else if(end1 == null && !end1Carried) {
			end1 = ae;
			return true;
		}else if(end1 == ae || end2 == ae) {
			return false;
		}else {
			throw new RuntimeException("Connection problems in the... piping, ifyouknowwhatImean O_-' ");
		}
	}
	/**
	 * visszaadja önmagát, ha ez egy dangling pipe (azaz egyik
	 * vége null és nem isCarried), egyébként nullt ad vissza.
	 * @return
	 */
	public Pipe pickUpDangling() {
		if ((end1 == null && !end1Carried)) {
			end1Carried = true;
			return this;
		}else if((end2 == null && !end2Carried)) {
			end2Carried = true;
			return this;
		}
		return null;
	}
	/**
	 *  rá enged lépni egy karaktert, ha
	 *  nem foglalt, illetve nem viszik éppen
	 *  ha ragacsos, összeragacsozik
	 *  ha csúszós véletlenszerűen egyik végére dob
	 */
	@Override public Element acceptCharacter(Element from, Character who) {
		if (isOccupied || end2 == null || end1 == null){ // ha fel van véve az egyik fele, akkor lehessen-e rálépni? perpillanat ha rajta áll valaki, akkor nem lehet felvenni, tehát szimmetriai okokból ne lehessen rálépni
			return null;
		}
		if (from != end1 && from != end2 && from != null) {
			return null;
		}
		if(isSticky > 0) {
			who.getStickied();
		}
		if (isSlippery > 0) {
			if(Proto.randomNextDouble() > 0.50) {
				return end1; 
			}else {
				return end2;
			}
		}
		isOccupied = true;
		return this;
	}
	/**
	 * lelépett róla egy karakter. Ismét szabad
	 */
	@Override public void characterExited() {
		isOccupied = false;
	}
	/**
	 * Kap vizet, visszaadja, hogy elfogadta-e
	 * @return
	 */
	public boolean addWater() {
		if (hasWater) {
			return false;
		}else {
			hasWater = true;
			return true;
		}
	}
	/**
	 * Szívnak belőle vizet, visszaadja, hogy volt-e benne
	 * ha volt, azt kivesz, már nem lesz
	 * @return
	 */
	public boolean drainWater() {
		if (!hasWater) {
			return false;
		}else {
			hasWater = false;
			return true;
		}
	}
	/**
	 * Amennyiben a cső egyik vége szabadon van, elfolyatja a kapott vizet.
	 * Ha mindkét vége foglalt, akkor a nyomás miatt nem csinál semmit.
	 * @return
	 */
	public boolean wasteWater() {
		if ((end1 == null || end2 == null) && !(end1Carried || end2Carried)) { // dangling: azaz az egyik vége null, és nem szállítják
			//50 PONT A GRIFFENDÉLNEK
			return true;
		}
		return false;
	}
	/**
	 * leteszi p pumpát középre, magát
	 * átcsatlakoztatja hozzá, és létrehoz még egy csövet az új pumpa, és a másik vég között.
	 */
	@Override public boolean placePump(Pump p) {
		// occupied csövet nem lehet felszedni!
		ActiveElement prevend1 = end1;
		isOccupied = false; //temporary, hogy  a többi működjön
		end1.disconnectPipe(this);
		Proto.newPipe(prevend1, p); //Productionben nem Proto. dolog lesz hanem new Pipe(args)
		boolean res2 = p.connectPipe(this, -1);
		isOccupied = true; // íme, itt vissza is jött
		if (!res2)
			throw new RuntimeException("A cső csatlakoztatása az új pumpához sikertelen!");
		
		return true;
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(isSticky > 0) isSticky--;
		if(isSlippery > 0) isSlippery--;

	}
	@Override
	public boolean stickyPipe() {
		isSticky = 3;
		return true;
	}
	@Override
	public boolean slipperyPipe() {
		isSlippery = 3;
		return true;
	}
	@Override
	public Object[] get() {
		// TODO Auto-generated method stub
		return null;
	}

}
