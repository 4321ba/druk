package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class Pipe extends Element {

	ActiveElement end1;//nem lehet null!
	ActiveElement end2;//mindig ez a NULL ha van null!
	boolean isOccupied = false;
	
	boolean isBroken = false;
	boolean fix() {
		boolean prevIsBroken = isBroken;
		isBroken = false;
		return prevIsBroken;
	}
	boolean pierce() {
		boolean prevIsBroken = isBroken;
		isBroken = true;
		if (hasWater) {
			PointCounter.thePC.addSaboteurPoint();
			hasWater = false;
		}
		return !prevIsBroken;
	}
	
	
	public Pipe(ActiveElement ae1, ActiveElement ae2) {
		end1 = ae1;
		end2 = ae2;
		boolean res1 = end1.placePipe(this);//TODO ez a fv tényleg jó-e ehhez
		assert(res1);
		boolean res2 = end2.placePipe(this);
		assert(res2);
		b = new Button(getCoords(), this);
	}

	boolean hasConnectionTo(ActiveElement ae) {
		return end1 == ae || end2 == ae;
	}
	ActiveElement getOtherEnd(ActiveElement ae) {//vissazadja az egyik végét, amelyik nem ae
		assert(end1 != end2);
		if (end1 != ae)
			return end1;
		return end2;
	}
	boolean disconnectFrom(ActiveElement ae) {
		assert(end1 != end2);
		if (ae != end1 && ae != end2)
			return false;
		if (ae == end1)
			end1 = end2;
		end2 = null;
		return true;
	}
	boolean connectTo(ActiveElement ae) {
		if (end2 != null)
			return false;
		end2 = ae;
		return true;
	}
	
	@Override boolean acceptCharacter(Element from) {
		if (isOccupied)
			return false;
		if (from != end1 && from != end2)
			return false;
		isOccupied = true;
		return true;
	}
	@Override void characterExited() {
		isOccupied = false;
	}
	boolean addWater() {
		if (hasWater)
			return false;
		if (isBroken) {
			PointCounter.thePC.addSaboteurPoint();
			return true;
		}
		hasWater = true;
		return true;
	}
	boolean drainWater() {
		if (!hasWater)
			return false;
		hasWater = false;
		return true;
	}
	


	@Override public void draw(Graphics g) {
		g.setColor(new Color(isBroken? 200 : 50 , end2==null?200:0, isOccupied?255:0));
		Coords e1 = end1.getCoords();
		Coords e2 = end2.getCoords();
		g.drawLine(e1.x, e1.y, e2.x, e2.y);
		super.draw(g);
	}
	
	@Override
	Coords getCoords() {
		Coords e1 = end1.getCoords();
		Coords e2 = end2.getCoords();
		return new Coords((e1.x+e2.x)/2, (e1.y+e2.y)/2);
	}
}
