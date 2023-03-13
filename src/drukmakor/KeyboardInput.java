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
