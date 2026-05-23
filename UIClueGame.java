import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

public class UIClueGame extends Application {
    private StackPane rootContainer; // Swaps views inside this master container
    private ArrayList<Player> players = new ArrayList<>();
    private int numPlayers;
    private final String[] suspectsList = {" ", "Col. Mustard", "Miss Scarlet", "Prof. Plum", "Mr. Green", "Mrs. Peacock", "Dr. Orchid"};

    @Override
    public void start(Stage primaryStage) {
        rootContainer = new StackPane();
        rootContainer.getStyleClass().add("root-container");

        Scene scene = new Scene(rootContainer, 800, 740);
        // Attaches your clean custom website-like CSS rules
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Initialize and show the Home Screen instantly
        rootContainer.getChildren().add(buildHomeScreen());

        primaryStage.setTitle("CLUE");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private VBox buildHomeScreen() {
    VBox homeLayout = new VBox(20); // 20px spacing between your elements
    homeLayout.setAlignment(Pos.CENTER);

    // 1. BACKGROUND INITIALIZATION (Your cover art setup goes here)

    // 2. EXTRA LARGE CUSTOM LOGO SETUP
    StackPane logoContainer = new StackPane();

    try {
        File logoFile = new File("clue_cover.jpg"); 
        if (logoFile.exists()) {
            Image logoImg = new Image(logoFile.toURI().toString());
            ImageView logoView = new ImageView(logoImg);
            
            logoView.setPreserveRatio(true);
            logoView.setFitWidth(650);   // Keeps your logo wide and bold
            logoView.setFitHeight(200);  // Explicitly limits image vertical canvas height
            
            logoContainer.getChildren().add(logoView);
        } else {
            Label fallbackLabel = new Label("[ LOGO PLACEHOLDER ]");
            fallbackLabel.getStyleClass().add("title-text");
            fallbackLabel.setStyle("-fx-font-size: 54px;");
            logoContainer.getChildren().add(fallbackLabel);
        }
    } catch (Exception e) {
        System.out.println("Could not load custom logo graphic.");
    }
    
    // --- INTRO ANIMATION TRACK ---
    logoContainer.setOpacity(0.0);
    logoContainer.setTranslateY(40); 
    
    FadeTransition fadeInLogo = new FadeTransition(Duration.millis(1200), logoContainer);
    fadeInLogo.setFromValue(0.0); 
    fadeInLogo.setToValue(1.0);
    
    TranslateTransition riseUpLogo = new TranslateTransition(Duration.millis(1200), logoContainer);
    riseUpLogo.setFromY(40); 
    riseUpLogo.setToY(0); 
    
    ParallelTransition introAnim = new ParallelTransition(fadeInLogo, riseUpLogo);
    introAnim.setDelay(Duration.millis(300));
    introAnim.play();

    // 3. INTERACTIVE START BUTTON SETUP
    Button startBtn = new Button("START");
    startBtn.getStyleClass().add("sleek-button");
    startBtn.setOnAction(e -> fadeToNextScreen(buildPlayerCountScreen()));

    // Adds a clean, universal frame padding inside the VBox window
    homeLayout.setPadding(new Insets(20)); 

    // Layers them sequentially: Logo on top, Start Button cleanly below it
    homeLayout.getChildren().addAll(logoContainer, startBtn);
    return homeLayout;
}

    private VBox buildPlayerCountScreen() {
        VBox cardContainer = new VBox(25);
        cardContainer.getStyleClass().add("setup-card");
        cardContainer.setMaxSize(400, 300);
        cardContainer.setAlignment(Pos.CENTER);

        Label title = new Label("SELECT TOTAL SUSPECTS");
        title.getStyleClass().add("title-text");
        title.setStyle("-fx-font-size: 22px;");

        ComboBox<String> comboPlayers = new ComboBox<>();
        comboPlayers.getItems().addAll("3", "4", "5", "6");
        comboPlayers.setValue("3");
        comboPlayers.getStyleClass().add("combo-box");

        Button nextBtn = new Button("CHOOSE CHARACTERS →");
        nextBtn.getStyleClass().add("secondary-button");
        nextBtn.setOnAction(e -> {
            this.numPlayers = Integer.parseInt(comboPlayers.getValue());
            fadeToNextScreen(buildCharacterAssignmentScreen(this.numPlayers));
        });

        cardContainer.getChildren().addAll(title, comboPlayers, nextBtn);
        
        VBox screenWrapper = new VBox(cardContainer);
        screenWrapper.setAlignment(Pos.CENTER);
        return screenWrapper;
    }

    private VBox buildCharacterAssignmentScreen(int totalPlayers) {
        players.clear();
        VBox cardContainer = new VBox(15);
        cardContainer.getStyleClass().add("setup-card");
        cardContainer.setMaxSize(500, 450);
        cardContainer.setAlignment(Pos.CENTER);

        Label title = new Label("ASSIGN INVESTIGATORS");
        title.getStyleClass().add("title-text");
        title.setStyle("-fx-font-size: 22px;");
        cardContainer.getChildren().add(title);

        ArrayList<ComboBox<String>> menuSelectors = new ArrayList<>();

        for (int i = 1; i <= totalPlayers; i++) {
            int tempIndex = i;
            players.add(new Player("Player " + tempIndex, " "));

            HBox row = new HBox(20);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(5, 40, 5, 40));

            Label label = new Label("INVESTIGATOR " + tempIndex + ":");
            label.getStyleClass().add("body-text");
            label.setPrefWidth(150);

            ComboBox<String> dropdown = new ComboBox<>();
            dropdown.getItems().addAll(suspectsList);
            dropdown.setValue(" ");
            dropdown.getStyleClass().add("combo-box");
            dropdown.setPrefWidth(180);
            
            dropdown.setOnAction(e -> players.get(tempIndex - 1).setCharacterName(dropdown.getValue()));
            menuSelectors.add(dropdown);

            row.getChildren().addAll(label, dropdown);
            cardContainer.getChildren().add(row);
        }

        Label errorLabel = new Label("❌ Please assign unique suspect identity files.");
        errorLabel.getStyleClass().add("body-text");
        errorLabel.setStyle("-fx-text-fill: #ef5350; -fx-font-size: 13px;");
        errorLabel.setVisible(false);

        Button startBtn = new Button("ENTER MANSION →");
        startBtn.getStyleClass().add("sleek-button");
        startBtn.setOnAction(e -> {
            if (checkCharacters(players)) {
                errorLabel.setVisible(false);
                fadeToNextScreen(buildBoardScreen());
            } else {
                errorLabel.setVisible(true);
            }
        });

        cardContainer.getChildren().addAll(startBtn, errorLabel);

        VBox screenWrapper = new VBox(cardContainer);
        screenWrapper.setAlignment(Pos.CENTER);
        return screenWrapper;
    }

