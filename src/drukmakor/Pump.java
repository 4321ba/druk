package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class Pump extends ActiveElement {

	public Pump(Coords c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	private int inPipeIdx = 0;
	private int outPipeIdx = 1;


	boolean isBroken = false;
	@Override boolean fix() {
		boolean prevIsBroken = isBroken;
		isBroken = false;
		return prevIsBroken;
	}
	@Override void randomEvent() {
		isBroken = true;
	}

	@Override public boolean alterPump(int inPipeIdx, int outPipeIdx) {
		if (inPipeIdx == outPipeIdx)
			return false;
		if (inPipeIdx == this.inPipeIdx && outPipeIdx == this.outPipeIdx)
			return false;
		if (inPipeIdx < 0 || inPipeIdx >= MAX_CONNECTIONS || outPipeIdx < 0 || outPipeIdx >= MAX_CONNECTIONS)
			return false;
		this.inPipeIdx = inPipeIdx;
		this.outPipeIdx = outPipeIdx;
		return true;
	}
	
	@Override
	void pushWater() {
		if (pipes[outPipeIdx] == null || isBroken)
			return;
		if (hasWater)
			hasWater = !pipes[outPipeIdx].addWater();
	}	

	@Override
	void pullWater() {
		if (pipes[inPipeIdx] == null || isBroken)
			return;
		if (!hasWater)
			hasWater = pipes[inPipeIdx].drainWater();
	}
	
	@Override public void draw(Graphics g) {
		Coords nc = getCoordsForIdx(inPipeIdx);
		g.setColor(new Color(0, 230, 0));//zöldből a pirosba pumpál
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		nc = getCoordsForIdx(outPipeIdx);
		g.setColor(new Color(230, 0, 0));
		g.drawRect(nc.x-3, nc.y-3, 7, 7);
		g.setColor(new Color(isBroken? 200 : 50, 0, 0));
		super.draw(g);
	}
}
