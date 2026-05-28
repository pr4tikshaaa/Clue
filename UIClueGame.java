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
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

/**
 * The main GUI class, handles the home screen and player setup.
 */
public class UIClueGame extends Application {
     /** Swaps views inside this master container*/
    private StackPane rootContainer;
    /** Holds the list of players playing*/
    private ArrayList<Player> players = new ArrayList<>();
    /**Number of players playing stored in here */
    private int numPlayers;
     /**The characters to choose from */
    private final String[] suspectsList = {" ", "Colonel Mustard", "Miss Scarlet", "Professor Plum", "Mr. Green", "Mrs. Peacock", "Dr. Orchid"};
     /**The list of rooms */
    private final String[] roomsList = {"Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study"};

    /**Creating a new gameManager to handle the overall game logic */
    private GameManager gameManager;
    /**Creating a new physical board */
    private FXBoardPanel visualBoard;
    /**For some of the cues to let people know what to do next */
    private Label statusLabel;
    
    /**Starts your turn */
    private Button startTurnBtn;
    /**Rolls the dice */
    private Button rollDiceBtn;
    /**To make an accusation */
    private Button makeAccusationBtn;

    @Override
    /**
     * Creates and launches the main window
     * @param primaryStage (the main, blank window to be created automatically when starts)
     */
    public void start(Stage primaryStage) {
        rootContainer = new StackPane();
        rootContainer.getStyleClass().add("root-container");

        Scene scene = new Scene(rootContainer, 800, 740);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        rootContainer.getChildren().add(buildHomeScreen());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Used to layer items in a vertical layout (VBox = Vertical Box)
     * NOTE: There are some stylistic elements in this method that were AI generated (like setting font/background color, size)
     *      others are specified
     *  
     * @return a VBox containing layout of the home screen
     */
    public VBox buildHomeScreen() {
        VBox homeLayout = new VBox(20);
        homeLayout.setAlignment(Pos.CENTER);

        // homescreen logo image
        StackPane logoContainer = new StackPane();
        try {
            File logoFile = new File("clue_cover.jpg"); 
            if (logoFile.exists()) {
                Image logoImg = new Image(logoFile.toURI().toString());
                ImageView logoView = new ImageView(logoImg);
                logoView.setPreserveRatio(true);
                logoView.setFitWidth(650);   
                logoView.setFitHeight(200);  
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
        
        //Stylistic, AI generated
        logoContainer.setOpacity(0.0);
        logoContainer.setTranslateY(40); 
        
        //Stylistic, AI generated
        FadeTransition fadeInLogo = new FadeTransition(Duration.millis(1200), logoContainer);
        fadeInLogo.setFromValue(0.0); 
        fadeInLogo.setToValue(1.0);
        
        //Stylistic, AI generated
        TranslateTransition riseUpLogo = new TranslateTransition(Duration.millis(1200), logoContainer);
        riseUpLogo.setFromY(40); 
        riseUpLogo.setToY(0); 
        
        //Stylistic, AI generated
        ParallelTransition introAnim = new ParallelTransition(fadeInLogo, riseUpLogo);
        introAnim.setDelay(Duration.millis(300));
        introAnim.play();

        //Stylistic, AI generated
        Button startBtn = new Button("START");
        startBtn.getStyleClass().add("sleek-button");
        //Start button changes screen to the num players screen
        startBtn.setOnAction(e -> fadeToNextScreen(buildPlayerCountScreen()));

        homeLayout.setPadding(new Insets(20)); 
        homeLayout.getChildren().addAll(logoContainer, startBtn);
        return homeLayout;
    }

    /**
     * Method to choose how many players are playing
     * the field numPlayers changes to the value sorted by the dropdown comboPlayers
     * NOTE: There are some stylistic elements in this method that were AI generated (like setting font/background color, size)
     * 
     * @return a VBox containing layout of number of players screen
     */
    public VBox buildPlayerCountScreen() {
        VBox cardContainer = new VBox(25);
        cardContainer.getStyleClass().add("setup-card");
        cardContainer.setMaxSize(400, 300);
        cardContainer.setAlignment(Pos.CENTER);

        Label title = new Label("SELECT TOTAL SUSPECTS");
        title.getStyleClass().add("title-text");
        title.setStyle("-fx-font-size: 22px;");

        ComboBox<String> comboPlayers = new ComboBox<>();
        comboPlayers.getItems().addAll("3", "4", "5", "6");
        comboPlayers.setValue("3"); ; //sets default to 3 players
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

    /**
     * Creating a new player based on totalPlayers, and adding to field players (HBox = Horizontal Box)
     * Layers items horizontally.
     * Uses helper method checkCharacters to make sure same characters are not chosen.
     * NOTE: There are some stylistic elements in this method that were AI generated (setting font/background color, size)
     * 
     * @param totalPlayers the number of players chosen
     * @return a VBox containing layout for choosing character
     */
    public VBox buildCharacterAssignmentScreen(int totalPlayers) {
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
        //Creating a new player based on totalPlayers
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

        Label errorLabel = new Label("Please assign adequate and unique identities.");
        errorLabel.getStyleClass().add("body-text");
        errorLabel.setStyle("-fx-text-fill: #ef5350; -fx-font-size: 13px;");
        errorLabel.setVisible(false);

        Button startBtn = new Button("CONTINUE");
        startBtn.getStyleClass().add("sleek-button");
        startBtn.setOnAction(e -> {
            if (checkCharacters(players)) {
                errorLabel.setVisible(false);
                Stage primaryWindow = (Stage) rootContainer.getScene().getWindow();
                
                gameManager = new GameManager(this.players);

                showQRCodeScreen(primaryWindow, gameManager);
            } else {
                errorLabel.setVisible(true);
            }
        });

        cardContainer.getChildren().addAll(startBtn, errorLabel);

        VBox screenWrapper = new VBox(cardContainer);
        screenWrapper.setAlignment(Pos.CENTER);
        return screenWrapper;
    }

    /**
     * HELPER METHOD
     * checks to make sure the characters chosen are different
     * 
     * @param players ArrayList of players to compare the players chosen
     * @return true if players are different, false if not
     */
    public boolean checkCharacters(ArrayList<Player> players) {
        ArrayList<String> names = new ArrayList<>();
        for (Player p : players) {
            String character = p.getCharacterName();
            if (character == null || character.equals(" ") || names.contains(character)) {
                return false;
            }
            names.add(character);
        }
        return true;
    }

    /**
     * QR code screen shown after character selection
     * Players use the clue sheet the QR code leads to
     * @param stage main application manager
     * @param activeManager game manager
     */
    public void showQRCodeScreen(Stage stage, GameManager activeManager) {
        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0b1324; -fx-padding: 40;");

        Label title = new Label("SCAN THE GAME QR CODE");
        title.getStyleClass().add("title-text");
        title.setStyle("-fx-text-fill: #deb86b; -fx-font-size: 26px;");

        Label subtitle = new Label("Get your Clue sheet before entering the mansion.");
        subtitle.getStyleClass().add("body-text");

        StackPane qrContainer = new StackPane();

        try {
            File qrFile = new File("qr_code.jpeg");

            if (qrFile.exists()) {
                Image qrImg = new Image(qrFile.toURI().toString());

                ImageView qrView = new ImageView(qrImg);
                qrView.setFitWidth(300);
                qrView.setFitHeight(300);
                qrView.setPreserveRatio(true);

                qrContainer.getChildren().add(qrView);

            } else {
                Label fallback = new Label("[ QR CODE IMAGE HERE ]");
                fallback.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
                qrContainer.getChildren().add(fallback);
            }

        } catch (Exception e) {
            Label error = new Label("Could not load QR code image.");
            error.setStyle("-fx-text-fill: red;");
            qrContainer.getChildren().add(error);
        }

        Button continueBtn = new Button("CONTINUE");
        continueBtn.getStyleClass().add("sleek-button");

        continueBtn.setOnAction(e -> {
            launchPreGameHandPreview(stage, activeManager, 0);
        });

        root.getChildren().addAll(title, subtitle, qrContainer, continueBtn);

        fadeToNextScreen(root);
    }

     /**
     * Builds the Clue board
     * contains the buttons needed for the game
     *      start turn, roll dice, make accusation
     * Shows basic info
     *      like current player, dice number rolled
     * NOTE: There are some stylistic elements in this method that were AI generated (setting font/background color, size)
     * 
     * @param stage passes the main application window to this method
     * @param manager a GameManager (manages the overall clue game logic)
     * @return a BorderPane containing main game layout, with a menu (at the top, with buttons)
     *      and the game at the center
     */
    public BorderPane buildBoardScreen(Stage stage, GameManager manager) {
        this.gameManager = manager;
        BorderPane boardLayout = new BorderPane();
        
        visualBoard = new FXBoardPanel(gameManager.getBoard(), this.players); 
        boardLayout.setCenter(visualBoard);

        statusLabel = new Label("Game Started. Click 'START TURN' to begin.");
        statusLabel.getStyleClass().add("body-text");
        statusLabel.setStyle("-fx-text-fill: #deb86b; -fx-padding: 0 0 0 20;");

        // Stylistic element, AI generated
        HBox topActionBar = new HBox(20);
        topActionBar.setPadding(new Insets(15));
        topActionBar.setAlignment(Pos.CENTER_LEFT);
        topActionBar.setStyle("-fx-background-color: #0b1324;");

        startTurnBtn = new Button("START TURN");
        startTurnBtn.getStyleClass().add("secondary-button");
        
        rollDiceBtn = new Button("ROLL DICE");
        rollDiceBtn.getStyleClass().add("sleek-button");
        rollDiceBtn.setDisable(true); 

        makeAccusationBtn = new Button("MAKE ACCUSATION");
        makeAccusationBtn.setStyle("-fx-background-color: #a62b2b; -fx-text-fill: white; -fx-font-weight: bold;");
        makeAccusationBtn.setDisable(true);

        startTurnBtn.setOnAction(e -> {
            Player current = gameManager.getCurrentTurn();
            statusLabel.setText(current.getPlayerName() + " (" + current.getCharacterName() + ")'s Turn");
            startTurnBtn.setDisable(true);
            rollDiceBtn.setDisable(false);
            makeAccusationBtn.setDisable(false); // Can accuse instead of moving/rolling
        });

        rollDiceBtn.setOnAction(e -> {
            Player current = gameManager.getCurrentTurn();
            gameManager.rollDice(); 
            int steps = current.getRoll();
            
            statusLabel.setText(current.getCharacterName() + " rolled a " + steps + "! Click a highlighted tile to move.");
            rollDiceBtn.setDisable(true);
            makeAccusationBtn.setDisable(true); // Moving commits your turn choice

            ArrayList<Tile> validTiles = gameManager.getBoard().getValidMoves(current, steps);
            visualBoard.highlightPossibleMoves(validTiles);

            visualBoard.setOnTileClickedListener(clickedTile -> {
                gameManager.setPlayerPos(clickedTile.getRow(), clickedTile.getCol());
                visualBoard.clearHighlights();
                visualBoard.refreshPlayerPositions(); 
                
                if (clickedTile.isRoom()) {
                    Room currentRoom = clickedTile.getConnectedRoom();
                    gameManager.setPlayerPos(currentRoom.getPlayerSpot().getRow(), currentRoom.getPlayerSpot().getCol());
                    visualBoard.refreshPlayerPositions();
                    launchSuggestionFlow(gameManager.getCurrentTurn(), currentRoom);
                } else {
                    wrapUpTurn(); 
                }
            });
        });

        makeAccusationBtn.setOnAction(e -> launchAccusationFlow(gameManager.getCurrentTurn()));

        // Use a Spacer object to push the accusation trigger to the far right edge cleanly 
        // stylistic element, AI generated
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topActionBar.getChildren().addAll(startTurnBtn, rollDiceBtn, statusLabel, spacer, makeAccusationBtn);
        boardLayout.setTop(topActionBar);

        return boardLayout;
    }

    /**
     * Start the "Accusation" part
     *      checking to see if their accusation is correct, if not then take the actions needed
     * NOTE: There are some stylistic elements in this method that were AI generated (like setting font/background color, size)
     * 
     * @param accusingPlayer the player that wants to guess
     */
    public void launchAccusationFlow(Player accusingPlayer) {
        javafx.stage.Stage dialog = new javafx.stage.Stage();
        dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        dialog.setTitle("FINAL ACCUSATION");
        dialog.setResizable(false);

        StackPane modalRoot = new StackPane();
        modalRoot.setStyle("-fx-background-color: #0b1324; -fx-padding: 30;");
        modalRoot.setPrefSize(450, 480);
        
        Scene dialogScene = new Scene(modalRoot);
        dialogScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialog.setScene(dialogScene);

        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);

        Label title = new Label("DECLARE YOUR FINAL SOLUTION");
        title.getStyleClass().add("title-text");
        title.setStyle("-fx-text-fill: #ef5350; -fx-font-size: 18px;");

        Label warning = new Label("WARNING: If wrong, you are eliminated from taking turns!");
        warning.setStyle("-fx-text-fill: #b5c2d6; -fx-font-style: italic; -fx-font-size: 12px;");

        ComboBox<String> suspectCombo = new ComboBox<>();
        for (String s : suspectsList) { if(!s.trim().isEmpty()) suspectCombo.getItems().add(s); }
        suspectCombo.setValue(suspectCombo.getItems().get(0));
        suspectCombo.getStyleClass().add("combo-box");

        ComboBox<String> weaponCombo = new ComboBox<>();
        weaponCombo.getItems().addAll("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Wrench");
        weaponCombo.setValue("Candlestick");
        weaponCombo.getStyleClass().add("combo-box");

        ComboBox<String> roomCombo = new ComboBox<>();
        roomCombo.getItems().addAll(roomsList);
        roomCombo.setValue(roomsList[0]);
        roomCombo.getStyleClass().add("combo-box");

        Button submitBtn = new Button("SUBMIT FINAL ACCUSATION");
        submitBtn.setStyle("-fx-background-color: #a62b2b; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");

        layout.getChildren().addAll(
            title, warning, 
            new Label("Suspect Case File:"), suspectCombo, 
            new Label("Murder Weapon Asset:"), weaponCombo, 
            new Label("Crime Scene Location:"), roomCombo,
            submitBtn
        );
        modalRoot.getChildren().add(layout);

        submitBtn.setOnAction(e -> {
            String suspect = suspectCombo.getValue();
            String weapon = weaponCombo.getValue();
            String room = roomCombo.getValue();

            boolean isCorrect = gameManager.makeAccusation(suspect, weapon, room);
            modalRoot.getChildren().clear();

            VBox resultsBox = new VBox(20);
            resultsBox.setAlignment(Pos.CENTER);
            
            if (isCorrect) {
                Label winLabel = new Label("CORRECT! " + accusingPlayer.getPlayerName().toUpperCase() + " SOLVED THE CRIME!");
                winLabel.setStyle("-fx-text-fill: #66bb6a; -fx-font-size: 20px; -fx-font-weight: bold;");
                
                Label detailLabel = new Label("It was indeed " + suspect + " in the " + room + " with the " + weapon + ".");
                detailLabel.getStyleClass().add("body-text");

                Button exitBtn = new Button("CLOSE GAME");
                exitBtn.getStyleClass().add("sleek-button");
                exitBtn.setOnAction(ev -> {
                    dialog.close();
                });
                resultsBox.getChildren().addAll(winLabel, detailLabel, exitBtn);
            } else {
                Label loseLabel = new Label("INCORRECT ACCUSATION!");
                loseLabel.setStyle("-fx-text-fill: #ef5350; -fx-font-size: 20px; -fx-font-weight: bold;");
                accusingPlayer.setOut(true);

                boolean allOut = true;
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).isOut() == false) {
                        allOut = false;
                    }
                }
                
                if (allOut) {
                    Label endLabel = new Label("INCORRECT! GAME OVER.");
                    endLabel.setStyle("-fx-text-fill: rgb(188, 61, 39); -fx-font-size: 20px; -fx-font-weight: bold; -fx-alignment: center;");
                    
                    Label detailLabel = new Label("It was actually the " + suspect.toUpperCase() + " in the " + room.toUpperCase() + "\nwith the " + weapon.toUpperCase() + ".");
                    detailLabel.getStyleClass().add("body-text");

                    Button exitBtn = new Button("CLOSE GAME");
                    exitBtn.getStyleClass().add("sleek-button");
                    exitBtn.setOnAction(ev -> {
                        dialog.close();
                    });
                    resultsBox.getChildren().addAll(endLabel, detailLabel, exitBtn);
                } else {
                    Label penaltyLabel = new Label(accusingPlayer.getPlayerName() + " is out of the game, but must still reveal cards to disprove suggestions.");
                    penaltyLabel.getStyleClass().add("body-text");
                    penaltyLabel.setWrapText(true);

                    Button continueBtn = new Button("CONTINUE");
                    continueBtn.getStyleClass().add("sleek-button");
                    continueBtn.setOnAction(ev -> {
                        dialog.close();
                        wrapUpTurn();
                    });

                    resultsBox.getChildren().addAll(loseLabel, penaltyLabel, continueBtn);
                }
            }

            modalRoot.getChildren().add(resultsBox);
        });

        dialog.showAndWait(); //opens your pop-up window and completely pauses the execution of the surrounding Java code until the user closes that pop-up
    }

