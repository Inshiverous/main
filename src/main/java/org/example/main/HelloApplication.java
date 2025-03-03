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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.print.attribute.standard.MediaSize;
import java.io.IOException;
import java.util.Stack;

/**
 * Main application class for the Firm Locator project.
 * This app features a main menu with a world map and different info sections.
 * It includes a fade effect to smoothly transition between elements when interacting with buttons.
 */
public class HelloApplication extends Application {

    /// --- Images
    private Image worldMapImage;
    private Image backImage;

    private Image northAmericaImage;
    private Image centralAmericaImage;
    private Image southAmericaImage;
    private Image europeImage;
    private Image asiaImage;
    private Image africaImage;
    private Image oceaniaImage;
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

    // Back buttons
    private Button backButtonInScene2;

    // Region Pick Scene

    private Button NAButton;
    private Button CAButton; // Central America
    private Button SAButton;
    private Button EUButton;
    private Button AFButton;
    private Button ASButton;
    private Button OCButton;

    /// Labels
    // Main Menu Scene (scene1)
//    private Label titleLabel;

    // Region Pick Scene (scene2)
    private Label regionPickGuideLabel;

    /// Circles
    private Circle backCircle;

    /// ///--------------------------------------------------------------------------------------------------------/// ///
    /**
     * The entry point for the application.
     * Initializes the stage, scenes, buttons, and their corresponding actions.
     */
    @Override
    public void start(Stage stage) throws IOException {

        // Assigns image variables their images to be loaded.
        worldMapImage = getImage("worldmap.png");
        backImage = getImage("back.jpg");

        northAmericaImage = getImage("NABlank.png");
        centralAmericaImage = getImage("CABlank.png");
        southAmericaImage = getImage("SABlank.png");
        europeImage = getImage("EUBlank.jpeg");
        asiaImage = getImage("ASBlank.png");
        africaImage = getImage("AFBlank.png");
        oceaniaImage = getImage("OCBlank.jpeg");

        // Creates shapes & assigns their images

        // region picking scene
        worldMapRegionPickFrame = new Rectangle(1000, 497);
        worldMapRegionPickFrame.setFill(new ImagePattern(worldMapImage));

        // Back Button Circle
        backCircle = new Circle(20);
        backCircle.setFill(new ImagePattern(backImage));

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

        /// Main Menu Scene initialization

        HBox rootMainMenu = new HBox(50, leftAppElementsLayout, stack);
        Scene scene1 = new Scene(rootMainMenu, 1280, 960);



        /// Back Buttons

        // Back Button In Scene 2 (Region Select Scene)
        backButtonInScene2 = new Button();
        backButtonInScene2.setGraphic(backCircle); // Sets the image of the back button in scene 2 to the back button image.
        backButtonInScene2.setStyle("-fx-background-color:transparent; -fx-border-color: transparent; -fx-padding: 67px 150");
        backButtonInScene2.setAlignment(Pos.TOP_LEFT);
        // Back Button In Scene 3


        /// Region Pick Scene
        regionPickGuideLabel = new Label("Where do you want your firm?");
        regionPickGuideLabel.setStyle("-fx-background-color: ivory; " +
                "-fx-background-radius: 14; " +
                "-fx-border-color: black;" +
                "-fx-border-width: 5; " +
                "-fx-font-size: 50px;" +
                "-fx-font-weight: bold;");



        VBox regionPickLayout = new VBox(10, worldMapRegionPickFrame);
        regionPickLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
        regionPickLayout.setAlignment(Pos.CENTER);

        StackPane rootRegionPick = new StackPane();
        rootRegionPick.getChildren().addAll(backButtonInScene2, regionPickGuideLabel, regionPickLayout);
        StackPane.setAlignment(backButtonInScene2,Pos.TOP_LEFT);
        // Scene Buttons
        NAButton = createButtonAt(rootRegionPick, "NA", 250, 300, Color.BLUE);
        NAButton.setTextFill(Color.BLUE);

        CAButton = createButtonAt(rootRegionPick, "CA", 250, 400, Color.PURPLE);
        CAButton.setTextFill(Color.PURPLE);

        SAButton = createButtonAt(rootRegionPick, "SA", 350, 525, Color.RED);
        SAButton.setTextFill(Color.RED);

        EUButton = createButtonAt(rootRegionPick, "EU", 575, 300, Color.GREEN);
        EUButton.setTextFill(Color.GREEN);

        AFButton = createButtonAt(rootRegionPick, "AF", 565, 410, Color.ORANGE);
        AFButton.setTextFill(Color.ORANGE);

        ASButton = createButtonAt(rootRegionPick, "AS", 800, 325, Color.BROWN);
        ASButton.setTextFill(Color.BROWN);

        OCButton = createButtonAt(rootRegionPick, "OC", 925, 560, Color.CYAN);
        OCButton.setTextFill(Color.CYAN);





        StackPane.setAlignment(regionPickGuideLabel, Pos.TOP_CENTER);
        StackPane.setMargin(regionPickGuideLabel, new Insets(50, 0, 0, 0));

        regionPickLayout.toBack();
        Scene scene2 = new Scene(rootRegionPick, 1280, 960);


        /// Region Scenes

        // North America Scene

        // South America Scene

        // Europe Scene

        // Africa Scene

        // Asia Scene

        // Oceania Scene


        beginPlanningButton.setOnMouseClicked(e -> {
            stage.setScene(scene2);
        });

        backButtonInScene2.setOnMouseClicked(e -> {
            stage.setScene(scene1);
        });

        // Final Touches & Scene shown
        stage.setTitle("Firm Locator");
        stage.setScene(scene1);

        stage.setWidth(1280);
        stage.setHeight(960);
        stage.setResizable(false);

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
                "-fx-font-weight: bold;" +
                "-fx-border-radius: 5;");

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

    private static void setStyleTheme(Button button) {
        button.setStyle("-fx-background-color: ivory; " +
                "-fx-background-radius: 14; " +
                "-fx-border-color: black;" +
                "-fx-border-width: 5; " +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-radius: 5;");
    }


    /**
     * Helper method to create an image object from a file.
     * @param fileName The target file name for the image to use.
     * @return The image that was created from the file name.
     */    private Image getImage(String fileName) {
        Image image = null;
        try { // Image that displays a map of the Earth.
            image = new Image(getClass().getResource("/"+fileName).toExternalForm());
        } catch (Exception e) {
            System.out.println("Error loading" + fileName + "image: " + e.getMessage());
        }
        return image;
    }

    public static void main(String[] args) {
        launch();
    }
}
