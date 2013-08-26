//CSC 200 - Final Project Version 3
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

public class FinalProjectV3 extends JFrame implements KeyListener, Runnable {
	 int screenWidth = 400;
    int screenHeight = 550;
    
	 //variable declaration
	 BufferedImage backbuffer;
	 int x,y;
	 Thread gameloop; //thread
	 Sprite ball;
	 Sprite husky;
	 Sprite hamburger;
	 Sprite apple;
	 int score = 0;
	 
	 boolean hamburgerVisible = true;
	 boolean appleVisible = true;
	 boolean boomVisible = true;
	 boolean chocolateVisible = true;
	 
	 Random rand = new Random();
	 Random bounce = new Random();
	 
	 int xPoint1 = -5;
	 int xPoint2 = 5;
	 int xPoint3 = 0;

	 int yPoint1 = -5;
	 int yPoint2 = 5;
	 int yPoint3 = -10;
	 int yPoint4 = 10;
	 
	 int speedLimit = 100;
	 int dogSpeed = 5;
	 
	 
	 ImageEntity background;
	 Graphics2D g2d;
	 

	 

	 

	 

    
    public static void main(String[] args) {
        new FinalProjectV3();    
    }
    //constructor
    public FinalProjectV3() {
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
		  
		  //load the hamburger sprite
		  hamburger = new Sprite(this,g2d);
		  hamburger.load("rotateBurger.gif");		 
		  hamburger.setPosition(new Point(200,4));
		  hamburger.setVelocity(new Point(0,2));
		 
		  
		  
		  //load the apple gif
        apple = new Sprite(this,g2d);
        apple.load("rotateApple.gif");
		  apple.setPosition(new Point(150,0));
		  apple.setVelocity(new Point(0,2));
		  
		   
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
			  
			  g2d.setColor(Color.BLACK);
		     g2d.drawString("Score =" + score, 50, 50);
			  g2d.drawString("SPEED: " + speedLimit + "%", 50, 65);
			  
			  husky.setPosition(new Point(x,y)); //You need this method to change the position of the husky not the update position since 
			  												//the update position works with the velocity. Abeer
			  husky.transform();
			  husky.draw();
			  		
			  //condition when ball collides with husky
			  if(ball.collidesWith(husky.getBounds())){
			  		score = score+1;
					switch (bounce.nextInt(9)){ // randamize the bounce of the ball
						case 0:
						case 1:
						case 2: ball.setVelocity(new Point(xPoint1,yPoint1));
								break;
						case 3:
						case 4:
						case 5: ball.setVelocity(new Point(xPoint3,yPoint3));
								break;
						case 6:
						case 7:
						case 8:	ball.setVelocity(new Point(xPoint2, yPoint1));
								break;
						}
					}
			  //condition when hamburger collides with husky
			  if(hamburger.collidesWith(husky.getBounds())){
			  		score = score+10;
					hamburgerVisible = false;
					
					hamburger.setVelocity(new Point(0,-5));
					}	
			  			  
		     //condition when apple collides with husky
			  if(apple.collidesWith(husky.getBounds())){
			  		score = score+10;
					appleVisible = false;
					
					apple.setVelocity(new Point(0,-5));
					}	
					
			  //boundary for right side of the screen
			  if(ball.center().getX() > screenWidth - 20){
			  	switch (bounce.nextInt(6)){ // randamize the bounce of the ball
						case 0:
						case 1:
						case 2: ball.setVelocity(new Point(xPoint1, yPoint1)); // bounce upper left
								break;
						case 3:
						case 4:
						case 5: ball.setVelocity(new Point(xPoint1, yPoint2)); // bounce lower left
								break;
						}
					}
			  //boundary for left side of the screen
			  if(ball.center().getX() < 20) { 
			  	switch (bounce.nextInt(6)){ // randamize the bounce of the ball
						case 0:
						case 1:
						case 2: ball.setVelocity(new Point(xPoint2, yPoint1)); // bounce upper right
								break;
						case 3:
						case 4:
						case 5: ball.setVelocity(new Point(xPoint2, yPoint2)); // bounce lower right
								break;
						}
				}
				
			  //boundary for top of the screen
			  if(ball.center().getY() < 45) { 
					switch (bounce.nextInt(9)){ // randamize the bounce of the ball
						case 0:
						case 1:
						case 2: ball.setVelocity(new Point(xPoint1, yPoint2)); // bounce to the left
								break;
						case 3:
						case 4:
						case 5: ball.setVelocity(new Point(xPoint3, yPoint4)); // bounce strait down
								break;
						case 6:
						case 7:
						case 8:	ball.setVelocity(new Point(xPoint2, yPoint2)); // bounce to the right
								break;
						}

				}
			 
				
				update();
		
			  /*ball
			  ball.updatePosition();
			  ball.transform();
			  ball.draw();
			  
			  //hamburger
			  hamburger.updatePosition();
			  hamburger.transform();
			  hamburger.draw();
			  
			  //apple
			  apple.updatePosition();
			  apple.transform();
			  apple.draw();*/
			  

			  
			
			  
			 		
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
		  //g.setColor(Color.BLACK);
		  //g.drawString("Score =" +score, 50, 50);
    }
	 
	 public void update() {
	 		 			  
			  //ball
			  ball.updatePosition();
			  ball.transform();			  
			  ball.draw();
			  
			  
			  //hamburger
			  hamburger.updatePosition();
			  hamburger.transform();
			  if (hamburgerVisible){
			  hamburger.draw();
			  }
			  if(hamburger.center().getY() < 0) {
			   hamburger.setPosition(new Point(rand.nextInt(400),0));  
				hamburgerVisible = true; 
				hamburger.setVelocity(new Point(0,2));
				}
				
				
			
			  
			  //apple
			  apple.updatePosition();
			  apple.transform();
			  if (appleVisible){
			  apple.draw();
			  }
			  if(apple.center().getY() < 0) {
			   apple.setPosition(new Point(rand.nextInt(400),0));  
				appleVisible = true; 
				apple.setVelocity(new Point(0,2));
				}
				// increasing the balls and dog speed 
			  if(score > speedLimit && score < speedLimit + 100){
			 	xPoint1 = xPoint1 - 2;
				xPoint2 = xPoint2 + 2;

				yPoint1 = yPoint1 - 2;
				yPoint2 = yPoint2 + 2;
				yPoint3 = yPoint3 - 2;
				yPoint4 = yPoint4 + 2;
				
				dogSpeed = dogSpeed +2;

				speedLimit = speedLimit + 100;
				}

			
			}

	 
	 //keyboard handlers
    public void keyReleased(KeyEvent k) {
	  switch (k.getKeyCode()) {
		case KeyEvent.VK_LEFT: //left key pressed
    		if(x>-10){        
				x-=10; //update the value of x
    		}        
				break;
        case KeyEvent.VK_RIGHT: //right key pressed
     		if(x<309){      
			   x+=10; //update the value of y
    		}        
				break;			
        }
		  repaint();
	   }
	 
    public void keyTyped(KeyEvent k) { }
	 
    public void keyPressed (KeyEvent k){
	 
      switch (k.getKeyCode()) {
        case KeyEvent.VK_LEFT: //left key pressed
    		if(x>-10){        
				x-=dogSpeed; //update the value of x
    		}        
				break;
        case KeyEvent.VK_RIGHT: //right key pressed
     		if(x<309){      
			   x+= dogSpeed; //update the value of y
    		}        
				break;			
        }
		  repaint();
    }
}
