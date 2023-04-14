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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Skyjo extends Application {
    static boolean gameOver = false;
    static int XMAX = 1500;
    static int YMAX = 800;
    private List<Player> players = new ArrayList<>();
    private CardDeck deck = new CardDeck(false);
    private CardDeck discard = new CardDeck(true);
    private Menu menu = new Menu();

    private boolean isGameFinished() {
        for (Player player : players) {
            if (player.getPoints() <= 100) {
                return false;
            }
        }
        return true;
    }

    public void displayPlayer(int id){
        switch (id){
            case 6:
                players.get(id).setX(10*XMAX/50);
                players.get(id).setY(2*YMAX/3+YMAX/50);
                break;
            case 4 :
                players.get(id).setX(10*XMAX/50);
                players.get(id).setY(YMAX/3+YMAX/50);
                break;
            case 2 :
                players.get(id).setX(10*XMAX/50);
                players.get(id).setY(YMAX/50);
                break;
            case 0 :
                players.get(id).setX(20*XMAX/50);
                players.get(id).setY(YMAX/50);
                break;
            case 1 :
                players.get(id).setX(30*XMAX/50);
                players.get(id).setY(YMAX/50);
                break;
            case 3 :
                players.get(id).setX(40*XMAX/50);
                players.get(id).setY(YMAX/50);
                break;
            case 5 :
                players.get(id).setX(40*XMAX/50);
                players.get(id).setY(YMAX/3+YMAX/50);
                break;
            case 7 :
                players.get(id).setX(40*XMAX/50);
                players.get(id).setY(2*YMAX/3+YMAX/50);
                break;
        }
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
        deck.getCards().get(deck.size()-1).setX(20*XMAX/50);
        deck.getCards().get(deck.size()-1).setY(20*YMAX/50);
        deck.getCards().get(deck.size()-1).draw(gc);
    }

    public void drawDiscard(GraphicsContext gc){
        discard.getCards().get(discard.size()-1).setX(36*XMAX/50);
        discard.getCards().get(discard.size()-1).setY(20*YMAX/50);
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
                for(Player p : players){
                    for (Card c : p.getHand()){
                        c.clicked(mouseEvent);
                    }
                }
            });


            // Start Game
            for (int i = 0; i<8; i++){
                String playername = "Player "+(i+1);
                Player p = new Player(playername);
                players.add(p);
            }
            deck.deal(players);
            discard.addCard(deck.pick_up_card());

            // For each player in the game me assigned them a position in function of their position in the list
            for(int i = 0; i < players.size(); i++){
                displayPlayer(i);
                //And this is for assigned the position for their cards, so that the cards positions are relatives based on each player position
                players.get(i).fillGrid();
            }


            // Primary Scene
            stage.setScene(scene);
            stage.setTitle("Skyjo");
            //menu.show();
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // tick
    public void tick(GraphicsContext gc){

        drawPlate(gc);
        drawInfoPlayers(gc);
        for (Player p : players){
            p.drawHand(gc);
        }
        if (deck.size() >= 1){
            drawDeck(gc);
        }
        if (discard.size() >= 1){
            drawDiscard(gc);
        }


    }

    public static void main(String[] args) {
        launch();
    }
}
