import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

public class gameBoard extends Parent {
    private VBox rows = new VBox();
    private boolean enemy = false;
    public int ships = 5;

    private boolean isValidPointToPlace(Point2D point) {
        return isValidPointToPlace(point.getX(), point.getY());
    }
    private boolean isValidPointToPlace(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y <10;
    }

    // sätter up gameboard och säger hur många "cells" vi vill ha verticalt och horizontalt
    public gameBoard(boolean enemy, EventHandler<? super MouseEvent> handler){

        this.enemy = enemy;
        for (int y = 0; y < 10; y++){
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }
    //gör så man kan placera skepp i x och y led
    public boolean placeShip(Ship ship, int x, int y) {

        if (canPlaceShip(ship, x, y)) {
            int lenght = ship.shipLenght;

            if (ship.vertical){
                //kollar så att det finns plats att lägga skeppet verticalt där vi vill
                for (int i = y; i < y + lenght; i++){
                    Cell cell = getCell(x, i);
                    cell.ship = ship;
                    if (!enemy){
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }
            }
            else {
                //kollar så att det finns plats att lägga skeppet Horizontalt där vi vill
                for (int i = x; i < x + lenght; i++){
                    Cell cell = getCell(i, y);
                    cell.ship = ship;
                    if (!enemy) {
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }
            }
            return true;
        }
        return false;
    }

    //tar in koordinaterna x y och returnerar en cell från en 2d struktur
    public Cell getCell(int x, int y){
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    //neighbor metoden säger om det går att placera ett skepp bredvid, alltså att man ska ha en cell emellan
    private Cell[] getNeighbors(int x, int y){
        //Point2D är ett sätt att lagra positioner, en klass som används för att hantera 2D punkter
    Point2D[] points = new Point2D[] {
            new Point2D(x - 1,y),
            new Point2D(x + 1,y),
            new Point2D(x, y - 1),
            new Point2D(x, y + 1)
    };

    List<Cell> neighbors = new ArrayList<Cell>();

    for (Point2D p : points){
        if (isValidPointToPlace(p)) {
            neighbors.add(getCell((int)p.getX(), (int)p.getY()));
        }
    }
    return neighbors.toArray(new Cell[0]);
}

    // jobbar med getNeighbors för att tala om att det går att placera ut ett skepp
    public boolean canPlaceShip(Ship ship, int x, int y){
        int lenght = ship.shipLenght;
        boolean vertical = ship.vertical;

        if (vertical){
            for (int i = y; i < y + lenght; i++){
                if (!isValidPointToPlace(x, i))
                    return false;

                Cell cell = getCell(x, i);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getNeighbors(x, i)){
                    if (!isValidPointToPlace(x,i))
                    return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }
        else {
            for (int i = x; i < x + lenght; i++){
                if (!isValidPointToPlace(i, y))
                    return false;

                Cell cell = getCell(i, y);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getNeighbors(i, y)) {
                    if (!isValidPointToPlace(i, y))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }
        return vertical;
    }

    public class Cell extends Rectangle {
        public int x, y;
        public Ship ship = null;
        public boolean wasShot = false;
        private gameBoard board;

        public Cell(int x, int y, gameBoard board) {
            //grafiska koden för gameboard där vi ger den cell storlek och färg
            super(30,30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.BLACK);
        }
        public boolean shoot(){
            //skjut metod som även kollar ifall man själv blev skjuten och kollar om ditt skepp lever

            wasShot = true;
            setFill(Color.BLACK);

            if (ship != null) {
                ship.hitShip();
                setFill(Color.RED);
                if (!ship.isAlive()) {
                    board.ships--;
                }
                return true;
            }
            return false;
        }
    }
}

