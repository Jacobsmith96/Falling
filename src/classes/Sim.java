package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * @author Jacob_000 The Class handling the behavior of the simulator
 */
public class Sim extends JFrame implements ActionListener, KeyListener {
    /**
     * Buffered image used in graphics
     */
    public BufferedImage myImage;
    /**
     * Bufer for the graphics
     */
    public Graphics2D myBuffer;
    /**
     * States whether the game is still running or not
     */
    boolean running;
    /**
     * Background panel
     */
    JPanel panel;
    /**
     * Initializes the timer
     */
    Timer timer;
    /**
     * Tick of the timer
     */
    public int tick = 0;
    /**
     * ArrayList that tracks all currently pressed keys
     */
    ArrayList keys = new ArrayList();
    /**
     * Arraylist that tracks all the circles
     */
    ArrayList<Circle> circles = new ArrayList<Circle>();
    public int gravity = 1;
    JSlider grav;

    public enum State {
	CLASSIC, SPRING, TUNNEL, WAIT
    }

    public State state;

    /**
     * Default constructor for the Sim class
     */
    public Sim() {
	myImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
	myBuffer = myImage.createGraphics();
	state = State.WAIT;
	timer = new Timer(20, this); // Creates a new timer
	addKeyListener(this);// Sets up the key listeners
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	setUndecorated(true);
	JSlider grav = new JSlider(0, 10, 1);
	timer.start();
    }

    /**
     * Paints every tick of the timer and draws the double buffering image all
     * at once to prevents flashing of the graphics
     */
    public void paint(Graphics g) {
	g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
    }

    /**
     * Creates the window and starts the simulator
     */
    public void create() { // Sets up the JFrame and calls run() to start the
			   // program
	setSize(800, 800);
	repaint();
	show();
    }

    /**
     * Handles the painting of all the objects based upon the current state
     */
    public void paintObjects() {
	if (state == State.CLASSIC) {
	    myBuffer.setColor(Color.BLACK);
	    myBuffer.fillRect(0, 0, 800, 800);
	    myBuffer.setColor(new Color(tick, tick , tick ));
	    for (Circle i : circles) {
		i.paintCircle(myBuffer);
	    }
	} else if (state == State.SPRING) {
	    myBuffer.setColor(Color.BLACK);
	    myBuffer.fillRect(0, 0, 800, 800);
	    myBuffer.setColor(Color.GREEN);
	    for (Circle i : circles) {
		i.paintCircle(myBuffer);
	    }
	} else if (state == State.TUNNEL) {
	    myBuffer.setColor(Color.BLACK);
	    myBuffer.fillRect(0, 0, 800, 800);
	    myBuffer.setColor(Color.RED);
	    for (Circle i : circles) {
		i.paintCircle(myBuffer);
	    }
	    myBuffer.setColor(Color.WHITE);
	    myBuffer.drawLine(
		    400,
		    400,
		    circles.get(circles.size() - 1).getX()
			    + circles.get(circles.size() - 1).getRadius(),
		    circles.get(circles.size() - 1).getY()
			    + circles.get(circles.size() - 1).getRadius());
	    myBuffer.setColor(Color.CYAN);
	    myBuffer.drawLine(
		    circles.get(0).getX() + circles.get(0).getRadius(),
		    circles.get(0).getY(),
		    circles.get(circles.size() - 1).getX()
			    + circles.get(circles.size() - 1).getRadius(),
		    circles.get(circles.size() - 1).getY());
	    myBuffer.drawLine(
		    circles.get(0).getX(),
		    circles.get(0).getY() + circles.get(0).getRadius(),
		    circles.get(circles.size() - 1).getX(),
		    circles.get(circles.size() - 1).getY()
			    + circles.get(circles.size() - 1).getRadius());
	    myBuffer.drawLine(
		    circles.get(0).getX() + circles.get(0).getRadius() * 2,
		    circles.get(0).getY() + circles.get(0).getRadius(),
		    circles.get(circles.size() - 1).getX()
			    + circles.get(circles.size() - 1).getRadius() * 2,
		    circles.get(circles.size() - 1).getY()
			    + circles.get(circles.size() - 1).getRadius());
	    myBuffer.drawLine(
		    circles.get(0).getX() + circles.get(0).getRadius(),
		    circles.get(0).getY() + circles.get(0).getRadius() * 2,
		    circles.get(circles.size() - 1).getX()
			    + circles.get(circles.size() - 1).getRadius(),
		    circles.get(circles.size() - 1).getY()
			    + circles.get(circles.size() - 1).getRadius() * 2);
	} else if (state == State.WAIT) {
	    myBuffer.setColor(Color.BLACK);
	    myBuffer.fillRect(0, 0, 800, 800);
	    myBuffer.setColor(Color.WHITE);
	    myBuffer.setFont(new Font("Arial", Font.PLAIN, 20));
	    myBuffer.drawString("Select an option", 340, 380);
	    myBuffer.drawString("1. Classic", 340, 400);
	    myBuffer.drawString("2. Spring", 340, 420);
	    myBuffer.drawString("3. Tunnel", 340, 440);
	    myBuffer.drawString("4.", 340, 460);
	    myBuffer.drawString("5. Exit", 340, 480);
	}
    }

