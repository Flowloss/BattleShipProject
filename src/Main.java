import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;

public class Main extends Application {
    private boolean running = false; // ett stadie som tillåter dig att placera skepp medans denna är false
    private gameBoard enemyBoard, playerBoard; //olika spelarnas bräde
    private int shipsToPlace = 5;
    private boolean enemyTurn = false; //vems tur det är
    private Random random = new Random();
    //Skapar scenen där man lägger till allt innehåll
    private Parent createScene(){

        BorderPane root = new BorderPane();
        root.setPrefSize(1000, 1200);

        root.setRight(new Text("Right SideBar - Controls"));

        enemyBoard = new gameBoard(true, event -> {
            if (!running)
                return;

            gameBoard.Cell cell = (gameBoard.Cell) event.getSource();
            if (cell.wasShot)
                return;
            enemyTurn = !cell.shoot();

            if (enemyBoard.ships == 0) {
                System.out.println("You Win!");
            }

            if (enemyTurn)
                enemyMove();
        });
        playerBoard = new gameBoard(false, event -> {
            if (running)
                return;

            gameBoard.Cell cell = (gameBoard.Cell) event.getSource();
            if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y )) {
                if (--shipsToPlace == 0) {
                    startGame();
                }
            }
        });

        VBox vBox = new VBox(50, enemyBoard, playerBoard);
        vBox.setAlignment(Pos.CENTER);

        root.setCenter(vBox);

        return root;
    }
    //Gör så att enemy kan placera ut sina skepp. Detta sker randomly inom gridden
    private void enemyMove(){

        while (enemyTurn){
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            gameBoard.Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();

            if (playerBoard.ships == 0) {
                System.out.println("You Loose");
            }
        }
    }
    //startat skepp placering fasen och sedan spelet
    private void startGame(){

        int type = 5;

        while (type > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }
        running = true;
    }
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createScene());
        stage.setTitle("BattleShip");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