    /**
     * The method that starts the suggestion flow
     * Creates this in a new empty window, seperate from the main board
     * Uses the method from GameManager, findDisprovingPlayer, which finds first player who can disprove a suggestion
     *      and getDisprovingCards, which gets all cards the disproving player is able to disprove suggestion with
     * Uses wrapUpTurn(), helper method
     * NOTE: There are some stylistic elements in this method that were AI generated (like setting font/background color, size)
     * 
     * @param suggestingPlayer the player who is suggesting
     * @param room the room the player is in
     */
    public void launchSuggestionFlow(Player suggestingPlayer, Room room) {
        javafx.stage.Stage dialog = new javafx.stage.Stage();
        dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        dialog.setTitle("ROOM ARRIVAL: MAKE A SUGGESTION");
        dialog.setResizable(false);

        StackPane modalRoot = new StackPane();
        modalRoot.setStyle("-fx-background-color: #0b1324; -fx-padding: 30;");
        modalRoot.setPrefSize(450, 400);
        
        Scene dialogScene = new Scene(modalRoot);
        dialogScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialog.setScene(dialogScene);

        VBox step1 = new VBox(15);
        step1.setAlignment(Pos.CENTER);

        Label title1 = new Label("MAKE A SUGGESTION IN THE " + room.getName().toUpperCase());
        title1.getStyleClass().add("title-text");
        title1.setStyle("-fx-text-fill: #deb86b; -fx-font-size: 16px;");

        ComboBox<String> suspectCombo = new ComboBox<>();
        for (String s : suspectsList) { if(!s.trim().isEmpty()) suspectCombo.getItems().add(s); }
        suspectCombo.setValue(suspectCombo.getItems().get(0));
        suspectCombo.getStyleClass().add("combo-box");

        ComboBox<String> weaponCombo = new ComboBox<>();
        weaponCombo.getItems().addAll("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Wrench");
        weaponCombo.setValue("Candlestick");
        weaponCombo.getStyleClass().add("combo-box");

        Button submitSuggestionBtn = new Button("SUBMIT SUGGESTION");
        submitSuggestionBtn.getStyleClass().add("sleek-button");

        step1.getChildren().addAll(title1, new Label("Suspect:"), suspectCombo, new Label("Weapon:"), weaponCombo, submitSuggestionBtn);
        modalRoot.getChildren().add(step1);

        submitSuggestionBtn.setOnAction(e -> {
            String chosenSuspect = suspectCombo.getValue();
            String chosenWeapon = weaponCombo.getValue();
            String chosenRoom = room.getName();

            Player disprovingPlayer = gameManager.findDisprovingPlayer(chosenSuspect, chosenWeapon, chosenRoom); 
            ArrayList<String> matchingCards = gameManager.getDisprovingCards(disprovingPlayer, chosenSuspect, chosenWeapon, chosenRoom);

            modalRoot.getChildren().clear();

            if (disprovingPlayer == null || matchingCards.isEmpty()) {
                VBox noDisproveBox = new VBox(20);
                noDisproveBox.setAlignment(Pos.CENTER);
                
                Label lbl = new Label("Nobody could disprove your suggestion!");
                lbl.getStyleClass().add("body-text");
                lbl.setStyle("-fx-text-fill: #ef5350; -fx-font-size: 18px;");
                
                Button closeBtn = new Button("CONTINUE GAME");
                closeBtn.getStyleClass().add("sleek-button");
                closeBtn.setOnAction(ev -> {
                    dialog.close();
                    wrapUpTurn();
                });

                noDisproveBox.getChildren().addAll(lbl, closeBtn);
                modalRoot.getChildren().add(noDisproveBox);
            } else {
                showPassDeviceScreen(modalRoot, suggestingPlayer, disprovingPlayer, matchingCards, dialog);
            }
        });

        dialog.showAndWait();
    }
    
