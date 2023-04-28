package drukmakor;

public interface Tickable {
	/**
	 * Tick fv, amit minden felülír
	 * Egységnyi idő elteltére történő reakciót kell imlementálni a megvalósító osztályoknak.
	 */
	abstract public void tick();
	
	/**
	 * A get parancs által kiírt értékeket adja vissza, így megvalósítva az állapotok lekérdezését.
	 *  Ezt minden nem absztrakt leszármazott implementálja, melyben visszaadja a saját állapotát 
	 *  (tagváltozók összességét).
	 *  Ugyanemiatt került a Tickable interfészhez ez a függvény, 
	 * mert ez úgyis ideiglenes a prototípus erejéig (egyébként SoC).
	 * @return
	 */
	public Object[] get();
	
}
