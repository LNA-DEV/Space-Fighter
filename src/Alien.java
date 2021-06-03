import javax.swing.*;
import java.awt.*;

public class Alien extends Rectangle
{
    public AlienType type;
    public MoveDirection direction;
    private Image imgRed;
    private Image imgBlue;
    private Image imgYellow;
    public double speed;

    public Alien(int x, int y, AlienType alienType)
    {
        imgRed = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Alien.gif"));
        imgBlue = Toolkit.getDefaultToolkit().getImage(getClass().getResource("AlienBlue.png"));
        imgYellow = Toolkit.getDefaultToolkit().getImage(getClass().getResource("AlienYellow.png"));
        this.x = x;
        this.y = y;
        this.width = 55;
        this.height = 40;
        direction = MoveDirection.South;
        type = alienType;
        switch (type)
        {
            case Red:
                speed = 1;
                break;
            case Blue:
                speed = 2;
                break;
            case Yellow:
                speed = 1;
                direction = MoveDirection.East;
                this.width = 40;
                this.height = 55;
                break;
        }
    }

    public void draw(Graphics g, Component c)
    {
        switch (type)
        {
            case Red:
                g.drawImage(imgRed, this.x , this.y, c);
            break;
            case Blue:
                g.drawImage(imgBlue,this.x,this.y,c);
                break;
            case Yellow:
                g.drawImage(imgYellow,this.x,this.y,c);
                break;
        }

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
