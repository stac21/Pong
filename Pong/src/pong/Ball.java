package pong;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;
import entities.AbstractMoveableEntity;
import pong.Box;

public class Ball extends AbstractMoveableEntity {
	private Random rand;
	private float redColor, greenColor, blueColor;
	
	public Ball(double x, double y, double width, double height) {
		super(x, y, width, height);
		
		this.rand = new Random();
		this.redColor = 255;
		this.greenColor = 255;
		this.blueColor = 255;
	}
	
	public void draw() {
		glColor3f(this.redColor, this.greenColor, this.blueColor);
		
		glBegin(GL_QUADS);
			glVertex2d(x, y);
			glVertex2d(x + width, y);
			glVertex2d(x + width, y + height);
			glVertex2d(x, y + height);
		glEnd();
	}

	@Override
	public void update(double delta) {
		if (this.intersects(Main.box1) && this.x >= Main.box1.x) {
			this.dx = -this.dx;
			
			this.x = Main.box1.x + Main.box1.width  + 1;
		} else if (this.intersects(Main.box2) && this.x <= Main.X_RES - 20) {
			this.dx = -this.dx;
			
			this.x = Main.box2.x - Main.box1.width - 1;
		} else if (this.y <= 0) {
			this.dy = -this.dy;
			
			this.y = 1;
		} else if (this.y + this.height >= Main.Y_RES) {
			this.dy = -this.dy;
			
			this.y = Main.Y_RES - this.height - 1;
		}
			
		if (Main.randomize && System.currentTimeMillis() - Main.lastColorChange >= 100) {
			this.redColor = this.rand.nextFloat();
			this.greenColor = this.rand.nextFloat();
			this.blueColor = this.rand.nextFloat();
			
			Main.lastColorChange = System.currentTimeMillis();
		} else if (!Main.randomize) {
			this.redColor = 1.0f;
			this.greenColor = 1.0f;
			this.blueColor = 1.0f;
		}
		
		this.x += delta * this.dx;
		this.y += delta * this.dy;
	}
	
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public boolean intersects(Box other) {
		hitbox.setBounds((int)x, (int)y, (int)width, (int)height);
		
		return hitbox.intersects(other.x, other.y, other.width, other.height);
	}
}