package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class SourceView extends ActiveElementView {
	Source model;
	public SourceView(Source m, Coords c) {
		super(c);
		model = m;
	}
	@Override public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 245));
		super.draw(g);
	}
	@Override
	protected Source getModel() {
		return model;
	}
}
