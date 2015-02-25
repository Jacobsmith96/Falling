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
    public enum state{
	CLASSIC, SPRING
    }

    /**
     * Default constructor for the Sim class
     */
    public Sim() {
	myImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
	myBuffer = myImage.createGraphics();
	running = false; // Sets the game to not running
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
	if (running) {
	    myBuffer.setColor(Color.BLACK);
	    myBuffer.fillRect(0, 0, 800, 800);
	    for (Circle i : circles) {
		myBuffer.setColor(Color.YELLOW);
		i.paintCircle(myBuffer);
	    }
	} else {
	    myBuffer.setColor(Color.WHITE);
	    myBuffer.setFont(new Font("Arial", Font.PLAIN, 20));
	    myBuffer.drawString("Start? Y/N", 340, 400);

	}
    }

    public void reset() { // Restarts the program if the player chooses to do so
	timer.start();
	running = true;
	repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
	if (!keys.contains(key))
	    keys.add(key); // Adds the newly pressed key to the keys arrayList'
	if (keys.contains(KeyEvent.VK_Y)) {
	    if (!running) {
		reset();
	    }
	}
	if (keys.contains(KeyEvent.VK_N)) {
	    if (!running) {
		System.exit(0);
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
	paintObjects();
	repaint();
    }

}
