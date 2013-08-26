//CSC 200 - Final Project
//Bruce Canady & Tony Garcia

import java.awt.*;
import java.util.*;
import java.awt.image.*;
import javax.swing.*;
import java.net.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.applet.*;
import java.math.*;

public class FinalProject extends JFrame implements KeyListener, Runnable {
	 int screenWidth = 400;
    int screenHeight = 550;
    
	 //variable declaration
	 BufferedImage backbuffer;
	 int x,y;
	 Thread gameloop; //thread
	 Sprite ball;
	 Sprite husky;
	 
	 ImageEntity background;
	 Graphics2D g2d;
	 

    
    public static void main(String[] args) {
        new FinalProject();    
    }
    //constructor
    public FinalProject() {
        super("Husky Ball");
        setSize(screenWidth, screenHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create the back buffer for smooth graphics
        backbuffer = new BufferedImage(screenWidth, screenHeight,
            BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();
		  
		  //load the background
        background = new ImageEntity(this);
        background.load("blue-winter.png");
		  
		  //load the husky sprite
        husky = new Sprite(this,g2d);
        husky.load("siberian_husky.png");
		  husky.setPosition(new Point(0,0));
		  
		  
		  //load the ball sprite
        ball = new Sprite(this,g2d);
        ball.load("url.png");
		  ball.setPosition(new Point(275,40));
		  ball.setVelocity(new Point(0,5));
		   
		  gameloop = new Thread(this);
        gameloop.start();
		  
		  addKeyListener(this); //key listener
		  x=175;
		  y=430;
    }
	 
	 //identity transformation
    AffineTransform identity = new AffineTransform();
    
	 private URL getURL(String filename) {
        URL url = null;
        try {
            url = this.getClass().getResource(filename);
        }
        catch (Exception e) { }
        return url;
    }
	 

	 //run
	 public void run()
	 {
		 Thread t = Thread.currentThread();		
		
		 while(t == gameloop)
		 {
		  	 try
			 {
			 	Thread.sleep(20);
			 }
			 catch(Exception e) { }
			 
			  g2d.drawImage(background.getImage(), 0, 0, screenWidth-1, 
                screenHeight-1, this); 	
			  
			  husky.setPosition(new Point(x,y)); //You need this method to change the position of the husky not the update position since 
			  												//the update position works with the velocity. Abeer
			  husky.transform();
			  husky.draw();
			  		
			  //condition when ball collides with husky
			  if(ball.collidesWith(husky.getBounds())){
			  			
					ball.setVelocity(new Point(5,-5));		
					}
			  //boundary for right side of the screen
			  if(ball.center().getX() > screenWidth - 20){
					ball.setVelocity(new Point(-5,-5));
					}
			  //boundary for left side of the screen
			  if(ball.center().getX() < 20) { 
				ball.setVelocity(new Point(5,5));
				}
				
			  //boundary for top of the screen
			  if(ball.center().getY() < 45) { 
				ball.setVelocity(new Point(-5,5));
				}
			 
				
				
		
			  
			  ball.updatePosition();
			  ball.transform();
			  ball.draw();
			  
			 		
			 repaint();
		 }
	 }
	 //stop
	 public void stop()
	 {
		 gameloop = null; //stop the thread
	 }

    public void paint(Graphics g) {
    	  //draw image on back buffer
		  g.drawImage(backbuffer, 0, 0, this);
		  System.out.println("X " +x + "Y" +y);
    }
	 
	 //keyboard handlers
    public void keyReleased(KeyEvent k) { }
	 
    public void keyTyped(KeyEvent k) { }
	 
    public void keyPressed (KeyEvent k){
	 
      switch (k.getKeyCode()) {
        case KeyEvent.VK_LEFT: //left key pressed
    		if(x>-10){        
				x-=2.5; //update the value of x
    		}        
				break;
        case KeyEvent.VK_RIGHT: //right key pressed
     		if(x<309){      
			   x+=2.5; //update the value of y
    		}        
				break;			
        }
		  repaint();
    }
}
