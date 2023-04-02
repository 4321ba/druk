package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
/**
 * Aktív elem, ami mozgatja a vizet a bemenetéből saját magába, illetve saját magából a
kimenetébe. El tud romlani. Állhat rajta akár több Mechanic és Saboteur is. Ők állíthatják a
bemenetét és a kimenetét. Véges számú cső kapcsolódhat bele.
Tudja milyen csövek kapcsolódnak bele.
 *
 */
public class Pump extends ActiveElement {

	public Pump(Coords c) {
		super(c);
		// TODO Auto-generated constructor stub
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
	 * beállítja a kimeneti és a
bemeneti cső indexet
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
benne víz, akkor átadja a vizet outPipe-nak, amennyiben az befogadja
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
nincs már eleve a pumpában víz, akkor megkéri a bemeneti csövet, hogy adjon vizet
	 */
	@Override
	public void pullWater() {
		if (pipes[inPipeIdx] == null || isBroken)
			return;
		if (!hasWater)
			hasWater = pipes[inPipeIdx].drainWater();
	}
	
	
	
	
	
	@Override public void draw(Graphics g) {
		Coords nc = getCoordsForIdx(inPipeIdx);
		g.setColor(new Color(0, 230, 0));//zöldből a pirosba pumpál
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		nc = getCoordsForIdx(outPipeIdx);
		g.setColor(new Color(230, 0, 0));
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		g.setColor(new Color(isBroken? 200 : 50, 0, 0));
		super.draw(g);
	}
}
