package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class Mechanic extends Character {
	private Pipe holdingPipe = null;
	private Pump holdingPump = null;
	
	public Mechanic(Element cp) {
		super(cp);
	}
	boolean fix() {
		return currentPosition.fix();
	}

	boolean disconnectPipe(int index) {
		if (holdingPipe != null)
			return false;
		holdingPipe = currentPosition.disconnectPipe(index);
		return holdingPipe != null;
	}
	boolean connectPipe(int index) {
		if (holdingPipe == null)
			return false;
		boolean res = currentPosition.connectPipe(holdingPipe, index);
		if (res)
			holdingPipe = null;
		return res;
	}
	boolean pickUpDanglingPipe(int idx) {
		if (holdingPipe != null)
			return false;
		holdingPipe = currentPosition.pickUpDanglingPipe(idx);
		return holdingPipe != null;
	}

	boolean pickUpPump() {
		if (holdingPump != null)
			return false;
		holdingPump = currentPosition.pickUpPump();
		return holdingPump != null;
	}
	boolean placePump() {
		if(holdingPump == null)
			return false;
		boolean res1 = currentPosition.placePump(holdingPump);
		if (!res1)
			return false;
		boolean res2 = moveTo(holdingPump);
		if (!res2)
			throw new RuntimeException("Nem jól bekötött, nem tudunk átlépni a pumpára!");
		holdingPump = null;
		return true;
	}
	
	

	@Override public void draw(Graphics g) {
		/*if (hasPump) {
			g.setColor(new Color(50, 0, 0));
			Coords c = currentPosition.getCoords();
			g.fillOval(c.x, c.y-20, 12, 12);
		}*/
		if (holdingPump!=null)
			holdingPump.c = new Coords(currentPosition.getCoords().x+6, currentPosition.getCoords().y-14);
		if (holdingPipe!=null) {
			Coords c = currentPosition.getCoords().copy();
			c.y-=10;
			holdingPipe.drawfromplayer(c, g);
		}
		g.setColor(new Color(255,255,255));
		super.draw(g);
	}
	
}
