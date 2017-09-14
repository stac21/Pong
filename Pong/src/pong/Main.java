package pong;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
/*
 * I'm going to have to make seperate threads for each paddle as well
 * as the ball's movement. Fun.
 */

public class Main {
	private enum gamestates {
		START, PAUSED, PLAY
	};
	
	private static gamestates GAME_STATE = gamestates.START;
	private static long lastFrame;
	public static long lastColorChange;
	public static final int X_RES = 640;
	public static final int Y_RES = 480;
	private static int speed = 1;
	private static double dy = (speed + .5) / 10.0;
	private static double ballDX;
	private static double ballDY;
	public static boolean randomize = false;
	public static Box box1 = new Box(10, (Y_RES / 2 - 80 / 2), 10, 80);
	public static Box box2 = new Box(X_RES - 20, (Y_RES / 2 - 80 / 2), 10, 80);
	private static Ball ball = new Ball((X_RES / 2 - 10 / 2), (Y_RES / 2 - 10 / 2), 10, 10);
	private static double height = box1.getHeight();
	
	public Main() {
		try {
			Display.setDisplayMode(new DisplayMode(Main.X_RES, Main.Y_RES));
			//Display.setFullscreen(true);
			Display.setTitle("Pong");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Main.X_RES, Main.Y_RES, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		Background background = new Background();
		
		while (!Display.isCloseRequested()) {
			if (!Main.gameWon()) {
				// render code goes here, in case you can't remember, future Grant
				glClear(GL_COLOR_BUFFER_BIT);
			
				if (Main.randomize)
					background.draw();
				box1.draw();
				box2.draw();
				ball.draw();
			
				double delta = Main.getDelta();
			
				box1.update(delta);
				box2.update(delta);
				ball.update(delta);
			
				input();
			
				Display.update();
				Display.sync(60);
			} else {
				// TODO: display the game over sign
				glClear(GL_COLOR_BUFFER_BIT);
				
				if (Main.randomize)
					background.draw();
				box1.draw();
				box2.draw();
				ball.draw();
				
				GAME_STATE = gamestates.START;
				
				input();
				
				Display.update();
				Display.sync(120);
			}
		}
		
		Display.destroy();
		System.exit(0);
	}
	
	private static void input() {
		if (GAME_STATE == gamestates.START) {
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				int randDX = (int) (Math.random() * 2);
				int randDY = (int) (Math.random() * 2);
				double rateOfChange = (speed + 1) / 10.0;
				
				GAME_STATE = gamestates.PLAY;
				
				ball.setLocation((X_RES / 2 - 10 / 2),  (Y_RES / 2 - 10 / 2));
				box1.setLocation(10, (480 / 2 - 80 / 2));
				box2.setLocation(X_RES - 20, (Y_RES / 2 - 80 / 2));
				
				ball.setDX((randDX == 0) ? -rateOfChange : rateOfChange);
				ball.setDY((randDY == 0) ? -rateOfChange : rateOfChange);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
				speed = 1;
				dy = (speed + .5) / 10.0;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
				speed = 2;
				dy = (speed + .5) / 10.0;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
				speed = 3;
				dy = (speed + .5) / 10.0;
			} while (Keyboard.next()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT && 
						Keyboard.getEventKeyState())
					Main.randomize = (Main.randomize) ? false : true;
			}
		} else if (GAME_STATE == gamestates.PAUSED) {
			while (Keyboard.next()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_P && 
						Keyboard.getEventKeyState()) {
					GAME_STATE = gamestates.PLAY;
					ball.setDX(Main.ballDX);
					ball.setDY(Main.ballDY);
				} else if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT && 
						Keyboard.getEventKeyState())
					Main.randomize = (Main.randomize) ? false : true;
				else if (Keyboard.getEventKey() == Keyboard.KEY_SPACE && 
						Keyboard.getEventKeyState()) {
					int randDX = (int) (Math.random() * 2);
					int randDY = (int) (Math.random() * 2);
					double rateOfChange = (speed + 1) / 10.0;
					
					GAME_STATE = gamestates.PLAY;
					
					ball.setLocation((X_RES / 2 - 10 / 2),  (Y_RES / 2 - 10 / 2));
					box1.setLocation(10, (480 / 2 - 80 / 2));
					box2.setLocation(X_RES - 20, (Y_RES / 2 - 80 / 2));
					
					ball.setDX((randDX == 0) ? -rateOfChange : rateOfChange);
					ball.setDY((randDY == 0) ? -rateOfChange : rateOfChange);
				}
			}
		} else if (GAME_STATE == gamestates.PLAY) {
			double box1Y = box1.getY();
			double box2Y = box2.getY();
			
			if (Keyboard.isKeyDown(Keyboard.KEY_W) && box1Y >= 0)
				box1.setDY(-dy);
			else if (Keyboard.isKeyDown(Keyboard.KEY_S) && box1Y + height <= Y_RES)
				box1.setDY(dy);
			else
				box1.setDY(0);
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP) && box2Y >= 0)
				box2.setDY(-dy);
			else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && box2Y + height <= Y_RES)
				box2.setDY(dy);
			else
				box2.setDY(0);
			
			while (Keyboard.next()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT && 
						Keyboard.getEventKeyState())
					Main.randomize = (Main.randomize) ? false : true;
				else if (Keyboard.isKeyDown(Keyboard.KEY_P) && 
						Keyboard.getEventKeyState())
					Main.pause();
				else if (!Display.isFullscreen() && Keyboard.getEventKey() == Keyboard.KEY_F) {
					try {
						// figure out how to make this fullscreen
						Display.setFullscreen(true);
					} catch (LWJGLException e) {
						e.printStackTrace();
					}
					//Main.pause();
				} else if (Display.isFullscreen() && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					try {
						Display.setFullscreen(false);
					} catch (LWJGLException e) {
						e.printStackTrace();
					}
					// Main.pause();
				}
			}
		}
	}
	
	private static void pause() {
		GAME_STATE = gamestates.PAUSED;
		Main.ballDX = ball.getDX();
		Main.ballDY = ball.getDY();
		
		box1.setDY(0);
		box2.setDY(0);
		ball.setDX(0);
		ball.setDY(0);
	}
	
	private static boolean gameWon() {
		// if the ball is still within bounds then return true and false otherwise
		double ballX = ball.getX();
		
		return (ballX <= 0 || ballX + ball.getWidth() >= 640);
	}
	
	// calculates the system time
	private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

	// calculates the delta time so that movement is framerate independent
    private static double getDelta() {
        long currentTime = getTime();
        double delta = (double) (currentTime - lastFrame);
        lastFrame = getTime();
       
        return delta;
    }
	
	public static void main(String[] args)	 {
		new Main();
	}
}