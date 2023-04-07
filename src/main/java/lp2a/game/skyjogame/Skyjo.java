package lp2a.game.skyjogame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Skyjo extends Application {

    static int turn;
    static boolean gameOver = false;
    static int XMAX = 1500;
    static int YMAX = 800;
    private List<Player> players = new ArrayList<>();
    private CardDeck deck = new CardDeck();
    private Menu menu = new Menu();
    private int currentPlayerIndex = 0;

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
            case 0:
                players.get(id).setX(600);
                players.get(id).setY(450);
                break;
            case 1 :
                players.get(id).setX(600);
                players.get(id).setY(0);
                break;
        }
    }

    public void drawPlate(GraphicsContext gc){
        // calculate the center of the rectangle
        double centerX = XMAX / 2;
        double centerY = YMAX / 2;
        // calculate the radius of the circle
        double radius = Math.sqrt(centerX * centerX + centerY * centerY);
        // gradient
        RadialGradient gradient = new RadialGradient(0, 0, centerX, centerY, radius-30, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(130, 200, 210)),
                new Stop(1, Color.rgb(39, 120, 120)));

        // fill background
        gc.setFill(gradient);
        gc.fillRect(0,0, XMAX , YMAX);
    }


    @Override
    public void start(Stage stage) {
        try {
            VBox root = new VBox();
            Canvas plate = new Canvas(XMAX, YMAX);
            GraphicsContext gc = plate.getGraphicsContext2D();
            root.getChildren().add(plate);

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


            // Start Game
            Player p = new Player("Bob");
            players.add(p);
            deck.deal(players);

            for(int i = 0; i < players.size(); i++){
                displayPlayer(i);
                players.get(i).fillGrid();
                System.out.println("x: "+players.get(i).getX()+" y: "+players.get(i).getY());
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

        drawPlate(gc);
        players.get(0).drawHand(gc);
    }

    public static void main(String[] args) {
        launch();
    }
}
