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

//TODO mindennek jó láthatóság!!!, kommentek (ami még hátravan: desert
// + tesztek!? a pull/push miatt, cloc


/**
 * itt találhatóak a grafikus rendszer felállításáért, és összekötéséért felelős függvények
 */
public class Grafikus {
	
	/**
	 * eltárolja a rendszerben levő játékosokat, abból a célból, hogy
	 * tudja jelezni, hogy ki van éppen soron
	 */
	private static List<Character> players = new LinkedList<>();
	/**
	 * hozzáadja c-t a számon tartott játékosok listájába
	 */
	public static void addPlayer(Character c) {
		if (players.isEmpty())
			c.getView().setSoros(true);
		players.add(c);
	}
	/**
	 * eltünteti az összes játékost a számon tartott listából
	 */
	public static void clearPlayers() {
		players.clear();
		currPlIdx = 0;
	}
	/**
	 * visszaadja, hogy van-e játékos a rendszerben
	 * getSoros hívás előtt szükséges ellenőrizni, vagy esetleg
	 * az indexelési hibát elkapni
	 */
	public static boolean areTherePlayers() {
		return !players.isEmpty();
	}
	/**
	 * visszaadja az épp soron levő játékost, akinek a köre van
	 * / akit éppen azt szeretnénk, hogy a billentyűzet irányítson
	 */
	public static Character getSoros() {
		return players.get(currPlIdx);
	}
	/**
	 * az épp soron levő játékos indexe a tömbben
	 */
	private static int currPlIdx = 0;
	/*
	 * továbblép a következő játékosra. Ha a játékosok végére ért, akkor kezdi előlről
	 */
	private static void gotoNextPlayer() {
		if (!areTherePlayers())
			return;
		getSoros().getView().setSoros(false);
		++currPlIdx;
		if (currPlIdx >= players.size())
			currPlIdx = 0;
		getSoros().getView().setSoros(true);
	}

	/**
	 * az alkalmazás belépési pontja
	 * feldolgozza a parancssori argumentumokat, majd azoknak megfelelően
	 * elindítja a gui-t, illetve a parancsértelmezőt; vagy nem
	 */
	public static void main(String[] args) {
		boolean showgui = true;
		if (args.length >= 1 && args[0].equals("nogui"))
			showgui = false;
		
		boolean getFromStdin = !showgui;
		if (args.length >= 1 && args[0].equals("stdingui"))
			getFromStdin = true;
		
		if (showgui) {
			Desert.get().clearDrawable();
			SwingUtilities.invokeLater(() -> Grafikus.createAndShowGUI());
			if (!getFromStdin) {
				// megpróbáljuk, hogy legalább itt ne legyen thread-safety probléma
				try {
					FileInputStream fis = new FileInputStream("palya.txt");
					SwingUtilities.invokeLater(() -> Proto.interpret(fis));
					// bezárást az interpret elvégzi
				} catch (FileNotFoundException e) {
					System.out.println("Failed to open palya.txt in cwd: " + e.getMessage());
				}
			}
		}
        
		if (getFromStdin)
			Proto.interpret(System.in);
	}
	/**
	 * létrehozza a guit, és beköti a dolgokat
	 * elindítja az időzítőket
	 * a gui szálról illik meghívni
	 */
	private static void createAndShowGUI() {
		
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
        
        new Timer(17, (e) -> pane.repaint()).start();
		new Timer(500, (e) -> Proto.tick()).start();
		new Timer(5000, (e) -> gotoNextPlayer()).start();
	}

}