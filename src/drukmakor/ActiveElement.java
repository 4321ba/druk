package drukmakor;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class ActiveElement extends Element {

	static final int MAX_CONNECTIONS = 8;
	List<Pipe> pipes = new ArrayList<Pipe>();
	@Override boolean acceptCharacter(Element from) {
		if (from == null)
			return true;
		return pipes.contains(from);//referencia szerinti hasonlítás kéne
	}
	@Override boolean connectPipe(Pipe p) {
		if (!p.hasConnectionTo(null))
			throw new RuntimeException("Nem jó csőcsatlakoztatási próbálkozás!");
		if (pipes.size() >= MAX_CONNECTIONS)
			return false;
		if (p.hasConnectionTo(this))//ez lehetséges ha pl hurokélt akarunk csinálni
			return false;
		for (Pipe pipe: pipes)
			if (!pipe.hasConnectionTo(null) && p.hasConnectionTo(pipe.getOtherEnd(this)))//azért, hogy ha két mechanic 2 pipeot huzigál uarról az aeről, még megengedett legyen
				return false;
		pipes.add(p);
		p.connectTo(this);
		return true;
	}
	@Override boolean disconnectPipe(Pipe p) {
		boolean res1 = pipes.remove(p);
		if (!res1)
			return false;
		boolean res2 = p.disconnectFrom(this);
		if (!res2)
			pipes.add(p);
		return res2;
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
	@Override public void draw(Graphics g) {//színbeállítás a gyerekeknél kötelező
		g.fillOval(c.x-6, c.y-6, 12, 12);
		super.draw(g);
	}
	
	@Override
	Coords getCoords() {
		return c;
	}
}
