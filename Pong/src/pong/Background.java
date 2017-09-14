package pong;

import java.util.Random;
import static org.lwjgl.opengl.GL11.*;

public class Background  {
	private float redColor, greenColor, blueColor;
	private Random rand;
	
	public Background() {
		this.rand = new Random();
	}
	
	public void draw() {
		this.redColor = this.rand.nextFloat();
		this.greenColor = this.rand.nextFloat();
		this.blueColor = this.rand.nextFloat();
		
		glColor3f(this.redColor, this.greenColor, this.blueColor);
		
		glRectf(0, 0, Main.X_RES, Main.Y_RES);
	}
}
