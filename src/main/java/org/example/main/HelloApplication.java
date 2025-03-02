package org.example.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.util.Duration;

import java.io.IOException;

/**
 * Main application class for the Firm Locator project.
 * This app features a main menu with a world map and different info sections.
 * It includes a fade effect to smoothly transition between elements when interacting with buttons.
 */
public class HelloApplication extends Application {

    /// --- Images
    private Image worldMapImage;

    ///// --- Shape Elements

    /// Rectangles
    private Rectangle worldMapRegionPickFrame;
    private StackPane mainMenuRectangle;
    private StackPane worldMapMainMenuImageFrame;
    private StackPane creditsInfoBox;
    private StackPane projectInfoBox;

    private StackPane activeRect; // Tracks which rectangle in the main menu is currently being displayed.

    /// Buttons
    private Button beginPlanningButton;
    private Button creditsButton;
    private Button projectInfoButton;

    /// Labels
    private Label titleLabel;

    /// ///--------------------------------------------------------------------------------------------------------/// ///
    /**
     * The entry point for the application.
     * Initializes the stage, scenes, buttons, and their corresponding actions.
     */
    @Override
    public void start(Stage stage) throws IOException {

        // Assigns image variables their images to be loaded.
        try {
            worldMapImage = new Image(getClass().getResource("/worldmap.png").toExternalForm());
        } catch (Exception e) {
            System.out.println("Error loading WORLDMAP image: " + e.getMessage());
        }

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

        // Stack of all StackPanes to show them in layers
        StackPane stack = new StackPane(projectInfoBox, creditsInfoBox, worldMapMainMenuImageFrame, mainMenuRectangle);
        stack.setAlignment(Pos.CENTER_RIGHT);
        stack.setPadding(new Insets(50));

        // Set opacities to have mainMenuRectangle be the default.
        activeRect = mainMenuRectangle; // Initialize the active rectangle
        setContentOpacity(worldMapMainMenuImageFrame, 0);
        setContentOpacity(creditsInfoBox, 0);
        setContentOpacity(projectInfoBox, 0);
        setContentOpacity(mainMenuRectangle, 1);

        // Initialize buttons
        beginPlanningButton = createButton("Begin Planning", worldMapMainMenuImageFrame, stack);
        creditsButton = createButton("Credits/Sources", creditsInfoBox, stack);
        projectInfoButton = createButton("Project Info.", projectInfoBox, stack);

        // Button Layout
        VBox leftAppElementsLayout = new VBox(90);
        leftAppElementsLayout.getChildren().addAll(beginPlanningButton, creditsButton, projectInfoButton);
        leftAppElementsLayout.setStyle("-fx-padding: 50; -fx-alignment: center-left; -fx-font-size: 50");
        leftAppElementsLayout.setAlignment(Pos.CENTER_LEFT);
        leftAppElementsLayout.setOnMouseExited(e -> mainMenuRectangle.toFront());

        // Fade into the default rectangle whenever a button is not being hovered over
        leftAppElementsLayout.setOnMouseExited(e -> fadeEffect(mainMenuRectangle));

        // Scene initialization
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

    /**
     * Helper method to create a StackPane containing a rectangle with a specified stroke color.
     * @param strokeColor The color of the rectangle's border.
     * @return A StackPane containing the rectangle.
     */
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

    /**
     * Helper method to create a StackPane containing a rectangle with a specified stroke color and an image.
     * @param strokeColor The color of the rectangle's border.
     * @param image The image to be displayed inside the rectangle.
     * @return A StackPane containing the rectangle and the image.
     */
    private StackPane createRectanglePane(Color strokeColor, Image image) {
        Rectangle rect = new Rectangle(600, 750, Color.IVORY);
        rect.setArcWidth(28);
        rect.setArcHeight(20);
        rect.setStrokeWidth(10);
        rect.setOpacity(1);
        rect.setStroke(strokeColor);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(590);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);

        StackPane container = new StackPane(rect, imageView);
        container.setAlignment(Pos.CENTER);

        return container;
    }

    /**
     * Helper method to create a StackPane containing a rectangle with a specified stroke color and a text label.
     * @param strokeColor The color of the rectangle's border.
     * @param text The text to be displayed inside the rectangle.
     * @param fontSize The font size for the text.
     * @return A StackPane containing the rectangle and the text label.
     */
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

