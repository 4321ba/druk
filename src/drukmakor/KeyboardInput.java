package drukmakor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 's')
			Main.skip();
		if (e.getKeyChar() == 'b')
			Main.saboteurbreakpipe();
		if (e.getKeyChar() == 'f')
			Main.mechanicfix();
		if (e.getKeyChar() == 'a')
			Main.alterpump();
		if (e.getKeyChar() == 'c')
			Main.connectpipe();
		if (e.getKeyChar() == 'd')
			Main.disconnectpipe();
		if (e.getKeyChar() == 'p')
			Main.pickuppump();
		if (e.getKeyChar() == 'o')
			Main.placepump();
		if (e.getKeyChar() == 'e')
			Main.danglingpipe();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
}
