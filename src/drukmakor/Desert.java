package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Desert extends JPanel {

	private static Desert desert = new Desert();
	public static Desert get() { return desert; }
	private Desert() {}
	
	private static final long serialVersionUID = 1L;
	private List<Drawable> drawableList = new ArrayList<Drawable>();
	public void addDrawable(Drawable d) {
		drawableList.add(d);
	}
	public void clearDrawable() {
		drawableList.clear();
		addDrawable(PointCounter.get());
	}

	@Override protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	    g.setColor(new Color(140, 78, 7));
		g.fillRect(0, 0, getWidth(), getHeight());
	    //g.setColor(new Color(40,100,40));
		//g.drawRoundRect(1, 2, 30, 40, 3, 4);
		g.setColor(new Color(0,0,0));
		g.drawString("s:skip b:breakpipe f:fix a:alterpump c:connectpipe d:dcpipe p:pickuppump o:placepump e:pickupdanglingpipe numbers:inputforpreviousthings", 10, 30);
		for (Drawable dr : drawableList)
			dr.draw(g);
	}
}
