package drukmakor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

	private enum DoingSomething {
		NOTHING,
	    MOVETO,
	    ALTERPUMP,
	    DISCONNECTPIPE, 
	    CONNECTPIPE,
	    PICKUPDANGLINGPIPE,
	}
	private static String[] layout = new String[] {
		"f: Fix",
		"b: Pierce Pipe",
		"p: Pick Up Pump",
		"o: Place Pump",
		"t: Sticky Pipe",
		"l: Slippery Pipe",
		"wasd: Move To",
		"m + number: Move To",
		"c + number: Connect Pipe",
		"e + number: Disconnect Pipe",
		"r + number: Pick Up Dangling Pipe",
		"q + number + number: Alter Pump",
	};
	public static String[] getLayout() {
		return layout;
	}
	private DoingSomething doingSomething = DoingSomething.NOTHING;
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (!Grafikus.areTherePlayers())
			return;
		Character character = Grafikus.getSoros();
		char c = e.getKeyChar();
		if (c == 'f')
			character.fix();
		else if (c == 'b')
			character.piercePipe();
		else if (c == 'p')
			character.pickUpPump();
		else if (c == 'o')
			character.placePump();
		else if (c == 't')
			character.stickyPipe();
		else if (c == 'l')
			character.slipperyPipe();
		else if (c == 'm')
			doingSomething = DoingSomething.MOVETO;
		else if (c == 'c')
			doingSomething = DoingSomething.CONNECTPIPE;
		else if (c == 'e')
			doingSomething = DoingSomething.DISCONNECTPIPE;
		else if (c == 'r')
			doingSomething = DoingSomething.PICKUPDANGLINGPIPE;
		else if (c == 'q')
			doingSomething = DoingSomething.ALTERPUMP;
		else if (c >= '0' && c <= '9')
			numberInput(c-'0');
		else if (c == 'w' || c == 'a' || c == 's' || c == 'd')
			wasdInput(c);
	}

	@Override public void keyPressed(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}
	
	private void wasdInput(char wasd) {
		Element[] neighbours = Grafikus.getSoros().getCurrentPosition().getNeighbours();
		Element chosenOne = null;
		if (neighbours.length == 2) { // ha 2 út közül kell választanunk, akkor amelyik a legjobban a megfelelő égtáj felé néz
			Element e1 = neighbours[0];
			Coords c1 = e1.getView().getCoords();
			Element e2 = neighbours[1];
			Coords c2 = e2.getView().getCoords();
			if (wasd == 'w')
				chosenOne = c1.y < c2.y ? e1 : e2;
			else if (wasd == 'a')
				chosenOne = c1.x < c2.x ? e1 : e2;
			else if (wasd == 's')
				chosenOne = c1.y > c2.y ? e1 : e2;
			else
				chosenOne = c1.x > c2.x ? e1 : e2;
		} else {
			int idx = 0;
			if (wasd == 'w') idx = 3;
			else if (wasd == 'a') idx = 2;
			else if (wasd == 's') idx = 1;
			else idx = 0;
			chosenOne = neighbours[idx];
		}
		if (chosenOne != null)
			Grafikus.getSoros().moveTo(chosenOne);
	}

	private int firstAlterPump = -1;
	private void numberInput(int n) {
		Character character = Grafikus.getSoros();
		switch (doingSomething) {
		case ALTERPUMP:
			if (firstAlterPump == -1) {
				firstAlterPump = n;
				return;
			}
			character.alterPump(firstAlterPump, n);
			firstAlterPump = -1;
			break;
		case DISCONNECTPIPE:
			character.disconnectPipe(n);
			break;
		case CONNECTPIPE:
			character.connectPipe(n);
			break;
		case PICKUPDANGLINGPIPE:
			character.pickUpDanglingPipe(n);
			break;
		case MOVETO:
			Element[] neighbours = Grafikus.getSoros().getCurrentPosition().getNeighbours();
			if (n < neighbours.length && neighbours[n] != null)
				character.moveTo(neighbours[n]);
			break;
		case NOTHING:
			break;
		}
		doingSomething = DoingSomething.NOTHING;
	}
	
	
}
