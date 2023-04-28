package drukmakor;

/**
 * Innen kell eljuttatni a vizet a ciszternákba a szerelőknek. Csövek kapcsolódnak hozzá.
 * A szerelők új csöveket is csatlakoztathatnak hozzá. Mindig pumpál vizet a hozzá kapcsolódó
 * csövekbe.
 * Tudja melyik csövek kapcsolódnak bele.
 *
 */
public class Source extends ActiveElement {

	/**
	 *  az összes kimeneti csőbe megpróbál vizet benyomni
	 */
	@Override public void pushWater() {
		for (Pipe p : pipes)
			if (p != null)
				p.addWater();
	}
	
	
	@Override
	public Object[] get() {
		int noValidPipes = MAX_CONNECTIONS; // number of valid pipes
		while (noValidPipes > 0 && pipes[noValidPipes - 1] == null)
			noValidPipes--;
		Object[] ret = new Object[noValidPipes]; // <csövek>
		for (int i = 0; i < noValidPipes; ++i)
			ret[i] = pipes[i];
		return ret;
	}
}
