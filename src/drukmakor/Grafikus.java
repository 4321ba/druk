package drukmakor;

import java.awt.Container;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//TODO mindennek jó láthatóság, kommentek



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
		currPlIdx = 0;
	}
	public static boolean areTherePlayers() {
		return !players.isEmpty();
	}
	public static Character getSoros() {
		return players.get(currPlIdx);
	}
	static int currPlIdx = 0;
	static void incr() {
		if (!areTherePlayers())
			return;
		players.get(currPlIdx).getView().setSoros(false);
		++currPlIdx;
		if (currPlIdx>=players.size())
			currPlIdx = 0;
		players.get(currPlIdx).getView().setSoros(true);
	}

	public static void main(String[] args) {
		boolean showgui = true;
		if (args.length >= 1 && args[0].equals("nogui"))
			showgui = false;
		boolean getFromStdin = !showgui;
		if (args.length >= 1 && args[0].equals("stdingui"))
			getFromStdin = true;
		if (showgui) {
			Timer timer2 = new Timer(500, (e) -> Proto.tick());
			timer2.start();
			Timer timer3 = new Timer(5000, (e) -> incr());
			timer3.start();
			Desert.get().clearDrawable();
			SwingUtilities.invokeLater(() -> Grafikus.createAndShowGUI());
		}
        
		if (getFromStdin)
			Proto.interpret(System.in);
		else {
			try {
				Proto.interpret(new FileInputStream("palya.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}