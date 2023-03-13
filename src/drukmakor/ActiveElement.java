package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class ActiveElement extends Element {

	static final int MAX_CONNECTIONS = 8;
	List<Pipe> pipes = new ArrayList<Pipe>();
	@Override boolean acceptCharacter(Element from) {
		return pipes.contains(from);//referencia szerinti hasonlítás kéne
	}
	@Override boolean placePipe(Pipe p) {
		if (pipes.size() >= MAX_CONNECTIONS)
			return false;
		for (Pipe pipe: pipes)
			if (p.hasConnectionTo(pipe.getOtherEnd(this)))
				return false;
		pipes.add(p);
		return true;
	}
	void pushWater() {
		
	}
	void pullWater() {
		
	}
	void randomEvent() {
		
	}
	
	
	public ActiveElement(Coords c) {
		this.c = c;
		b = new Button(getCoords(), this);
	}
	Coords c;
	@Override public void draw(Graphics g) {//TODO színbeállítás a gyerekeknél kötelező
		g.fillOval(c.x-6, c.y-6, 12, 12);
		super.draw(g);
	}
	
	@Override
	Coords getCoords() {
		return c;
	}
}
