package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Element implements Drawable {
	protected boolean hasWater = false;

	boolean fix() {
		return false;
	}
	boolean piercePipe() {
		return false;//for pipe
	}
	boolean acceptCharacter(Element from) {
		return false;
	}
	void characterExited() {}
	boolean connectPipe(Pipe p) {
		return false;
	}
	boolean disconnectPipe(Pipe p) {
		return false;
	}
	Pipe pickUpDanglingPipe() {
		return null;
	}
	boolean pickUpPump() {
		return false;
	}
	Pump placePump() {
		return null;
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
