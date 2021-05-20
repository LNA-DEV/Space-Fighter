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
    HealthBar healthBar;
    Vector<Alien> aliens = new Vector<>();
    Vector<Bullet> bullets = new Vector<>();
    Image background;
    Font gameFont;
    int Points;
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
        this.setBackground(Color.DARK_GRAY);
        frame.add(this);
        frame.setVisible(true);

        gameFont = new Font("Microsoft Gothic UI", 0, 30);

        background = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Background.gif"));

        player = new SpaceShip(frame.getSize().width / 2, frame.getSize().height - 120);

        healthBar = new HealthBar(player.Health);

        timer = new Timer(2000, this);
        timer.start();

        GameThread = new Thread(this);
        GameThread.start();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this);

        g.setColor(Color.white);
        g.setFont(gameFont);
        g.drawString("Points: " + Points, 325, 50);

        player.draw(g, this);

        healthBar.draw(g, this);

        Enumeration<Alien> AlienEnum = aliens.elements();
        while (AlienEnum.hasMoreElements())
        {
            Alien alien = AlienEnum.nextElement();
            alien.draw(g, this);
        }
        Enumeration<Bullet> BulletsEnum = bullets.elements();
        while (BulletsEnum.hasMoreElements())
        {
            Bullet bullet = BulletsEnum.nextElement();
            bullet.draw(g, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Random random = new Random();
        int randomX = random.nextInt(frame.getSize().width - 50);
        int y = -10;
        Alien x = new Alien(randomX, y);
        aliens.addElement(x);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_D)
        {
            player.direction = MoveDirection.East;
        }
        else if (e.getKeyCode() == KeyEvent.VK_A)
        {
            player.direction = MoveDirection.West;
        }
        else if (e.getKeyCode() == KeyEvent.VK_W)
        {
            player.direction = MoveDirection.North;
        }
        else if (e.getKeyCode() == KeyEvent.VK_S)
        {
            player.direction = MoveDirection.South;
        }
        player.move(frame);

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            bullets.add(new Bullet(player.x, player.y));
        }
    }

    @Override
    public void run()
    {
        frame.requestFocusInWindow();
        frame.addKeyListener(this);
        while (true)
        {
            try
            {
                Thread.sleep(20);

                healthBar.Health = player.Health;

                Enumeration<Alien> AlienEnum = aliens.elements();
                while (AlienEnum.hasMoreElements()) {
                    Alien alien = AlienEnum.nextElement();
                    alien.move(frame);
                    if (CheckCollision(player, alien))
                    {
                        aliens.remove(alien);
                        player.Health -= 1;
                    }

                    //Delete aliens if they hit the bottom of the screen
                    if (alien.y > frame.getSize().height)
                    {
                        aliens.remove(alien);
                        player.Health--;
                    }
                }

                Enumeration<Bullet> BulletsEnum = bullets.elements();
                while (BulletsEnum.hasMoreElements())
                {
                    Bullet bullet = BulletsEnum.nextElement();
                    bullet.move(frame);

                    //Delete bullets if they hit the screen
                    if (bullet.y > frame.getSize().height || bullet.y < 0)
                    {
                        bullets.remove(bullet);
                    }

                    //Collision detection
                    Enumeration<Alien> AlienEnumeration = aliens.elements();
                    while (AlienEnumeration.hasMoreElements())
                    {
                        Alien alien = AlienEnumeration.nextElement();
                        if (CheckCollision(alien, bullet))
                        {
                            aliens.remove(alien);
                            bullets.remove(bullet);
                            Points++;
                        }
                    }
                }
                if (player.Health == 0)
                {
                    System.exit(0);
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            repaint();
        }
    }

    private boolean CheckCollision(Rectangle rect1, Rectangle rect2)
    {
        if (rect1.intersects(rect2))
        {
            return true;
        }
        return false;
    }






    //------------------------------------------------------------------------------






    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }
}