import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.Math;

//Portions of this code are taken from BouncingBall created by Chad Myers.

// Thomas Nguyen nguy3817 5500699
// Brandon Chan chanx411 5357757

public class BallScreenSaver extends AnimationFrame {
    private int BORDER = 30;
    private double x, y, speedX, speedY;
    private int ballSize = 10;
    private double START_SPEED;
    private int number = 15;
    private Ball [] balls;
    private CollisionLogger collisionLogger;
    private static final int COLLISION_BUCKET_WIDTH = 20;
    private int saveCounter=0;

    // Constructor Start
    public BallScreenSaver(int w, int h, String Window, int numBalls) {
        super(w, h, Window);
        setFPS(60);
        number = numBalls;
        // creates a list of balls based on number of balls
        balls = new Ball [number];
        // iterates through the list of balls and creates a ball that goes in random direction with random speed
        for(int i = 0; i<number; i++) {
            START_SPEED = randdouble(50,200);
            balls[i] = new Ball(randdouble(BORDER+20,800-BORDER*2),randdouble(BORDER+20,800-BORDER*2),ballSize,Color.GREEN);
            double dir = randdouble(0, Math.PI * 2);
            balls[i].setSpeedX(Math.cos(dir) * START_SPEED);
            balls[i].setSpeedY(Math.sin(dir) * START_SPEED);
            //creates the last ball to be red
            if (i == number-1) {
                balls[i].setColor(Color.red);
            }
        }
        collisionLogger = new CollisionLogger(this.getWidth(), this.getHeight(), COLLISION_BUCKET_WIDTH);
    } // End Constructor

    // Credit to Chad
    // Finds random number between min and max
    public double randdouble(double min, double max) {
        //a utility method to get a random double between min and max.
        return (Math.random() * (max - min) + min);
    }

    public void action(){
        // Updates the position based on the speed and the FPS
        for(int i = 0; i < number; i++) {
            double fps = getFPS();
            balls[i].setPos(balls[i].getSpeedX()/fps + balls[i].getXPos(),balls[i].getSpeedY()/fps + balls[i].getYPos());

            // If two balls intersect and one is green and the other is red, change the green to red
            for(int k = i+1; k < number; k++) {
                if (balls[i].intersect(balls[k])) {
                    balls[i].collide(balls[k]);
                    collisionLogger.collide(balls[i], balls[k]);

                    if (balls[i].getColor() == Color.RED || balls[k].getColor() == Color.RED) {
                        balls[k].setColor(Color.RED);
                        balls[i].setColor(Color.RED);
                    }
                }
            }


            // If the ball hits the border, inverts the direction of travel in X
            if (balls[i].getXPos() < BORDER || balls[i].getXPos() + balls[i].getRadius() * 2 > getHeight() - BORDER) {
                balls[i].setSpeedX(balls[i].getSpeedX() * -1);
            }
            // If the ball hits the border, inverts the direction of travel in Y
            if (balls[i].getYPos() < BORDER || balls[i].getYPos() + balls[i].getRadius() * 2 > getWidth() - BORDER) {
                balls[i].setSpeedY(balls[i].getSpeedY() * -1);
            }
        }
    }

    //Draws the background and border
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.white);
        g.drawRect(BORDER,BORDER,getWidth()-BORDER*2,getHeight()-BORDER*2);
        //sets all the ball to a certain color at the start
        for(int i = 0; i<number;i++){
            Ball ballyball = balls[i];
            g.setColor(ballyball.getColor());
            g.fillOval((int) balls[i].getXPos(), (int) balls[i].getYPos(), (int) ballyball.getRadius() * 2, (int) ballyball.getRadius() * 2);
        }
    }

    // If left or right key is pressed, change the speed
    public void processKeyEvent(KeyEvent e)
    {
        int saveCounter = 0;

        int keyCode = e.getKeyCode();
        // if left key is pressed slow down by 10%
        if (keyCode == KeyEvent.VK_LEFT)
        {
            System.out.println("Left key pressed...");
            for(int i = 0; i <= balls.length; i++) {
                Ball ballyball = balls[i];
                ballyball.setSpeedX(ballyball.getSpeedX() * .9);
                ballyball.setSpeedY(ballyball.getSpeedY() * .9);
            }
        }
        // if right key is pressed speed up by 10%
        else if (keyCode == KeyEvent.VK_RIGHT)
        {
            System.out.println("Right key pressed...");
            for(int i = 0; i <= balls.length; i++) {
                Ball ballyball = balls[i];
                ballyball.setSpeedX(balls[i].getSpeedX() * 1.1);
                ballyball.setSpeedY(balls[i].getSpeedY() * 1.1);
            }
        }

        // if P is pressed, take a screenshot
        if (e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_P) {
            EasyBufferedImage image = EasyBufferedImage.createImage(collisionLogger.getNormalizedHeatMap());
            try {
               image.save("heatmap"+saveCounter+".png", EasyBufferedImage.PNG);
                saveCounter++;
            } catch (IOException exc) { exc.printStackTrace();
                System.exit(1);
           }

       }
    }

    // We found that the number of balls affects our printed picture. 15 is the best number to use
    public static void main(String[] args) {
        // creates and starts a ballscreensaver
        BallScreenSaver bb = new BallScreenSaver(800,800,"Bally",15);
        bb.start();
    }
}