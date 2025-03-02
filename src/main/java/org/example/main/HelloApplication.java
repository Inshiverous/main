package org.example.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import java.io.IOException;

public class HelloApplication extends Application {

    /// --- Images
    private Image worldMapImage;

    /// --- Shape Elements
    private Rectangle worldMapFrame;

    ///* --- Start Method
    ///
    ///
    ///
    ///
    ///
    ///  --- Start Method
    @Override
    public void start(Stage stage) throws IOException {

        // Assigns image variables their images to be loaded.

        try {
            worldMapImage = new Image(getClass().getResource("/worldmap.png").toExternalForm());
        } catch (Exception e) {
            System.out.println("Error loading WORLDMAP image: " + e.getMessage());
        }

        /// --------------------------------------------

        // Creates shapes & assigns their images
        worldMapFrame = new Rectangle(1000, 497);
        worldMapFrame.setFill(new ImagePattern(worldMapImage));

        // App layout

        VBox layout = new VBox(10, worldMapFrame);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().add(layout);

        Scene scene = new Scene(root, 1280, 960);
        root.setStyle("-fx-background-color: lightgray;");


        // Final Touches & Scene shown
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}