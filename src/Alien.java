import javax.swing.*;
import java.awt.*;

public class Alien extends Rectangle
{
    public int type;
    public MoveDirection direction;
    private Image img;
    public int speed;

    public Alien(int x, int y)
    {
        img = 	Toolkit.getDefaultToolkit().getImage(getClass().
                getResource("Alien.gif"));
        //This bezieht sich auf die Klasse und ansonsten wird das x aus der Methode verwendet
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        direction = MoveDirection.South;
        speed = 2;
    }

    public void draw(Graphics g, Component c)
    {
        g.drawImage(img, this.x , this.y, c);
        return;
    }

    public void move(JFrame frame)
    {
        if (direction == MoveDirection.North && y >= 10)
        {
            y -= speed;
        }
        else if (direction == MoveDirection.South && y <= frame.getSize().height - 125)
        {
            y += speed;
        }
        else if (direction == MoveDirection.West && x >= 10)
        {
            x -= speed;
        }
        else if (direction == MoveDirection.East && x <= frame.getSize().width - 75)
        {
            x += speed;
        }
    }
}
