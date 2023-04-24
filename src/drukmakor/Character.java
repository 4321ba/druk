package drukmakor;

import java.security.InvalidParameterException;
/**
 * A Mechanic és Saboteur osztályok ősosztálya, a két szerep típus azonos lehetőségeit valósítja
 * meg, így a mozgást és a pumpák víznyomásának irányát.
 *
 */
public abstract class Character implements Tickable {
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
		Pr.fv(this, "Character", cp);
		boolean res = cp.acceptCharacter(null);
		if (!res)
			throw new InvalidParameterException("Nem fogadta be a cp a karaktert!");
		currentPosition = cp;
		Pr.ret();
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
		Pr.fv(this, "alterPump", inPipeIdx, outPipeIdx);
		return Pr.ret(currentPosition.alterPump(inPipeIdx, outPipeIdx));
	}
	/**
	 * a paraméterként kapott Element-re próbál rálépni
	 * @param e
	 * @return
	 */
	public boolean moveTo(Element e) {
		Pr.fv(this, "moveTo", e); 
		if (!e.acceptCharacter(currentPosition))
			return Pr.ret(false);
		currentPosition.characterExited();
		currentPosition = e;
		return Pr.ret(true);
	}
	
	
}
