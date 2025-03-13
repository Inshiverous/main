package org.example.main;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.shape.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Main application class for the Firm Locator project.
 * This app features a main menu with a world map and different info sections.
 * It includes a fade effect to smoothly transition between elements when interacting with buttons.
 */
public class Main extends Application {

    ///// --- Shape Elements

    /// Rectangles
    private StackPane mainMenuRectangle;


    public static StackPane activeRect; // Tracks which rectangle in the main menu is currently being displayed.

    private Button backButtonToWorldMap;

    // Forward Buttons
    private Button forwardButtonToScoreResults;

    // Region Pick Scene

    /// Circles
    ///
    /// ASK CHATGPT HOW TO FLIP AN IMAGE FOR A BUTTON THAT GOES INTO THE SCORE RESULTS PAGE.

    // Back Circle Assets
    private Circle backCircleMM; // to go back to main menu

    private Circle backCircle;



    // to go back to region, an enum will account for the region to go back to.
    private Circle backCircleSR;


    // Forward Circle Assets
    private Circle forwardCircleSR;
    private Circle forwardCircleToMM;





    /// Styles

    private final Color IVORY = Color.rgb(255,255,240);
    private final Color DARK_IVORY = Color.rgb(180,180,169);

    private static final Insets backButtonPadding = new Insets(67,0,0,150);
    private static final Insets forwardButtonPadding = new Insets(0,0,0,150);

    /// ENUMS

    // These two account for if the pin is placed or not, and if the animation for the pin being placed was executed.
    public static Boolean pinPlaced = false;
    public static Boolean pinPlacedAnimationExecuted = false;

    // These two account for the region whenever the user goes back from score results to the map in the region they picked.
    Scene intoScene;
    StackPane intoRoot;

    public static int currentRegionIndex = 0;
    public static String currentResourceType = "";
    public static int currentResourceTypeIndex = 0;
    public static String currentRegion = "";

    public static Integer score = 0;


    /// Regions & Their Firms
    public static int[][] NAResources = Algorithms.parseFile("NA.txt");
    public static int[][] CAResources = Algorithms.parseFile("CA.txt");
    public static int[][] SAResources = Algorithms.parseFile("SA.txt");
    public static int[][] EUResources = Algorithms.parseFile("EU.txt");
    public static int[][] AFResources = Algorithms.parseFile("AF.txt");
    public static int[][] ASResources = Algorithms.parseFile("AS.txt");
    public static int[][] OCResources = Algorithms.parseFile("OC.txt");

    public ArrayList<int[][]> regionResources = new ArrayList<>();

    public static final String[] resourceTypes = {"Iron","Aluminum","Plastic","Rubber","Propylene","Sand", "Copper", "Lead", "Zinc", "Textiles"};
    public static final String[] regionsList = {"North America", "Central America", "South America", "Europe", "Africa", "Asia", "Oceania"};


    /// MUTATOR METHODS

    /**
     * Mutator method that allows the Helpers class to modify activeRect to properly run portions of its code.
     * @param active the current element set that is to be shown in the main menu
     */
    public static void setActiveRect(StackPane active) {
        activeRect = active;
    }

    public static void setCurrentRegion(int region) {
        currentRegionIndex = region;
        currentRegion = regionsList[region];
        currentResourceTypeIndex = (int) (Math.random() * 10);
        currentResourceType = resourceTypes[currentResourceTypeIndex];
    }

    public static void setScore(int newScore) {
        score = newScore;
    }

    public static void reset() {
        pinPlaced = false;
        pinPlacedAnimationExecuted = false;
        currentRegionIndex = 0;
        currentResourceType = "";
        currentResourceTypeIndex = 0;
        currentRegion = "";
        score = 0;
    }

    ///  Labels

    public static Label SRScoreStatsLabel = Helpers.createHeaderLabel("Region: " + currentRegion + "\n" + "Score: " + score);


