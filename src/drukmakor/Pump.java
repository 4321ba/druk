package drukmakor;

/**
 * Aktív elem, ami mozgatja a vizet a bemenetéből saját magába, illetve saját magából a
 * kimenetébe. El tud romlani. Állhat rajta akár több Mechanic és Saboteur is. Ők állíthatják a
 * bemenetét és a kimenetét. Véges számú cső kapcsolódhat bele.
 * Tudja milyen csövek kapcsolódnak bele.
 *
 */
public class Pump extends ActiveElement {

	public Pump() {
		Pr.fv(this, "Pump");
		Pr.ret();
	}
	/**
	 * megjavítja a pumpát
	 */
	@Override public boolean fix() {
		Pr.fv(this, "fix");
		return Pr.ret(Pr.inBool("isBroken"));
	}
	/**
	 * isBroken igaz lesz
	 */
	@Override public void randomEvent() {
		Pr.fv(this, "randomEvent");
		Pr.ret();
	}
	/**
	 * beállítja a kimeneti és a bemeneti cső indexet
	 */
	@Override public boolean alterPump(int inPipeIdx, int outPipeIdx) {
		Pr.fv(this, "alterPump", inPipeIdx, outPipeIdx);
		if (inPipeIdx == outPipeIdx)
			return Pr.ret(false);
		if (inPipeIdx < 0 || inPipeIdx >= MAX_CONNECTIONS || outPipeIdx < 0 || outPipeIdx >= MAX_CONNECTIONS)
			return Pr.ret(false);
		return Pr.ret(true);
	}
	/**
	 * ha nincs eltörve, és van kimeneti cső beállítva, és van
	 * benne víz, akkor átadja a vizet outPipe-nak, amennyiben az befogadja
	 */
	@Override
	public void pushWater() {
		Pr.fv(this, "pushWater");
		if (pipes[Pr.inInt("outPipeIdx")] == null || Pr.inBool("isBroken")) {
			Pr.ret();
			return;
		}
		if (Pr.inBool("hasWater"))
			pipes[Pr.inInt("outPipeIdx")].addWater();
		Pr.ret();
	}	
	/**
	 * ha nincs eltörve, és van bemeneti cső beállítva, és
	 * nincs már eleve a pumpában víz, akkor megkéri a bemeneti csövet, hogy adjon vizet
	 */
	@Override
	public void pullWater() {
		Pr.fv(this, "pullWater");
		if (pipes[Pr.inInt("inPipeIdx")] == null || Pr.inBool("isBroken")) {
			Pr.ret();
			return;
		}
		if (!Pr.inBool("hasWater"))
			pipes[Pr.inInt("inPipeIdx")].drainWater();
		Pr.ret();
	}
	@Override
	public Object[] get() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
