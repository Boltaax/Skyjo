package lp2a.game.skyjogame;

import javafx.scene.paint.Color;

import java.util.*;

public class Bot implements Playable{
    private int x;
    private int y;
    private String name;
    private List<Card> hand;
    private int points;
    private Color playercolor = Color.GREY;
    Random random = new Random();
    private Card[][] grid = new Card[4][3];
    String[] botNames = {"Spock","Aziel","Dimension","Athena","Lorelei","Cerberus","Gold","Damon",
            "Abel Tron","Emilia Tron", "Botzilla", "RoboCop", "Circuit", "Cyber", "Droid", "Botanist",
            "Botman", "Botman Begins", "Botanical", "Botarazzi", "Bottle flip",
            "Botany Bay", "Bottoms Up", "Botanica",
            "Botiful Mind", "Botsicle"};

    public Bot() {
        this.points = 0;
        this.name = botNames[random.nextInt(botNames.length)];
        this.hand = new ArrayList<>();
    }

    public Bot(String name) {
        this.points = 0;
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Color getPlayercolor() {
        return playercolor;
    }
}
