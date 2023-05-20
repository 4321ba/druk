package drukmakor;

import java.awt.Graphics;

/**
 * Interfész azoknak, akiket ki lehet rajzolni.
 */
public interface Drawable {
	/**
	 * a leszármazott ebben a függvényben rajzolja ki magát, a rajzolás g-re történik
	 */
	public void draw(Graphics g);
}
