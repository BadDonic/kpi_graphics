import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lab 2 Dzenik Danylo KP-62");
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 800);
        scene.setFill(Color.GRAY);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}