    /**
     * The screen that shows to pass the screen to the correct person (Cue)
     * NOTE: There are some stylistic elements in this method that were AI generated (like setting font/background color, size)
     * 
     * @param root the main container
     * @param localSuggPlayer the player who made the OG suggestion
     * @param respondent the player whose turn it is to look at their cards to disprove the suggestion
     * @param cards the cards of the respondent
     * @param stage the active window passed forward to be closed at the end of the reveal sequence
     */
    public void showPassDeviceScreen(StackPane root, Player localSuggPlayer, Player respondent, ArrayList<String> cards, javafx.stage.Stage stage) {
        root.getChildren().clear();
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        Label instructions = new Label("PASS DEVICE TO: " + respondent.getPlayerName().toUpperCase());
        instructions.getStyleClass().add("title-text");
        instructions.setStyle("-fx-text-fill: #deb86b; -fx-font-size: 18px;");

        Label warning = new Label("Keep screen hidden from " + localSuggPlayer.getPlayerName() + "!");
        warning.getStyleClass().add("body-text");

        Button confirmIdentityBtn = new Button("I AM " + respondent.getPlayerName().toUpperCase());
        confirmIdentityBtn.getStyleClass().add("secondary-button");
        
        confirmIdentityBtn.setOnAction(e -> showCardSelectionScreen(root, localSuggPlayer, respondent, cards, stage));

        box.getChildren().addAll(instructions, warning, confirmIdentityBtn);
        root.getChildren().add(box);
    }
    
