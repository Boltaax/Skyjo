package lp2a.game.skyjogame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Skyjo extends Application {
    // Attributes
    static boolean gameOver = true; // to know if the game is over
    static Screen screen = Screen.getPrimary();
    static int XMAX = (int) screen.getBounds().getWidth(); // variables to store the width of the screen
    static int YMAX = (int) (screen.getBounds().getHeight()*0.95); // variables to store the height of the screen
    static List<Player> players = new ArrayList<>(); // list of players in the game
    static int[] possible_cards = new int[15]; // Creating the array for the number of possible cards that are pickable for each value
    static int pickable_cards = 150; // Number of cards that aren't visible
    static CardDeck deck = new CardDeck(false, 20*XMAX/50, 20*YMAX/50); // the deck of cards
    static CardDeck discard = new CardDeck(true, 36*XMAX/50, 20*YMAX/50); // the discard pile
    static int currentPlayerIndex = 0; // the index of the current player
    static int lastPlayerIndex = -1; // the index of the last which will play during the turn
    static GameState gameState = GameState.ROUND_START; // the state of the game, used to know what to do depending on the state
    private MainMenu mainMenu = new MainMenu();
    static Player lastWinner = null;

    // Methods


    /**
     * This method displays the player's hand on the screen at the correct position
     * @param id the id of the player to display
     */
    public static void displayPlayer(int id){
        // set the size of the cards
        for(Card c : players.get(id).getHand()){
            c.setWidth(XMAX/25);
            c.setHeight(YMAX/10);
            c.setHeight(YMAX/10);
        }
        // set the position of the player depending on his id
        switch (id) {
            case 0 -> {
                players.get(id).setX(10 * XMAX / 50);
                players.get(id).setY(2 * YMAX / 3 + YMAX / 50);
            }
            case 1 -> {
                players.get(id).setX(10 * XMAX / 50);
                players.get(id).setY(YMAX / 3 + YMAX / 50);
            }
            case 2 -> {
                players.get(id).setX(10 * XMAX / 50);
                players.get(id).setY(YMAX / 50);
            }
            case 3 -> {
                players.get(id).setX(20 * XMAX / 50);
                players.get(id).setY(YMAX / 50);
            }
            case 4 -> {
                players.get(id).setX(30 * XMAX / 50);
                players.get(id).setY(YMAX / 50);
            }
            case 5 -> {
                players.get(id).setX(40 * XMAX / 50);
                players.get(id).setY(YMAX / 50);
            }
            case 6 -> {
                players.get(id).setX(40 * XMAX / 50);
                players.get(id).setY(YMAX / 3 + YMAX / 50);
            }
            case 7 -> {
                players.get(id).setX(40 * XMAX / 50);
                players.get(id).setY(2 * YMAX / 3 + YMAX / 50);
            }
        }
        // set the position of the cards
        players.get(id).fillGrid();
    }

    /**
     * This method displays the background of the game
     * @param gc the graphics context
     */
    public void drawPlate(GraphicsContext gc){
        // calculate the center of the rectangle
        double centerX = (65*XMAX/50) / 2;
        double centerY = YMAX / 2;
        // calculate the radius of the circle
        double radius = Math.sqrt(centerX * centerX + centerY * centerY);
        // gradient
        RadialGradient gradient = new RadialGradient(0, 0, centerX, centerY, radius-30, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(130, 200, 210)),
                new Stop(1, Color.rgb(39, 120, 180)));

        // fill background
        gc.setFill(gradient);
        gc.fillRect(9*XMAX/50,0, XMAX , YMAX);
    }

    /**
     * This method displays the information of the players on the screen (name, hand points, total points)
     * @param gc the graphics context
     */
    public void drawInfoPlayers(GraphicsContext gc){
        //Instantiating the Light.Distant class
        Light.Distant light = new Light.Distant();

        //Setting the properties of the light source
        light.setAzimuth(45.0);  //Angle of the light in the XY plane in degrees
        light.setElevation(45.0); //Angle of the light in the YZ plane in degrees

        //Instantiating the Lighting class
        Lighting lighting = new Lighting();

        //Setting the source of the light
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);

        gc.setEffect(lighting);
        gc.setFill(Color.rgb(40,120,200));

        gc.fillRect(0,0,19*XMAX/100, YMAX);

        //fill the global rectangle with one rectangle for each player
        for (int i = 0; i < players.size(); i++){
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(0,i*YMAX/8,9*XMAX/50, (i+1)*YMAX/8);
        }
        gc.setEffect(null);

        //Add players name and score
        for (int i = 0; i < players.size(); i++){
            gc.setFont(Font.loadFont(getClass().getResourceAsStream("Gameria.ttf"),XMAX/60));
            gc.setFill(players.get(i).getPlayercolor());
            gc.fillText(players.get(i).getName(), XMAX/50 ,i*YMAX/8+3*YMAX/100);
            gc.setFill(Color.BLACK);
            gc.fillText("Hand Points  "+players.get(i).calculatePoints(),XMAX/50,i*YMAX/8+7*YMAX/100);
            gc.fillText("Total Points  "+players.get(i).getPoints(),XMAX/50,i*YMAX/8+11*YMAX/100);
        }

    }

    /**
     * This method displays the deck on the screen
     * @param gc the graphics context
     */
    public void drawDeck(GraphicsContext gc){
        // set the size of the cards
        for(Card c : deck.getCards()){
            c.setHeight(YMAX/6);
            c.setWidth(XMAX/16);
        }
        // set the position of the deck
        deck.getCards().get(deck.size()-1).setX(19*XMAX/50);
        deck.getCards().get(deck.size()-1).setY(25*YMAX/50);
        // draw the deck
        deck.getCards().get(deck.size()-1).draw(gc);
    }

    /**
     * This method displays the discard on the screen
     * @param gc the graphics context
     */
    public void drawDiscard(GraphicsContext gc){
        // set the size of the cards
        for(Card c : discard.getCards()){
            c.setHeight(YMAX/6);
            c.setWidth(XMAX/16);
        }
        // set the position of the discard
        discard.getCards().get(discard.size()-1).setX(36*XMAX/50);
        discard.getCards().get(discard.size()-1).setY(25*YMAX/50);
        // draw the discard
        discard.getCards().get(discard.size()-1).draw(gc);
    }

    /**
     * This method displays the discard on the screen
     * @param stage is basically all scenes and displayed things in the code are showed on the stage
     */

    @Override
    public void start(Stage stage) {
        try {
            VBox root = new VBox();
            Canvas plate = new Canvas(XMAX, YMAX);
            GraphicsContext gc = plate.getGraphicsContext2D();
            root.getChildren().add(plate);
            deck.setX(XMAX);
            discard.setX(XMAX);

            new AnimationTimer() {
                long lastTick = 0;

                @Override
                public void handle(long now) {
                    if (lastTick == 0) { // This is testing when we exit the code it stops refreshing frames.
                        lastTick = now;
                        tick(gc);
                        return;
                    }
                    // Here we are defining the frequency
                    if (now - lastTick > 1000000000 / 20) { // 1000000000 could be a constant based on your PC power and the more we divide, the faster it will be refreshing
                        lastTick = now;
                        tick(gc);
                    }
                }
            }.start();
            Scene scene = new Scene(root, XMAX, YMAX);

            // Controls for the game
            // ESCAPE to go back to the menu
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.ESCAPE) {
                    gameOver = true;
                    // remove all the cards from the players' hands
                    for (Player p : players) {
                        p.getHand().clear();
                    }
                    // remove all the players from the list
                    players.clear();
                    currentPlayerIndex = 0;
                    lastPlayerIndex = -1;
                    gameState = GameState.ROUND_START;
                }
            });
            // Mouse click to play a card, click on the menu buttons or click on the deck or discard
            scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                // Click on the cards
                if (gameState != GameState.PRE_ROUND && gameState != GameState.WAITING && players.size() > 0) {
                    for (Card c : players.get(currentPlayerIndex).getHand()) {
                        c.clicked(mouseEvent);
                    }
                }
                // Click on the menu buttons
                for(MenuButton menuButton : mainMenu.getAllButtons()){
                    menuButton.clicked(mouseEvent);
                    if(mainMenu.getButtonExitRed().isClicked()){
                        stage.close();
                    }
                }
                // Click on the deck
                if (gameState != GameState.PRE_ROUND && gameState != GameState.ROUND_START && gameState != GameState.DISCARD_CLICK) {
                    deck.getCards().get(deck.size()-1).clicked(mouseEvent);
                }
                // Click on the discard
                if (gameState != GameState.PRE_ROUND && gameState != GameState.ROUND_START) {
                    discard.getCards().get(discard.size()-1).clicked(mouseEvent);
                }
            });

            // Start Game

            // Primary Scene
            stage.setScene(scene);
            stage.setTitle("Skyjo");
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // tick, code that is executed every frame

    public void tick(GraphicsContext gc){
        // If the game is not over, we draw the game
        if (!gameOver) {
            drawPlate(gc);
            drawInfoPlayers(gc);
            for (Player p : players) {
                p.drawHand(gc);
            }
            if (deck.size() >= 1) {
                drawDeck(gc);
            }
            if (discard.size() >= 1) {
                drawDiscard(gc);
            }
            // Display the current player at the center of the screen
            if (currentPlayerIndex != -1 && players.size() > 0) {
                players.get(currentPlayerIndex).displayCenter();
                gc.setFill(Color.BLACK);
                String cp = players.get(currentPlayerIndex).getName();
                gc.fillText("It is the turn of ", (46*XMAX/100), (36*YMAX/100));
                gc.setFill(players.get(currentPlayerIndex).getPlayercolor());
                gc.fillText(cp, (60*XMAX/100), (36*YMAX/100));
            }

            // Play the game
            GameManager.game();
        } else { // If the game is over, we draw the menu
            mainMenu.draw(gc);
            if(mainMenu.getButtonOkGreen().isClicked() && mainMenu.getPlayables().size() > 1){
                mainMenu.getButtonOkGreen().setClicked(false);
                gameOver = false;
                // get the players from the menu, convert them to players or bots and add them to the game
                for(int i = 0; i < mainMenu.getPlayables().size(); i++){
                    // if the player is a bot, we add a bot, else we add a player
                    if (mainMenu.getPlayables().get(i).isBot()){
                        Bot b = new Bot(mainMenu.getPlayables().get(i).getName());
                        players.add(b);
                    } else {
                        Player p = new Player(mainMenu.getPlayables().get(i).getName(), mainMenu.getPlayables().get(i).getPlayercolor());
                        players.add(p);
                    }
                }
                mainMenu= new MainMenu();
                // Deal cards
                deck.deal(players);
                // Pick up the first card of the deck and put it in the discard
                discard.addCard(deck.pick_up_card());
                //make the discard card visible
                discard.setVisible(true);
                // For each player in the game we assigned them a position in function of their position in the list
                for(int i = 0; i < players.size(); i++){
                    displayPlayer(i);
                    //And this is for assigned the position for their cards, so that the cards positions are relatives based on each player position
                    players.get(i).fillGrid();
                }
            }
            if(mainMenu.getButtonPlusBot().isClicked()){
                mainMenu.getButtonPlusBot().setClicked(false);
                if(mainMenu.getPlayables().size() < 8){
                    mainMenu.add_bot();
                }
            }
            if(mainMenu.getButtonPlusPlayer().isClicked()){
                mainMenu.getButtonPlusPlayer().setClicked(false);
                if(mainMenu.getPlayables().size() < 8){
                     mainMenu.add_player();
                }
            }
            for(int i = 0; i< mainMenu.getSuppr_buttons().size(); i++){
                if(mainMenu.getSuppr_buttons().get(i).isClicked()){
                    mainMenu.getSuppr_buttons().get(i).setClicked(false);
                    mainMenu.remove_playable(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        // launch the game
        launch();
    }
}
