package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class Mechanic extends Character {
	private Pipe holdingPipe = null;
	boolean hasPump = false;
	public Mechanic(Element cp) {
		super(cp);
	}
	boolean fix() {
		return currentPosition.fix();
	}

	boolean disconnectPipe(Pipe p) {
		if (holdingPipe != null)
			return false;
		if (!currentPosition.disconnectPipe(p))
			return false;
		holdingPipe = p;
		return true;
	}
	boolean connectPipe() {
		if (holdingPipe == null)
			return false;
		boolean res = currentPosition.connectPipe(holdingPipe);
		if (res)
			holdingPipe = null;
		return res;
	}
	boolean pickUpDanglingPipe() {
		if (holdingPipe != null)
			return false;
		holdingPipe = currentPosition.pickUpDanglingPipe();
		return holdingPipe != null;
	}

	boolean pickUpPump() {
		if (hasPump)
			return false;
		hasPump = currentPosition.pickUpPump();
		return hasPump;
	}
	boolean placePump() {
		if(!hasPump)
			return false;
		Pump pu = currentPosition.placePump();
		hasPump = pu == null;
		boolean res = moveTo(pu);
		if (!res)
			throw new RuntimeException("Nem jól bekötött, nem tudunk átlépni a pumpára!");
		return !hasPump;
	}
	
	

	@Override public void draw(Graphics g) {
		if (hasPump) {
			g.setColor(new Color(50, 0, 0));
			Coords c = currentPosition.getCoords();
			g.fillOval(c.x, c.y-20, 12, 12);
		}
		if (holdingPipe!=null) {
			Coords c = currentPosition.getCoords().copy();
			c.y-=10;
			holdingPipe.drawfromplayer(c, g);
		}
		g.setColor(new Color(255,255,255));
		super.draw(g);
	}
	
}
