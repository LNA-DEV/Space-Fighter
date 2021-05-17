import javax.swing.*;
import java.awt.*;

public class SpaceShip extends Rectangle
{
    public int speed;
    public MoveDirection direction;

    public SpaceShip(int x, int y)
    {
        //This bezieht sich auf die Klasse und ansonsten wird das x aus der Methode verwendet
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        direction = MoveDirection.Idle;
        speed = 5;
    }

    public void move(JFrame frame)
    {
        if (direction == MoveDirection.North && y >= 10)
        {
            y -= speed;
        }
        else if (direction == MoveDirection.South && y <= frame.getSize().height - 100)
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