    /**
     * The screen for the respondent to see what card they want to show to localSuggPlayer (Cue)
     * NOTE: There are some stylistic elements in this method that were AI generated (like setting font/background color, size)
     * 
     * @param root the main container
     * @param localSuggPlayer the player who made the OG suggestion
     * @param respondent the player whose turn it is to look at their cards to disprove the suggestion
     * @param cards the cards of the respondent
     * @param stage the active window passed forward to be closed at the end of the reveal sequence
     */
    public void showCardSelectionScreen(StackPane root, Player localSuggPlayer, Player respondent, ArrayList<String> cards, javafx.stage.Stage stage) {
        root.getChildren().clear();
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);

        Label title = new Label(respondent.getPlayerName() + ", CHOOSE A CARD TO SHOW");
        title.getStyleClass().add("title-text");

        ToggleGroup cardGroup = new ToggleGroup();
        VBox radioContainer = new VBox(10);
        radioContainer.setAlignment(Pos.CENTER_LEFT);
        radioContainer.setPadding(new javafx.geometry.Insets(0, 0, 0, 120));

        for (String cardName : cards) {
            RadioButton rb = new RadioButton(cardName);
            rb.setToggleGroup(cardGroup);
            rb.getStyleClass().add("body-text");
            rb.setStyle("-fx-text-fill: #ffffff;");
            radioContainer.getChildren().add(rb);
        }
        ((RadioButton)radioContainer.getChildren().get(0)).setSelected(true);

