import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

public class SpaceFighterMain extends JPanel implements Runnable, ActionListener, KeyListener
{
    JFrame frame;
    SpaceShip player;
    HealthBar healthBar;
    Vector<Alien> aliens = new Vector<>();
    Vector<Bullet> bullets = new Vector<>();
    Image background;
    Font gameFont;
    int Points;
    boolean GameRunning = false;
    private Timer timer;
    private Thread GameThread;

    public static void main(String[] args)
    {
        new SpaceFighterMain();
    }

    SpaceFighterMain()
    {
        frame = new JFrame("Space-Fighter");
        frame.setSize(500, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.DARK_GRAY);
        frame.add(this);
        frame.setVisible(true);
        frame.setResizable(false);

        gameFont = new Font("Microsoft Gothic UI", 0, 30);

        background = Toolkit.getDefaultToolkit().getImage(getClass().getResource("SpaceBackground.png"));

        player = new SpaceShip(frame.getSize().width / 2, frame.getSize().height - 120);

        healthBar = new HealthBar(player.Health);

        timer = new Timer(2000, this);

        GameThread = new Thread(this);
        GameThread.start();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(background, 0, -40, this);

        g.setColor(Color.white);
        g.setFont(gameFont);
        g.drawString("Points: " + Points, 325, 50);

        if (player.Health == 0)
        {
            g.setColor(Color.CYAN);
            g.drawString("Press ENTER to start the Game",25,500);
        }

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
        if (e.getKeyCode() == KeyEvent.VK_D&& GameRunning == true)
        {
            player.direction = MoveDirection.East;
        }
        if (e.getKeyCode() == KeyEvent.VK_A&& GameRunning == true)
        {
            player.direction = MoveDirection.West;
        }
        if (e.getKeyCode() == KeyEvent.VK_W&& GameRunning == true)
        {
            player.direction = MoveDirection.North;
        }
        if (e.getKeyCode() == KeyEvent.VK_S&& GameRunning == true)
        {
            player.direction = MoveDirection.South;
        }
        player.move(frame);

        if (e.getKeyCode() == KeyEvent.VK_SPACE&& GameRunning == true)
        {
            bullets.add(new Bullet(player.x, player.y));
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER && GameRunning == false)
        {
            GameRunning = true;
            player.Health= 3;
            Points = 0;
            aliens.clear();
            timer.start();
            repaint();
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

                if (GameRunning)
                {
                    healthBar.Health = player.Health;

                    if (player.Health == 0)
                    {
                        GameRunning = false;
                        timer.stop();
                    }

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
                        if (alien.y > frame.getSize().height -150)
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