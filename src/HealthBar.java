import java.awt.*;

public class HealthBar extends Rectangle
{
    private Image img;
    public int Health;

    HealthBar(int health)
    {
        Health = health;
        img = 	Toolkit.getDefaultToolkit().getImage(getClass().
                getResource("Heart.png"));
    }

    public void draw(Graphics g, Component c)
    {
        if (Health >= 1)
        {
            g.drawImage(img, 10 , 10, c);
        }
        if (Health >= 2)
        {
            g.drawImage(img, 70 , 10, c);
        }
        if (Health == 3)
        {
            g.drawImage(img, 130 , 10, c);
        }
        return;
    }
}
