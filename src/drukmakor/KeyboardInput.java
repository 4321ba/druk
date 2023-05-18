package drukmakor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 's')
			Grafikus.skip();
		if (e.getKeyChar() == 'b')
			Grafikus.saboteurbreakpipe();
		if (e.getKeyChar() == 'f')
			Grafikus.mechanicfix();
		if (e.getKeyChar() == 'a')
			Grafikus.alterpump();
		if (e.getKeyChar() == 'c')
			Grafikus.connectpipe();
		if (e.getKeyChar() == 'd')
			Grafikus.disconnectpipe();
		if (e.getKeyChar() == 'p')
			Grafikus.pickuppump();
		if (e.getKeyChar() == 'o')
			Grafikus.placepump();
		if (e.getKeyChar() == 'e')
			Grafikus.danglingpipe();
		if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
			Grafikus.numberinput(e.getKeyChar()-'0');
		
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
