
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class EscapePlay extends JPanel implements KeyListener, ActionListener{
	private Timer timer;
	private int delay = 8;
	private ObstacleGenerator generator;
	private boolean playing = false;
	//private double xDir = 0;
	private long counter = 0;
	private ObsQueue<Obs> queue;
	private Graphics g;
	private int ovalPosition = 350;
	private int moveRate;
	private int score;
	private int ballHeight = 20;
	private int ballWidth = 20;
	private int ballX = 500;
	private boolean playedOnce;
	private int lastScore;
	private boolean restartState;
	private boolean preGame = true;
	private int highscore;
	private boolean lastHigh;
	private int generateRate = 50;
	private BufferedImage img;
	private BufferedImage img2;
//	private boolean showMessageD;
//	private boolean showMessageE;
//	private int deltaD;
//    private int deltaE;
//	private boolean showD;
	
	{
		try(BufferedReader r = new BufferedReader(new FileReader("C://Users//Asus//Desktop//folders//2. Programming related//EscapeHigh//highscore.txt"))){
			String s = r.readLine();
			if(s == null){
				highscore = 0;
			}else{
				highscore = Integer.parseInt(s);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public EscapePlay(){
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		generator = new ObstacleGenerator();
		queue = new ObsQueue<>();
		try{
			img = ImageIO.read(getClass().getResourceAsStream("/iconSmall.png"));
			img2 = ImageIO.read(getClass().getResourceAsStream("/roundmed.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g){
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		if(playing){
			g.setColor(Color.red);
			g.fillOval(ovalPosition, ballX, ballWidth, ballHeight);
			
			g.setColor(Color.green);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Score: " + score, 10, 20);
			
			g.setColor(Color.yellow);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Highscore: " + (score >= highscore ? score : highscore), 550, 20);
			
//			if(counter % 35 == 0){
//				queue.enqueue(new Obs(0));
//				if(queue.getSize() == 20){
//					queue.dequeue();
//				}
//			}
//			if(counter%10 == 0){
//				score++;
//			}
//			
//			if(counter > 100000){
//				counter = 0;
//			}
			for(Obs obs : queue){
				obs.draw(g);
			}
	
	//		if(counter % 5000 == 0){
	//			if(counter > 100000){
	//				counter = 0;
	//				generator.generate((Graphics2D)g, xDir);
	//			}
	//		}
			counter++;
			if(ovalPosition < 1){
				ovalPosition = 1;
			}
			
			if(ovalPosition > 667){
				ovalPosition = 667;
			}
			
//			if(showD && score - deltaD < 5){
//				g.setColor(Color.red);
//				g.setFont(new Font("Serif", Font.BOLD, 40));
//				g.drawString("DEQUEUE", 400, 500);
//			}
			
			tick();
			tickOval();
		}else if(playedOnce && !preGame){
			g.setColor(Color.red);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("You lost", 279, 240);
			
			
			
			if(lastHigh){
				g.setColor(Color.blue);
				g.setFont(new Font("Serif", Font.BOLD, 60));
				g.drawString("New Highscore: " + highscore , 100, 330);
			}else{
				
				g.setColor(Color.green);
				g.setFont(new Font("Serif", Font.BOLD, 40));
				g.drawString("Score: " + lastScore, 278, 300);
				
				g.setColor(Color.yellow);
				g.setFont(new Font("Serif", Font.BOLD, 40));
				g.drawString("Highscore: " + highscore , 220, 345);
			}
			
			g.setColor(Color.white);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("Press ENTER to restart game!", 90, 400);
			
			
		}else if(preGame){
			g.setColor(Color.yellow);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("Press LEFT or RIGHT arrow to start the game!", 50, 200);
			
			g.drawImage(img, 300, 350, null);
			g.drawImage(img2, 400, 280, null);
			
			g.setColor(Color.black);
			g.setFont(new Font("Serif", Font.BOLD, 15));
			g.drawString("Vezhgoni perdorimin e QUEUE!", 410, 330);
			
			g.setColor(Color.black);
			g.setFont(new Font("Serif", Font.BOLD, 15));
			g.drawString("e QUEUE!", 445, 360);
		}
		
		g.dispose();
	}
	
	private class Obs{
		int x;
		int y;
		boolean set = false;

		Obs(int x){
			this.x = x;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
		public void draw(Graphics g){
			if(!set){
				y = generator.generate((Graphics2D)g, x);
			}else{
				generator.generateSet((Graphics2D)g, y, x);
			}
			set = true;
		}
		
		public void increaseX(){
			x+=7;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		if(playing){
			lastHigh = false;
			
			if(counter % generateRate == 0){
				queue.enqueue(new Obs(0));
//				if(queue.getSize() == 20){
//					queue.dequeue();
//				}
			}
			if(counter%10 == 0){
				score++;
			}
			
			
			if(counter > 100000){
				counter = 0;
			}
			
			if(queue.getFirst() != null && queue.getFirst().getX() >= 550){
				queue.dequeue();
			}
			
			for(Obs obs : queue){
				Rectangle ball = new Rectangle(ovalPosition, ballX, ballWidth, ballHeight);
				Rectangle obstacle = new Rectangle(obs.getY(), obs.getX(), generator.getWidth(), generator.getHeight());
				if(ball.intersects(obstacle)){
					if(!playedOnce){
						playedOnce = true;
					}
					lastScore = score;
					playing = false;
					
					if(lastScore > highscore){
						highscore = lastScore;
						lastHigh = true;
						try(FileWriter fw = new FileWriter("C://Users//Asus//Desktop//folders//2. Programming related//EscapeHigh//highscore.txt")){
							fw.write(Integer.toString(highscore));
						}catch(IOException e){
							e.printStackTrace();
						}
					}
					break;
				}
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(!playedOnce || (playedOnce && restartState)){
				playing = true;
				moveRate = -7;
				preGame = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(!playedOnce || (playedOnce && restartState)){
				playing = true;
				moveRate = 7;
				preGame = false;
				
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			restartGame();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			moveRate = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			moveRate = 0;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void tick(){
		for(Obs obs : queue){
			obs.increaseX();
		}
	}
	
	public void tickOval(){
		ovalPosition += moveRate;
	}
	
	public void restartGame(){
		queue.dequeueAll();
		score = 0;
		restartState = true;
		ovalPosition = 350;
		preGame = true;
	}
}
