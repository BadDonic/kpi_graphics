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
        primaryStage.setTitle("Lab 1 Dzenik Danylo KP-62");
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 800);
        scene.setFill(Color.GRAY);

        Rectangle rectangle = new Rectangle(480, 400, 40, 350);
        root.getChildren().add(rectangle);

        Polygon triangleRed = new Polygon(500, 50, 280, 400, 720, 400);
        triangleRed.setFill(Color.RED);
        root.getChildren().add(triangleRed);

        Polygon triangleWhite = new Polygon(500, 100, 330, 373, 670, 373);
        triangleWhite.setFill(Color.WHITE);
        root.getChildren().add(triangleWhite);

        Circle circleRed = new Circle(500, 177,35);
        circleRed.setFill(Color.RED);
        root.getChildren().add(circleRed);

        Circle circleYellow = new Circle(500, 252,35);
        circleYellow.setFill(Color.YELLOW);
        root.getChildren().add(circleYellow);

        Circle circleGreen = new Circle(500, 327,35);
        circleGreen.setFill(Color.GREEN);
        root.getChildren().add(circleGreen);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}