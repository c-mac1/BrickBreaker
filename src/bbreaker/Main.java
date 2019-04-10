package bbreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		//create a jframe (outer window)
		JFrame obj = new JFrame();
		Gameplay gameplay = new Gameplay();
		
		//properties
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Breakout Ball");
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
		obj.setVisible(true);

	}

}
