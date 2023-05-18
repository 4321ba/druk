package drukmakor;

import java.awt.Graphics;

public abstract class CharacterView implements Drawable {
	private boolean soros = false;
	public void setSoros(boolean nv) { soros = nv; }
	
	protected abstract Character getModel();
	
	@Override public void draw(Graphics g) {
		Coords c = getModel().getCurrentPosition().getView().getCoords();
		g.drawRect(c.x-5, c.y-15, 10, 20);
		if (soros)
			g.fillOval(c.x-5, c.y-25, 10, 10);
	}
}
