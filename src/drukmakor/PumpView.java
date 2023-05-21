package drukmakor;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Megjeleníti a vele összekapcsolt, hozzá tartozó típusú modell objektumot.
 * Ehhez el kell őt tárolnia, és kérdezgetnie a rajzolás alatt.
 */
public class PumpView extends ActiveElementView {
	/**
	 * a viewhoz tartozó modell objektum
	 */
	private Pump model;
	/**
	 * visszaadja a viewhoz tartozó modell objektumot
	 */

	private final BufferedImage emptypumpIMG;
	private final BufferedImage emptypumpbrokenIMG;
	private final BufferedImage fullpumpIMG;
	private final BufferedImage fullpumpbrokenIMG;

	@Override
	protected Pump getModel() {
		return model;
	}
	/**
	 * konstruktor, beállítja a model attribútum értékét, és a pozíciót
	 */
	public PumpView(Pump m, Coords c) {
		super(c);
		model = m;

		try {
			emptypumpIMG = ImageIO.read(new File("emptypump.png"));
			emptypumpbrokenIMG = ImageIO.read(new File("emptypumpbroken.png"));
			fullpumpIMG = ImageIO.read(new File("fullpump.png"));
			fullpumpbrokenIMG = ImageIO.read(new File("fullpumpbroken.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	/**
	 * kirajzolja g-re a pumpot
	 */
	@Override public void draw(Graphics g) {
		Coords nc = getCoordsForIdx(model.getInPipeIdx());
		g.setColor(new Color(0, 230, 0)); // zöldből a pirosba pumpál
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		nc = getCoordsForIdx(model.getOutPipeIdx());
		g.setColor(new Color(230, 0, 0));
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		g.setColor(new Color(model.getIsBroken()? 200 : 50, 0, 0));
		super.draw(g);
		if (model.getHasWater()) {
			g.drawImage(model.getIsBroken() ? fullpumpbrokenIMG : fullpumpIMG, coords.x-31, coords.y-31, null);
		} else {
			g.drawImage(model.getIsBroken() ? emptypumpbrokenIMG : emptypumpIMG, coords.x-31, coords.y-31, null);
		}
		/*if (!model.getHasWater())
			return;
		g.setColor(new Color(100, 100, 245));
		g.fillOval(coords.x-2, coords.y+6, 5, 12);*/
		
	}
	/**
	 * setter
	 */
	public void setCoords(Coords c) {
		coords = c.copy();
	}
}
