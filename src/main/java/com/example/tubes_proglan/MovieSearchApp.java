package com.example.tubes_proglan;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MovieSearchApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieSearch.fxml"));
        Parent root = loader.load();

        // Set the stage
        primaryStage.setTitle("Movie Search App");
        Scene sceneMain = new Scene(root, 600, 400);
        sceneMain.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(sceneMain);

        // Set controller
        MovieSearchController controller = loader.getController();
        // Add any initialization logic for the controller here

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
