package src;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application{
    private double width = 200;
    private double height = 200;
    public static void main(String[] args) {
        launch( args);
        
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent rootPanel = new UI();
        final Scene scene = new Scene(rootPanel, width, height);
        stage.setTitle("AutoQueue");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        stage.resizableProperty().set(false);

        ImageFinder a = new ImageFinder();
        a.runner();
    }
}
