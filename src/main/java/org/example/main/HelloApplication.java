package org.example.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import java.io.IOException;
import java.util.Stack;

public class HelloApplication extends Application {

    /// --- Images
    private Image worldMapImage;

    ///// --- Shape Elements


    /// Rectangles
        // regionPickScene
    private Rectangle worldMapRegionPickFrame;
    private StackPane mainMenuRectangle;
    private StackPane worldMapMainMenuImageFrame;
    private StackPane creditsInfoBox;
    private StackPane projectInfoBox;

    /// Buttons
        // mainMenuScene
    private Button beginPlanningButton;
    private Button creditsButton;
    private Button projectInfoButton;

    /// Labels
        // mainMenuScene
    private Label titleLabel;




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



        // region picking scene
        worldMapRegionPickFrame = new Rectangle(1000, 497);
        worldMapRegionPickFrame.setFill(new ImagePattern(worldMapImage));

        ////---- App Layout

        // Main Menu

        worldMapMainMenuImageFrame = createRectanglePane(Color.BLACK, worldMapImage);
        creditsInfoBox = createRectanglePane(Color.BLACK, "This project was made by\nThe Holy Trinity of Coding\n" +
                                                             "a team consisting of Levi Daniel, Aeden Kramer, and Jon Cheever." +

                                                             "\n\nOur project is inspired by the\nhighlighted problem reported by\n" +
                                                             "JustAuto (just-auto.com)\n\n"+

                                                             "We got our firm locations from resource maps\nposted by semiconductors.org and hcss.nl", 20);


        projectInfoBox = createRectanglePane(Color.BLACK, "This project addresses the widespread, present problem of \n" +
                                                              "resource shortages in automotive industry, ranging from raw materials, \n" +
                                                              "to semiconductors to the manufacturers of car parts.\n\n"+
                                                              "This project exists to encourage participation of new firms by displaying \n" +
                                                              "an array of firms to help supply the vertical stages to automotive production. \n\n" +
                                                              "With participation in the market, shortages would fall and the automotive industry would\n" +
                                                              "skyrocket & boom in market value.", 20);


        mainMenuRectangle = createRectanglePane(Color.BLACK, "THE FIRM STARTUP", 60);


        StackPane stack = new StackPane(projectInfoBox, creditsInfoBox, worldMapMainMenuImageFrame, mainMenuRectangle);
        stack.setAlignment(Pos.CENTER_RIGHT);
        stack.setPadding(new Insets(50));

        beginPlanningButton = createButton("Begin Planning", worldMapMainMenuImageFrame, stack);
        creditsButton = createButton("Credits/Sources", creditsInfoBox, stack);
        projectInfoButton = createButton("Project Info.", projectInfoBox, stack);

        // Make sure this rectangle is showing by default.
        mainMenuRectangle.toFront();




        VBox leftAppElementsLayout = new VBox(90);
        leftAppElementsLayout.getChildren().addAll(beginPlanningButton, creditsButton, projectInfoButton);
        leftAppElementsLayout.setStyle("-fx-padding: 50; -fx-alignment: center-left; -fx-font-size: 50");
        leftAppElementsLayout.setAlignment(Pos.CENTER_LEFT);
        leftAppElementsLayout.setOnMouseExited(e -> mainMenuRectangle.toFront());


        HBox rootMainMenu = new HBox(50, leftAppElementsLayout, stack);

        Scene scene1 = new Scene(rootMainMenu, 1280, 960);


        // Region Pick Scene
        VBox regionPickLayout = new VBox(10, worldMapRegionPickFrame);
        regionPickLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
        regionPickLayout.setAlignment(Pos.CENTER);

        StackPane rootRegionPick = new StackPane();
        rootRegionPick.getChildren().add(regionPickLayout);

        Scene scene2 = new Scene(rootRegionPick, 1280, 960);
        rootRegionPick.setStyle("-fx-background-color: lightgray;");


        // Final Touches & Scene shown
        stage.setTitle("Firm Locator");
        stage.setScene(scene1);
        stage.show();
    }

    // Helper method to create similar rectangles of the style of the app to declutter code.

    private StackPane createRectanglePane(Color strokeColor) {
        Rectangle rect = new Rectangle(600, 750, Color.IVORY);
        rect.setArcWidth(28);
        rect.setArcHeight(20);
        rect.setStrokeWidth(10);
        rect.setOpacity(1);
        rect.setStroke(strokeColor);

        StackPane container = new StackPane(rect);
        container.setAlignment(Pos.CENTER_RIGHT);

        return container;
    }

    // Same Helper Method but with the option to have an image inside of it.
    private StackPane createRectanglePane(Color strokeColor, Image image) {
        Rectangle rect = new Rectangle(600, 750, Color.IVORY);
        rect.setArcWidth(28);
        rect.setArcHeight(20);
        rect.setStrokeWidth(10);
        rect.setOpacity(1);
        rect.setStroke(strokeColor);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(580);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);


        StackPane container = new StackPane(rect, imageView);
        container.setAlignment(Pos.CENTER);

        return container;
    }

    private StackPane createRectanglePane(Color strokeColor, String text, int fontSize) {
        StackPane container = createRectanglePane(strokeColor);

        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        label.setTextFill(Color.BLACK);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setWrapText(true);
        label.setMaxWidth(580);
        label.setStyle("-fx-background-color: rgba(255,255,255,0); -fx-border-radius: 5;");

        StackPane textContainer = new StackPane(label);
        textContainer.setAlignment(Pos.CENTER);
        textContainer.setMaxWidth(600);
        textContainer.setMaxHeight(600);

        container.getChildren().add(textContainer);

        return container;
    }

    private Button createButton(String text, StackPane targetRect, StackPane priorityRect) {
        Button button = new Button(text);

        button.setStyle("-fx-background-color: ivory; " +
                "-fx-background-radius: 14; " +
                "-fx-border-color: black;" +
                "-fx-border-width: 10; " +
                "-fx-padding: 10px 20px;" +
                "-fx-font-size: 50px;" +
                "-fx-font-weight: bold;");

        button.setOnMouseEntered(e -> { // Display a different element when the button is hovered over.
            targetRect.toFront();
        });

        return button;
    }
    public static void main(String[] args) {
        launch();
    }
}