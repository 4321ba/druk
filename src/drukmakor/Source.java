package drukmakor;

/**
 * Innen kell eljuttatni a vizet a ciszternákba a szerelőknek. Csövek kapcsolódnak hozzá.
 * A szerelők új csöveket is csatlakoztathatnak hozzá. Mindig pumpál vizet a hozzá kapcsolódó
 * csövekbe.
 * Tudja melyik csövek kapcsolódnak bele.
 *
 */
public class Source extends ActiveElement {

	public Source() {
		Pr.fv(this, "Source");
		Pr.ret();
	}
	/**
	 *  az összes kimeneti csőbe megpróbál vizet benyomni
	 */
	@Override public void pushWater() {
		Pr.fv(this, "pushWater");
		for (Pipe p : pipes)
			if (p != null)
				p.addWater();
		Pr.ret();
	}
}
