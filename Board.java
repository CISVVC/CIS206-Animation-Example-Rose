import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel
        implements ActionListener {

    private final int B_WIDTH = 720;
    private final int B_HEIGHT = 360;

    private final int X_ORG  = 0;
    private final int Y_ORG = B_HEIGHT/2;

    private final int X_RES = B_WIDTH/360;
    private final int Y_RES = 100;

    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 25;

    private final int RES = 150;

    private Image star;
    private Timer timer;
    private int x, y;
    private int deg;

    public Board() {
        initBoard();
    }


    private void initBoard() {

//        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        setDoubleBuffered(true);

        
        x = X_ORG;
        y = Y_ORG;
        deg = 0;
        
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRose(g,7.0/3.0,2);
        drawCircle(g);
    }

    private double r_x(double k,int theta) {
       return Math.cos(k*rad(theta)) * Math.cos(rad(theta));
    }
    private double r_y(double k,int theta) {
        return Math.cos(k*rad(theta)) * Math.sin(rad(theta));
    }
    private void drawRose(Graphics g,double k,int n) {
        // https://en.wikipedia.org/wiki/Rose_(mathematics)
        // x = cos(k * theta) * cos(theta)
        // y = cos(k * theta) * sine(theta)
        int X_O = B_WIDTH/2;
        int Y_O = B_HEIGHT/2;
        int period = 360;
        g.setColor(Color.magenta);
        int lastX = X_O+ (int)(r_x(k,0) * RES);
        int lastY = Y_O+ (int)(r_y(k,0) * RES);
        for(int theta =0; theta<period * n;theta++) {
            int nextX = X_O + (int)(r_x(k,theta) * RES);
            int nextY = Y_O + (int)(r_y(k,theta) * RES);
            g.drawLine(lastX, lastY, nextX, nextY);
            lastX = nextX;
            lastY = nextY;
        }
    }

    private void drawCircle(Graphics g) {

        g.setColor(Color.blue);
        g.fillOval(x,y,10,10);
/*
Synchronizes this toolkit's graphics state. Some window systems may do buffering of graphics events.
This method ensures that the display is up-to-date. It is useful for animation.
*/
        Toolkit.getDefaultToolkit().sync();
    }

    private double rad(double deg) {
        return deg * Math.PI / 180.0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int X_O = B_WIDTH/2;
        int Y_O = B_HEIGHT/2;
        deg++;
        if(deg > 360*2) {
            deg = 0;
        }
        x = X_O + (int)(r_x(7/3.0,deg) * RES);
        y= Y_O + (int)(r_y(7.0/3.0,deg) * RES);

        repaint();
    }

    public double toRadians(int deg) {
        return deg * Math.PI / 180.0;
    }
}
