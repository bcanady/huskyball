//CSC 200 - Final Project Version 6
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

public class FinalProjectV6 extends JFrame implements KeyListener, Runnable {
	 int screenWidth = 400;
    int screenHeight = 550;
    
	 //variable declaration
	 BufferedImage backbuffer;
	 int x,y;
	 Thread gameloop; //thread
	 Sprite ball,husky,hamburger,apple,chocolate,dynamite;
	 
	 boolean stop = false;
	 boolean huskyVisible = true;
	 boolean hamburgerVisible = false;
	 boolean appleVisible = false;
	 boolean dynamiteVisible = false;
	 boolean chocolateVisible = false;
	 Random rand = new Random();
	 boolean rightKeyPressed,leftKeyPressed,spaceKeyPressed;
	 
	 int bounce = 0;
	 int score = 0;
	 
	 ImageEntity background;
	 Graphics2D g2d;
	 
	 //explosion variables
    Image image;
    Point pos = new Point(300,200);
	 
	 //animation variables for explosion
    int currentFrame = 30;
    int totalFrames = 30;
    int animationDirection = 1;
    int frameCount = 0;
    int frameDelay = 1;
	 

    
    public static void main(String[] args) {
        new FinalProjectV6();    
    }
    //constructor
    public FinalProjectV6() {
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
		  		  
		  //load the ball sprite
        ball = new Sprite(this,g2d);
        ball.load("url.png");
		  ball.setPosition(new Point(175,0));
		  ball.setVelocity(new Point(0,5));
		  
		  //load the hamburger sprite
        hamburger = new Sprite(this,g2d);
		  hamburger.load("rotateBurger.gif");		 
		  hamburger.setPosition(new Point(20,0));
		  hamburger.setVelocity(new Point(0,0));
		  
		  //load the apple gif
        apple = new Sprite(this,g2d);
        apple.load("rotateApple.gif");
		  apple.setPosition(new Point(150,0));
		  apple.setVelocity(new Point(0,0));
		  
		  //load the chocolate sprite
        chocolate = new Sprite(this,g2d);
		  chocolate.load("rotateChocolate.gif");		 
		  chocolate.setPosition(new Point(200,0));
		  chocolate.setVelocity(new Point(0,0));
		  
		  //load the dynamite sprite
        dynamite = new Sprite(this,g2d);
		  dynamite.load("rotateBoom.gif");		 
		  dynamite.setPosition(new Point(250,0));
		  dynamite.setVelocity(new Point(0,0));
		  
		  //load the ball animation strip
        Toolkit tk = Toolkit.getDefaultToolkit();
        image = tk.getImage(getURL("explosion.png"));
		  
		   
		  gameloop = new Thread(this);
        gameloop.start();
		  
		  addKeyListener(this); //key listener
		  x=150;
		  y=448;
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
			  
			  g2d.setFont(new Font("Ariel", Font.BOLD, 24)); 
			  g2d.setColor(Color.BLACK);        
		     g2d.drawString("Score = " +score, 10, 60);
			  
			  husky.setPosition(new Point(x,y)); 
			  												
			  if ((leftKeyPressed)&&(!rightKeyPressed)); //husky.moveLeft();
           if ((rightKeyPressed)&&(!leftKeyPressed)); //husky.moveRight();
			  if (spaceKeyPressed); //husky.moveRight();
			  		
			  //condition when ball collides with husky
			  if(ball.collidesWith(husky.center())){
			  		score = score+1;
					bounce = bounce+1;
					
					//determines which way the ball will bounce off the husky
					if(ball.velocity().getX() == 0 && leftKeyPressed)
					ball.setVelocity(new Point(-5,-5));
					if(ball.velocity().getX() == 0 && rightKeyPressed)
					ball.setVelocity(new Point(5,-5));
					if(ball.velocity().getX() == 0)
					ball.setVelocity(new Point(5,-5));
						
					if(ball.velocity().getX() == 5 && leftKeyPressed)
					ball.setVelocity(new Point(5,-5));
					if(ball.velocity().getX() == 5 && rightKeyPressed)
					ball.setVelocity(new Point(-5,-5));
					if(ball.velocity().getX() == 5)
					ball.setVelocity(new Point(-5,-5));
					
					if(ball.velocity().getX() == -5 && leftKeyPressed)
					ball.setVelocity(new Point(5,-5));
					if(ball.velocity().getX() == -5 && rightKeyPressed)
					ball.setVelocity(new Point(-5,-5));
					if(ball.velocity().getX() == -5)
					ball.setVelocity(new Point(-5,-5));
					}
			  
			  //condition when hamburger collides with husky
			  if(hamburger.collidesWith(husky.getBounds())){
			  	if(hamburgerVisible){
			  		score = score+20;
					}
				hamburger.setPosition(new Point(rand.nextInt(390),0));  
				hamburgerVisible = true; 
				hamburger.setVelocity(new Point(0,rand.nextInt(9)+1));
					
					}	
			  			  			  
		     //condition when apple collides with husky
			  if(apple.collidesWith(husky.getBounds())){
			  	if(appleVisible){
			  		score = score+10;
					}																					/*---*/
				apple.setPosition(new Point(rand.nextInt(390),0));  
				appleVisible = true; 
				apple.setVelocity(new Point(0,rand.nextInt(9)+1));
					}	
			  //condition when chocolate collides with husky	
			  if(chocolate.collidesWith(husky.getBounds())){
			  	if(chocolateVisible){
			  		score = score/2;
					}																					/*---*/
				chocolate.setPosition(new Point(rand.nextInt(390),0));  
				chocolateVisible = true; 
				chocolate.setVelocity(new Point(0,rand.nextInt(9)+1));
					}	
					
			  //condition when dynamite collides with husky	
			  if(dynamite.collidesWith(husky.getBounds())){				
				if(dynamiteVisible){
					 huskyVisible = false;
		
					 //stop();										
					}																					/*---*/
					dynamiteVisible = false;
					
					dynamite.setVelocity(new Point(0,-5));									
				}
					
			
			//ball bounce off right/left sides of screen			
			if(ball.velocity().getX() == -5 && ball.velocity().getY() == 5)	
			if (ball.center().getX() < 20)			
            ball.setVelocity(new Point(5,5));
			
			if(ball.velocity().getX() == -5 && ball.velocity().getY() == -5)	
			if (ball.center().getX() < 20)			
            ball.setVelocity(new Point(5,-5));	
				
		   if(ball.velocity().getX() == 5 && ball.velocity().getY() == -5)	
			if (ball.center().getX() > screenWidth-20)			
            ball.setVelocity(new Point(-5,-5));	
		
			if(ball.velocity().getX() == 5 && ball.velocity().getY() == 5)	
			if (ball.center().getX() > screenWidth-20)			
            ball.setVelocity(new Point(-5,5));	
				
			
   	
			 
		 //ball bounce off top of screen
        if (ball.velocity().getX() == -5 && ball.velocity().getY() == -5)
		  if (ball.center().getY() < 42)
            ball.setVelocity(new Point(-5,5));
		  if (ball.velocity().getX() == 5 && ball.velocity().getY() == -5)
        if (ball.center().getY() < 42)
            ball.setVelocity(new Point(5,5));
		  if (ball.velocity().getX() == 0 && ball.velocity().getY() == -5)
        if (ball.center().getY() < 42)
            ball.setVelocity(new Point(0,5));
				
		  //game over conditions
		  if (ball.center().getY() > 550) 
		  	{ 
        		g2d.drawString("Game Over",130,170);
				stop(); 																				/*---*/
			}
		  if (score < 0)
		  		g2d.drawString("Game Over",130,170); 
		  
		  update();
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
	 
	 public void update() {

			  //husky movement
			  if(leftKeyPressed){			  
			  if(x>-5){
			  		x=x-10;		
					}
				}			  
			  if(rightKeyPressed){
			  if(x<305){
			  		x=x+10;
					}
				}			  
			  
			  husky.transform();
			  husky.draw(); 
			 
			  //ball
			  ball.updatePosition();
			  ball.transform();			  
			  ball.draw();
			 
			  
			  //hamburger
			  if(bounce >= 8){
			  hamburger.updatePosition();
			  hamburger.transform();
			  hamburgerVisible = true;
			  if (hamburgerVisible){
			  hamburger.draw();
			  }
			  if(hamburger.center().getY() < 0) {
			   hamburger.setPosition(new Point(rand.nextInt(390),0));  
				hamburgerVisible = true; 
				hamburger.setVelocity(new Point(0,rand.nextInt(9)+1));				/*---*/
				}
			  if(hamburger.center().getY() > 570){
			   hamburger.setPosition(new Point(rand.nextInt(390),0));  
				hamburgerVisible = true; 
				hamburger.setVelocity(new Point(0,rand.nextInt(9)+1));				/*---*/
				}
				
			  }
			  
			  //apple
			  if(bounce >= 4){
			  apple.updatePosition();
			  apple.transform();
			  appleVisible = true; 
			  if (appleVisible){
			  apple.draw();
			  }
			  if(apple.center().getY() < 0) {
			   apple.setPosition(new Point(rand.nextInt(390),0));  
				appleVisible = true; 
				apple.setVelocity(new Point(0,rand.nextInt(9)+1));						/*---*/
				}
				if(apple.center().getY() > 570){
			   apple.setPosition(new Point(rand.nextInt(390),0));  
				appleVisible = true; 
				apple.setVelocity(new Point(0,rand.nextInt(9)+1));
				}
			  }
			  
			  //chocolate
			  if(bounce >= 10){
			  chocolate.updatePosition();
			  chocolate.transform();
			  chocolateVisible = true;
			  if (chocolateVisible){
			  chocolate.draw();
			  }
			  if(chocolate.center().getY() < 0) {
			   chocolate.setPosition(new Point(rand.nextInt(390),0));  
				chocolateVisible = true; 
				chocolate.setVelocity(new Point(0,rand.nextInt(9)+1));				/*---*/
				}
			  if(chocolate.center().getY() > 570){
			   chocolate.setPosition(new Point(rand.nextInt(390),0));  
				chocolateVisible = true; 
				chocolate.setVelocity(new Point(0,rand.nextInt(9)+1));
				}
				
			  }			  			
			
			  //dynamite
			  if(bounce >= 2){
			  dynamite.updatePosition();
			  dynamite.transform();
			  dynamiteVisible = true; 
			  if (dynamiteVisible){
			  dynamite.draw();
			  }
			  if(dynamite.center().getY() < 0) {
			   dynamite.setPosition(new Point(rand.nextInt(390),0));  
				dynamiteVisible = true; 
				dynamite.setVelocity(new Point(0,rand.nextInt(9)+1));					/*---*/
				}
				if(dynamite.center().getY() > 570){
			   dynamite.setPosition(new Point(rand.nextInt(390),0));  
				dynamiteVisible = true; 
				dynamite.setVelocity(new Point(0,rand.nextInt(9)+1));
				}
			  }
		
		  //draw the current frame of animation
        drawFrame(image, g2d, pos.x, pos.y, 6, currentFrame, 128, 128);
			  
		  //see if it's time to animate
        frameCount++;
        if(huskyVisible == false){ 
		  		    
				frameCount=0;
            //update the animation frame
            currentFrame += animationDirection;
            if (currentFrame > totalFrames - 1) {
                currentFrame = 0;
                pos.x = x;
                pos.y = y;
					 
            }
            else if (currentFrame < 0) {
               currentFrame = totalFrames - 1;
           }
			 
			
	}
	}
	    //draw a single frame of animation
    public void drawFrame(Image source, Graphics2D dest,
                          int x, int y, int cols, int frame,
                          int width, int height)
    {
        int fx = (frame % cols) * width;
        int fy = (frame / cols) * height;
        dest.drawImage(source, x, y, x+width, y+height,
                       fx, fy, fx+width, fy+height, this);
    }
	 
	 //keyboard handlers
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:  leftKeyPressed=true; break;
      case KeyEvent.VK_RIGHT: rightKeyPressed=true; break;
		case KeyEvent.VK_SPACE: spaceKeyPressed=true; break;
    }
  }

  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:  leftKeyPressed=false; break;
      case KeyEvent.VK_RIGHT: rightKeyPressed=false; break;
    }
  }
  
  public void keyTyped(KeyEvent e) {  }
}