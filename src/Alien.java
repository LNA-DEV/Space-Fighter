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
        img = 	Toolkit.getDefaultToolkit().getImage(getClass().getResource("Alien.gif"));
        this.x = x;
        this.y = y;
        this.width = 55;
        this.height = 40;
        direction = MoveDirection.South;
        speed = 1;
    }

    public void draw(Graphics g, Component c)
    {
        g.drawImage(img, this.x , this.y, c);
        return;
    }

    public void move(JFrame frame)
    {
        if (direction == MoveDirection.North)
        {
            y -= speed;
        }
        else if (direction == MoveDirection.South)
        {
            y += speed;
        }
        else if (direction == MoveDirection.West)
        {
            x -= speed;
        }
        else if (direction == MoveDirection.East)
        {
            x += speed;
        }
    }
}
