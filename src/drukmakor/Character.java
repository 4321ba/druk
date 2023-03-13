package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Character implements Drawable {
	protected Element currentPosition;
	public Character(Element cp) {//accept??? pl cső legyen foglalt
		currentPosition = cp;
	}
	public boolean alterPump(Pump pump, Pipe inPipe, Pipe outPipe) {
		if (pump == currentPosition)
			return pump.alter(inPipe, outPipe);
		return false;
	}
	public boolean moveTo(Element e) {
		if (!e.acceptCharacter(currentPosition))
			return false;
		currentPosition.characterExited();
		currentPosition = e;
		return true;
	}
	
	boolean soros = false;
	@Override public void draw(Graphics g) {
		Coords c = currentPosition.getCoords();
		g.drawRect(c.x-5, c.y-15, 10, 20);
		if (soros)
			g.fillOval(c.x-5, c.y-25, 10, 10);
	}
}
