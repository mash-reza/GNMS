package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.Menu;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Setting the screen to the center and its size
//        Screen screen = Screen.getPrimary();
//        Rectangle2D bounds = screen.getVisualBounds();
//        primaryStage.setX(bounds.getWidth() * 0.2);
//        primaryStage.setY(bounds.getHeight() * 0.1);
//        primaryStage.setWidth(bounds.getWidth() * 0.6);
//        primaryStage.setHeight(bounds.getHeight() * 0.8);
        Menu menu = new Menu();
        primaryStage.setTitle("سیستم مدیریت حساب گیم نت");
        primaryStage.setScene(new Scene(menu.getvBox()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
