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
    Boss boss;
    int Points;
    boolean GameRunning = false;
    private Timer timer;
    private Thread GameThread;
    AudioPlayer BossPlayer;
    Random random = new Random();

    public static void main(String[] args)
    {
        new SpaceFighterMain();
    }

    SpaceFighterMain()
    {
        frame = new JFrame("Space-Fighter");
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().height / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height - 75);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.DARK_GRAY);
        frame.add(this);
        frame.setVisible(true);
        frame.setResizable(false);

        gameFont = new Font("Microsoft Gothic UI", 0, 30);

        background = Toolkit.getDefaultToolkit().getImage(getClass().getResource("SpaceBackground.png"));

        player = new SpaceShip(frame.getSize().width / 2, frame.getSize().height - 120);

        healthBar = new HealthBar(player.Health);

        try {
            BossPlayer = new AudioPlayer("./Resources/Soundtrack/BossSpirit.wav");

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

        Random random = new Random();
        timer = new Timer(1200, this);

        try {
            AudioPlayer audioPlayer = new AudioPlayer("./Resources/Soundtrack/SpaceSound.wav");
            audioPlayer.play();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

        GameThread = new Thread(this);
        GameThread.start();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(background, 0, -40, this);

        g.setColor(Color.white);
        g.setFont(gameFont);
        g.drawString("Points: " + Points, 10, 100);

        player.draw(g, this);

        healthBar.draw(g, this);

        if (boss != null) {
            boss.draw(g, this);
        }

        Enumeration<Alien> AlienEnum = aliens.elements();
        while (AlienEnum.hasMoreElements()) {
            Alien alien = AlienEnum.nextElement();
            alien.draw(g, this);
        }
        Enumeration<Bullet> BulletsEnum = bullets.elements();
        while (BulletsEnum.hasMoreElements()) {
            Bullet bullet = BulletsEnum.nextElement();
            bullet.draw(g, this);
        }

        g.drawString("HighScore: " + HighScoreManager.LoadScoreFromFile(), 10, 150);

        if (player.Health == 0) {
            g.setColor(Color.CYAN);
            g.drawString("Press ENTER to start", 10, 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int randomX = random.nextInt(frame.getSize().width - 50);
        int randomY = random.nextInt(frame.getSize().height - 80);
        while (randomY < frame.getSize().height - 350) {
            randomY = random.nextInt(frame.getSize().height - 80);
        }
        int y = -10;

        if (random.nextInt(100) < 30) {
            Alien x = new Alien(randomX, y, AlienType.Blue);
            x.speed = x.speed + Points / 15;
            aliens.addElement(x);
        } else if (random.nextInt(100) < 30) {
            Alien x = new Alien(-10, randomY, AlienType.Yellow);
            aliens.addElement(x);
        } else {
            Alien x = new Alien(randomX, y, AlienType.Red);
            x.speed = x.speed + Points / 15;
            aliens.addElement(x);
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_D && GameRunning == true ||
                e.getKeyCode() == KeyEvent.VK_RIGHT && GameRunning == true) {
            player.direction = MoveDirection.East;
        }
        if (e.getKeyCode() == KeyEvent.VK_A && GameRunning == true ||
                e.getKeyCode() == KeyEvent.VK_LEFT && GameRunning == true) {
            player.direction = MoveDirection.West;
        }
        if (e.getKeyCode() == KeyEvent.VK_W && GameRunning == true ||
                e.getKeyCode() == KeyEvent.VK_UP && GameRunning == true) {
            player.direction = MoveDirection.North;
        }
        if (e.getKeyCode() == KeyEvent.VK_S && GameRunning == true ||
                e.getKeyCode() == KeyEvent.VK_DOWN && GameRunning == true) {
            player.direction = MoveDirection.South;
        }
        player.move(frame);

        if (e.getKeyCode() == KeyEvent.VK_SPACE && GameRunning == true && bullets.size() <= 3) {
            bullets.add(new Bullet(player.x, player.y));
            try {
                AudioPlayer audioPlayer = new AudioPlayer("./Resources/Soundtrack/Laser.wav");
                audioPlayer.playOnce();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER && GameRunning == false) {
            GameRunning = true;
            player.Health = 3;
            Points = 0;
            boss = null;
            BossPlayer.pause();
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
        while (true) {
            try {
                Thread.sleep(10);

                if (GameRunning) {
                    healthBar.Health = player.Health;
                    if (player.Health == 0) {
                        GameRunning = false;
                        timer.stop();
                    }

                    if (Points % 50 == 0 && Points != 0) {
                        int randomX = random.nextInt(frame.getSize().width - 50);
                        boss = new Boss(randomX, -100);
                    }

                    if (boss != null) {
                        boss.move(frame);
                        BossPlayer.play();
                    }

                    Enumeration<Alien> AlienEnum = aliens.elements();
                    while (AlienEnum.hasMoreElements()) {
                        Alien alien = AlienEnum.nextElement();
                        alien.move(frame);
                        if (CheckCollision(player, alien)) {
                            aliens.remove(alien);
                            player.Health -= 1;
                            try {
                                AudioPlayer audioPlayer = new AudioPlayer("./Resources/Soundtrack/Hit.wav");
                                audioPlayer.playOnce();
                            } catch (Exception ex) {
                                System.out.println("Error with playing sound.");
                                ex.printStackTrace();
                            }
                        }

                        if (boss != null && CheckCollision(player, boss))
                        {
                            boss = null;
                            player.Health -= 2;
                            BossPlayer.pause();
                        }

                        if (boss != null && boss.y > frame.getSize().height - 80)
                        {
                            boss = null;
                            player.Health -= 3;
                            BossPlayer.pause();
                        }

                        //Delete aliens if they hit the screen
                            if (alien.y > frame.getSize().height - 80) {
                            aliens.remove(alien);
                            player.Health--;
                            try {
                                AudioPlayer audioPlayer = new AudioPlayer("./Resources/Soundtrack/Hit.wav");
                                audioPlayer.playOnce();
                            } catch (Exception ex) {
                                System.out.println("Error with playing sound.");
                                ex.printStackTrace();
                            }
                        }
                        if (alien.x > frame.getSize().width) {
                            aliens.remove(alien);
                        }
                    }

                    Enumeration<Bullet> BulletsEnum = bullets.elements();
                    while (BulletsEnum.hasMoreElements()) {
                        Bullet bullet = BulletsEnum.nextElement();
                        bullet.move(frame);

                        //Delete bullets if they hit the screen
                        if (bullet.y > frame.getSize().height || bullet.y < 0) {
                            bullets.remove(bullet);
                            Points--;
                        }

                        if (boss != null && CheckCollision(bullet, boss)) {
                            boss.lifes--;
                            bullets.remove(bullet);
                            if (boss.lifes == 0) {
                                boss = null;
                                BossPlayer.pause();
                                Points += 15;
                            }
                            try {
                                AudioPlayer audioPlayer = new AudioPlayer("./Resources/Soundtrack/EnemyHit.wav");
                                audioPlayer.playOnce();
                            } catch (Exception ex) {
                                System.out.println("Error with playing sound.");
                                ex.printStackTrace();
                            }
                        }

                        //Collision detection
                        Enumeration<Alien> AlienEnumeration = aliens.elements();
                        while (AlienEnumeration.hasMoreElements()) {
                            Alien alien = AlienEnumeration.nextElement();
                            if (CheckCollision(alien, bullet)) {
                                aliens.remove(alien);
                                bullets.remove(bullet);
                                Points++;
                                try {
                                    AudioPlayer audioPlayer = new AudioPlayer("./Resources/Soundtrack/EnemyHit.wav");
                                    audioPlayer.playOnce();
                                } catch (Exception ex) {
                                    System.out.println("Error with playing sound.");
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                    HighScoreManager.newScore(Points);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            repaint();
        }
    }

    private boolean CheckCollision(Rectangle rect1, Rectangle rect2)
    {
        if (rect1.intersects(rect2)) {
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