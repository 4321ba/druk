package drukmakor;

public abstract class ActiveElementView extends ElementView {

	
	public ActiveElementView(Coords c) {
		Main.d.drl.add(this);
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
	
	private Coords c;
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
