package drukmakor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Főmenüt kezelő osztály
 *
 */
public class TitleFrame {
	public static JFrame window;
	public static boolean visible=true;
	/**
	 * Gombok
	 */
	private static JButton newGame, loadGame, charSelectLeft, charSelectRight, toggleButton;
	/**
	 * Feliratok a játékosok számlálásához
	 */
	private static JLabel mechN,sabN;
	private static int mech=0, sab=0, activeNo=1;
	/**
	 * JPanel-ök és JLabel-ek, amiket váltogatva jelenítődik meg az aktuális állapot
	 */
	public static JPanel bgPanel[]=new JPanel[3];
	public JLabel bgLabel[]=new JLabel[3];
	static JPanel jp;
	
	/**Ablak inicializálása
	 * @throws IOException
	 */
	public TitleFrame() throws IOException{
		window=new JFrame();
		jp=new JPanel();
		jp.setBackground(new Color(0,0,0));
		jp.setLayout(null);
		JLabel title=new JLabel("Drukmákor 1.0");
		title.setFont(new Font("Consolas",Font.PLAIN,50));
		title.setForeground(Color.white);
		title.setSize(700,100);
		title.setLocation(50,40);
		
		newGame=createButton(100,200,"Start Game");
		newGame.addActionListener(new NewGameActionListener());
		
		loadGame=createButton(100,300,"Add Player");
		loadGame.addActionListener(new AddPActionListener());
		
		toggleButton = createButton(930,20,"Toggle");
		toggleButton.addActionListener(new ToggleButtActionListener());
		toggleButton.setVisible(false);
		
		charSelectLeft=createButton(570,600,"Saboteur");
		charSelectRight=createButton(930,600,"Mechanic");
		charSelectLeft.setVisible(false);
		charSelectRight.setVisible(false);
		charSelectLeft.addActionListener(new SabButtActionListener());
		charSelectRight.addActionListener(new MechButtActionListener());
		
		mechN=newLabel(100,415,"Mechanics: "+mech+" players");
		sabN=newLabel(100,465,"Saboteurs: "+sab+" players");
		
		jp.add(title);
		jp.add(newGame);
		jp.add(loadGame);
		jp.add(charSelectLeft);
		jp.add(charSelectRight);
		jp.add(toggleButton);
		createObject(0,"images/bloodysabgirl.jpg",500);
		createObject(1,"images/mechgirl.png",870);
		createObject(2,"images/chongyue.jpg",870);
		jp.add(mechN);
		jp.add(sabN);
		window.add(jp);
		window.setTitle("Drukmakor");
		window.setSize(1280,720);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	/**Gomb gyártó függvény
	 * @param posx x pozíció
	 * @param posy y pozíció
	 * @param text felirat
	 * @return gomb
	 */
	private JButton createButton(int posx, int posy, String text) {
		JButton charSelect=new JButton(text);
		charSelect.setFont(new Font("Consolas",Font.PLAIN,30));
		charSelect.setForeground(Color.white);
		charSelect.setSize(200,50);
		charSelect.setLocation(posx,posy);
		charSelect.setContentAreaFilled(false);
		charSelect.setOpaque(false);
		charSelect.setBorder(BorderFactory.createBevelBorder(0));
		return charSelect;
	}
	/**
	 * Feliratokat frissítő függvény 
	 */
	public static void updateFrame() {
		mechN.setText("Mechanics: "+mech+" players");
		sabN.setText("Saboteurs: "+sab+" players");
	}
	
	/** Felirat gyártó függvény
	 * @param posx x pozíció
	 * @param posy y pozíció
	 * @param text felirat
	 * @return felirat
	 */
	private static JLabel newLabel(int posx,int posy,String text) {
		JLabel loadGameN=new JLabel(text);
		loadGameN.setFont(new Font("Consolas",Font.PLAIN,20));
		loadGameN.setForeground(Color.white);
		loadGameN.setSize(300,100);
		loadGameN.setLocation(posx, posy);
		return loadGameN;
	}
	/**
	 * @return látszik-e a főmenü
	 */
	public boolean GetVisible() {
		return visible;
	}
	/**
	 * @param b főmenü láthatósága
	 */
	public static void SetVisible(boolean b) {
		visible=b;
		window.setVisible(b);
	}

	/** Kép hozzáadása
	 * @param bgNum helye a tároló tömbben
	 * @param objFileName fájlnév
	 * @param x koordináta
	 * @throws IOException
	 */
	public void createObject(int bgNum, String objFileName, int x) throws IOException {
		bgPanel[bgNum]=new JPanel();
		bgPanel[bgNum].setBounds(x,100,328,472);
		bgPanel[bgNum].setBackground(Color.blue);
		bgPanel[bgNum].setOpaque(false);
		bgPanel[bgNum].setLayout(null);
		
		window.add(bgPanel[bgNum]);
		
		bgLabel[bgNum]=new JLabel();
		bgLabel[bgNum].setBounds(0,0,328,472);
		ImageIcon objectIcon= new ImageIcon(ImageIO.read(new File(objFileName)));
		bgLabel[bgNum].setIcon(objectIcon);
		bgPanel[bgNum].add(bgLabel[bgNum]);
		
		bgPanel[bgNum].setVisible(false);
	}
	
	private static class AddPActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			newGame.setEnabled(false);
			loadGame.setEnabled(false);
			charSelectLeft.setVisible(true);
			charSelectRight.setVisible(true);
			toggleButton.setVisible(true);
			bgPanel[0].setVisible(true);
			bgPanel[activeNo].setVisible(true);
			//bgPanel[2].setVisible(true);
		}
	}
	
	private static class SabButtActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			newGame.setEnabled(true);
			loadGame.setEnabled(true);
			sab++;
			updateFrame();
			charSelectLeft.setVisible(false);
			charSelectRight.setVisible(false);
			toggleButton.setVisible(false);
			bgPanel[0].setVisible(false);
			bgPanel[1].setVisible(false);
			bgPanel[2].setVisible(false);
		}
	}
	
	private static class MechButtActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			newGame.setEnabled(true);
			loadGame.setEnabled(true);
			mech++;
			updateFrame();
			charSelectLeft.setVisible(false);
			charSelectRight.setVisible(false);
			toggleButton.setVisible(false);
			bgPanel[0].setVisible(false);
			bgPanel[1].setVisible(false);
			bgPanel[2].setVisible(false);
		}
	}
	
	private static class NewGameActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			SetVisible(false);
		}
	}
	private static class ToggleButtActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			bgPanel[activeNo].setVisible(false);
			activeNo++;
			activeNo=2-activeNo%2;
			bgPanel[activeNo].setVisible(true);
		}
	}
}
