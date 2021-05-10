import java.awt.*;

public class SpaceShip extends Rectangle
{
    public int speed;
    public int direction;

    public SpaceShip(int x, int y)
    {
        //This bezieht sich auf die Klasse und ansonsten wird das x aus der Methode verwendet
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
    }
}
