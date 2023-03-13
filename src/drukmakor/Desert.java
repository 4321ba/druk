package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Desert extends JPanel {

	private static final long serialVersionUID = 1L;
	List<Drawable> drl = new ArrayList<Drawable>();

	@Override protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	    g.setColor(new Color(140, 78, 7));
		g.fillRect(0, 0, getWidth(), getHeight());
	    //g.setColor(new Color(40,100,40));
		//g.drawRoundRect(1, 2, 30, 40, 3, 4);
		for (Drawable dr : drl)
			dr.draw(g);
	}
}
