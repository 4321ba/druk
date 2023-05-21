package drukmakor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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

public class TitleFrame {
	private static JFrame window;
	public static boolean visible=true;
	private static JButton newGame, loadGame, charSelectLeft, charSelectRight;
	private static JLabel mechN,sabN;
	private static int mech=0, sab=0;
	/**
	 * JPanel-ök és JLabel-ek, amiket váltogatva jelenítődik meg az aktuális állapot
	 */
	public static JPanel bgPanel[]=new JPanel[2];
	public JLabel bgLabel[]=new JLabel[2];
	static JPanel jp;
	/**
	 * A képernyő mérete
	 */
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int)screenSize.getWidth();
	private int height = (int)screenSize.getHeight();
	
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
		
		newGame=new JButton("Start Game");
		newGame.setFont(new Font("Consolas",Font.PLAIN,30));
		newGame.setForeground(Color.white);
		newGame.setBorder(BorderFactory.createBevelBorder(0));
		newGame.setSize(200,50);
		newGame.setContentAreaFilled(false);
		newGame.setLocation(100, 200);
		newGame.addActionListener(new NewGameActionListener());
		
		loadGame=new JButton("Add Player");
		loadGame.setFont(new Font("Consolas",Font.PLAIN,30));
		loadGame.setForeground(Color.white);
		loadGame.setBorder(BorderFactory.createBevelBorder(0));
		loadGame.setContentAreaFilled(false);
		loadGame.setSize(200,50);
		loadGame.setBackground(Color.gray);
		loadGame.setLocation(100, 300);
		loadGame.addActionListener(new AddPActionListener());
		
		charSelectLeft=createButton(570,"Saboteur");
		charSelectRight=createButton(930,"Mechanic");
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
		createObject(0,"src/images/bloodysabgirl.jpg",500);
		//bgPanel[0].setVisible(true);
		createObject(1,"src/images/mechgirl.png",870);
		jp.add(mechN);
		jp.add(sabN);
		window.add(jp);
		window.setTitle("Drukmakor");
		//pack();
		//window.setSize(800,538);
		window.setSize(1280,720);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	private JButton createButton(int posx, String text) {
		JButton charSelect=new JButton(text);
		charSelect.setFont(new Font("Consolas",Font.PLAIN,30));
		charSelect.setForeground(Color.white);
		charSelect.setSize(200,50);
		charSelect.setLocation(posx,600);
		charSelect.setContentAreaFilled(false);
		charSelect.setOpaque(false);
		charSelect.setBorder(BorderFactory.createBevelBorder(0));
		return charSelect;
	}
	public static void updateFrame() {
		mechN.setText("Mechanics: "+mech+" players");
		sabN.setText("Saboteurs: "+sab+" players");
	}
	
	private static JLabel newLabel(int posx,int posy,String text) {
		JLabel loadGameN=new JLabel(text);
		loadGameN.setFont(new Font("Consolas",Font.PLAIN,20));
		loadGameN.setForeground(Color.white);
		loadGameN.setSize(300,100);
		loadGameN.setLocation(posx, posy);
		return loadGameN;
	}
	public boolean GetVisible() {
		return visible;
	}
	public static void SetVisible(boolean b) {
		visible=b;
		window.setVisible(b);
	}

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
			bgPanel[0].setVisible(true);
			bgPanel[1].setVisible(true);
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
			bgPanel[0].setVisible(false);
			bgPanel[1].setVisible(false);
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
			bgPanel[0].setVisible(false);
			bgPanel[1].setVisible(false);
		}
	}
	
	private static class NewGameActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			SetVisible(false);
		}
	}
}
