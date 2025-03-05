package org.example.main;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

import javax.print.attribute.standard.MediaSize;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
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
    private StackPane mainMenuRectangle;
    private StackPane worldMapMainMenuImageFrame;
    private StackPane creditsInfoBox;
    private StackPane projectInfoBox;

    // Maps
    private Rectangle worldMapRegionPickFrame;
    private Rectangle NAMap;
    private Rectangle CAMap;
    private Rectangle SAMap;
    private Rectangle EUMap;
    private Rectangle ASMap;
    private Rectangle AFMap;
    private Rectangle OCMap;

    private StackPane activeRect; // Tracks which rectangle in the main menu is currently being displayed.

    /// Buttons
    private Button beginPlanningButton;
    private Button creditsButton;
    private Button projectInfoButton;

    // Back buttons
    private Button backButtonToMainMenu;
    private Button backButtonToWorldMap;

    private Button backButtonToPrevRegionSR;

    // Forward Buttons
    private Button forwardButtonToScoreResults;

    // Region Pick Scene

    private Button NAButton;
    private Button CAButton; // Central America
    private Button SAButton;
    private Button EUButton;
    private Button AFButton;
    private Button ASButton;
    private Button OCButton;

    /// Labels
    // Main Menu Scene

    // Firm Picking Scene
    private Label firmPickerGuideLabel;

    // Region Pick Scene
    private Label regionPickGuideLabel;

    // Map Scenes
    private Label NAGuideLabel;
    private Label SAGuideLabel;
    private Label CAGuideLabel;
    private Label EUGuideLabel;
    private Label ASGuideLabel;
    private Label AFGuideLabel;
    private Label OCGuideLabel;

    private Label SRGuideLabel;

    /// Circles
    ///
    /// ASK CHATGPT HOW TO FLIP AN IMAGE FOR A BUTTON THAT GOES INTO THE SCORE RESULTS PAGE.

    // Back Circle Assets
    private Circle backCircleMM; // to go back to main menu

    private Circle backCircleFP; // to go back to firm picker

    private Circle backCircle;

    // to go back to region picker
    private Circle backCircleNA;
    private Circle backCircleCA;
    private Circle backCircleSA;
    private Circle backCircleEU;
    private Circle backCircleAF;
    private Circle backCircleAS;
    private Circle backCircleOC;

    // to go back to region, an enum will account for the region to go back to.
    private Circle backCircleSR;


    // Forward Circle Assets
    private Circle forwardCircleSR;

    /// Styles

    private final Color IVORY = Color.rgb(255,255,240);
    private final Color DARK_IVORY = Color.rgb(180,180,169);

    /// ENUMS

    // These two account for if the pin is placed or not, and if the animation for the pin being placed was executed.
    Boolean pinPlaced = false;
    Boolean pinPlacedAnimationExecuted = false;

    // These two account for the region whenever the user goes back from score results to the map in the region they picked.

    Scene intoScene;
    StackPane intoRoot;

    /// ///--------------------------------------------------------------------------------------------------------/// ///
    /**
     * The entry point for the application.
     * Initializes the stage, scenes, buttons, and their corresponding actions.
     */
    @Override
    public void start(Stage stage) throws IOException {


            // Assigns image variables their images to be loaded.
            worldMapImage = getImage("WMBlank.png");
            backImage = getImage("back.jpg");

            northAmericaImage = getImage("NABlank.jpg");
            centralAmericaImage = getImage("CABlank.jpg");
            southAmericaImage = getImage("SABlank.jpg");
            europeImage = getImage("EUBlank.jpg");
            asiaImage = getImage("ASBlank.jpg");
            africaImage = getImage("AFBlank.jpg");
            oceaniaImage = getImage("OCBlank.jpg");

            /// Create shapes & assign their images

            // 3 : 4 or 4 : 3 (x200)
            NAMap = new Rectangle(800, 600);
            NAMap.setFill(new ImagePattern(northAmericaImage));

            CAMap = new Rectangle(800, 600);
            CAMap.setFill(new ImagePattern(centralAmericaImage));

            SAMap = new Rectangle(800, 600);
            SAMap.setFill(new ImagePattern(southAmericaImage));

            EUMap = new Rectangle(800, 600);
            EUMap.setFill(new ImagePattern(europeImage));

            ASMap = new Rectangle(800, 600);
            ASMap.setFill(new ImagePattern(asiaImage));

            AFMap = new Rectangle(800, 600);
            AFMap.setFill(new ImagePattern(africaImage));

            OCMap = new Rectangle(800, 600);
            OCMap.setFill(new ImagePattern(oceaniaImage));


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
                                                                   "With healthy cooperation & participation in the market, shortages would\n fall and the automotive industry would " +
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

            // Initialize Main Menu Butons
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
            Scene mainMenuScene = new Scene(rootMainMenu, 1280, 960);



            /// Back Buttons

            // Back Button In Region Select Scene
            backButtonToMainMenu = createBackButtonLayout(backCircleMM);

            // Back Button In Map Scenes

            backButtonToWorldMap = createBackButtonLayout(backCircle);

            backButtonToPrevRegionSR = createBackButtonLayout(backCircleSR);

            forwardButtonToScoreResults = createBackButtonLayout(forwardCircleSR);
            forwardButtonToScoreResults.setScaleX(-1); // Flip image horizontally

            /// Region Pick Scene
            regionPickGuideLabel = createHeaderLabel("Where do you want your firm?");

            VBox regionPickLayout = new VBox(10, worldMapRegionPickFrame);
            regionPickLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            regionPickLayout.setAlignment(Pos.CENTER);

            StackPane rootRegionPick = new StackPane();
            rootRegionPick.getChildren().addAll(backButtonToMainMenu, regionPickGuideLabel, regionPickLayout);
            StackPane.setAlignment(backButtonToMainMenu,Pos.TOP_LEFT);

            // Region Scene Buttons
            NAButton = createButtonAt(rootRegionPick, "NA", 250, 300, Color.BLUE);
            CAButton = createButtonAt(rootRegionPick, "CA", 250, 400, Color.PURPLE);
            SAButton = createButtonAt(rootRegionPick, "SA", 350, 525, Color.RED);
            EUButton = createButtonAt(rootRegionPick, "EU", 575, 300, Color.GREEN);
            AFButton = createButtonAt(rootRegionPick, "AF", 565, 410, Color.ORANGE);
            ASButton = createButtonAt(rootRegionPick, "AS", 800, 325, Color.BROWN);
            OCButton = createButtonAt(rootRegionPick, "OC", 925, 560, Color.DARKCYAN);

            StackPane.setAlignment(regionPickGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(regionPickGuideLabel, new Insets(50, 0, 0, 0));

            regionPickLayout.toBack();
            Scene regionPickScene = new Scene(rootRegionPick, 1280, 960);


            /// Region Scenes
            String regionScenesHeaderLabel = "Place your firm wherever!";

            // North America Scene

            NAGuideLabel = createHeaderLabel(regionScenesHeaderLabel);

            VBox NALayout = new VBox(10, NAMap);
            NALayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            NALayout.setAlignment(Pos.CENTER);

            StackPane rootNAScene = new StackPane();
            rootNAScene.getChildren().addAll(NAGuideLabel, NALayout);

            StackPane.setAlignment(NAGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(NAGuideLabel, new Insets(50, 0, 0, 0));

            NALayout.toBack();
            Scene NAScene = new Scene(rootNAScene, 1280, 960);


            // Central America Scene
            CAGuideLabel = createHeaderLabel(regionScenesHeaderLabel);

            VBox CALayout = new VBox(10, CAMap);
            CALayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            CALayout.setAlignment(Pos.CENTER);

            StackPane rootCAScene = new StackPane();
            rootCAScene.getChildren().addAll(CAGuideLabel, CALayout);

            StackPane.setAlignment(CAGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(CAGuideLabel, new Insets(50, 0, 0, 0));

            CALayout.toBack();
            Scene CAScene = new Scene(rootCAScene, 1280, 960);


            // South America Scene
            SAGuideLabel = createHeaderLabel(regionScenesHeaderLabel);

            VBox SALayout = new VBox(10, SAMap);
            SALayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            SALayout.setAlignment(Pos.CENTER);

            StackPane rootSAScene = new StackPane();
            rootSAScene.getChildren().addAll(SAGuideLabel, SALayout);

            StackPane.setAlignment(SAGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(SAGuideLabel, new Insets(50, 0, 0, 0));

            SALayout.toBack();
            Scene SAScene = new Scene(rootSAScene, 1280, 960);

            // Europe Scene

            EUGuideLabel = createHeaderLabel(regionScenesHeaderLabel);

            VBox EULayout = new VBox(10, EUMap);
            EULayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            EULayout.setAlignment(Pos.CENTER);

            StackPane rootEUScene = new StackPane();
            rootEUScene.getChildren().addAll(EUGuideLabel, EULayout);

            StackPane.setAlignment(EUGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(EUGuideLabel, new Insets(50, 0, 0, 0));

            EULayout.toBack();
            Scene EUScene = new Scene(rootEUScene, 1280, 960);

            // Africa Scene

            AFGuideLabel = createHeaderLabel(regionScenesHeaderLabel);

            VBox AFLayout = new VBox(10, AFMap);
            AFLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            AFLayout.setAlignment(Pos.CENTER);

            StackPane rootAFScene = new StackPane();
            rootAFScene.getChildren().addAll(AFGuideLabel, AFLayout);

            StackPane.setAlignment(AFGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(AFGuideLabel, new Insets(50, 0, 0, 0));

            AFLayout.toBack();
            Scene AFScene = new Scene(rootAFScene, 1280, 960);

            // Asia Scene

            ASGuideLabel = createHeaderLabel(regionScenesHeaderLabel);

            VBox ASLayout = new VBox(10, ASMap);
            ASLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            ASLayout.setAlignment(Pos.CENTER);

            StackPane rootASIAScene = new StackPane();
            rootASIAScene.getChildren().addAll(ASGuideLabel, ASLayout);

            StackPane.setAlignment(ASGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(ASGuideLabel, new Insets(50, 0, 0, 0));

            ASLayout.toBack();
            Scene ASIAScene = new Scene(rootASIAScene, 1280, 960);

            // Oceania Scene

            OCGuideLabel = createHeaderLabel(regionScenesHeaderLabel);

            VBox OCLayout = new VBox(10, OCMap);
            OCLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            OCLayout.setAlignment(Pos.CENTER);

            StackPane rootOCScene = new StackPane();
            rootOCScene.getChildren().addAll(OCGuideLabel, OCLayout);

            StackPane.setAlignment(OCGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(OCGuideLabel, new Insets(50, 0, 0, 0));

            OCLayout.toBack();
            Scene OCScene = new Scene(rootOCScene, 1280, 960);


            // Score Results
            SRGuideLabel = createHeaderLabel("Score Results:");

            VBox SRLayout = new VBox(10);
            SRLayout.setAlignment(Pos.CENTER);

            StackPane rootSRScene = new StackPane();
            rootSRScene.getChildren().addAll(backButtonToPrevRegionSR, SRGuideLabel, SRLayout);

            StackPane.setAlignment(backButtonToPrevRegionSR, Pos.CENTER_LEFT);
            StackPane.setAlignment(SRGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(SRGuideLabel, new Insets(50, 0, 0, 0));

            SRLayout.toBack();
            Scene SRScene = new Scene(rootSRScene, 1280, 960);


            /// Mouse Events




            backButtonToWorldMap.setOnMouseClicked(e -> {
                intoScene = null;
                intoRoot = null;
                fadeTransitionToScene(stage.getScene().getRoot(), regionPickScene, rootRegionPick, stage);
                System.out.println(intoScene);
                System.out.println(intoRoot);

            });


            beginPlanningButton.setOnMouseClicked(e -> fadeTransitionToScene(rootMainMenu, regionPickScene, rootRegionPick, stage));
            backButtonToMainMenu.setOnMouseClicked(e -> fadeTransitionToScene(rootRegionPick, mainMenuScene, rootMainMenu, stage));

            NAButton.setOnMouseClicked(e ->{fadeTransitionToScene(rootRegionPick, NAScene, rootNAScene, stage); intoScene = NAScene; intoRoot = rootNAScene; addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot); addBackButtonAsChild(backButtonToWorldMap, intoRoot);});
            SAButton.setOnMouseClicked(e -> {fadeTransitionToScene(rootRegionPick, SAScene, rootSAScene, stage); intoScene = SAScene; intoRoot = rootSAScene; addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot); addBackButtonAsChild(backButtonToWorldMap, intoRoot);});
            CAButton.setOnMouseClicked(e -> {fadeTransitionToScene(rootRegionPick, CAScene, rootCAScene, stage); intoScene = CAScene; intoRoot = rootCAScene; addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot); addBackButtonAsChild(backButtonToWorldMap, intoRoot);});
            EUButton.setOnMouseClicked(e -> {fadeTransitionToScene(rootRegionPick, EUScene, rootEUScene, stage); intoScene = EUScene; intoRoot = rootEUScene; addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot); addBackButtonAsChild(backButtonToWorldMap, intoRoot);});
            ASButton.setOnMouseClicked(e -> {fadeTransitionToScene(rootRegionPick, ASIAScene, rootASIAScene, stage); intoScene = ASIAScene; intoRoot = rootASIAScene; addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot); addBackButtonAsChild(backButtonToWorldMap, intoRoot);});
            AFButton.setOnMouseClicked(e -> {fadeTransitionToScene(rootRegionPick, AFScene, rootAFScene, stage); intoScene = AFScene; intoRoot = rootAFScene; addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot); addBackButtonAsChild(backButtonToWorldMap, intoRoot);});
            OCButton.setOnMouseClicked(e -> {fadeTransitionToScene(rootRegionPick, OCScene, rootOCScene, stage); intoScene = OCScene; intoRoot = rootOCScene; addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot); addBackButtonAsChild(backButtonToWorldMap, intoRoot);});

            forwardButtonToScoreResults.setOnMouseClicked(e -> {fadeTransitionToScene(intoRoot, SRScene, rootSRScene, stage);});
            backButtonToPrevRegionSR.setOnMouseClicked(e -> {fadeTransitionToScene(rootSRScene, intoScene, intoRoot, stage);});


            // Hovering over the region map reveals a pin & the circle radius.
            enablePinTracking(NAMap, rootNAScene);
            enablePinTracking(CAMap, rootCAScene);
            enablePinTracking(SAMap, rootSAScene);
            enablePinTracking(EUMap, rootEUScene);
            enablePinTracking(AFMap, rootAFScene);
            enablePinTracking(ASMap, rootASIAScene);
            enablePinTracking(OCMap, rootOCScene);

            /// Stage
            // Final Touches & Scene shown
            stage.setTitle("Firm Locator");
            stage.setScene(mainMenuScene);

            stage.setWidth(1280);
            stage.setHeight(960);
            stage.setResizable(false);

            stage.show();
    }




    private void enablePinTracking(Rectangle rect, StackPane root) {

        pinPlaced = false;


        // Load the pin image
        ImageView pin = new ImageView(new Image("pin.png")); // Ensure this path is correct
        pin.setFitWidth(40); // Adjust size as needed
        pin.setFitHeight(40);
        pin.setVisible(false); // Initially hidden


        Circle circle = new Circle();
        circle.setRadius(40);
        circle.setFill(Color.BLUE);
        circle.setOpacity(0.5);
        circle.setStrokeWidth(3);
        circle.setStroke(Color.BLACK);
        circle.setVisible(false);


        root.getChildren().addAll(circle, pin);
        // Mouse entered event
        rect.setOnMouseEntered(event -> {
            if (!pinPlaced || !pinPlacedAnimationExecuted) {
                pin.setVisible(true);  // Make pin visible when mouse enters
                circle.setVisible(true);
                updatePinAndCirclePosition(event.getX(), event.getY(), pin, circle, pinPlaced);
            }
        });

        // Mouse exited event
        rect.setOnMouseExited(event -> {
            if (!pinPlaced || !pinPlacedAnimationExecuted) {
                pin.setVisible(false);  // Hide pin when mouse exits
                circle.setVisible(false);
            }
        });

        // Mouse moved event
        rect.setOnMouseMoved(event -> {
            if (!pinPlaced || !pinPlacedAnimationExecuted) {
                // Ensure the UI update happens on the JavaFX application thread
                Platform.runLater(() -> updatePinAndCirclePosition(event.getX(), event.getY(), pin, circle, pinPlaced));
            }
        });

        rect.setOnMouseClicked(event -> {
            pinPlaced = true;
            placePinAnimation(pin, circle);
            if (root.getChildren().size() == 5) {
                root.getChildren().remove(1);
            }

        });
    }

    private void updatePinAndCirclePosition(double mouseX, double mouseY, ImageView pin, Circle circle, boolean pinPlaced) {
        // This method is called from Platform.runLater to ensure thread safety

        // Calculate the new position of the pin relative to the map
        double newX = mouseX - 400;  // Adjust to center the pin over the mouse
        double newY = mouseY - 280;  // Adjust to center the pin over the mouse

        // Update the pin's position by setting translateX and translateY to move it

        if (!pinPlaced) { // if the pin is not placed, move the pin with the mouse.
            pin.setTranslateX(newX);
            pin.setTranslateY(newY);
            circle.setTranslateX(newX);
            circle.setTranslateY(newY+60);
        }

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
        button.setStyle("-fx-background-color: rgb(255, 255, 240); " +
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

    /**
     * Performs a fade transition when switching between scenes.
     * This method fades out the current scene, switches to the new scene, and then fades it in.
     *
     * @param currentRoot The root node of the current scene that will fade out.
     * @param newScene The new scene to switch to after the fade-out.
     * @param newRoot The root node of the new scene that will fade in.
     * @param stage The primary stage where the scene transition occurs.
     */
    private void fadeTransitionToScene(Parent currentRoot, Scene newScene, Parent newRoot, Stage stage) {
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


    private Label createHeaderLabel(String text) {
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

    private Button createBackButtonLayout(Circle circleAsset) {
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

    private void placePinAnimation(ImageView pin, Circle circle) {

        if (!pinPlacedAnimationExecuted) {


            // Get circle center coordinates
            double circleX = circle.getTranslateX();
            double circleY = circle.getTranslateY();

            System.out.println("Circle center coords: " + circleX + ", " + circleY);
            System.out.println("Initial pin coords: " + pin.getTranslateX() + ", " + pin.getTranslateY());


            // Move pin to the center of the circle; Quadratic speed
            Path path = new Path();
            path.getElements().add(new MoveTo(pin.getTranslateX()+20, pin.getTranslateY()));
            path.getElements().add(new QuadCurveTo(pin.getTranslateX()+20, pin.getTranslateY() - 80, circleX+20, circleY));

            PathTransition moveToCircle = new PathTransition(Duration.seconds(1), path, pin);


            moveToCircle.play();

            pinPlacedAnimationExecuted = true;
        }

    }



    // Forward Button Addition into the next scene to remove redundant code.
    private void addForwardButtonAsChild(Button button, StackPane intoRoot) {
        if (!intoRoot.getChildren().contains(button)) {
            intoRoot.getChildren().add(button);
            StackPane.setAlignment(button, Pos.CENTER_RIGHT);
        }
    }

    private void addBackButtonAsChild(Button button, StackPane intoRoot) {
        if (!intoRoot.getChildren().contains(button)) {
            intoRoot.getChildren().add(button);
            StackPane.setAlignment(button, Pos.TOP_LEFT);
        }
    }


    public static void main(String[] args) {
        launch();
    }
}

