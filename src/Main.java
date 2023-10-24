import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private static final int CUBE = 50;

    @Override
    public void start(Stage Stage) throws Exception {
        Rectangle cube = new Rectangle(CUBE, CUBE);
        cube.setLocation(100, 100);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScene.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        String css = this.getClass().getResource("application.css").toExternalForm();

        scene.getStylesheets().add(css);
        Stage.setScene(scene);
        Stage.show();
    }
}
