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
	/**
	 * Konstruktor, a currentPosition értékét állítja, és befogadtatja
	 * vele a karaktert (acceptCharacter). Különben a cső például nem tudna arról, hogy
	 * állnak rajta
	 * @param cp
	 */
	public Character(Element cp) {

		isStuck = 0;
		boolean res = cp.acceptCharacter(null, this);
		if (!res)
			throw new InvalidParameterException("Nem fogadta be a cp a karaktert!");
		currentPosition = cp;
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
		if (!e.acceptCharacter(currentPosition, this))
			return false;
		currentPosition.characterExited();
		currentPosition = e;
		return true;
	}
	
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
	
	
	public void getStickied() {
		isStuck = 2;
	}
	
	@Override
	public void tick() {
		isStuck--;
	}
	
}
