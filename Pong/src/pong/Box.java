package pong;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;
import java.util.Random;

import org.lwjgl.opengl.*;
import org.lwjgl.*;

import entities.MoveableEntity;
import entities.AbstractEntity;
import entities.AbstractMoveableEntity;
import pong.Ball;

public class Box extends AbstractMoveableEntity {
	
	private Rectangle hitbox = new Rectangle();
	private Random rand;
	private float redColor, greenColor, blueColor;
	
	public Box(double x, double y, double width, double height) {
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
	
	public void update(double delta) {
		if (Main.randomize && System.currentTimeMillis() - Main.lastColorChange >= 100) {
			this.redColor = this.rand.nextFloat();
			this.greenColor = this.rand.nextFloat();
			this.blueColor = this.rand.nextFloat();
		} else if (!Main.randomize) {
			this.redColor = 1.0f;
			this.greenColor = 1.0f;
			this.blueColor = 1.0f;
		}
		
		this.x += delta * dx;
		this.y += delta * dy;
	}
	
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY() {
		this.y = y;
	}
	
	public void setWidth() {
		this.width = width;
	}
	
	public void setHeight() {
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