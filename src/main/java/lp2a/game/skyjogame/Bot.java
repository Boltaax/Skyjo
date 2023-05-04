package lp2a.game.skyjogame;

import java.util.*;

public class Bot implements Playable{
    private int x;
    private int y;
    private String name;
    private List<Card> hand;
    private int points;
    Random random = new Random();
    private Card[][] grid = new Card[4][3];
    String[] botNames = {"Spock","Aziel","Dimension","Athena","Lorelei","Cerberus","Gold","Damon",
            "Abel Tron","Emilia Tron", "Botzilla", "RoboCop", "Circuit", "Cyber", "Droid", "Botanist",
            "Botman", "Botty McBotface", "Botman Begins", "Botanical", "Botarazzi", "Bot-tle Rocket",
            "Botany Bay", "Botanist Prime", "Bot-tom's Up", "Botanica", "Botanique Elite",
            "Bot-iful Mind", "Bot-sicle", "Bot-hemian Rhapsody"};

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
}
