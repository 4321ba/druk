package drukmakor;

import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//+7 óra prototípus nekem :))

public class Main {
	static Desert d = new Desert();
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

	static WaterController wc = new WaterController();
	public static void main(String[] args) throws IOException {
		
        Timer timer2 = new Timer(500, (e) -> wc.tick());
        timer2.start();
        
        d.drl.add(PointCounter.thePC);
        
		Source s1 = new Source(new Coords(900, 400));
		d.drl.add(s1);
		wc.vertices.add(s1);
		Source s2 = new Source(new Coords(1100, 600));
		d.drl.add(s2);
		wc.vertices.add(s2);
		Source s3 = new Source(new Coords(730, 500));
		d.drl.add(s3);
		wc.vertices.add(s3);
		Cistern c1 = new Cistern(new Coords(150, 450));
		d.drl.add(c1);
		wc.vertices.add(c1);
		Cistern c2 = new Cistern(new Coords(260, 450));
		d.drl.add(c2);
		wc.vertices.add(c2);
		Cistern c3 = new Cistern(new Coords(170, 340));
		d.drl.add(c3);
		wc.vertices.add(c3);
		
		Pump p1 = new Pump(new Coords(500, 100));
		d.drl.add(p1);
		wc.vertices.add(p1);
		Pump p2 = new Pump(new Coords(600, 300));
		d.drl.add(p2);
		wc.vertices.add(p2);
		Pump p3 = new Pump(new Coords(550, 600));
		d.drl.add(p3);
		wc.vertices.add(p3);
		Pump p4 = new Pump(new Coords(720, 650));
		d.drl.add(p4);
		wc.vertices.add(p4);
		
		Pipe pi1 = new Pipe(s1, p1);
		d.drl.add(pi1);
		Pipe pi2 = new Pipe(s2, p2);
		d.drl.add(pi2);
		Pipe pi3 = new Pipe(s3, p3);
		d.drl.add(pi3);
		Pipe pi4 = new Pipe(p1, p2);
		d.drl.add(pi4);
		Pipe pi5 = new Pipe(p1, c1);
		d.drl.add(pi5);
		Pipe pi6 = new Pipe(p3, c2);
		d.drl.add(pi6);
		Pipe pi7 = new Pipe(p4, c3);
		d.drl.add(pi7);
		Pipe pi8 = new Pipe(p2, p4);
		d.drl.add(pi8);
		Pipe pi9 = new Pipe(p3, p1);
		d.drl.add(pi9);
		Pipe pi10 = new Pipe(p4, s2);
		d.drl.add(pi10);
		
		p1.alter(pi1, pi5);
		//p2.alter(pi2, pi4);
		p3.alter(pi3, pi6);
		p4.alter(pi10, pi7);
		
		
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
	}
	
	static void registernewpump(Pump p) {
		d.drl.add(p);
		wc.vertices.add(p);
	}
	static void registernewpipe(Pipe p) {
		d.drl.add(p);
	}
	
	
	
	
	static void playergoto(Element e) {
		if (isalteringpump) {
			if (alteringelements[0] == null)
				alteringelements[0] = e;
			else if (alteringelements[1] == null)
				alteringelements[1] = e;
			else if (alteringelements[2] == null) {
				alteringelements[2] = e;
				if (alteringelements[0] instanceof Pump && alteringelements[1] instanceof Pipe && alteringelements[2] instanceof Pipe &&
						players[currPlIdx].alterPump((Pump)alteringelements[0], (Pipe)alteringelements[1], (Pipe)alteringelements[2])) {
					incr();
					alterpump();
				}
			}
			return;
		}
		if (isdisconnectingpipe) {
			if (e instanceof Pipe && players[currPlIdx] instanceof Mechanic && ((Mechanic)players[currPlIdx]).disconnectPipe((Pipe)e)) {
				incr();
				disconnectpipe();
			}
			return;
		}
		
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
	
	static boolean isalteringpump = false;
	static Element[] alteringelements = new Element[3];
	static void alterpump() {
		isdisconnectingpipe = false;
		isalteringpump = !isalteringpump;
		if (!isalteringpump)
			for (int i = 0; i < 3; ++i)
				alteringelements[i]=null;
	}
	
	static void connectpipe() {
		if (players[currPlIdx] instanceof Mechanic && ((Mechanic)players[currPlIdx]).connectPipe())
			incr();
	}
	static boolean isdisconnectingpipe = false;
	static void disconnectpipe() {
		isalteringpump = false;
		isdisconnectingpipe = !isdisconnectingpipe;
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
		if (players[currPlIdx] instanceof Mechanic && ((Mechanic)players[currPlIdx]).pickUpDanglingPipe())
			incr();
	}

}
