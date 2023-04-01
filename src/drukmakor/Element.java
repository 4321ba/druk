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
	boolean connectPipe(Pipe p, int idx) {
		return false;
	}
	Pipe disconnectPipe(int idx) {
		return null;
	}
	boolean alterPump(int inPipeIdx, int outPipeIdx) {
		return false;
	}
	Pipe pickUpDanglingPipe(int idx) {
		return null;
	}
	Pump pickUpPump() {
		return null;
	}
	boolean placePump(Pump p) {
		return false;
	}
	
	
	
	
	abstract Coords getCoords();
	
	Button b;
	
	@Override
	public void draw(Graphics g) {
		b.c=getCoords();
		b.draw(g);
		if (!hasWater)
			return;
		Coords c = getCoords();
		g.setColor(new Color(100, 100, 245));
		g.fillOval(c.x-2, c.y+6, 5, 12);
	}
}
