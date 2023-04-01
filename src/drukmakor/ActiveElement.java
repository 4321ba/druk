package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public abstract class ActiveElement extends Element {

	static final int MAX_CONNECTIONS = 8;
	Pipe[] pipes = new Pipe[MAX_CONNECTIONS];
	@Override boolean acceptCharacter(Element from) {
		if (from == null)
			return true;
		for (Pipe p: pipes)
			if (p == from)//referencia szerinti hasonlítás
				return true;
		return false;
	}
	@Override boolean connectPipe(Pipe p, int idx) {
		if (idx == -1) {//bármilyen szabad helyre
			idx = MAX_CONNECTIONS; // hogy bekrepáljon ha nincs szabad hely
			for (int i = 0; i < MAX_CONNECTIONS; ++i) {
				if (pipes[i] == null) {
					idx = i;
					break;
				}
			}
		}
		if (idx >= MAX_CONNECTIONS || pipes[idx] != null)
			return false;
		boolean res = p.connectTo(this);
		if (res)
			pipes[idx] = p;
		return res;
	}
	@Override Pipe disconnectPipe(int idx) {
		if (pipes[idx] == null)
			return null;
		Pipe p = pipes[idx];
		boolean res2 = p.disconnectFrom(this);
		if (!res2) 
			return null;
		pipes[idx] = null;
		return p;
	}
	
	boolean disconnectPipe(Pipe p) {// kell a visszatérési érték???? úgyis amikor meghívják, mindig truet kéne visszaadjon TODO void ??
		for (int i = 0; i < MAX_CONNECTIONS; ++i)
			if (pipes[i] == p)//referencia szerinti hasonlítás
				return null != disconnectPipe(i);
		return false;
	}
	
	@Override Pipe pickUpDanglingPipe(int idx) {
		if (pipes[idx]!= null)
			return pipes[idx].pickUpDangling();
		return null;
	}
	
	void pushWater() {}
	void pullWater() {}
	void randomEvent() {}
	
	void tick(boolean isPushing) {
		if (isPushing)
			pushWater();
		else
			pullWater();
	    Random rand = new Random();
	    if (rand.nextDouble() < 0.01)
	    	randomEvent();
	}
	
	
	public ActiveElement(Coords c) {
		Main.d.drl.add(this);
		WaterController.get().add(this);
		this.c = c;
		b = new Button(getCoords(), this);
	}
	
	Coords getCoordsForIdx(int idx) {
		Coords c2 = c.copy();
		double phi = 2*Math.PI*idx / MAX_CONNECTIONS;
		c2.x += Math.cos(phi) * 30;
		c2.y += Math.sin(phi) * 30;
		return c2;
	}
	Coords getCoordsForPipe(Pipe p) {
		for (int i = 0; i < MAX_CONNECTIONS; ++i)
			if (pipes[i] == p)
				return getCoordsForIdx(i);
		throw new RuntimeException("nincs is benne a pipe");
	}
	
	Coords c;
	@Override public void draw(Graphics g) {//színbeállítás a gyerekeknél kötelező
		g.fillOval(c.x-6, c.y-6, 12, 12);
		g.setColor(new Color(0, 0, 50));
		for (int i = 0; i < MAX_CONNECTIONS; ++i) {
			Coords nc = getCoordsForIdx(i);
			g.drawRect(nc.x-4, nc.y-4, 9, 9);
			g.drawString(Integer.toString(i), nc.x-3, nc.y+6);
		}
		super.draw(g);
	}
	
	@Override
	Coords getCoords() {
		return c;
	}
}
