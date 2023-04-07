package lp2a.game.skyjogame;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int x;
    private int y;
    private String name;
    private List<Card> hand;
    private int points;
    private Card[][] grid = new Card[4][3];

    public Player() {
        this.points = 0;
        this.name = "test";
        this.hand = new ArrayList<>();
    }

    public Player(String name) {
        this.points = 0;
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }
    public Card removeCard(int index) {
        return this.hand.remove(index);
    }
    public Card getCard(int index) {
        return this.hand.get(index);
    }
    public int getHandSize() {
        return this.hand.size();
    }
    public int getPoints(){
        return this.points;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void fillGrid(){
        int i = 0;
        int j = 0;
        for (Card card : hand){
            if(j==3) {
                j = 0;
                i++;
            }
            grid[i][j] = card;
            card.setX(i*(card.getWidth()+5)+ x);
            card.setY(j*(card.getHeight()+5)+ y);
            j++;
        }
    }

    public String getName() {
        return this.name;
    }

    public int calculatePoints() {
        int totalPoints = 0;
        for (Card card : hand) {
            totalPoints += card.getValue();
        }
        return totalPoints;
    }

    public void drawHand(GraphicsContext gc){
        for(Card c : hand){
            c.draw(gc);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}