package drukmakor;

import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//TODO mozgás, teszt, filebol koord, nogui/stdingui, characterbe függvények, mindennek jó láthatóság, ..., clear



public class Grafikus {
	
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("Drukmakor");
        frame.setSize(1280, 720);
        frame.setMinimumSize(new Dimension(320, 240));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.add(Desert.get());
        frame.setVisible(true);
        Desert.get().grabFocus(); // setvisible után kell, különben nem érzi
        
        KeyboardInput ki = new KeyboardInput();
        Desert.get().addKeyListener(ki);
        
        Timer timer = new Timer(17, (e) -> pane.repaint());
        timer.start();
        

	}
	private static List<Character> players = new LinkedList<>();
	public static void addPlayer(Character c) {
		if (players.isEmpty())
			c.getView().setSoros(true);
		players.add(c);
	}
	public static void clearPlayers() {
		players.clear();
		currPlIdx = 0;//TODO ha ekkor van billentyűlenyomás??
	}
	static int currPlIdx = 0;
	static void incr() {
		players.get(currPlIdx).getView().setSoros(false);
		++currPlIdx;
		if (currPlIdx>=players.size())
			currPlIdx = 0;
		players.get(currPlIdx).getView().setSoros(true);
	}

	public static void main(String[] args) throws IOException {
		
        Timer timer2 = new Timer(500, (e) -> Proto.tick());
        timer2.start();
        Desert.get().clearDrawable();
        SwingUtilities.invokeLater(() -> Grafikus.createAndShowGUI());
        

		Proto.interpret(System.in);
	}


	static void playergoto(Element e) {
		if (players.get(currPlIdx).moveTo(e))
			incr();
	}
	static void skip() {
		incr();
	}
	static void saboteurbreakpipe() {
		if (players.get(currPlIdx) instanceof Saboteur && ((Saboteur)players.get(currPlIdx)).piercePipe())
			incr();
	}
	static void mechanicfix() {
		if (players.get(currPlIdx) instanceof Mechanic && ((Mechanic)players.get(currPlIdx)).fix())
			incr();
	}
	
	public enum dst {
		NOTHING,
	    ALTERINGPUMP,
	    DCPIPE, 
	    CONNECTPIPE,
	    PICKUPDANGLING,
	}
	static dst doingSomething = dst.NOTHING;
	static int prevalter = -1;
	static void alterpump() {
		doingSomething = dst.ALTERINGPUMP;
	}
	static void numberinput(int n) {
		switch (doingSomething) {
			case ALTERINGPUMP:
				if (prevalter == -1)
					prevalter = n;
				else {
					if (players.get(currPlIdx).alterPump(prevalter, n))
						incr();
					prevalter = -1;
					doingSomething = dst.NOTHING;
				}
				break;
			case DCPIPE:
				if (players.get(currPlIdx) instanceof Mechanic && ((Mechanic)players.get(currPlIdx)).disconnectPipe(n))
					incr();
				doingSomething = dst.NOTHING;
				break;
			case CONNECTPIPE:
				if (players.get(currPlIdx) instanceof Mechanic && ((Mechanic)players.get(currPlIdx)).connectPipe(n))
					incr();
				doingSomething = dst.NOTHING;
				break;
			case PICKUPDANGLING:
				if (players.get(currPlIdx) instanceof Mechanic && ((Mechanic)players.get(currPlIdx)).pickUpDanglingPipe(n))
					incr();
				doingSomething = dst.NOTHING;
				break;
		case NOTHING:
			break;
		default:
			break;
		}
	}
	
	static void connectpipe() {
		doingSomething = dst.CONNECTPIPE;
	}
	static void disconnectpipe() {
		doingSomething = dst.DCPIPE;
	}
	static void pickuppump() {
		if (players.get(currPlIdx) instanceof Mechanic && ((Mechanic)players.get(currPlIdx)).pickUpPump())
			incr();
	}
	static void placepump() {
		if (players.get(currPlIdx) instanceof Mechanic && ((Mechanic)players.get(currPlIdx)).placePump())
			incr();
	}
	static void danglingpipe() {
		doingSomething = dst.PICKUPDANGLING;
	}

}