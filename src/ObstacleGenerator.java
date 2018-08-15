
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics2D;

public class ObstacleGenerator {
	private Random random;
	private int height = 20;
	private int width = 240;
	
	public ObstacleGenerator(){
		random = new Random();
	}
	
	public int generate(Graphics2D g, double x){
		g.setColor(Color.white);
		int y = 0;
		g.fillRect(y = random.nextInt(479), (int)x, width, height);
		return y;
	}
	
	public void generateSet(Graphics2D g, double y, double x){
		g.setColor(Color.white);
		g.fillRect((int)y, (int)x, width, height);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}