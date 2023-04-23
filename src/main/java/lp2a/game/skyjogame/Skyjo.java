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
import java.util.List;


public class Skyjo extends Application {
    static boolean gameOver = false;
    static Screen screen = Screen.getPrimary();
    static int XMAX = (int) screen.getBounds().getWidth();
    static int YMAX = (int) screen.getBounds().getHeight();
    private static List<Player> players = new ArrayList<>();
    private static List<Bot> bots = new ArrayList<>();
    private CardDeck deck = new CardDeck(false, 20*XMAX/50, 20*YMAX/50);
    private CardDeck discard = new CardDeck(true, 36*XMAX/50, 20*YMAX/50);
    private static int currentPlayerIndex = 0;
    private int turn = 0;
    private Menu menu = new Menu();
    private MainMenu mainMenu = new MainMenu();

    private boolean isGameFinished() {
        for (Player player : players) {
            if (player.getPoints() <= 100) {
                return false;
            }
        }
        return true;
    }
    public void displayPlayer(int id){
        for(Card c : players.get(id).getHand()){
            c.setWidth(XMAX/25);
            c.setHeight(YMAX/10);
            c.setHeight(YMAX/10);
        }
        switch (id){
            case 0:
                players.get(id).setX(10*XMAX/50);
                players.get(id).setY(2*YMAX/3+YMAX/50);
                break;
            case 1 :
                players.get(id).setX(10*XMAX/50);
                players.get(id).setY(YMAX/3+YMAX/50);
                break;
            case 2 :
                players.get(id).setX(10*XMAX/50);
                players.get(id).setY(YMAX/50);
                break;
            case 3 :
                players.get(id).setX(20*XMAX/50);
                players.get(id).setY(YMAX/50);
                break;
            case 4 :
                players.get(id).setX(30*XMAX/50);
                players.get(id).setY(YMAX/50);
                break;
            case 5 :
                players.get(id).setX(40*XMAX/50);
                players.get(id).setY(YMAX/50);
                break;
            case 6 :
                players.get(id).setX(40*XMAX/50);
                players.get(id).setY(YMAX/3+YMAX/50);
                break;
            case 7 :
                players.get(id).setX(40*XMAX/50);
                players.get(id).setY(2*YMAX/3+YMAX/50);
                break;
        }
        players.get(id).fillGrid();
    }

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
            gc.setFont(Font.loadFont(getClass().getResourceAsStream("Pixelade.ttf"),XMAX/50));
            gc.setFill(players.get(i).getPlayercolor());
            gc.fillText(players.get(i).getName(), XMAX/50 ,i*YMAX/8+3*YMAX/100);
            gc.setFill(Color.BLACK);
            gc.fillText("Hand Points : "+players.get(i).calculatePoints(),XMAX/50,i*YMAX/8+7*YMAX/100);
            gc.fillText("Total Points : "+players.get(i).getPoints(),XMAX/50,i*YMAX/8+11*YMAX/100);
        }

    }

    public void drawDeck(GraphicsContext gc){
        for(Card c : deck.getCards()){
            c.setHeight(YMAX/6);
            c.setWidth(XMAX/16);
        }
        deck.getCards().get(deck.size()-1).setX(19*XMAX/50);
        deck.getCards().get(deck.size()-1).setY(25*YMAX/50);
        deck.getCards().get(deck.size()-1).draw(gc);
    }

    public void drawDiscard(GraphicsContext gc){
        for(Card c : discard.getCards()){
            c.setHeight(YMAX/6);
            c.setWidth(XMAX/16);
        }
        discard.getCards().get(discard.size()-1).setX(36*XMAX/50);
        discard.getCards().get(discard.size()-1).setY(25*YMAX/50);
        discard.getCards().get(discard.size()-1).draw(gc);
    }


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
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / 2) {
                        lastTick = now;
                        tick(gc);
                    }
                }
            }.start();
            Scene scene = new Scene(root, XMAX, YMAX);

            // Controls
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.ESCAPE) {
                    gameOver = true;
                    menu.show();
                }
            });
            scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                for (Card c : players.get(currentPlayerIndex).getHand()) {
                    c.clicked(mouseEvent);
                }

                for(MenuButton menuButton : mainMenu.getAllButtons()){
                    menuButton.clicked(mouseEvent);
                    if(mainMenu.getButtonExitRed().isClicked()){
                        stage.close();
                    }
                }
                deck.getCards().get(deck.size()-1).clicked(mouseEvent);
                discard.getCards().get(discard.size()-1).clicked(mouseEvent);

            });


            // Start Game
            for (int i = 0; i<8; i++){
                String playername = "Player "+(i+1);
                Player p = new Player(playername);
                players.add(p);
            }
            deck.deal(players);
            discard.addCard(deck.pick_up_card());
            //make the discard card visible
            discard.getCards().get(discard.size()-1).setVisible(true);

            // For each player in the game we assigned them a position in function of their position in the list
            for(int i = 0; i < players.size(); i++){
                displayPlayer(i);
                //And this is for assigned the position for their cards, so that the cards positions are relatives based on each player position
                players.get(i).fillGrid();
            }


            // Primary Scene
            stage.setScene(scene);
            stage.setTitle("Skyjo");
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // tick
    public void tick(GraphicsContext gc){

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
        } else {
            mainMenu.draw(gc);
            if(mainMenu.getButtonOkGreen().isClicked()){
                mainMenu.getButtonOkGreen().setClicked(false);
                gameOver = false;
            }
            if(mainMenu.getButtonPlusBot().isClicked()){
                mainMenu.getButtonPlusBot().setClicked(false);
                if(players.size() + bots.size() < 8 ){
                    bots.add(new Bot());
                }
            }
            if(mainMenu.getButtonMinusBot().isClicked()){
                mainMenu.getButtonMinusBot().setClicked(false);
                if(bots.size() >= 1 ){
                    bots.remove(0);
                }
            }
        }

        // The code below will be implemented with functions inside the Multiplayer class

        players.get(currentPlayerIndex).displayCenter();

        // Round 0
        if (turn == 0) {
            //make the current player choose 2 cards
            List<Card> chosenCards = new ArrayList<>();
            for (Card c : players.get(currentPlayerIndex).getHand()){
                if (c.isClicked() && chosenCards.size() < 2){
                    chosenCards.add(c);
                    c.setVisible(true);
                }
            }
            // go to the next player if the current player has chosen 2 cards
            if (chosenCards.size() == 2 && currentPlayerIndex < players.size()-1) {
                displayPlayer(currentPlayerIndex);
                currentPlayerIndex++;
            } else if (chosenCards.size() == 2 && currentPlayerIndex == players.size()-1){
                // count the points of each player, the player with the highest score will start the next round, if there is a tie, the first player will start the next round
                int max = 0;
                Player firstPlayer = null;
                displayPlayer(currentPlayerIndex);
                for (Player p : players){
                    if (p.calculatePoints() >= max){
                        if (p.calculatePoints() == max){
                            // the player with the lowest index will start the next round
                            if (players.indexOf(p) < players.indexOf(firstPlayer)){
                                firstPlayer = p;
                            }
                        } else {
                            max = p.calculatePoints();
                            firstPlayer = p;
                        }
                    }
                }
                currentPlayerIndex = players.indexOf(firstPlayer);
                turn++;
                // reset the clicked state of the cards
                for (Player p : players){
                    for (Card c : p.getHand()){
                        c.setClicked(false);
                    }
                }
                // debug message show the index of the player who will start the next round
                System.out.println("Player "+(currentPlayerIndex+1)+" will start the next round");
            }
        } else {
            // Round 1
            // exchange a card from the player's hand with the card on the discard pile
            if (discard.getCards().get(discard.size()-1).isClicked()) {
                // make the player choose one card from his hand
                for (Card c : players.get(currentPlayerIndex).getHand()) {
                    if (c.isClicked()) {
                        // exchange the chosen card with the card on the discard pile
                        players.get(currentPlayerIndex).replaceCard(c, discard.pick_up_card());
                        discard.addCard(c);
                        // make the discard card visible
                        discard.getCards().get(discard.size() - 1).setVisible(true);
                        // reset the clicked state of the cards
                        for (Card card : players.get(currentPlayerIndex).getHand()) {
                            card.setClicked(false);
                        }
                        discard.setClicked(false);
                        // go to the next player
                        displayPlayer(currentPlayerIndex);
                        if (currentPlayerIndex < players.size() - 1) {
                            currentPlayerIndex++;
                        } else {

                            currentPlayerIndex = 0;
                            turn++;
                        }
                    }
                }
            }
            else
            // exchange a card from the player's hand with the card on the top of the deck or reveal a card of the player hand
            if (deck.getCards().get(deck.size()-1).isClicked()){
                // put the card on the top of the deck on the discard pile
                discard.addCard(deck.pick_up_card());
                // make the discard card visible
                discard.getCards().get(discard.size()-1).setVisible(true);
                // if the player click on the discard pile, he will exchange a card from his hand with the card on the discard pile
                if (discard.getCards().get(discard.size()-1).isClicked()) {
                    // make the player choose one card from his hand
                    for (Card c : players.get(currentPlayerIndex).getHand()) {
                        if (c.isClicked()) {
                            // exchange the chosen card with the card on the discard pile
                            players.get(currentPlayerIndex).replaceCard(c, discard.pick_up_card());
                            discard.addCard(c);
                            // make the discard card visible
                            discard.getCards().get(discard.size()-1).setVisible(true);
                            // reset the clicked state of the cards
                            for (Card card : players.get(currentPlayerIndex).getHand()) {
                                card.setClicked(false);
                            }
                            discard.setClicked(false);
                            // go to the next player
                            displayPlayer(currentPlayerIndex);
                            if (currentPlayerIndex < players.size()-1) {
                                currentPlayerIndex++;
                            } else {
                                currentPlayerIndex = 0;
                                turn++;
                            }
                        }
                    }
                }
                else {
                    // if the player click on a card of his hand which is not visible, he will reveal it
                    for (Card c : players.get(currentPlayerIndex).getHand()) {
                        if (c.isClicked() && !c.isVisible()) {
                            c.setVisible(true);
                            // reset the clicked state of the cards
                            for (Card card : players.get(currentPlayerIndex).getHand()) {
                                card.setClicked(false);
                            }
                            // go to the next player
                            displayPlayer(currentPlayerIndex);
                            if (currentPlayerIndex < players.size() - 1) {
                                currentPlayerIndex++;
                            } else {
                                currentPlayerIndex = 0;
                                turn++;
                            }
                        }
                    }
                    // reset the clicked state
                    deck.setClicked(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
