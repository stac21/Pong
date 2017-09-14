package entities;

import java.awt.Rectangle;

public abstract class AbstractEntity implements Entity {

	public double x, y, width, height;
	protected Rectangle hitbox = new Rectangle();
	
	public AbstractEntity(double x, double y, double width,
			double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void setLocation(double x, double y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
	}

	@Override
	public void setX(double x) {
		// TODO Auto-generated method stub
		this.x = x;
	}

	@Override
	public void setY(double y) {
		// TODO Auto-generated method stub
		this.y  =y;
	}

	@Override
	public void setWidth(double width) {
		// TODO Auto-generated method stub
		this.width = width;
	}

	@Override
	public void setHeight(double height) {
		// TODO Auto-generated method stub
		this.height = height;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public boolean intersects(Entity other) {
		// TODO Auto-generated method stub
		hitbox.setBounds((int)x, (int)y, (int)width, (int)height);
		return hitbox.intersects(other.getX(), other.getY(), 
				other.getWidth(), other.getHeight());
	}
}