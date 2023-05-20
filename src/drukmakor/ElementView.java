package drukmakor;

/**
 * Az elementeket megjelenítő view osztályok közös őse. Lehet koordinátát kérdezni tőle.
 */
public abstract class ElementView implements Drawable {
	/**
	 * visszaadja a view pozícióját
	 */
	public abstract Coords getCoords();
}
