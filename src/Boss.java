import javax.swing.*;
import java.awt.*;

public class Boss extends Rectangle
{
    public MoveDirection direction;
    private Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Boss.png"));
    public double speed = 1;
    public int lifes = 10;

    public Boss(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = 90;
        this.height = 80;
        direction = MoveDirection.South;
    }

    public void draw(Graphics g, Component c)
    {
        g.drawImage(img, this.x, this.y, c);
        return;
    }

    public void move(JFrame frame)
    {
        if (direction == MoveDirection.North) {
            y -= speed;
        } else if (direction == MoveDirection.South) {
            y += speed;
        } else if (direction == MoveDirection.West) {
            x -= speed;
        } else if (direction == MoveDirection.East) {
            x += speed;
        }
    }
}
