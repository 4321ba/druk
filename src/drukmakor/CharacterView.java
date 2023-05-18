package drukmakor;

import java.awt.Graphics;

public abstract class CharacterView implements Drawable {
	private boolean soros = false;
	public void setSoros(boolean nv) { soros = nv; }
	

	private Character character;
	public CharacterView(Character c) {
		character = c;
	}
	
	@Override public void draw(Graphics g) {
		Coords c = character.getCurrentPosition().getView().getCoords();
		g.drawRect(c.x-5, c.y-15, 10, 20);
		if (soros)
			g.fillOval(c.x-5, c.y-25, 10, 10);
	}
}