    public void reset() { // Restarts the program if the player chooses to do so
	timer.start();
	state = State.WAIT;
	repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
	if (!keys.contains(key))
	    keys.add(key); // Adds the newly pressed key to the keys arrayList'
	if (keys.contains(KeyEvent.VK_1)) {
	    if (state == State.WAIT) {
		state = State.CLASSIC;
	    }
	}
	if (keys.contains(KeyEvent.VK_2)) {
	    if (state == State.WAIT) {
		state = State.SPRING;
	    }
	}
	if (keys.contains(KeyEvent.VK_3)) {
	    if (state == State.WAIT) {
		state = State.TUNNEL;
	    }
	}
	if (keys.contains(KeyEvent.VK_5)) {
	    if (state == State.WAIT) {
		System.exit(0);
	    }
	}
	if (keys.contains(KeyEvent.VK_G)) {
	    if (state == State.SPRING) {

		if (gravity < 5) {
		    gravity = gravity + 1;
		} else
		    gravity = 1;
		while (circles.size() > 0) {
		    circles.remove(0);
		}

	    }
	}
	if (keys.contains(KeyEvent.VK_LEFT)) {
	    if (state == State.TUNNEL) {
		for (Circle a : circles) {
		    a.setX(a.getX() - (circles.indexOf(a)));
		}
	    }
	}
	if (keys.contains(KeyEvent.VK_RIGHT)) {
	    if (state == State.TUNNEL) {
		for (Circle a : circles) {
		    a.setX(a.getX() + (circles.indexOf(a)));
		}
	    }
	}
	if (keys.contains(KeyEvent.VK_UP)) {
	    if (state == State.TUNNEL) {
		for (Circle a : circles) {
		    a.setY(a.getY() - (circles.indexOf(a)));
		}
	    }
	}
	if (keys.contains(KeyEvent.VK_DOWN)) {
	    if (state == State.TUNNEL) {
		for (Circle a : circles) {
		    a.setY(a.getY() + (circles.indexOf(a)));
		}
	    }
	}
	if (keys.contains(KeyEvent.VK_ESCAPE)) {
	    if (state != State.WAIT) {
		state = State.WAIT;
		while (circles.size() > 0) {
		    circles.remove(0);
		}
		reset();
	    }
	}

    }

    @Override
    public void keyReleased(KeyEvent e) {
	int key = e.getKeyCode();
	if (keys.size() > 0)
	    keys.remove(keys.indexOf(key)); // Removes the released key from the
					    // keys ArrayList

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (state == State.CLASSIC) {
	    if (circles.size() < 2) {
		int randX = (int) (Math.random() * 500);
		int randY = (int) (Math.random() * 200);
		int randRad = (int) (Math.random() * 100);
		int randvX = (int) (Math.random() * 5) + 1;
		int randvY = (int) (Math.random() * 5) + 1;
		circles.add(new Circle(randX, randY, randRad, randvX, randvY));
	    }
	    for (Circle a : circles) {
		for (Circle b : circles) {
		    if (a != b && a.collision(b)) {

		    }
		}
		a.update();
	    }
	} else if (state == State.SPRING) {
	    if (circles.size() < 50) {
		int x = 375;
		int y = 300;
		int rad = 25;
		int vX = 10;
		int vY = 2;
		circles.add(new Circle(x, y, rad, vX, vY));

	    }

	    for (Circle b : circles) {
		b.setVY(b.getVY() + gravity);
		b.update();
		if (b.reversed) {
		    b.setVY(b.getVY() + gravity);
		    b.reversed = false;

		}
	    }
	} else if (state == State.TUNNEL) {
	    if (circles.size() < 10) {
		for (int x = 50; x <= 100; x += 5) {
		    circles.add(new Circle(400 - (x + 10), 400 - (x + 10),
			    10 + x, 0, 0));
		}
	    }
	}
	tick += 1;
	if(tick>255){
	    tick = 0;
	}
	paintObjects();
	repaint();
    }
}
