package drukmakor;

/**
 * A játékos olyan változata, aki tud szabotálni, vagyis elrontani pumpákat, illetve kilyukasztani
 * csöveket.
 * Tudja, hogy melyik elemen áll és (csak) azt képes elrontani/átállítani.
 *
 */
public class Saboteur extends Character {
	/**
	 * meghívja az ősosztály konstruktorát
	 * @param cp
	 */
	public Saboteur(Element cp) {
		super(cp);
	}
	
	public boolean slipperyPipe() {
		return currentPosition.slipperyPipe();
	}

	@Override
	public Object[] get() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
