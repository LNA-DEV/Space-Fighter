import javax.swing.*;
import java.awt.*;

public class Bullet extends Rectangle
{
    public MoveDirection direction;
    private Image img;
    public int speed;

    public Bullet(int x, int y)
    {
        img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Laser.png"));
        this.x = x + 10;
        this.y = y + 10;
        this.width = 20;
        this.height = 64;
        direction = MoveDirection.North;
        speed = 20;
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
