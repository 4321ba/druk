package drukmakor;

/**
 * Ide kell eljuttatni a vizet a szerelőknek. Csövek kapcsolódhatnak hozzá. Időközönként
 * termelődik itt új cső. Tudja mennyi víz jutott el bele. A szerelők itt vehetnek fel új pumpát,
 * bármennyit. Mindig kiszívja a vizet a hozzá kapcsolódó csövekből.
 * Tudja milyen csövek kapcsolódnak bele.
 *
 */
public class Cistern extends ActiveElement {

	public Cistern() {
		Pr.fv(this, "Cistern");
		Pr.ret();
	}
	/**
	 * keletkezik egy új cső, ami lelóg róla
	 */
	@Override public void randomEvent() {
		Pr.fv(this, "randomEvent");
		for (Pipe p : pipes) { // van-e még szabad hely a ciszternán, ha nincs akkor nem hozunk létre új pipeot (különben exceptiont dob)
			if (p == null) {
				new Pipe(this, null);
				Pr.ret();
				return;
			}
		}
		Pr.ret();
	}
	/**
	 * végigmegy az összes rákötött csövön, és kiszívja
	 * belőlük a vizet, növelve ezzel waterLevelt, és pontot szerezve a szerelőknek
	 */
	@Override public void pullWater() {
		Pr.fv(this, "pullWater");
		for (Pipe p : pipes)
			if (p != null)
				p.drainWater();
		Pr.ret();
	}
	
	/**
	 *  a dangling pipeokba belétol 1-1 vizet, és
	 *  ezzel analóg módon levon egy-egy pontot a szerelőktől (és a pipe-ok adnak egy-egy
	 *  pontot a szabotőröknek)
	 */
	@Override public void pushWater() {
		Pr.fv(this, "pushWater");
		for (Pipe p : pipes)
			if (p != null && Pr.inInt("waterLevel") > 0)
				p.wasteWater();
		Pr.ret();
	}
	/**
	 * egy új pumpát ad vissza mindig: bármikor lehet
	 * új pumpát felvenni a ciszternánál
	 */
	@Override public Pump pickUpPump() {
		Pr.fv(this, "pickUpPump");
		return Pr.ret(new Pump());
	}
	
	
	
	@Override
	public Object[] get() {
		int noValidPipes = MAX_CONNECTIONS; // number of valid pipes
		while (noValidPipes > 0 && pipes[noValidPipes - 1] == null)
			noValidPipes--;
		Object[] ret = new Object[noValidPipes + 1]; // <csövek> <vízszint (egész szám)>
		for (int i = 0; i < noValidPipes; ++i)
			ret[i] = pipes[i];
		ret[noValidPipes] = 0; // TODO kicserélni waterLevel-re ha fel lesz véve
		return ret;
	}

}
