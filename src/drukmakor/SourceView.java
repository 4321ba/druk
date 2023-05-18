package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class SourceView extends ActiveElementView {
	Source source;
	public SourceView(Source s, Coords co) {
		super(s, co);
		source = s;
	}
	@Override public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 245));
		super.draw(g);
	}
}
