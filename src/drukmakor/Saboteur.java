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
	/**
	 *  csúszósozza az elemet, amin áll (csak akkor lehet sikeres, ha csövön áll), 
	 *  visszaadja, amit az Element azonos nevű függvényétől kapott
	 * @return
	 */
	public boolean slipperyPipe() {
		return currentPosition.slipperyPipe();
	}

	
	

	/**
	 * A get parancs által kiírandó értékeket adja vissza a megfelelő sorrendben, így megvalósítva az állapotok lekérdezését.
	 */
	@Override
	public Object[] get() {
		//<aktuális pozíció> <meddig ragad (egész szám)>
		return new Object[] { currentPosition, isStuck };
	}
	
	
	

	/**
	 * a modell objektumhoz tartozó view
	 */
	private SaboteurView view;
	/**
	 * visszaadja a modell objektumhoz tartozó viewt
	 */
	public SaboteurView getView() { return view; }
	/**
	 * setter
	 */
	public void setView(SaboteurView v) { view = v; }
}
