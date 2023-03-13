package drukmakor;

import java.awt.Color;
import java.awt.Graphics;

public class Saboteur extends Character {
	public Saboteur(Element cp) {
		super(cp);
	}
	boolean piercePipe() {
		return currentPosition.pierce();//TODO ezt csak csövön lehessen!!!
	}
	
	
	

	@Override public void draw(Graphics g) {
		g.setColor(new Color(255,128,128));
		super.draw(g);
	}
}
