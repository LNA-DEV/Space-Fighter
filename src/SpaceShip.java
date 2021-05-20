import javax.swing.*;
import java.awt.*;

public class SpaceShip extends Rectangle
{
    public int speed;
    public MoveDirection direction;
    public int Health = 3;
    private Image img;

    public SpaceShip(int x, int y)
    {
        img = 	Toolkit.getDefaultToolkit().getImage(getClass().getResource("Rocket.gif"));
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 80;
        direction = MoveDirection.Idle;
        speed = 13;
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
        direction = MoveDirection.Idle;
    }
}
