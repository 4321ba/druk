package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Element implements Drawable {
	protected boolean hasWater = false;

	boolean fix() {
		return false;
	}
	boolean breac() {//for pump
		return false;
	}
	boolean pierce() {
		return false;//for pipe
	}
	abstract boolean acceptCharacter(Element from);
	void characterExited() {}
	boolean placePipe(Pipe p) {
		return false;
	}
	boolean pickUpPipe(Pipe p) {
		return false;
	}
	
	
	abstract Coords getCoords();
	
	Button b;
	
	@Override
	public void draw(Graphics g) {
		b.draw(g);
		if (!hasWater)
			return;
		Coords c = getCoords();
		g.setColor(new Color(100, 100, 245));
		g.fillOval(c.x-2, c.y+6, 5, 12);
	}
}
