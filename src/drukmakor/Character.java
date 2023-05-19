package drukmakor;

import java.security.InvalidParameterException;
/**
 * A Mechanic és Saboteur osztályok ősosztálya, a két szerep típus azonos lehetőségeit valósítja
 * meg, így a mozgást és a pumpák víznyomásának irányát.
 *
 */
public abstract class Character implements Tickable {
	/**
	 * Változó, ami eltárolja, hogy képesek vagyunk-e mozogni
	 */
	protected int isStuck;
	/**
	 * az az elem, amelyiken a Character áll, nem lehet null
	 */
	protected Element currentPosition;
	public Element getCurrentPosition() { return currentPosition; }
	/**
	 * Konstruktor, a currentPosition értékét állítja, és befogadtatja
	 * vele a karaktert (acceptCharacter). Különben a cső például nem tudna arról, hogy
	 * állnak rajta
	 * @param cp
	 */
	public Character(Element cp) {

		isStuck = 0;
		currentPosition = cp.acceptCharacter(null, this);
		if (currentPosition == null)
			throw new InvalidParameterException("Nem fogadta be a cp a karaktert!");
	}
	/**
	 * amennyiben pump-on áll, beállítja
	 * pump bemeneti csövének a paraméterként kapott inPipeIdx indexű pipe-ot, és
	 * kimeneti csőnek a paraméterként kapott outPipeIdx indexűt (és visszaadja, hogy
	 * sikerült-e)
	 * @param inPipeIdx
	 * @param outPipeIdx
	 * @return
	 */
	public boolean alterPump(int inPipeIdx, int outPipeIdx) {
		return currentPosition.alterPump(inPipeIdx, outPipeIdx);
	}
	/**
	 * a paraméterként kapott Element-re próbál rálépni
	 * @param e
	 * @return
	 */
	public boolean moveTo(Element e) {
		if (isStuck > 0)
			return false;
		Element new_ = e.acceptCharacter(currentPosition, this);
		if (new_ == null)
			return false;
		currentPosition = new_;
		return true;
	}

	/**
	 * ragacsozza az elemet, amin áll (csak akkor lehet sikeres, ha csövön áll)
	 * @return
	 */
	public boolean stickyPipe() {
		return currentPosition.stickyPipe();
	}
	
	/**
	 * kiszúrja az elemet, amin áll (csak akkor lehet sikeres, ha csövön áll)
	 * @return
	 */
	public boolean piercePipe() {
		return currentPosition.piercePipe();
	}
	
	/**
	 * összeragasztózza a karaktert, beállítja isStuck-ot 3-ra (3 körig lesz ragasztós)
	 */
	public void getStickied() {
		isStuck = 3;
	}
	/**
	 * isStuckot csökkenti 1-gyel, ha >0
	 */
	@Override
	public void tick() {
		if (isStuck > 0)
			isStuck--;
	}

	
	
	
	
	
	
	
	
	// innentől leszármazottak függvényei, a könnyebb meghívhatóság érdekében
	// alapértelmezetten mind false-t ad

	/**
	 * megpróbálja megjavítani az elemet, amin áll
	 * @return
	 */
	public boolean fix() {
		return false;
	}
	/**
	 * arról a pumpáról, amin áll, megkísérli lekapcsolni a
	 * paraméterként kapott indexű csatlakozón lévő (lista eleme) Pipe-ot. Amennyiben ez
	 * sikeres, ez lesz a holdingPipe.
	 * @param index
	 * @return
	 */
	public boolean disconnectPipe(int index) {
		return false;
	}
	/**
	 * megkísérli a holdingPipe csövet csatlakoztatni ahhoz
	 * az elemhez, amin áll, ahhoz a csatlakozóhoz
	 * @param index
	 * @return
	 */
	public boolean connectPipe(int index) {
		return false;
	}
	/**
	 * felveszi az indexedik helyen levő lelógó
	 * csövet (a ciszternánál), ha sikerül
	 * @param idx
	 * @return
	 */
	public boolean pickUpDanglingPipe(int idx) {
		return false;
	}
	/**
	 * felvesz egy pumpát (a ciszternánál), ha sikerül ez lesz a
	 * holdingPump
	 * @return
	 */
	public boolean pickUpPump() {
		return false;
	}
	/**
	 * megkéri az elemet, amin áll, hogy rakja le a pumpát (ha van nála)
	 * @return
	 */
	public boolean placePump() {
		return false;
	}

	
	

	/**
	 *  csúszósozza az elemet, amin áll (csak akkor lehet sikeres, ha csövön áll), 
	 *  visszaadja, amit az Element azonos nevű függvényétől kapott
	 * @return
	 */
	public boolean slipperyPipe() {
		return false;
	}

	
	
	
	
	
	public abstract CharacterView getView();
}
