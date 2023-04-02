package drukmakor;

/**
 * Ide kell eljuttatni a vizet a szerelőknek. Csövek kapcsolódhatnak hozzá. Időközönként
termelődik itt új cső. Tudja mennyi víz jutott el bele. A szerelők itt vehetnek fel új pumpát,
bármennyit. Mindig kiszívja a vizet a hozzá kapcsolódó csövekből.
Tudja milyen csövek kapcsolódnak bele.
 *
 */
public class Cistern extends ActiveElement {

	public Cistern() {
		
	}
	private int waterLevel = 0;
	/**
	 * keletkezik egy új cső, ami lelóg róla
	 */
	@Override public void randomEvent() {
		for (Pipe p : pipes) {// van-e még szabad hely a ciszternán, ha nincs akkor nem hozunk létre új pipeot
			if (p == null) {
				new Pipe(this, null);
				return;
			}
		}
	}
	/**
	 * végigmegy az összes rákötött csövön, és kiszívja
belőlük a vizet, növelve ezzel waterLevelt, és pontot szerezve a szerelőknek
	 */
	@Override public void pullWater() {
		for (Pipe p : pipes)
			if (p != null && p.drainWater()) {
				++waterLevel;
			}
	}
	
	/**
	 *  a dangling pipeokba belétol 1-1 vizet, és
ezzel analóg módon levon egy-egy pontot a szerelőktől (és a pipe-ok adnak egy-egy
pontot a szabotőröknek)
	 */
	@Override public void pushWater() {
		for (Pipe p : pipes) {
			if (p != null && waterLevel > 0) {
				if (p.wasteWater()) {
					--waterLevel;
				}
			}
		}
	}
	/**
	 * egy új pumpát ad vissza mindig: bármikor lehet
új pumpát felvenni a ciszternánál
	 */
	@Override public Pump pickUpPump() {
		return new Pump();
	}

}
