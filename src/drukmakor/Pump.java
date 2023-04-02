package drukmakor;

/**
 * Aktív elem, ami mozgatja a vizet a bemenetéből saját magába, illetve saját magából a
kimenetébe. El tud romlani. Állhat rajta akár több Mechanic és Saboteur is. Ők állíthatják a
bemenetét és a kimenetét. Véges számú cső kapcsolódhat bele.
Tudja milyen csövek kapcsolódnak bele.
 *
 */
public class Pump extends ActiveElement {

	public Pump() {
		Pr.fv(this, "Pump");
		Pr.ret();
	}
	/**
	 * bemeneti cső indexe
	 */
	private int inPipeIdx = 0;
	/**
	 * kimeneti cső, indexe
	 */
	private int outPipeIdx = 1;

	/**
	 * el van-e törve
	 */
	private boolean isBroken = false;
	/**
	 * megjavítja a pumpát
	 */
	@Override public boolean fix() {
		Pr.fv(this, "fix");
		boolean prevIsBroken = isBroken;
		isBroken = false;
		return Pr.ret(prevIsBroken);
	}
	/**
	 * isBroken igaz lesz
	 */
	@Override public void randomEvent() {
		Pr.fv(this, "randomEvent");
		isBroken = true;
		Pr.ret();
	}
	/**
	 * beállítja a kimeneti és a
bemeneti cső indexet
	 */
	@Override public boolean alterPump(int inPipeIdx, int outPipeIdx) {
		Pr.fv(this, "alterPump", inPipeIdx, outPipeIdx);
		if (inPipeIdx == outPipeIdx)
			return Pr.ret(false);
		if (inPipeIdx == this.inPipeIdx && outPipeIdx == this.outPipeIdx)
			return Pr.ret(false);
		if (inPipeIdx < 0 || inPipeIdx >= MAX_CONNECTIONS || outPipeIdx < 0 || outPipeIdx >= MAX_CONNECTIONS)
			return Pr.ret(false);
		this.inPipeIdx = inPipeIdx;
		this.outPipeIdx = outPipeIdx;
		return Pr.ret(true);
	}
	/**
	 * ha nincs eltörve, és van kimeneti cső beállítva, és van
benne víz, akkor átadja a vizet outPipe-nak, amennyiben az befogadja
	 */
	@Override
	public void pushWater() {
		Pr.fv(this, "pushWater");
		if (pipes[outPipeIdx] == null || isBroken) {
			Pr.ret();
			return;
		}
		if (hasWater)
			hasWater = !pipes[outPipeIdx].addWater();
		Pr.ret();
	}	
	/**
	 * ha nincs eltörve, és van bemeneti cső beállítva, és
nincs már eleve a pumpában víz, akkor megkéri a bemeneti csövet, hogy adjon vizet
	 */
	@Override
	public void pullWater() {
		Pr.fv(this, "pullWater");
		if (pipes[inPipeIdx] == null || isBroken) {
			Pr.ret();
			return;
		}
		if (!hasWater)
			hasWater = pipes[inPipeIdx].drainWater();
		Pr.ret();
	}
	
	
}