    /// ///--------------------------------------------------------------------------------------------------------/// ///
    /**
     * The entry point for the application.
     * Initializes the stage, scenes, buttons, and their corresponding actions.
     * @param stage The window of which everything is viewed on.
     */
    @Override
    public void start(Stage stage) throws IOException {
        for (int[][] ints : Arrays.asList(NAResources, CAResources, SAResources, EUResources, AFResources, ASResources, OCResources)) {
            regionResources.add(ints);
        }


        // Assigns image variables their images to be loaded.
        /// --- Images
        Image worldMapImage = getImage("WMBlank.png");
        Image backImage = getImage("back.jpg");

        Image northAmericaImage = getImage("NABlank.jpg");
        Image centralAmericaImage = getImage("CABlank.jpg");
        Image southAmericaImage = getImage("SABlank.jpg");
        Image europeImage = getImage("EUBlank.jpg");
        Image asiaImage = getImage("ASBlank.jpg");
        Image africaImage = getImage("AFBlank.jpg");
        Image oceaniaImage = getImage("OCBlank.jpg");

            /// Create shapes & assign their images

            // 3 : 4 or 4 : 3 (x200)
        Rectangle NAMap = new Rectangle(800, 600);
            NAMap.setFill(new ImagePattern(northAmericaImage));

        Rectangle CAMap = new Rectangle(800, 600);
            CAMap.setFill(new ImagePattern(centralAmericaImage));

        Rectangle SAMap = new Rectangle(800, 600);
            SAMap.setFill(new ImagePattern(southAmericaImage));

        Rectangle EUMap = new Rectangle(800, 600);
            EUMap.setFill(new ImagePattern(europeImage));

        Rectangle ASMap = new Rectangle(800, 600);
            ASMap.setFill(new ImagePattern(asiaImage));

        Rectangle AFMap = new Rectangle(800, 600);
            AFMap.setFill(new ImagePattern(africaImage));

        Rectangle OCMap = new Rectangle(800, 600);
            OCMap.setFill(new ImagePattern(oceaniaImage));


            // region picking scene
        // Maps
        Rectangle worldMapRegionPickFrame = new Rectangle(1000, 497);
        worldMapRegionPickFrame.setFill(new ImagePattern(worldMapImage));



            ////---- App Layout

            // Main Menu
        StackPane worldMapMainMenuImageFrame = Helpers.createRectanglePane(Color.BLACK, worldMapImage);
        StackPane creditsInfoBox = Helpers.createRectanglePane(Color.BLACK, """
                This project was made by
                The Holy Trinity of Coding
                a team consisting of Levi Daniel, Aeden Kramer, and Jon Cheever.
                
                Our project is inspired by a
                highlighted problem reported by
                JustAuto (just-auto.com) of mass
                raw material shortages worldwide.
                
                We got our firm locations from Google Maps and
                various Wikipedia pages locating different
                resources.""", 20);

        StackPane projectInfoBox = Helpers.createRectanglePane(Color.BLACK, """
                This project addresses the widespread, present problem of\s
                resource shortages in automotive industry, ranging from raw materials,\s
                to semiconductors to the manufacturers of car parts.
                
                This project exists to encourage participation in the market by allowing
                users to play a simple game to locate certain natural resources and gain
                knowledge on where to work in to combat shortages with the added labor
                that comes with participation.  """, 20);

            mainMenuRectangle = Helpers.createRectanglePane(Color.BLACK, "THE FIRM STARTUP", 60);

            // Stack of all StackPanes to show them in layers
            StackPane stack = new StackPane(projectInfoBox, creditsInfoBox, worldMapMainMenuImageFrame, mainMenuRectangle);
            stack.setPadding(new Insets(50));

        // Ensure alignments apply properly
        Platform.runLater(() -> {
            StackPane.setAlignment(projectInfoBox, Pos.CENTER_RIGHT);
            StackPane.setAlignment(creditsInfoBox, Pos.CENTER_RIGHT);
            StackPane.setAlignment(mainMenuRectangle, Pos.CENTER_RIGHT);
            StackPane.setAlignment(worldMapMainMenuImageFrame, Pos.CENTER_RIGHT);
            worldMapMainMenuImageFrame.setMaxWidth(Double.MAX_VALUE);
            worldMapMainMenuImageFrame.setMaxHeight(Double.MAX_VALUE);
            worldMapMainMenuImageFrame.setTranslateX(400);
        });

            // Set opacities to have mainMenuRectangle be the default.
            activeRect = mainMenuRectangle; // Initialize the active rectangle
            Helpers.setContentOpacity(worldMapMainMenuImageFrame, 0);
            Helpers.setContentOpacity(creditsInfoBox, 0);
            Helpers.setContentOpacity(projectInfoBox, 0);
            Helpers.setContentOpacity(mainMenuRectangle, 1);

            // Initialize Main Menu Buttons
        /// Buttons
        ///
        // Main Menu Scene
        Button beginPlanningButton = Helpers.createButton("Begin Planning", worldMapMainMenuImageFrame, stack, activeRect);
        Button creditsButton = Helpers.createButton("Credits/Sources", creditsInfoBox, stack, activeRect);
        Button projectInfoButton = Helpers.createButton("Project Info.", projectInfoBox, stack, activeRect);

            // Button Layout
            VBox leftAppElementsLayout = new VBox(90);
            leftAppElementsLayout.getChildren().addAll(beginPlanningButton, creditsButton, projectInfoButton);
            leftAppElementsLayout.setStyle("-fx-padding: 50; -fx-alignment: center-left; -fx-font-size: 50");
            leftAppElementsLayout.setAlignment(Pos.CENTER_LEFT);

            leftAppElementsLayout.setOnMouseExited(e -> mainMenuRectangle.toFront());

            // Fade into the default rectangle whenever a button is not being hovered over
            leftAppElementsLayout.setOnMouseExited(e -> Helpers.fadeEffect(mainMenuRectangle, activeRect));

            /// Main Menu Scene initialization

            StackPane rootMainMenu = new StackPane(leftAppElementsLayout, stack);
            leftAppElementsLayout.toFront();
            Scene mainMenuScene = new Scene(rootMainMenu, 1920, 1080);



            /// Back Buttons

            // Back Button In Region Select Scene
        // Back buttons
        Button backButtonToMainMenu = Helpers.createBackButtonLayout(backCircleMM, backImage, backButtonPadding);

            // Back Button In Map Scenes

            backButtonToWorldMap = Helpers.createBackButtonLayout(backCircle, backImage, backButtonPadding);

        Button backButtonToPrevRegionSR = Helpers.createBackButtonLayout(backCircleSR, backImage, backButtonPadding);

            forwardButtonToScoreResults = Helpers.createBackButtonLayout(forwardCircleSR, backImage, forwardButtonPadding);
            forwardButtonToScoreResults.setScaleX(-1); // Flip button horizontally to flip the image to face forward.

        Button forwardButtonToMainMenu = Helpers.createBackButtonLayout(forwardCircleToMM, backImage, forwardButtonPadding);
            forwardButtonToMainMenu.setScaleX(-1); // Flip button horizontally to flip the image to face forward.

            /// Region Pick Scene
        /// Labels
        // Region Pick Scene
        Label regionPickGuideLabel = Helpers.createHeaderLabel("Where do you want to go?");

        StackPane worldMapRegionPickPane = new StackPane();
            VBox regionPickLayout = new VBox(10, worldMapRegionPickPane);
            regionPickLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            regionPickLayout.setAlignment(Pos.CENTER);

            StackPane rootRegionPick = new StackPane();


            // Region Scene Buttons
        Button NAButton = Helpers.createButtonAt(worldMapRegionPickPane, "NA", 370, 325, Color.BLUE);
        Button CAButton = Helpers.createButtonAt(worldMapRegionPickPane, "CA", 385, 400, Color.PURPLE);
        Button SAButton = Helpers.createButtonAt(worldMapRegionPickPane, "SA", 450, 500, Color.RED);
        Button EUButton = Helpers.createButtonAt(worldMapRegionPickPane, "EU", 700, 300, Color.GREEN);
        Button AFButton = Helpers.createButtonAt(worldMapRegionPickPane, "AF", 700, 430, Color.ORANGE);
        Button ASButton = Helpers.createButtonAt(worldMapRegionPickPane, "AS", 950, 350, Color.BROWN);
        Button OCButton = Helpers.createButtonAt(worldMapRegionPickPane, "OC", 1050, 560, Color.DARKCYAN);
            worldMapRegionPickPane.getChildren().add(worldMapRegionPickFrame);
            worldMapRegionPickFrame.toBack();

            rootRegionPick.getChildren().addAll(worldMapRegionPickPane, backButtonToMainMenu, regionPickGuideLabel, regionPickLayout);
            StackPane.setAlignment(backButtonToMainMenu,Pos.TOP_LEFT);

            StackPane.setAlignment(regionPickGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(regionPickGuideLabel, new Insets(50, 0, 0, 0));

            regionPickLayout.toBack();
            Scene regionPickScene = new Scene(rootRegionPick, 1920, 1080);


            /// Region Scenes
            String regionScenesHeaderLabel = "Place your pin!";

            // North America Scene

        // Map Scenes
        Label NAGuideLabel = Helpers.createHeaderLabel(regionScenesHeaderLabel);

            VBox NALayout = new VBox(10, NAMap);
            NALayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            NALayout.setAlignment(Pos.CENTER);

            StackPane rootNAScene = new StackPane();
            rootNAScene.getChildren().addAll(NAGuideLabel, NALayout);

            StackPane.setAlignment(NAGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(NAGuideLabel, new Insets(50, 0, 0, 0));

            NALayout.toBack();
            Scene NAScene = new Scene(rootNAScene, 1920, 1080);


            // Central America Scene
        Label CAGuideLabel = Helpers.createHeaderLabel(regionScenesHeaderLabel);

            VBox CALayout = new VBox(10, CAMap);
            CALayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            CALayout.setAlignment(Pos.CENTER);

            StackPane rootCAScene = new StackPane();
            rootCAScene.getChildren().addAll(CAGuideLabel, CALayout);

            StackPane.setAlignment(CAGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(CAGuideLabel, new Insets(50, 0, 0, 0));

            CALayout.toBack();
            Scene CAScene = new Scene(rootCAScene, 1920, 1080);


            // South America Scene
        Label SAGuideLabel = Helpers.createHeaderLabel(regionScenesHeaderLabel);

            VBox SALayout = new VBox(10, SAMap);
            SALayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            SALayout.setAlignment(Pos.CENTER);

            StackPane rootSAScene = new StackPane();
            rootSAScene.getChildren().addAll(SAGuideLabel, SALayout);

            StackPane.setAlignment(SAGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(SAGuideLabel, new Insets(50, 0, 0, 0));

            SALayout.toBack();
            Scene SAScene = new Scene(rootSAScene, 1920, 1080);

            // Europe Scene

        Label EUGuideLabel = Helpers.createHeaderLabel(regionScenesHeaderLabel);

            VBox EULayout = new VBox(10, EUMap);
            EULayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            EULayout.setAlignment(Pos.CENTER);

            StackPane rootEUScene = new StackPane();
            rootEUScene.getChildren().addAll(EUGuideLabel, EULayout);

            StackPane.setAlignment(EUGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(EUGuideLabel, new Insets(50, 0, 0, 0));

            EULayout.toBack();
            Scene EUScene = new Scene(rootEUScene, 1920, 1080);

            // Africa Scene

        Label AFGuideLabel = Helpers.createHeaderLabel(regionScenesHeaderLabel);

            VBox AFLayout = new VBox(10, AFMap);
            AFLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            AFLayout.setAlignment(Pos.CENTER);

            StackPane rootAFScene = new StackPane();
            rootAFScene.getChildren().addAll(AFGuideLabel, AFLayout);

            StackPane.setAlignment(AFGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(AFGuideLabel, new Insets(50, 0, 0, 0));

            AFLayout.toBack();
            Scene AFScene = new Scene(rootAFScene, 1920, 1080);

            // Asia Scene

        Label ASGuideLabel = Helpers.createHeaderLabel(regionScenesHeaderLabel);

            VBox ASLayout = new VBox(10, ASMap);
            ASLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            ASLayout.setAlignment(Pos.CENTER);

            StackPane rootASIAScene = new StackPane();
            rootASIAScene.getChildren().addAll(ASGuideLabel, ASLayout);

            StackPane.setAlignment(ASGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(ASGuideLabel, new Insets(50, 0, 0, 0));

            ASLayout.toBack();
            Scene ASIAScene = new Scene(rootASIAScene, 1920, 1080);

            // Oceania Scene

        Label OCGuideLabel = Helpers.createHeaderLabel(regionScenesHeaderLabel);

            VBox OCLayout = new VBox(10, OCMap);
            OCLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 16;");
            OCLayout.setAlignment(Pos.CENTER);

            StackPane rootOCScene = new StackPane();
            rootOCScene.getChildren().addAll(OCGuideLabel, OCLayout);

            StackPane.setAlignment(OCGuideLabel, Pos.TOP_CENTER);
            StackPane.setMargin(OCGuideLabel, new Insets(50, 0, 0, 0));

            OCLayout.toBack();
            Scene OCScene = new Scene(rootOCScene, 1920, 1080);


            // Score Results
            Label SRGuideLabel = Helpers.createHeaderLabel("Score Results:");


            VBox SRLayout = new VBox(10);
            SRLayout.setAlignment(Pos.CENTER);

            StackPane rootSRScene = new StackPane();
            rootSRScene.getChildren().addAll(backButtonToPrevRegionSR, SRGuideLabel, SRScoreStatsLabel, SRLayout, forwardButtonToMainMenu);

            StackPane.setAlignment(backButtonToPrevRegionSR, Pos.TOP_LEFT);
            StackPane.setAlignment(forwardButtonToMainMenu, Pos.CENTER_RIGHT);
            StackPane.setAlignment(SRGuideLabel, Pos.TOP_CENTER);
            StackPane.setAlignment(SRScoreStatsLabel, Pos.CENTER);
            StackPane.setMargin(SRGuideLabel, new Insets(50, 0, 0, 0));

            SRLayout.toBack();
            Scene SRScene = new Scene(rootSRScene, 1920, 1080);


            /// Mouse Events




            backButtonToWorldMap.setOnMouseClicked(e -> {
                intoScene = null;
                intoRoot = null;
                Helpers.fadeTransitionToScene(stage.getScene().getRoot(), regionPickScene, rootRegionPick, stage);
                System.out.println(intoScene);
                System.out.println(intoRoot);

            });


            beginPlanningButton.setOnMouseClicked(e -> Helpers.fadeTransitionToScene(rootMainMenu, regionPickScene, rootRegionPick, stage));
            backButtonToMainMenu.setOnMouseClicked(e -> Helpers.fadeTransitionToScene(rootRegionPick, mainMenuScene, rootMainMenu, stage));

            NAButton.setOnMouseClicked(e ->{Helpers.fadeTransitionToScene(rootRegionPick, NAScene, rootNAScene, stage); intoScene = NAScene; intoRoot = rootNAScene; Helpers.addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot, false); Helpers.addBackButtonAsChild(backButtonToWorldMap, intoRoot); setCurrentRegion(0);});
            SAButton.setOnMouseClicked(e -> {Helpers.fadeTransitionToScene(rootRegionPick, SAScene, rootSAScene, stage); intoScene = SAScene; intoRoot = rootSAScene; Helpers.addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot, false); Helpers.addBackButtonAsChild(backButtonToWorldMap, intoRoot); setCurrentRegion(2);});
            CAButton.setOnMouseClicked(e -> {Helpers.fadeTransitionToScene(rootRegionPick, CAScene, rootCAScene, stage); intoScene = CAScene; intoRoot = rootCAScene; Helpers.addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot, false); Helpers.addBackButtonAsChild(backButtonToWorldMap, intoRoot); setCurrentRegion(1);});
            EUButton.setOnMouseClicked(e -> {Helpers.fadeTransitionToScene(rootRegionPick, EUScene, rootEUScene, stage); intoScene = EUScene; intoRoot = rootEUScene; Helpers.addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot, false); Helpers.addBackButtonAsChild(backButtonToWorldMap, intoRoot); setCurrentRegion(3);});
            ASButton.setOnMouseClicked(e -> {Helpers.fadeTransitionToScene(rootRegionPick, ASIAScene, rootASIAScene, stage); intoScene = ASIAScene; intoRoot = rootASIAScene; Helpers.addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot, false); Helpers.addBackButtonAsChild(backButtonToWorldMap, intoRoot); setCurrentRegion(5);});
            AFButton.setOnMouseClicked(e -> {Helpers.fadeTransitionToScene(rootRegionPick, AFScene, rootAFScene, stage); intoScene = AFScene; intoRoot = rootAFScene; Helpers.addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot, false); Helpers.addBackButtonAsChild(backButtonToWorldMap, intoRoot); setCurrentRegion(4); });
            OCButton.setOnMouseClicked(e -> {Helpers.fadeTransitionToScene(rootRegionPick, OCScene, rootOCScene, stage); intoScene = OCScene; intoRoot = rootOCScene; Helpers.addForwardButtonAsChild(forwardButtonToScoreResults, intoRoot, false); Helpers.addBackButtonAsChild(backButtonToWorldMap, intoRoot); setCurrentRegion(6);});

            forwardButtonToScoreResults.setOnMouseClicked(e -> {Helpers.fadeTransitionToScene(intoRoot, SRScene, rootSRScene, stage);});
            backButtonToPrevRegionSR.setOnMouseClicked(e -> {Helpers.fadeTransitionToScene(rootSRScene, intoScene, intoRoot, stage);});

            forwardButtonToMainMenu.setOnMouseClicked(e -> {Helpers.fadeTransitionToScene(rootSRScene, mainMenuScene, rootMainMenu, stage); reset();});


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
            System.setProperty("prism.allowhidpi", "true");

            stage.setTitle("Firm Locator");
            stage.setScene(mainMenuScene);

            stage.setWidth(1920);
            stage.setHeight(1080);
            stage.setResizable(true);

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
        circle.setRadius(5);
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
            Helpers.disappearAnimation(root.getChildren().get(root.getChildren().size()-1), pinPlaced);
            Helpers.appearAnimation(root.getChildren().get(root.getChildren().size()-2), pinPlaced);
            pinPlaced = true;
            placePinAnimation(pin, circle);

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


    private void placePinAnimation(ImageView pin, Circle circle) {

        if (!pinPlacedAnimationExecuted) {

//            int[][] currentRegionResource = ;

            int circleXInt = (int) circle.getTranslateX();
            int circleYInt = (int) circle.getTranslateY();


            ///  Debug
            System.out.println();
            System.out.println(currentRegionIndex);
            System.out.println(currentRegion);
            System.out.println();
            System.out.println(currentResourceTypeIndex);
            System.out.println(currentResourceType);

            ScoreCalculator scoreCalculator = new ScoreCalculator(circleXInt, circleYInt, regionResources.get(currentRegionIndex));

            setScore(scoreCalculator.getPoints(regionResources.get(currentRegionIndex)));

            SRScoreStatsLabel.setText("Region: " + currentRegion + "\n" +
                                      "Score: " + score);


            // Get circle center coordinates
            double circleX = circle.getTranslateX();
            double circleY = circle.getTranslateY();

            System.out.println("Circle center coords: " + circleX + ", " + circleY);
            System.out.println("Initial pin coords: " + pin.getTranslateX() + ", " + pin.getTranslateY());


            // Move pin to the center of the circle; Quadratic speed
            Path path = new Path();
            path.getElements().add(new MoveTo(pin.getTranslateX() + 20, pin.getTranslateY()));
            path.getElements().add(new QuadCurveTo(pin.getTranslateX() + 20, pin.getTranslateY() - 80, circleX + 20, circleY));

            PathTransition moveToCircle = new PathTransition(Duration.seconds(1), path, pin);


            moveToCircle.play();

            pinPlacedAnimationExecuted = true;

        }



    }

    /**
     * Helper method to create an image object from a file.
     * @param fileName The target file name for the image to use.
     * @return The image that was created from the file name.
     */
    public Image getImage(String fileName) {
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

