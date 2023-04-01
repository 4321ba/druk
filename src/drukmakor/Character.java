package drukmakor;

import java.awt.Graphics;
import java.security.InvalidParameterException;

public abstract class Character implements Drawable {
	protected Element currentPosition;
	public Character(Element cp) {
		boolean res = cp.acceptCharacter(null);
		if (!res)
			throw new InvalidParameterException("Nem fogadta be a cp a karaktert!");
		currentPosition = cp;
	}
	public boolean alterPump(int inPipeIdx, int outPipeIdx) {
		return currentPosition.alterPump(inPipeIdx, outPipeIdx);
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
