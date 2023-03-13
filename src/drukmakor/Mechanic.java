package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class Mechanic extends Character {
	private Pipe holdingPipe = null;
	boolean hasPump = false;
	public Mechanic(Element cp) {
		super(cp);
	}
	boolean fixCurrent() {
		return currentPosition.fix();
	}

	boolean pickUpPipe(Pipe p) {
		if (holdingPipe != null || !currentPosition.pickUpPipe(p))//rövidzár??
			return false;
		holdingPipe = p;
		//p.disconnectFrom(currentPosition);//TODO
		return true;
	}
	boolean connectPipe() {
		if (holdingPipe == null)
			return false;
		return currentPosition.placePipe(holdingPipe);
	}

	boolean pickUpPump() {
		return false;//TODO
	}
	boolean placePump() {
		return false;//TODO
	}
	
	

	@Override public void draw(Graphics g) {
		g.setColor(new Color(255,255,255));
		super.draw(g);
	}
	
}
