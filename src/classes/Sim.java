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
import java.util.ArrayList;
import java.util.Vector;

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
    public final int GRAVITY = 1;

    public enum State {
	CLASSIC, SPRING, WAIT
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

    public void paintObjects() {
	if (state == State.CLASSIC || state == State.SPRING) {
	    myBuffer.setColor(Color.BLACK);
	    myBuffer.fillRect(0, 0, 800, 800);
	    myBuffer.setColor(Color.YELLOW);
	    for (Circle i : circles) {
		i.paintCircle(myBuffer);
	    }
	} else {
	    myBuffer.setColor(Color.WHITE);
	    myBuffer.setFont(new Font("Arial", Font.PLAIN, 20));
	    myBuffer.drawString("Select an option", 340, 380);
	    myBuffer.drawString("1. Classic", 340, 400);
	    myBuffer.drawString("2. Spring", 340, 420);
	    myBuffer.drawString("3.", 340, 440);
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
	if (keys.contains(KeyEvent.VK_5)) {
	    if (state == State.WAIT) {
		System.exit(0);
	    }
	}
	if (keys.contains(KeyEvent.VK_ESCAPE)) {
	    if (state != State.WAIT) {
		state = State.WAIT;
		for (Circle a : circles) {
		    circles.remove(a);
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
	System.out.println(keys);
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
	    for (Circle a : circles) {
		circles.remove(a);
	    }
	    for (Circle a : circles) {
		if (circles.size() < 50) {
		    int x = 375;
		    int y = 10;
		    int rad = 25;
		    int vX = 3;
		    int vY = 2;
		    circles.add(new Circle(x, y, rad, vX, vY));
		}
		a.update();
	    }
	}
	paintObjects();
	repaint();
    }
}
