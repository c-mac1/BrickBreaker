package bbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// properties
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;

	private int playerX = 310;
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballXDirection = -1;
	private int ballYDirection = -2;

	private MapGen map;

	/**
	 * 
	 */
	public Gameplay() {
		map = new MapGen(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g) {
		// draw things
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);

		// map
		map.draw((Graphics2D) g);

		// borders
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);

		// paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		// ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);

		//scores 
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		if (totalBricks<=0) {
			play = false;
			ballXDirection = 0;
			ballYDirection = 0;
			
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD, 35));
			g.drawString("YOU HAVE WON! SCORES: "+score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("PRESS ENTER TO RESTART", 230, 350);
			
		}
		
		
		if (ballPosY > 570) {
			play = false;
			ballXDirection = 0;
			ballYDirection = 0;
			
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD, 35));
			g.drawString("GAME OVER, SCORES: "+score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("PRESS ENTER TO RESTART", 230, 350);
			
		}
		
		g.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		if (play) {
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYDirection = -ballYDirection;
			}

			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map.length; j++) {
					if (map.map[i][j] > 0) {
						int brickx = j*map.brickWidth+80;
						int bricky = i*map.brickHeight+50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight; 
						
						Rectangle rect = new Rectangle(brickx, bricky, brickHeight, brickWidth);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=5;
							
							if (ballPosX +19 <= brickRect.x || ballPosX +1 >= brickRect.x + brickRect.width) {
								ballXDirection=-ballXDirection;
							} else {
								ballYDirection =- ballYDirection;
							}
							break A;
						}
					}
				}
			}

			ballPosX += ballXDirection;
			ballPosY += ballYDirection;
			if (ballPosX < 0) {
				ballXDirection = -ballXDirection;
			}

			if (ballPosY < 0) {
				ballYDirection = -ballYDirection;
			}

			if (ballPosX > 670) {
				ballXDirection = -ballXDirection;
			}
		}

		repaint();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXDirection = -1;
				ballYDirection = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGen(3, 7);
				
				
				repaint();
			}
		}
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
