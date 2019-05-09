import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Sun extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws FileNotFoundException {
    Group root = new Group();
    Scene scene = new Scene(root, 1000, 200);

    Image image = new Image(new FileInputStream("lab3/img/sun.png"));

    ImageView imageView = new ImageView(image);

    imageView.setX(50);
    imageView.setY(25);

    imageView.setFitHeight(150);
    imageView.setFitWidth(150);

    imageView.setPreserveRatio(true);
    root.getChildren().add(imageView);

    int cycleCount = 2; //
    int time = 4000;

    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time), root);
    scaleTransition.setToX(2);
    scaleTransition.setToY(2);
    scaleTransition.setCycleCount(cycleCount);
    scaleTransition.setAutoReverse(true);

    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(time), root);
    translateTransition.setFromX(50);
    translateTransition.setToX(750);
    translateTransition.setCycleCount(cycleCount + 2);
    translateTransition.setAutoReverse(true);

    RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
    rotateTransition.setByAngle(180f);
    rotateTransition.setCycleCount(cycleCount);
    rotateTransition.setAutoReverse(true);

    ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(time), root);
    scaleTransition2.setToX(0.5);
    scaleTransition2.setToY(0.5);
    scaleTransition2.setCycleCount(cycleCount);
    scaleTransition2.setAutoReverse(true);

    ParallelTransition parallelTransition = new ParallelTransition();
    parallelTransition.getChildren().addAll(
        translateTransition,
        scaleTransition,
        rotateTransition,
        scaleTransition2
    );

    parallelTransition.setCycleCount(Timeline.INDEFINITE);
    parallelTransition.play();

    primaryStage.setTitle("Lab3");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
