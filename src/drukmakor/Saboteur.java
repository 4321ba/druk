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
	 * kiszúrja az elemet, amin áll (csak akkor lehet sikeres, ha csövön áll)
	 * @return
	 */
	public boolean piercePipe() {
		Pr.fv(this, "piercePipe");
		return Pr.ret(currentPosition.piercePipe());
	}

	
}
