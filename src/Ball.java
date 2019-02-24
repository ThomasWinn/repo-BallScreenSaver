import java.awt.*;
import java.lang.Math;

public class Ball extends Circle{

    private double speedX = 30;
    private double speedY = 30;
    private double dist;

    // X pos, Y pos, Radius
    public Ball(double xPos, double yPos, double r, Color col) {
        super(xPos, yPos, r);
        color = col;

    }

    public void setSpeedX(double speedHorizontal) {
        speedX = speedHorizontal;
    }

    public void setSpeedY(double speedVertical) {
        speedY = speedVertical;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    // Update the ball's position based on its current velocity and time elapsed
    public void updatePosition(double time) {
        double changeX = speedX * time;
        this.x += changeX;
        double changeY = speedY * time;
        this.y += changeY;
        this.setPos(x,y);
    }

    // Check if the passed Ball object overlaps with the current Ball
    public boolean intersect(Ball a) {
        dist = Math.sqrt(Math.pow(this.getXPos() - a.getXPos(),2)+Math.pow(this.getYPos() - a.getYPos(),2));

        // The point where both the balls meet. When distance between them is equal to the distance of their radius's added together
        if (dist <= this.getRadius() + a.getRadius()) {


            return true;
        }
        else{

            return false;
        }
    }

    // When balls collide together -> update speed of balls in x and y direction -> Changes color of balls if condition is met
    public void collide(Ball curr) {

        double distance;
        double deltaX; // Delta X
        double deltaY; // Delta Y
        double newColVelocity;
        double newNormVelocity;
        double newColVelocity1;
        double newNormVelocity1;
        double finalXVeloicty;
        double finalYVelocity;
        double finalXVelocity1;
        double finalYVelocity1;

        // Checks to see if balls collided
        if(intersect(curr)) {

            distance = Math.sqrt(Math.pow(this.getXPos() - curr.getXPos(), 2) +
                    Math.pow(this.getYPos() - curr.getYPos(), 2));

            deltaX = (this.getXPos() - curr.getXPos()) / distance;
            deltaY = (this.getYPos() - curr.getYPos()) / distance;

            // Redevine the original velocities according to the new coordinate system
            newColVelocity = this.getSpeedX() * deltaX + this.getSpeedY() * deltaY;
            newNormVelocity= -1 * this.getSpeedX() * deltaY + this.getSpeedY() * deltaX;

            newColVelocity1 = curr.getSpeedX() * deltaX + curr.getSpeedY() * deltaY;
            newNormVelocity1 = -1 * curr.getSpeedX() * deltaY + curr.getSpeedY() * deltaX;

            double xSpeed = (newColVelocity1);
            double ySpeed= (newNormVelocity);

            double xSpeed2 = (newColVelocity);
            double ySpeed2 = (newNormVelocity1);


            // Computing FINAL X AND Y VELOCITY
            finalXVeloicty = xSpeed * deltaX - ySpeed * deltaY;
            finalYVelocity = xSpeed * deltaY + ySpeed * deltaX;

            finalXVelocity1 = xSpeed2 * deltaX - ySpeed2 * deltaY;
            finalYVelocity1 = xSpeed2 * deltaY + ySpeed2 * deltaX;

            this.setSpeedX(finalXVeloicty);
            this.setSpeedY(finalYVelocity);

            curr.setSpeedX(finalXVelocity1);
            curr.setSpeedY(finalYVelocity1);

            // UPDATES COLOR OF THE BALLS
            if (this.getColor() != curr.getColor()) {
                this.setColor(Color.RED);
                curr.setColor(Color.RED);
            }

        }
    }
}