/**
 * This class provides a GUI menu for playing Toads and Frogs, and Chomp. 
 * 
 * @author Ethan McQueen, Kei Ng
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu {

	public static void main (String[] args){
		final JFrame frame = new JFrame("Main Menu");
		frame.setSize(250,150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);							// Center main menu window on the screen

		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		frame.getContentPane().add(panel);

		JButton button1 = new JButton("Play Toads and Frogs"); 		// Button "Play Toads and Frogs"
		panel.add(button1);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(new PlayToadsAndFrogs());
				t.start();
			}
		});
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton button2 = new JButton("Play Chomp"); 				// Button "Play Chomp"
		panel.add(button2);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(new PlayChomp());
				t.start();
			}
		});
		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton button3 = new JButton("Play Snort"); 				// Button "Play Snort"
		panel.add(button3);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {     
				frame.dispose();
				Thread t = new Thread(new PlaySnort());
				t.start();
			}
		});
		button3.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		frame.setVisible(true);
	}
}