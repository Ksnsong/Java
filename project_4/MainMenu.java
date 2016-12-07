/**
 * This class provides a GUI menu for playing Toads and Frogs, Chomp, Snort, Elephants and Rhinos, and Blue-Red Chomp. 
 * @author Ethan McQueen, Kei Ng
 * @version 4.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu {
	
	/** 
     *  Main method for testing. 
     */
	public static void main (String[] args){
		new MainMenu().chooseGame();
	}
	
	/** 
     *  Creates a menu for choosing a game to play. 
     */
	private void chooseGame() {
		final JFrame frame = new JFrame("Main Menu");
		frame.setSize(500,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);							// Center main menu window on the screen

		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2,20,20));
		frame.getContentPane().add(panel);
		
		makeButton(0, frame, panel, "Toads and Frogs", "PlayToadsAndFrogs");
		makeButton(1, frame, panel, "Settings", "PlayToadsAndFrogs");
		makeButton(0, frame, panel, "Chomp", "PlayChomp");
		makeButton(1, frame, panel, "Settings", "PlayChomp");
		makeButton(0, frame, panel, "Snort", "PlaySnort");
		makeButton(1, frame, panel, "Settings", "PlaySnort");
		makeButton(0, frame, panel, "Elephants and Rhinos", "PlayElephantsAndRhinos");
		makeButton(1, frame, panel, "Settings", "PlayElephantsAndRhinos");
		makeButton(0, frame, panel, "Blue-Red Chomp", "PlayBlueRedChomp");
		makeButton(1, frame, panel, "Settings", "PlayBlueRedChomp");
		makeButton(2, frame, panel, "Play Combination Game", "");
		
		frame.setVisible(true);
	}
	
	/** 
     *  Make a button for the main menu GUI. 
     */
	private void makeButton(final int type, final JFrame frame, JPanel panel, String buttonText, final String gameType) {
		JButton button = new JButton(buttonText);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				frame.dispose();
				Thread t;
				if (type == 0) {
					t = new Thread(gameFactory(gameType).giveRandomGame(0,0,null));
				} else if (type == 1) {
					t = new Thread(gameFactory(gameType).setupGame());
				} else {
					t = new Thread(new PlayCombinationGameMenu());
				}
				t.start();
			}
		});
	}
	
	/** 
     *  Provide a new PlayGame object of a specified type. 
     */
	protected static PlayGame gameFactory(String gameType) {
		if (gameType.equals("PlayToadsAndFrogs"))
			return new PlayToadsAndFrogs();
		else if (gameType.equals("PlayChomp"))
			return new PlayChomp();
		else if (gameType.equals("PlaySnort"))
			return new PlaySnort();
		else if (gameType.equals("PlayElephantsAndRhinos"))
			return new PlayElephantsAndRhinos();
		else if (gameType.equals("PlayBlueRedChomp"))
			return new PlayBlueRedChomp();
		else
			return new PlayToadsAndFrogs();
	}
}