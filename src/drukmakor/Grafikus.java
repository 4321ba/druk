package drukmakor;

import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Grafikus {
	private static Desert desert = new Desert();
	public static Desert getDesert() { return desert; }
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("Drukmakor");
        frame.setSize(1280, 720);
        frame.setMinimumSize(new Dimension(320, 240));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        //pane.setLayout(new GridBagLayout());
        pane.add(desert);
        frame.setVisible(true);
        desert.grabFocus();//setvisible alatt kell különben nem érzi!!!!
        
        KeyboardInput ki = new KeyboardInput();
        desert.addKeyListener(ki);
        
        Timer timer = new Timer(17, (e) -> pane.repaint());
        timer.start();
        

	}
	private static List<Character> players = new LinkedList<>();
	public static void addPlayer(Character c) {
		players.add(c);
	}
	static int currPlIdx = 0;//TODO minden ilyen private
	static void incr() {
		players.get(currPlIdx).getView().setSoros(false);
		++currPlIdx;
		if (currPlIdx>=players.size())
			currPlIdx-=players.size();
		players.get(currPlIdx).getView().setSoros(true);
	}

	public static void main(String[] args) throws IOException {
		
        Timer timer2 = new Timer(500, (e) -> Proto.tick());
        timer2.start();
        
        desert.addDrawable(PointCounter.get());
        
		Source s1 = Proto.newSource(new Coords(900, 400));
		Source s2 = Proto.newSource(new Coords(1100, 600));
		Source s3 = Proto.newSource(new Coords(730, 500));
		Cistern c1 = Proto.newCistern(new Coords(150, 450));
		Cistern c2 = Proto.newCistern(new Coords(260, 450));
		Cistern c3 = Proto.newCistern(new Coords(170, 340));
		
		Pump p1 = Proto.newPump(new Coords(500, 100));
		Pump p2 = Proto.newPump(new Coords(600, 300));
		Pump p3 = Proto.newPump(new Coords(550, 600));
		Pump p4 = Proto.newPump(new Coords(720, 650));
		
		Pipe pi1 = Proto.newPipe(s1, p1);
		Pipe pi2 = Proto.newPipe(s2, p2);
		Pipe pi3 = Proto.newPipe(s3, p3);
		Pipe pi4 = Proto.newPipe(p1, p2);
		Pipe pi5 = Proto.newPipe(p1, c1);
		Pipe pi6 = Proto.newPipe(p3, c2);
		Pipe pi7 = Proto.newPipe(p4, c3);
		Pipe pi8 = Proto.newPipe(p2, p4);
		Pipe pi9 = Proto.newPipe(p3, p1);
		Pipe pi10 = Proto.newPipe(p4, s2);
		
		p1.alterPump(0, 2);
		//p2.alter(pi2, pi4);
		p3.alterPump(0, 1);
		p4.alterPump(2, 0);
		
		
		Mechanic m1 = Proto.newMechanic(p1);
		m1.getView().setSoros(true);
		Mechanic m2 = Proto.newMechanic(c1);
		Saboteur sz1 = Proto.newSaboteur(p2);
		Saboteur sz2 = Proto.newSaboteur(s2);
		
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