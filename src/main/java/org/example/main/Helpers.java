package org.example.main;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Helpers {

    /**
     * Helper method to create a StackPane containing a rectangle with a specified stroke color.
     * @param strokeColor The color of the rectangle's border.
     * @return A StackPane containing the rectangle.
     */
    public static StackPane createRectanglePane(Color strokeColor) {
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
    public static StackPane createRectanglePane(Color strokeColor, Image image) {
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
    public static StackPane createRectanglePane(Color strokeColor, String text, int fontSize) {
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
    public static Button createButton(String text, StackPane targetRect, StackPane priorityRect, StackPane activeRect) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: rgb(255, 255, 240); " +
                "-fx-background-radius: 14; " +
                "-fx-border-color: black;" +
                "-fx-border-width: 10; " +
                "-fx-padding: 10px 20px;" +
                "-fx-font-size: 50px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-radius: 5;");

        button.setOnMouseEntered(e -> { // Display a different element when the button is hovered over.
            fadeEffect(targetRect, activeRect);
        });



        button.setAlignment(Pos.CENTER); // Attempt to center buttons
        return button;
    }


    /**
     * Applies a fade effect to the target StackPane, transitioning it smoothly between the active and target rectangles.
     * @param target The StackPane to which the fade effect will be applied.
     */
    public static void fadeEffect(StackPane target, StackPane activeRect) {
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
            fadeOut.getKeyFrames().add(new KeyFrame(Duration.millis(150), new KeyValue(activeImage.opacityProperty(), 0)));
        }
        if (activeText != null) {
            fadeOut.getKeyFrames().add(new KeyFrame(Duration.millis(150), new KeyValue(activeText.opacityProperty(), 0)));
        }

        // Fade-in animation for target elements
        Timeline fadeIn = new Timeline();
        if (targetImage != null) {
            fadeIn.getKeyFrames().add(new KeyFrame(Duration.millis(250), new KeyValue(targetImage.opacityProperty(), 1.0)));
        }
        if (targetText != null) {
            fadeIn.getKeyFrames().add(new KeyFrame(Duration.millis(250), new KeyValue(targetText.opacityProperty(), 1.0)));
        }

        // Ensure smooth transition: fade-in starts AFTER fade-out completes
        fadeOut.setOnFinished(e -> {
            // Bring target rectangle to front (so it’s visible)
            target.toFront();

            // Set the target elements to full opacity before fading in (prevents issues)
            if (targetImage != null) targetImage.setOpacity(0);
            if (targetText != null) targetText.setOpacity(0);

            // Update the active rectangle reference
            Main.setActiveRect(target);

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
    public static void setContentOpacity(StackPane rect, double opacity) {
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
    public static ImageView getImageView(StackPane rect) {
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
    public static Label getLabel(StackPane rect) {
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

    public static Button createButtonAt(StackPane pane, String text, double x, double y, Color textColor) {
        Button button = new Button(text);
        button.setTranslateX(x);
        button.setTranslateY(y);
        button.setTextFill(textColor);
        setStyleTheme(button);
        pane.getChildren().add(button);
        StackPane.setAlignment(button, Pos.TOP_LEFT);

        return button;
    }

    public static void setStyleTheme(Button button) {
        button.setStyle("-fx-background-color: ivory; " +
                "-fx-background-radius: 14; " +
                "-fx-border-color: black;" +
                "-fx-border-width: 5; " +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-radius: 5;");
    }




    /**
     * Performs a fade transition when switching between scenes.
     * This method fades out the current scene, switches to the new scene, and then fades it in.
     *
     * @param currentRoot The root node of the current scene that will fade out.
     * @param newScene The new scene to switch to after the fade-out.
     * @param newRoot The root node of the new scene that will fade in.
     * @param stage The primary stage where the scene transition occurs.
     */
    public static void fadeTransitionToScene(Parent currentRoot, Scene newScene, Parent newRoot, Stage stage) {
        // Fade out effect for the current scene
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            // Switch scene after fade-out
            stage.setScene(newScene);

            // Fade-in effect for the new scene
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }


    public static Label createHeaderLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-background-color: ivory; " +
                "-fx-background-radius: 14; " +
                "-fx-border-color: black;" +
                "-fx-border-width: 5; " +
                "-fx-font-size: 50px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-radius: 5;");

        return label;
    }

    public static Button createBackButtonLayout(Circle circleAsset, Image backImage) {
        circleAsset = new Circle(20);
        circleAsset.setFill(new ImagePattern(backImage));
        Button button = new Button();
        button.setGraphic(circleAsset); // Sets the image of the back button in scene 2 to the back button image.
        button.setStyle("-fx-background-color:transparent; -fx-border-color: transparent; -fx-padding: 67px 150");
//        button.setMinSize(30, 30);
//        button.setMaxSize(30, 30);
        button.setAlignment(Pos.TOP_LEFT);



        return button;
    }

    // Forward Button Addition into the next scene to remove redundant code.
    public static void addForwardButtonAsChild(Button button, StackPane intoRoot) {
        if (!intoRoot.getChildren().contains(button)) {
            intoRoot.getChildren().add(button);
            StackPane.setAlignment(button, Pos.CENTER_RIGHT);
        }
    }

    public static void addBackButtonAsChild(Button button, StackPane intoRoot) {
        if (!intoRoot.getChildren().contains(button)) {
            intoRoot.getChildren().add(button);
            StackPane.setAlignment(button, Pos.TOP_LEFT);
        }
    }

}
