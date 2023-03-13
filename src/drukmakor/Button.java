package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button implements Drawable, MouseListener {
	Coords c;
	Element e;
	Button(Coords c, Element connectedelement) {
		this.c = c; 
		e = connectedelement;
		Main.d.addMouseListener(this);
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(new Color(Main.isalteringpump?0:255, 100, 100));
		g.drawRect(c.x-4, c.y-4, 9, 9);
	}
	void click() {
		Main.playergoto(e);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Coords clickpos = new Coords(e.getX(), e.getY());
		if (Math.abs(clickpos.x - c.x)< 6 &&Math.abs(clickpos.y - c.y)< 6)
			click();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