    private boolean checkCharacters(ArrayList<Player> players) {
    ArrayList<String> names = new ArrayList<>();
    for (Player p : players) {
        String character = p.getCharacterName();
        // If they left it blank or picked a duplicate character, return false
        if (character == null || character.equals(" ") || names.contains(character)) {
            return false;
        }
        names.add(character);
    }
    return true;
}

    // Add these instance variables to the top of your UIClueGame class if you don't have them:
private GameManager gameManager;
private FXBoardPanel visualBoard;
private Label statusLabel;

private BorderPane buildBoardScreen() {
    BorderPane boardLayout = new BorderPane();
    
    // CRUCIAL BUG FIX: Your GameManager constructor strictly demands EXACTLY 4 players minimum
    // to run its token placement rules without throwing an IndexOutOfBounds Exception.
    // If the user selected 3 players, we temporarily patch a dummy AI character to satisfy the engine.
    if (this.players.size() < 4) {
        // Find a character name from the list that isn't currently assigned
        String backupCharacter = "Dr. Orchid";
        for (String suspect : suspectsList) {
            if (!suspect.equals(" ") && !checkCharactersContains(suspect)) {
                backupCharacter = suspect;
                break;
            }
        }
        this.players.add(new Player("Bot Player", backupCharacter));
    }
    
    // 1. Initialize our backend engine safely with 4 players loaded
    gameManager = new GameManager(this.players);
    
    // 2. Instantiate your custom canvas layout using the manager's board
    visualBoard = new FXBoardPanel(gameManager.getBoard()); 
    boardLayout.setCenter(visualBoard);

    // 3. Action bar status reporting label setup
    statusLabel = new Label("Game Started. Click 'START TURN' to begin.");
    statusLabel.getStyleClass().add("body-text");
    statusLabel.setStyle("-fx-text-fill: #deb86b; -fx-padding: 0 0 0 20;");

    // 4. Create your structural top bar controls
    HBox topActionBar = new HBox(20);
    topActionBar.setPadding(new Insets(15));
    topActionBar.setAlignment(Pos.CENTER_LEFT);
    topActionBar.setStyle("-fx-background-color: #0b1324;");

    Button startTurnBtn = new Button("START TURN");
    startTurnBtn.getStyleClass().add("secondary-button");
    
    Button rollDiceBtn = new Button("ROLL DICE 🎲");
    rollDiceBtn.getStyleClass().add("sleek-button");
    rollDiceBtn.setDisable(true); 

    // START TURN ACTION
    startTurnBtn.setOnAction(e -> {
        Player current = gameManager.getCurrentTurn();
        statusLabel.setText(current.getPlayerName() + " (" + current.getCharacterName() + ")'s Turn");
        startTurnBtn.setDisable(true);
        rollDiceBtn.setDisable(false);
    });

    // ROLL DICE ACTION
    rollDiceBtn.setOnAction(e -> {
        Player current = gameManager.getCurrentTurn();
        gameManager.rollDice(); 
        int steps = current.getRoll();
        
        statusLabel.setText(current.getCharacterName() + " rolled a " + steps + "! Click a highlighted tile to move.");
        rollDiceBtn.setDisable(true);

        // Fetch valid grid options from your pathfinding algorithm
        ArrayList<Tile> validTiles = gameManager.getBoard().getValidMoves(current, steps);
        
        // Tells your FXBoardPanel canvas to highlight these legal options visually
        visualBoard.highlightPossibleMoves(validTiles);

        // Map mouse click listeners onto the highlighted visual tiles
        visualBoard.setOnTileClickedListener(clickedTile -> {
            if (validTiles.contains(clickedTile)) {
                // Update underlying model array coordinates
                gameManager.setPlayerPos(clickedTile.getRow(), clickedTile.getCol());
                
                // Clear active overlay frames and update canvas asset icons
                visualBoard.clearHighlights();
                visualBoard.refreshPlayerPositions(); 
                
                // Switch turn back over to next investigator row entry
                gameManager.setNextTurn();
                startTurnBtn.setDisable(false);
                statusLabel.setText("Turn ended. Waiting for next player...");
            }
        });
    });

    topActionBar.getChildren().addAll(startTurnBtn, rollDiceBtn, statusLabel);
    boardLayout.setTop(topActionBar);

    return boardLayout;
}

// Quick helper method to find unassigned players during configuration safety patching
private boolean checkCharactersContains(String name) {
    for (Player p : this.players) {
        if (p.getCharacterName() != null && p.getCharacterName().equals(name)) {
            return true;
        }
    }
    return false;
}

