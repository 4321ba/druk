package drukmakor;

/**
 * Aktív elem, ami mozgatja a vizet a bemenetéből saját magába, illetve saját magából a
 * kimenetébe. El tud romlani. Állhat rajta akár több Mechanic és Saboteur is. Ők állíthatják a
 * bemenetét és a kimenetét. Véges számú cső kapcsolódhat bele.
 * Tudja milyen csövek kapcsolódnak bele.
 *
 */
public class Pump extends ActiveElement {

	/**
	 * van-e benne víz
	 */
	private boolean hasWater;
	public boolean getHasWater() { return hasWater; }
	
	/**
	 * bemeneti cső indexe
	 */
	private int inPipeIdx = 0;
	public int getInPipeIdx() { return inPipeIdx; }
	/**
	 * kimeneti cső, indexe
	 */
	private int outPipeIdx = 1;
	public int getOutPipeIdx() { return outPipeIdx; }

	/**
	 * el van-e törve
	 */
	private boolean isBroken = false;
	public boolean getIsBroken() { return isBroken; }
	/**
	 * megjavítja a pumpát
	 */
	@Override public boolean fix() {
		boolean prevIsBroken = isBroken;
		isBroken = false;
		return prevIsBroken;
	}
	/**
	 * isBroken igaz lesz
	 */
	@Override public void randomEvent() {
		isBroken = true;
	}
	/**
	 * beállítja a kimeneti és a bemeneti cső indexet
	 */
	@Override public boolean alterPump(int inPipeIdx, int outPipeIdx) {
		if (inPipeIdx == outPipeIdx)
			return false;
		if (inPipeIdx == this.inPipeIdx && outPipeIdx == this.outPipeIdx)
			return false;
		if (inPipeIdx < 0 || inPipeIdx >= MAX_CONNECTIONS || outPipeIdx < 0 || outPipeIdx >= MAX_CONNECTIONS)
			return false;
		this.inPipeIdx = inPipeIdx;
		this.outPipeIdx = outPipeIdx;
		return true;
	}
	/**
	 * ha nincs eltörve, és van kimeneti cső beállítva, és van
	 * benne víz, akkor átadja a vizet outPipe-nak, amennyiben az befogadja
	 */
	@Override
	public void pushWater() {
		if (pipes[outPipeIdx] == null || isBroken)
			return;
		if (hasWater)
			hasWater = !pipes[outPipeIdx].addWater();
	}	
	/**
	 * ha nincs eltörve, és van bemeneti cső beállítva, és
	 * nincs már eleve a pumpában víz, akkor megkéri a bemeneti csövet, hogy adjon vizet
	 */
	@Override
	public void pullWater() {
		if (pipes[inPipeIdx] == null || isBroken)
			return;
		if (!hasWater)
			hasWater = pipes[inPipeIdx].drainWater();
	}

	

	/**
	 * A get parancs által kiírandó értékeket adja vissza a megfelelő sorrendben, így megvalósítva az állapotok lekérdezését.
	 */
	@Override
	public Object[] get() {
		int noValidPipes = MAX_CONNECTIONS; // number of valid pipes
		while (noValidPipes > 0 && pipes[noValidPipes - 1] == null)
			noValidPipes--;
		Object[] ret = new Object[noValidPipes + 4]; // <csövek> <van-e benne víz> <eltört-e> <bemeneti index> <kimeneti index>
		for (int i = 0; i < noValidPipes; ++i)
			ret[i] = pipes[i];
		ret[noValidPipes] = hasWater;
		ret[noValidPipes+1] = isBroken;
		ret[noValidPipes+2] = inPipeIdx;
		ret[noValidPipes+3] = outPipeIdx;
		return ret;
	}
	
	private PumpView view;
	@Override public PumpView getView() { return view; }
	public void setView(PumpView v) { view = v; }
}
