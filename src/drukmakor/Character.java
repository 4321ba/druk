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

	
	
	
	
	
	
	
	
	
	
	
	public abstract CharacterView getView();
}
