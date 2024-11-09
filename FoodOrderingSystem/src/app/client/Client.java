package app.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Client extends Application{
    private double xoffset = 0;
    private double yoffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage window = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/client/views/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.initStyle(StageStyle.TRANSPARENT);
        window.show();

        root.setOnMousePressed(event -> {
            xoffset=event.getSceneX();
            yoffset=event.getSceneY();
        });
        root.setOnMouseDragged(e->{
            primaryStage.setX(e.getScreenX()-xoffset);
            primaryStage.setY(e.getScreenY()-yoffset);
        });

    }

    public static void main(String[] args) {
        System.out.println("\n\n ------------------ [ Client is running ::: Ready to send requests to the server! ] ------------------ \n\n");

        launch(args);
    }
}