package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class Pump extends ActiveElement {

	public Pump(Coords c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	private Pipe inPipe;
	private Pipe outPipe;//DISCONNECT PIPE-nál ezeket is dcni!


	boolean isBroken = false;
	boolean fix() {
		boolean prevIsBroken = isBroken;
		isBroken = false;
		return prevIsBroken;
	}
	boolean breac() {
		boolean prevIsBroken = isBroken;
		isBroken = true;
		return !prevIsBroken;
	}
	@Override void randomEvent() {
		breac();
	}
	
	
	public boolean alter(Pipe inP, Pipe outP) {
		if (inP == outP)
			return false;
		if (inPipe == inP && outPipe == outP)
			return false;
		if (!pipes.contains(inP) || !pipes.contains(outP))
			return false;
		inPipe = inP;
		outPipe = outP;
		return true;
	}
	
	@Override
	void pushWater() {
		if (outPipe == null || isBroken)
			return;
		if (hasWater)
			hasWater = !outPipe.addWater();
	}	

	@Override
	void pullWater() {
		if (inPipe == null || isBroken)
			return;
		if (!hasWater)
			hasWater = inPipe.drainWater();
	}
	
	@Override public void draw(Graphics g) {
		if (inPipe != null) {
			Coords cin = inPipe.getCoords();
			cin = new Coords((3*c.x+cin.x)/4, (3*c.y+cin.y)/4);
			g.setColor(new Color(0, 230, 0));//zöldből a pirosba pumpál
			g.drawRoundRect(cin.x-1, cin.y-1, 4, 4, 1, 1);
		}
		if (outPipe != null) {
			Coords cout = outPipe.getCoords();
			cout = new Coords((3*c.x+cout.x)/4, (3*c.y+cout.y)/4);
			g.setColor(new Color(230, 0, 0));
			g.drawRoundRect(cout.x-1, cout.y-1, 4, 4, 1, 1);
		}
		g.setColor(new Color(isBroken? 200 : 50, 0, 0));
		super.draw(g);
	}
}
