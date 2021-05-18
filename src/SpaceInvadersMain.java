import jdk.nashorn.internal.ir.IfNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

public class SpaceInvadersMain extends JPanel implements Runnable, ActionListener, KeyListener
{
    JFrame frame;
    SpaceShip player;
    Vector<Alien> aliens = new Vector<Alien>();
    private Timer timer;
    private Thread GameThread;


    public static void main(String[] args)
    {
        new SpaceInvadersMain();
    }

    SpaceInvadersMain()
    {
        frame = new JFrame("SpaceInvaders");
        frame.setSize(500, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        frame.add(this);
        frame.setVisible(true);

        player = new SpaceShip(frame.getSize().width / 2, frame.getSize().height - 120);

        timer = new Timer(2000, this);
        timer.start();

        GameThread = new Thread(this);
        GameThread.start();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g); //super-Aufruf nicht vergessen

        player.draw(g,this);

        Enumeration<Alien> AlienEnum = aliens.elements();
        while(AlienEnum.hasMoreElements())
        {
            Alien alien = AlienEnum.nextElement();
            alien.draw(g,this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Random random = new Random();
        int randomX = random.nextInt(frame.getSize().width);
        int y = -10;
        Alien x = new Alien(randomX, y);
        aliens.addElement(x);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            player.direction = MoveDirection.East;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            player.direction = MoveDirection.West;
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            player.direction = MoveDirection.North;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            player.direction = MoveDirection.South;
        }
        player.move(frame);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    @Override
    public void run()
    {
        frame.requestFocusInWindow();
        frame.addKeyListener(this);
        while(true)
        {
            try
            {
                Thread.sleep(20);
                Enumeration<Alien> AlienEnum = aliens.elements();
                while(AlienEnum.hasMoreElements())
                {
                    Alien alien = AlienEnum.nextElement();
                    if (alien.y > frame.getSize().height)
                    {
                        aliens.remove(alien);
                    }
                    alien.move(frame);
                }
            }
            catch (InterruptedException e) {}
            repaint();
        }
    }
}