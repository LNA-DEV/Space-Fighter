import jdk.nashorn.internal.ir.IfNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpaceInvadersMain extends JPanel implements Runnable, ActionListener, KeyListener
{
    JFrame frame;
    SpaceShip player;
    private Timer timer;
    private Thread GameThread;


    public static void main(String[] args)
    {
        new SpaceInvadersMain();
    }

    SpaceInvadersMain()
    {
        frame = new JFrame("SpaceInvaders");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        frame.add(this);
        frame.setVisible(true);

        player = new SpaceShip(100, 100);

        GameThread = new Thread(this);
        GameThread.start();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g); //super-Aufruf nicht vergessen

        g.setColor(Color.red);
        g.fillRect(player.x, player.y, player.width,player.height);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

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
            }
            catch (InterruptedException e) {}
            repaint();
        }
    }
}