    // --- SMOOTH CROSS-SCREEN TRANSITION FADING HELPER ---
    // --- CINEMATIC MIDNIGHT CROSS-SCREEN FADE ---
private void fadeToNextScreen(javafx.scene.Node nextScreen) {
    // Create a solid dark mask that sits over the entire screen layout
    Pane fadeOverlay = new Pane();
    fadeOverlay.setStyle("-fx-background-color: #0b1324;"); // Smooth deep navy/black fade color
    fadeOverlay.setOpacity(0.0);
    
    // Add it to the top layer of our screen stack
    rootContainer.getChildren().add(fadeOverlay);

    // 1. Fade the dark overlay IN to hide the current view
    FadeTransition fadeToDark = new FadeTransition(Duration.millis(300), fadeOverlay);
    fadeToDark.setFromValue(0.0);
    fadeToDark.setToValue(1.0);

    fadeToDark.setOnFinished(event -> {
        // 2. Once everything is completely dark, swap out the screens underneath safely
        rootContainer.getChildren().clear();
        rootContainer.getChildren().add(nextScreen);
        
        // Re-add our cover to the top stack so we can fade it back out
        rootContainer.getChildren().add(fadeOverlay);

        // 3. Fade the dark overlay OUT to elegantly reveal your new screen
        FadeTransition revealNewScreen = new FadeTransition(Duration.millis(300), fadeOverlay);
        revealNewScreen.setFromValue(1.0);
        revealNewScreen.setToValue(0.0);
        
        // Clean up memory by removing the overlay entirely once the transition finishes
        revealNewScreen.setOnFinished(e -> rootContainer.getChildren().remove(fadeOverlay));
        revealNewScreen.play();
    });

    fadeToDark.play();
}

    public static void main(String[] args) {
        launch(args);
    }
}
