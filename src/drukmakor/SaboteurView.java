package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Megjeleníti a vele összekapcsolt, hozzá tartozó típusú modell objektumot.
 * Ehhez el kell őt tárolnia, és kérdezgetnie a rajzolás alatt.
 */
public class SaboteurView extends CharacterView {
	/**
	 * a viewhoz tartozó modell objektum
	 */
	private Saboteur model;
	
	
	private final BufferedImage profileIMG;

	
	/**
	 * konstruktor, beállítja a model attribútum értékét
	 */
	public SaboteurView(Saboteur m) {
		model = m;
		
		try {
			profileIMG = ImageIO.read(new File("images/bloodysabgirl_icon.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * visszaadja a viewhoz tartozó modell objektumot
	 */
	@Override
	protected Saboteur getModel() {
		return model;
	}
	/**
	 * kirajzolja g-re a saboteurt
	 */
	@Override public void draw(Graphics g) {
		g.setColor(new Color(255,128,128));
		
		
		Coords cpc = model.getCurrentPosition().getView().getCoords();
		
		g.drawImage(profileIMG, cpc.x-31, cpc.y-31, null);
		super.draw(g);

	}
}
