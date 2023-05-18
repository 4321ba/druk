package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class SaboteurView extends CharacterView {
	Saboteur saboteur;
	public SaboteurView(Saboteur s) {
		super(s);
		saboteur = s;
	}
	@Override public void draw(Graphics g) {
		g.setColor(new Color(255,128,128));
		super.draw(g);
	}
}
