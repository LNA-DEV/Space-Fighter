import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;

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
        for (int i = 10; i <= (Health * 60) - 10; i += 60)
        {
            g.drawImage(img, i , 10, c);
        }
        return;
    }
}
