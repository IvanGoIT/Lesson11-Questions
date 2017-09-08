import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JavaFXMultiThreadExample extends Application {
    public static boolean isProgramActive = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();

        final Button button = new Button("text");

        button.setTranslateX(10);
        button.setTranslateY(10);

        root.getChildren().addAll(button);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Thread thread = new Thread(() -> {
            while(isProgramActive) {
                final double x = button.getTranslateX() + 2;
                System.out.println("х=" + x);

                Platform.runLater(() -> {
                    button.setTranslateX(x);
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        button.setOnMouseClicked(event -> {
            thread.start();
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        isProgramActive = false;
    }
}