        Button revealBtn = new Button("CONFIRM SELECTION");
        revealBtn.getStyleClass().add("sleek-button");

        revealBtn.setOnAction(e -> {
            RadioButton selected = (RadioButton) cardGroup.getSelectedToggle();
            String finalRevealedCardName = selected.getText();
            showPassDeviceBackScreen(root, localSuggPlayer, finalRevealedCardName, stage);
        });

        box.getChildren().addAll(title, radioContainer, revealBtn);
        root.getChildren().add(box);
    }

    /**
     * Screen to show which player to give the screen back to (Cue)
     * NOTE: There are some stylistic elements in this method that were AI generated (like setting font/background color, size)
     * 
     * @param root the main container
     * @param localSuggPlayer the player who made the OG suggestion
     * @param cardToShow cards chosen, to be shown to the localSuggPlayer
     * @param stage the active window passed forward to be closed at the end of the reveal sequence
     */
    public void showPassDeviceBackScreen(StackPane root, Player localSuggPlayer, String cardToShow, javafx.stage.Stage stage) {
        root.getChildren().clear();
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        Label instructions = new Label("PASS DEVICE BACK TO: " + localSuggPlayer.getPlayerName().toUpperCase());
        instructions.getStyleClass().add("title-text");
        instructions.setStyle("-fx-text-fill: #deb86b; -fx-font-size: 18px;");

        Button viewCardBtn = new Button("I AM " + localSuggPlayer.getPlayerName().toUpperCase() + " (VIEW CARD)");
        viewCardBtn.getStyleClass().add("secondary-button");

        viewCardBtn.setOnAction(e -> showFinalCardReveal(root, cardToShow, stage));

        box.getChildren().addAll(instructions, viewCardBtn);
        root.getChildren().add(box);
    }

     /**
     * Once the player confirms they are the right player, this method will show the cards for them to note down
     * NOTE: There are some stylistic elements in this method that were AI generated (like setting font/background color, size)
     * 
     * @param root
     * @param evidenceCard
     * @param stage
     */
    public void showFinalCardReveal(StackPane root, String evidenceCard, javafx.stage.Stage stage) {
        root.getChildren().clear();
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        Label evidenceHeader = new Label("EVIDENCE DISCOVERED:");
        evidenceHeader.getStyleClass().add("body-text");

        Label cardDisplay = new Label("[ " + evidenceCard.toUpperCase() + " ]");
        cardDisplay.getStyleClass().add("title-text");
        cardDisplay.setStyle("-fx-font-size: 26px; -fx-text-fill: #ef5350; -fx-background-color: #1a233a; -fx-padding: 15 40 15 40;");

        Button finalizeTurnBtn = new Button("HIDE EVIDENCE & END TURN");
        finalizeTurnBtn.getStyleClass().add("sleek-button");

        finalizeTurnBtn.setOnAction(e -> {
            stage.close(); 
            wrapUpTurn();  
        });

        box.getChildren().addAll(evidenceHeader, cardDisplay, finalizeTurnBtn);
        root.getChildren().add(box);
    }

     /**
     * HELPER METHOD
     * finishes the player's turn, and tells which player is next    
     */
    public void wrapUpTurn() {
        do {
            gameManager.setNextTurn();
        } while (gameManager.getCurrentTurn().isOut());


        if (startTurnBtn != null && rollDiceBtn != null && makeAccusationBtn != null) {
            startTurnBtn.setDisable(false);
            rollDiceBtn.setDisable(true);
            makeAccusationBtn.setDisable(true);
        }
        
        if (statusLabel != null) {
            Player nextUp = gameManager.getCurrentTurn();
            statusLabel.setText("Turn passed! Ready for " + nextUp.getPlayerName() + " (" + nextUp.getCharacterName() + "). Click 'START TURN'");
        }
    }

     /**
     * HELPER MEtHOD
     * Transitions between each screen (like fading in and out)
     * NOTE: Stylistic element, generated by AI
     * 
     * @param nextScreen the screen
     */
    public void fadeToNextScreen(javafx.scene.Node nextScreen) {
        Pane fadeOverlay = new Pane();
        fadeOverlay.setStyle("-fx-background-color: #0b1324;"); 
        fadeOverlay.setOpacity(0.0);
        
        rootContainer.getChildren().add(fadeOverlay);

        FadeTransition fadeToDark = new FadeTransition(Duration.millis(300), fadeOverlay);
        fadeToDark.setFromValue(0.0);
        fadeToDark.setToValue(1.0);

        fadeToDark.setOnFinished(event -> {
            rootContainer.getChildren().clear();
            rootContainer.getChildren().add(nextScreen);
            rootContainer.getChildren().add(fadeOverlay);

            FadeTransition revealNewScreen = new FadeTransition(Duration.millis(300), fadeOverlay);
            revealNewScreen.setFromValue(1.0);
            revealNewScreen.setToValue(0.0);
            
            revealNewScreen.setOnFinished(e -> rootContainer.getChildren().remove(fadeOverlay));
            revealNewScreen.play();
        });

        fadeToDark.play();
    }
    
    /**
     * Recursive method, handles the entire pass and play, each player gets to see their cards
     * NOTE: There are some stylistic elements in this method that were AI generated (like setting font/background color, size)
     * 
     * @param stage the primary JavaFX window used to host and switch game screens
     * @param activeManager the game manager
     * @param playerIndex the player's index
     */
    public void launchPreGameHandPreview(Stage stage, GameManager activeManager, int playerIndex) {
        ArrayList<Player> sessionPlayers = activeManager.getPlayers(); 

        if (playerIndex >= sessionPlayers.size()) {
            fadeToNextScreen(buildBoardScreen(stage, activeManager));
            return;
        }

        Player currentPlayer = sessionPlayers.get(playerIndex);

        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0b1324; -fx-padding: 40;");

        //Pass device screen
        Label passLabel = new Label("PASS DEVICE TO: " + currentPlayer.getPlayerName().toUpperCase());
        passLabel.setStyle("-fx-text-fill: #deb86b; -fx-font-size: 24px; -fx-font-weight: bold;");

        Label secretLabel = new Label("(" + currentPlayer.getCharacterName() + "'s secret hand)");
        secretLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-style: italic; -fx-font-size: 14px;");

        Button confirmIdentityBtn = new Button("I AM " + currentPlayer.getPlayerName().toUpperCase() + " (VIEW HAND)");
        confirmIdentityBtn.getStyleClass().add("sleek-button"); 

        root.getChildren().addAll(passLabel, secretLabel, confirmIdentityBtn);

        if (playerIndex == 0) {
            fadeToNextScreen(root);
        } else {
            rootContainer.getChildren().clear();
            rootContainer.getChildren().add(root);
        }

        //Reveal hand screen
        confirmIdentityBtn.setOnAction(e -> {
            root.getChildren().clear(); 

            Label handTitle = new Label(currentPlayer.getPlayerName().toUpperCase() + "'s CARD HAND");
            handTitle.setStyle("-fx-text-fill: #deb86b; -fx-font-size: 22px; -fx-font-weight: bold;");
            
            Label instruction = new Label("Take note of your cards, " + currentPlayer.getCharacterName() + "!");
            instruction.setStyle("-fx-text-fill: #b5c2d6; -fx-font-size: 14px;");

            HBox cardContainer = new HBox(15);
            cardContainer.setAlignment(Pos.CENTER);
            cardContainer.setPadding(new javafx.geometry.Insets(20, 0, 20, 0));

            for (Card card : currentPlayer.getHand()) {
                VBox cardVisual = new VBox(10);
                cardVisual.setAlignment(Pos.CENTER);
                cardVisual.setPrefSize(120, 180);
                
                cardVisual.setStyle("-fx-background-color: #1a233a; " + "-fx-border-color: #deb86b; " + "-fx-border-width: 2; " + "-fx-border-radius: 8; " + "-fx-background-radius: 8;");

                Label cardName = new Label(card.getName().toUpperCase());
                cardName.setWrapText(true);
                cardName.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 13px; -fx-text-alignment: center;");
                
                Label cardType = new Label("[" + card.getType().toUpperCase() + "]");
                cardType.setStyle("-fx-text-fill: #859bb5; -fx-font-size: 11px;");

                cardVisual.getChildren().addAll(cardName, cardType);
                cardContainer.getChildren().add(cardVisual);
            }

            String nextButtonText = (playerIndex == sessionPlayers.size() - 1) 
                ? "START GAME & ENTER MANSION" 
                : "DONE (PASS TO NEXT PLAYER)";

            Button nextPlayerBtn = new Button(nextButtonText);
            nextPlayerBtn.getStyleClass().add("sleek-button");
            
            nextPlayerBtn.setOnAction(ev -> {
                if (playerIndex == sessionPlayers.size() - 1) {
                    fadeToNextScreen(buildBoardScreen(stage, activeManager));
                } else {
                    launchPreGameHandPreview(stage, activeManager, playerIndex + 1);
                }
            });

            root.getChildren().addAll(handTitle, instruction, cardContainer, nextPlayerBtn);
        });
    }

    /**
     * the main method, runs the GUI part 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}