package classes;

import java.awt.Graphics;

/**
 * @author Jacob Smith Class that handles the circles in the simulator
 */
public class Circle {
    /**
     * X position of the circle
     */
    public int xVal;
    /**
     * Y position of the circle
     */
    public int yVal;
    /**
     * Radius of the circle
     */
    public int r;

    /**
     * X velocity of the circle
     */
    public int vX;
    /**
     * Y Velocity of the circle
     */
    public int vY;

    /**
     * Default constructor for the circle
     */
    public Circle() {
	xVal = 0;
	yVal = 0;
	r = 10;
	vX = 0;
	vY = 0;
    }

    /**
     * @param x
     *            The x value of the circle
     * @param y
     *            The y value of the circle
     * @param rad
     *            The length of the circle's radius
     * @param velX
     *            The x velocity of the circle
     * @param velY
     *            The y velocity of the circle
     */
    public Circle(int x, int y, int rad, int velX, int velY) {
	xVal = x;
	yVal = y;
	r = rad;
	vX = velX;
	vY = velY;
    }

    /**
     * @param g
     *            The graphics being used Paints a circle based on radius and
     *            x/y
     */
    public void paintCircle(Graphics g) {
	g.drawOval(getX(), getY(), 2 * getRadius(), 2 * getRadius());
    }

    /**
     * @param x
     *            the new X value of the circle
     */
    public void setX(int x) {
	xVal = x;
    }

    /**
     * @param the
     *            new Y value of the circle
     */
    public void setY(int y) {
	yVal = y;
    }

    /**
     * @param rad
     *            the new radius value of the circle
     */
    public void setRadius(int rad) {
	r = rad;
    }

    /**
     * @param newVX
     *            New vx value
     */
    public void setVX(int newVX) {
	vX = newVX;
    }

    /**
     * @param newVY
     *            New vy value
     */
    public void setVY(int newVY) {
	vY = newVY;
    }

    /**
     * @return the X value of the circle
     */
    public int getX() {
	return xVal;
    }

    /**
     * @return the Y value of the circle
     */
    public int getY() {
	return yVal;
    }

    /**
     * @return the radius of the circle
     */
    public int getRadius() {
	return r;
    }

    /**
     * @return current value of vX
     */
    public int getVX() {
	return vX;
    }

    /**
     * @return current value of vY
     */
    public int getVY() {
	return vY;
    }

    public void update() {
	if (xVal + vX <= 0) {
	    vX = Math.abs(vX);
	}
	if (xVal + (r * 2) >= 800) {
	    if (vX > 0)
		vX = Math.abs(vX) * -1;
	}
	if (yVal + vY <= 0) {
	    vY = Math.abs(vY);
	}
	if (yVal + (r * 2) >= 800) {
	    if (vY > 0)
		vY = Math.abs(vY) * -1;
	}
	xVal = xVal + vX;
	yVal = yVal + vY;
	setVY(getVY()/* +GRAVITY */);

    }

    /**
     * @param circ1
     *            Other circle in the simulation
     * @return true if two circles are colliding
     */
    public boolean collision(Circle circ1) {

	double xDif = getX() - circ1.getX();
	double yDif = getY() - circ1.getY();
	double distanceSquared = xDif * xDif + yDif * yDif;
	boolean collision = distanceSquared < (getRadius() + circ1.getRadius())
		* (getRadius() + circ1.getRadius());
	return collision;
    }
}
