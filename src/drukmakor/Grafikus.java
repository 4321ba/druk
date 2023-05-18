package drukmakor;

import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;

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
        pane.add(d);
        frame.setVisible(true);
        d.grabFocus();//setvisible alatt kell különben nem érzi!!!!
        
        KeyboardInput ki = new KeyboardInput();
        d.addKeyListener(ki);
        
        Timer timer = new Timer(17, (e) -> pane.repaint());
        timer.start();
        

	}
	static Character[] players = new Character[4];
	static int currPlIdx = 0;
	static void incr() {
		players[currPlIdx].soros = false;
		++currPlIdx;
		if (currPlIdx>=4)
			currPlIdx-=4;
		players[currPlIdx].soros = true;
	}

	static WaterController wc = WaterController.get();
	public static void main(String[] args) throws IOException {
		
        Timer timer2 = new Timer(500, (e) -> wc.tick());
        timer2.start();
        
        d.drl.add(PointCounter.get());
        
		Source s1 = new Source(new Coords(900, 400));
		Source s2 = new Source(new Coords(1100, 600));
		Source s3 = new Source(new Coords(730, 500));
		Cistern c1 = new Cistern(new Coords(150, 450));
		Cistern c2 = new Cistern(new Coords(260, 450));
		Cistern c3 = new Cistern(new Coords(170, 340));
		
		Pump p1 = new Pump(new Coords(500, 100));
		Pump p2 = new Pump(new Coords(600, 300));
		Pump p3 = new Pump(new Coords(550, 600));
		Pump p4 = new Pump(new Coords(720, 650));
		
		Pipe pi1 = new Pipe(s1, p1);
		Pipe pi2 = new Pipe(s2, p2);
		Pipe pi3 = new Pipe(s3, p3);
		Pipe pi4 = new Pipe(p1, p2);
		Pipe pi5 = new Pipe(p1, c1);
		Pipe pi6 = new Pipe(p3, c2);
		Pipe pi7 = new Pipe(p4, c3);
		Pipe pi8 = new Pipe(p2, p4);
		Pipe pi9 = new Pipe(p3, p1);
		Pipe pi10 = new Pipe(p4, s2);
		
		p1.alterPump(0, 2);
		//p2.alter(pi2, pi4);
		p3.alterPump(0, 1);
		p4.alterPump(2, 0);
		
		
		Mechanic m1 = new Mechanic(p1);
		players[0] = m1;
		d.drl.add(m1);
		m1.soros = true;
		Mechanic m2 = new Mechanic(c1);
		players[1] = m2;
		d.drl.add(m2);
		Saboteur sz1 = new Saboteur(p2);
		players[2] = sz1;
		d.drl.add(sz1);
		Saboteur sz2 = new Saboteur(s2);
		players[3] = sz2;
		d.drl.add(sz2);
		
        SwingUtilities.invokeLater(() -> Main.createAndShowGUI());
        

		//Proto.interpret(System.in);
	}

	
	
	static void playergoto(Element e) {
		if (players[currPlIdx].moveTo(e))
			incr();
	}
	static void skip() {
		incr();
	}
	static void saboteurbreakpipe() {
		if (players[currPlIdx] instanceof Saboteur && ((Saboteur)players[currPlIdx]).piercePipe())
			incr();
	}
	static void mechanicfix() {
		if (players[currPlIdx] instanceof Mechanic && ((Mechanic)players[currPlIdx]).fix())
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
					if (players[currPlIdx].alterPump(prevalter, n))
						incr();
					prevalter = -1;
					doingSomething = dst.NOTHING;
				}
				break;
			case DCPIPE:
				if (players[currPlIdx] instanceof Mechanic && ((Mechanic)players[currPlIdx]).disconnectPipe(n))
					incr();
				doingSomething = dst.NOTHING;
				break;
			case CONNECTPIPE:
				if (players[currPlIdx] instanceof Mechanic && ((Mechanic)players[currPlIdx]).connectPipe(n))
					incr();
				doingSomething = dst.NOTHING;
				break;
			case PICKUPDANGLING:
				if (players[currPlIdx] instanceof Mechanic && ((Mechanic)players[currPlIdx]).pickUpDanglingPipe(n))
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
		if (players[currPlIdx] instanceof Mechanic && ((Mechanic)players[currPlIdx]).pickUpPump())
			incr();
	}
	static void placepump() {
		if (players[currPlIdx] instanceof Mechanic && ((Mechanic)players[currPlIdx]).placePump())
			incr();
	}
	static void danglingpipe() {
		doingSomething = dst.PICKUPDANGLING;
	}

}
