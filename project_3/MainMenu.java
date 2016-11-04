/**
 * This class provides a GUI menu for playing Toads and Frogs, Chomp, Snort, and Elephants and Rhinos. 
 * @author Ethan McQueen, Kei Ng
 * @version 1.24
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu {

	public static void main (String[] args){
		final JFrame frame = new JFrame("Main Menu");
		frame.setSize(500,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);							// Center main menu window on the screen

		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2,20,20));
		frame.getContentPane().add(panel);
		
		JButton buttonPlayToadsAndFrogs = new JButton("Play Toads and Frogs"); 		// Button "Play Toads and Frogs"
		panel.add(buttonPlayToadsAndFrogs);
		buttonPlayToadsAndFrogs.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlayToadsAndFrogs.giveRandomGame());
				t.start();
			}
		});
		
		JButton buttonPlayToadsAndFrogsSettings = new JButton("Settings"); // Button for Play Toads and Frogs Settings
		panel.add(buttonPlayToadsAndFrogsSettings);
		buttonPlayToadsAndFrogsSettings.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlayToadsAndFrogs.setupGame());
				t.start();
			}
		});

		JButton buttonPlayChomp = new JButton("Play Chomp"); 				// Button "Play Chomp"
		panel.add(buttonPlayChomp);
		buttonPlayChomp.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlayChomp.giveRandomGame());
				t.start();
			}
		});
		
		JButton buttonPlayChompSettings = new JButton("Settings"); // Button for Play Chomp Settings
		panel.add(buttonPlayChompSettings);
		buttonPlayChompSettings.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlayChomp.setupGame());
				t.start();
			}
		});
		
		JButton buttonPlaySnort = new JButton("Play Snort"); 				// Button "Play Snort"
		panel.add(buttonPlaySnort);
		buttonPlaySnort.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlaySnort.giveRandomGame());
				t.start();
			}
		});
		
		JButton buttonPlaySnortSettings = new JButton("Settings"); // Button for Play Snort Settings
		panel.add(buttonPlaySnortSettings);
		buttonPlaySnortSettings.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlaySnort.setupGame());
				t.start();
			}
		});
		
		JButton buttonPlayElephantsAndRhinos = new JButton("Play Elephants and Rhinos"); 	// Button "Play Elephants and Rhinos"
		panel.add(buttonPlayElephantsAndRhinos);
		buttonPlayElephantsAndRhinos.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlayElephantsAndRhinos.giveRandomGame());
				t.start();
			}
		});
		
		JButton buttonPlayElephantsAndRhinosSettings = new JButton("Settings"); // Button for Play Elephants and Rhinos Settings
		panel.add(buttonPlayElephantsAndRhinosSettings);
		buttonPlayElephantsAndRhinosSettings.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlayElephantsAndRhinos.setupGame());
				t.start();
			}
		});
		
		JButton buttonPlayBlueRedChomp = new JButton("Play Blue-Red Chomp"); 	// Button "Play Blue-Red Chomp"
		panel.add(buttonPlayBlueRedChomp);
		buttonPlayBlueRedChomp.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlayBlueRedChomp.giveRandomGame());
				t.start();
			}
		});
		
		JButton buttonPlayBlueRedChompSettings = new JButton("Settings"); // Button for Play Blue-Red Chomp Settings
		panel.add(buttonPlayBlueRedChompSettings);
		buttonPlayBlueRedChompSettings.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(PlayBlueRedChomp.setupGame());
				t.start();
			}
		});
		
		frame.setVisible(true);
	}
}