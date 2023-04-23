package lp2a.game.skyjogame;

import java.util.*;

public class Bot {
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

    public void addPoints(int points)
    {
        this.points =+ points;
    }

    public int getPoints() {
        return points;
    }

    public Card[][] getGrid() {
        return grid;
    }

    public void play ()
    {
        int value = Skyjo.getDiscard().getCards().get(Skyjo.getDiscard().size()-1).getValue();
        select_card(value);
        if ( this.x + this.y <0) // The value is worse so take a new card
        {
            //TODO : mettre l'ancien carte dans la defausse

            //TODO : play card in the "pioche"
            grid[x][y] =
        }
        else
        {
            value = Skyjo.getDeck().getCards().get(Skyjo.getDeck().size()-1).getValue();
            select_card(value);
            if ( this.x + this.y <0) // The value is worse so take a new card
            {
                //TODO : mettre l'ancien carte dans la defausse

                //TODO : play card in the "pioche"
                grid[x][y] =
            }
            else
            {
                //TODO : mettre la carte pioché dans la defausse
                //TODO : return a card
            }

        }


    }


    // This function select the card to play is the grobal x and y coordinate
    private void select_card(int value)
    {
        int select_x = 0, select_y = 0;


        for (int x = 0; x <=4; x++) // Find and select the lowest Card of the bot grid with x and y coordinate
        {
            for (int y = 0; y <=4; y++)
            {
                if (grid[x][y].getValue() > grid[select_x][select_y].getValue())
                {
                    select_y = y;
                    select_x = x;
                }
            }
        }
        if (grid[select_x][select_y].getValue() < value) // verification if the lowest card is lower than the new card
        {
            this.x = x;
            this.y = y;
        }
        else  // Set -1 if the value is worse
        {
            this.x = -1;
            this.y = -1;
        }
    }
}
