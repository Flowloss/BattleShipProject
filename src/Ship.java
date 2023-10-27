import javafx.scene.Parent;

public class Ship extends Parent{
    public int shipLenght;
    public boolean vertical = true;
    private int health;

    public Ship(int shipLenght, boolean vertical){
        //alla skepps längd, health är beroende av skeppets längd tex (5x1 skeppet har 5 health osv)
        this.shipLenght = shipLenght;
        this.vertical = vertical;
        health = shipLenght;
    }
    public void hitShip(){
        //hit metod som tar bort health
        health--;
    }
    public boolean isAlive(){
        //kollar om skeppet lever returnerar när skeppet nått 0 health
        return health > 0;
    }

}
