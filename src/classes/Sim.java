package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * @author Jacob_000
 * The Class handling the behavior of the simulator
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
     * Default constructor for the Sim class
     */
    public Sim() {
	myImage =  new BufferedImage(800,800,BufferedImage.TYPE_INT_RGB);//Creates image for double buffering
	myBuffer = myImage.createGraphics();//Gets the graphics from the new image
	running = false; //Sets the game to not running
	timer = new Timer(20, this); //Creates a new timer
	addKeyListener(this);//Sets up the key listeners
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Program terminates on exit
    }

    /**
     * Called when the program should start running
     */
    public void start(){
	running = true;
	timer.start();
    }
    /**
     * Paints every tick of the timer and draws the double buffering image all at once to prevents 
     * flashing of the graphics
     */
    public void paint(Graphics g)
    {
	g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
    }
    
    public void create(){ //Sets up the JFrame and calls run() to start the program
	setSize(800,800);
	repaint();
	show();
	start();
}
    @Override
    public void keyPressed(KeyEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub

    }

}