    /**
     * Creates a button with a specified label and links it to a target rectangle for fade effect.
     * @param text The text to be displayed on the button.
     * @param targetRect The StackPane that will be shown when the button is hovered.
     * @param priorityRect The StackPane that will be prioritized when hovered over.
     * @return The created button.
     */
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
            fadeEffect(targetRect);
        });

        button.setAlignment(Pos.CENTER); // Attempt to center buttons
        return button;
    }

    /**
     * Applies a fade effect to the target StackPane, transitioning it smoothly between the active and target rectangles.
     * @param target The StackPane to which the fade effect will be applied.
     */
    private void fadeEffect(StackPane target) {
        if (target == activeRect) return; // Avoid redundant animations

        // Get references to active and target elements
        ImageView activeImage = getImageView(activeRect);
        Label activeText = getLabel(activeRect);
        ImageView targetImage = getImageView(target);
        Label targetText = getLabel(target);

        // Ensure target elements start at opacity 0 (before fading in)
        if (targetImage != null) targetImage.setOpacity(0);
        if (targetText != null) targetText.setOpacity(0);

        // Fade-out animation for current active elements
        Timeline fadeOut = new Timeline();
        if (activeImage != null) {
            fadeOut.getKeyFrames().add(new KeyFrame(Duration.millis(300), new KeyValue(activeImage.opacityProperty(), 0)));
        }
        if (activeText != null) {
            fadeOut.getKeyFrames().add(new KeyFrame(Duration.millis(300), new KeyValue(activeText.opacityProperty(), 0)));
        }

        // Fade-in animation for target elements
        Timeline fadeIn = new Timeline();
        if (targetImage != null) {
            fadeIn.getKeyFrames().add(new KeyFrame(Duration.millis(500), new KeyValue(targetImage.opacityProperty(), 1.0)));
        }
        if (targetText != null) {
            fadeIn.getKeyFrames().add(new KeyFrame(Duration.millis(500), new KeyValue(targetText.opacityProperty(), 1.0)));
        }

        // Ensure smooth transition: fade-in starts AFTER fade-out completes
        fadeOut.setOnFinished(e -> {
            // Bring target rectangle to front (so it’s visible)
            target.toFront();

            // Set the target elements to full opacity before fading in (prevents issues)
            if (targetImage != null) targetImage.setOpacity(0);
            if (targetText != null) targetText.setOpacity(0);

            // Update the active rectangle reference
            activeRect = target;

            // Start fade-in animation
            fadeIn.play();
        });

        fadeOut.play();
    }

    /**
     * Helper method to set the opacity of the content inside a StackPane.
     * @param rect The StackPane whose contents' opacity will be set.
     * @param opacity The opacity level to set.
     */
    private void setContentOpacity(StackPane rect, double opacity) {
        if (rect.getChildren().size() > 1 && rect.getChildren().get(1) instanceof ImageView) {
            ((ImageView) rect.getChildren().get(1)).setOpacity(opacity);
        }
        if (rect.getChildren().size() > 2 && rect.getChildren().get(2) instanceof StackPane) {
            Label text = (Label) ((StackPane) rect.getChildren().get(2)).getChildren().get(0);
            text.setOpacity(opacity);
        }
    }

    /**
     * Helper method to retrieve an ImageView from a StackPane.
     * @param rect The StackPane to search for an ImageView.
     * @return The ImageView found inside the StackPane, or null if not found.
     */
    private ImageView getImageView(StackPane rect) {
        for (Node node : rect.getChildren()) {
            if (node instanceof ImageView) {
                return (ImageView) node;
            }
        }
        return null; // No image found
    }

    /**
     * Helper method to retrieve a Label from a StackPane.
     * @param rect The StackPane to search for a Label.
     * @return The Label found inside the StackPane, or null if not found.
     */
    private Label getLabel(StackPane rect) {
        for (Node node : rect.getChildren()) {
            if (node instanceof StackPane) {
                for (Node innerNode : ((StackPane) node).getChildren()) {
                    if (innerNode instanceof Label) {
                        return (Label) innerNode;
                    }
                }
            }
        }
        return null; // No label found
    }

    public static void main(String[] args) {
        launch();
    }
}
