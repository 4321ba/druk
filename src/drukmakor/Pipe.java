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
	/**
	 * van-e benne víz
	 */
	private boolean hasWater;
	public boolean getHasWater() { return hasWater; }
	/**
	 * eltárolja, hogy áll-e rajta játékos
	 */
	private boolean isOccupied;
	/**
	 * eltárolja, hogy ki van-e lyukaszva
	 */
	private boolean isPierced;
	public boolean getIsPierced() { return isPierced; }
	/**
	 * az 1-es vég játékosnál van-e
	 */
	private boolean end1Carried;
	/**
	 * a 2-es vég játékosnál van-e
	 */
	private boolean end2Carried;
	/**
	 * stickyPipe() hatására beállítódik egy intre, ha >0 true a logikai értéke, tick()-re csökken
	 */
	private int isSticky;
	public boolean getIsSticky() { return isSticky > 0; }
	/**
	 * slipperyPipe() hatására beállítódik egy intre, ha >0 true a logikai értéke, tick()-re csökken
	 */
	private int isSlippery;
	public boolean getIsSlippery() { return isSlippery > 0; }
	/**
	 * fix() hatására beállítódik egy intre, ha >0 true a logikai értéke, tick()-re csökken
	 */
	private int notPiercable;
	/**
	 * a cső egyik vége ide van csatlakoztatva, nem lehet null
	 */
	private ActiveElement end1;
	/**
	 * a cső másik vége ide van csatlakoztatva, null, ha játékos fogja, vagy dangling
	 */
	private ActiveElement end2;
	/**
	 * megjavítja a csövet, beállítja a szúrhatatlanságot is, amennyiben sikerült a javítás
	 */
	@Override public boolean fix() {
		if(isPierced) {
			isPierced = false;
			notPiercable = (int) (2 + Proto.randomNextDouble() * 3);
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
		if (notPiercable > 0 || isPierced)
			return false;
		isPierced = true;
		if (hasWater) {
			hasWater = false;
			//50 PONT A GRIFFENDÉLNEK
		}
		return true;
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
		}
		throw new RuntimeException("A cső egyik vége sem ae!");
	}
	/**
	 * rácsatlakoztatja ae-t a szabad végére. A hívás
	 * feltétele, hogy legyen a csőnek szabad vége. Hurokélt nem enged meg
	 * @param ae
	 * @return
	 */
	public boolean connectTo(ActiveElement ae) { // ctorból hívva még mindkettő null lesz!
		if(end1 == ae || end2 == ae) {
			return false;
		}else if (end1 == null && end1Carried) {
			end1 = ae;
			end1Carried = false;
			return true;
		}else if(end2 == null && end2Carried) {
			end2 = ae;
			end2Carried = false;
			return true;
		}else if(end1 == null && !end1Carried) { //construktorból volt
			end1 = ae;
			return true;
		}else if(end2 == null && !end2Carried) {
			end2 = ae;
			return true;
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
		if (isOccupied || end2 == null || end1 == null){ // ha fel van véve az egyik fele, akkor lehessen-e rálépni? ha rajta áll valaki, akkor nem lehet felvenni, tehát szimmetriai okokból ne lehessen rálépni
			return null;
		}
		if (from != end1 && from != end2 && from != null) {
			return null;
		}
		if (from != null)
			from.characterExited();
		if(isSticky > 0) {
			who.getStickied();
		}
		if (isSlippery > 0) {
			if(Proto.randomNextDouble() >= 0.50) {
				return end1.acceptCharacter(this, who); 
			}else {
				return end2.acceptCharacter(this, who);
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
		if (hasWater)
			return false;
		if (isPierced)
			return true;
		hasWater = true;
		return true;
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
	/**
	 * csökkenti a belső változók értékét, ahogy telik az idő (isSticky, isSlippery, notPiercable)
	 */
	@Override
	public void tick() {
		if(isSticky > 0) isSticky--;
		if(isSlippery > 0) isSlippery--;
		if(notPiercable > 0) notPiercable--;

	}
	/**
	 * ragacsossá teszi a csövet. Ha már eleve 3 körig ragadós, hamist ad vissza. 
	 * Egyébként true-t ad vissza és átállítja az isSticky-t 3-ra (3 körig lesz ragadós)
	 */
	@Override
	public boolean stickyPipe() {
		if (isSticky == 3)
			return false;
		isSticky = 3;
		return true;
	}
	/**
	 * csúszóssá teszi a csövet. Ha már eleve 3 körig csúszós, hamist ad vissza. 
	 * Egyébként true-t ad vissza és átállítja az isSlippery-t 3-ra (3 körig lesz csúszós)
	 */
	@Override
	public boolean slipperyPipe() {
		if (isSlippery == 3)
			return false;
		isSlippery = 3;
		return true;
	}
	

	/**
	 * A get parancs által kiírandó értékeket adja vissza a megfelelő sorrendben, így megvalósítva az állapotok lekérdezését.
	 */
	@Override
	public Object[] get() {
		//<egyik vég> <másik vég> <egyik vég carried-e> <másik vég
		//carried-e> <van-e benne víz> <kiszúrt-e> <foglalt-e> <csúszós (egész
		//		szám)> <ragadós (egész szám)> <mennyi ideig nem lyukasztható (egész
		//		szám)>
		return new Object[] { end1, end2, end1Carried, end2Carried, hasWater, isPierced, isOccupied, isSlippery, isSticky, notPiercable };
	}

	
	private PipeView view;
	public PipeView getView() { return view; }
	public void setView(PipeView v) { view = v; }
	
	@Override
	public ActiveElement[] getNeighbours() {
		return new ActiveElement[] { end1, end2 };
	}
}
