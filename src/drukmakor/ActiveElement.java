package drukmakor;

import java.security.InvalidParameterException;

/**
 * Képes egy csatlakozó csőből magába vizet húzni, és/vagy egy csatlakozó csőbe magából vizet
 * juttatni. Ugyanakkor lehetővé teszi, hogy csövet csatlakoztassanak hozzá.
 *
 */
public abstract class ActiveElement extends Element {

	static final int MAX_CONNECTIONS = 8;
	/**
	 * azok a csövek, amelyek hozzá csatlakoznak
	 */
	protected Pipe[] pipes = new Pipe[MAX_CONNECTIONS];
	public Pipe[] getPipes() { return pipes; }
	/**
	 *  rá enged lépni egy karaktert, ha
	 *  csatlakozik from elemhez
	 */
	@Override public Element acceptCharacter(Element from, Character who) {
		if (from == null)
			return this;
		for (Pipe p: pipes)
			if (p == from) // referencia szerinti hasonlítás
			{
				from.characterExited();
				return this;
			}
		return null;
	}
	
	/**
	 * csatlakoztat egy eddig egyik végén
	 * szabad csövet magához, a megadott indexű helyre. Ellenőrzi, hogy elég hely van-e a
	 * listában, hogy lenne-e hurokél, -1-es paraméter esetén bárhova lehet kötni
	 */
	@Override public boolean connectPipe(Pipe p, int idx) {
		if (idx == -1) {//bármilyen szabad helyre
			idx = MAX_CONNECTIONS; // hogy bekrepáljon ha nincs szabad hely
			for (int i = 0; i < MAX_CONNECTIONS; ++i) {
				if (pipes[i] == null) {
					idx = i;
					break;
				}
			}
		}
		if (idx >= MAX_CONNECTIONS || pipes[idx] != null || idx < 0)
			return false;
		boolean res = p.connectTo(this);
		if (res)
			pipes[idx] = p;
		return res;
	}
	/**
	 * felszedi a paraméterként kapottadik indexű
	 * cső saját oldalán levő végét és azt visszaadja
	 */
	@Override public Pipe disconnectPipe(int idx) {
		if (pipes[idx] == null)
			return null;
		Pipe p = pipes[idx];
		boolean res2 = p.disconnectFrom(this);
		if (!res2) 
			return null;
		pipes[idx] = null;
		return p;
	}
	/**
	 * p pipe-ot lecsatolja, és
	 * a pipe-ot is megkéri, hogy frissítse magát, híváskor kötelező, hogy p és this egymásra legyenek kapcsolva
	 * @param p
	 */
	public void disconnectPipe(Pipe p) {
		Pipe dcd = null;
		for (int i = 0; i < MAX_CONNECTIONS; ++i) {
			if (pipes[i] == p) { // referencia szerinti hasonlítás
				dcd = disconnectPipe(i);
				break;
			}
		}
		if (dcd == null)
			throw new InvalidParameterException("Nem sikerült a lecsatlakoztatás!");
	}
	/**
	 *  felveszi az indexedik helyen levő
	 *  lelógó csövet erről az elemről, nullt ad vissza, ha sikertelen
	 */
	@Override public Pipe pickUpDanglingPipe(int idx) {
		if (pipes[idx] != null)
			return pipes[idx].pickUpDangling();
		return null;
	}
	/**
	 *  a beállított kimeneti csö(vek)be megpróbálja benyomni a vizet,
	 *  alapértelmezetten nem csinál semmit, leszármazottak definiálhatják felül
	 */
	public void pushWater() {
	}
	/**
	 * a beállított bemeneti csö(vek)ből megpróbál vízhez jutni,
	 * alapértelmezetten nem csinál semmit, leszármazottak definiálhatják felül
	 */
	public void pullWater() {
	}
	/**
	 * néha meghívódik, alapértelmezetten nem csinál semmit,
	 * leszármazottak definiálhatják felül
	 */
	public void randomEvent() {
	}
	/**
	 * meghívja a saját pushWater és pullWater függvényét, illetve a
	 * randomEvent-et néha. Ezzel végrehajtja az aktív elem egy frissítési ciklusát
	 */
	@Override public void tick() {
		//if ()
		pushWater();
		pullWater();
	    if (Proto.randomNextDouble() >= 0.9)
	    	randomEvent();
	}
	
	
}
