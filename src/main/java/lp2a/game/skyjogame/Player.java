package lp2a.game.skyjogame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int x;
    private int y;
    private String name;
    private List<Card> hand;
    private int points;
    private Card[][] grid = new Card[4][3];
    private Color playercolor = Color.LIGHTBLUE;

    public Player() {
        this.points = 0;
        this.name = "Vous";
        this.hand = new ArrayList<>();
    }

    public Player(String name) {
        this.points = 0;
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public boolean getAllVisibleCards(){
        for (Card card : hand) {
            if(!card.isVisible()){
                return false;
            }
        }
        return true;
    }

    public List<Card> getHand() {
        return hand;
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
    public void setPoints(int points){
        this.points = points;
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
    public Color getPlayercolor() {
        return playercolor;
    }
    public void setPlayercolor(Color playercolor) {
        this.playercolor = playercolor;
    }

    public void fillGrid(){
        int i = 0;
        int j = 0;
        for (Card card : hand){ // For each card in the hand of a player
            if(j==3) { //We verify if the position of the card is at the end of the column
                j = 0;
                i++; //If yes we go the next column
            }
            grid[i][j] = card; // We define the position of the card in the grid of each player (he's hand)
            card.setX(i*(card.getWidth()+5)+ x); // We define the position of the card for the display
            card.setY(j*(card.getHeight()+5)+ y);
            j++; // Adding 1 to the position in the line
        }
    }

    public String getName() {
        return this.name;
    }

    public int calculatePoints() {
        int totalPoints = 0;
        for (Card card : hand) {
            if(card.isVisible()){
                totalPoints += card.getValue();
            }
        }
        return totalPoints;
    }

    public void drawHand(GraphicsContext gc){
        for(Card c : hand){
            c.draw(gc);
        }
    }

    // Choose n cards from the hand by clicking on them
    public List<Card> chooseCards(int nbr){
        List<Card> chosenCards = new ArrayList<>();
        // We wait for the player to click on the cards he wants to choose
        while(chosenCards.size() < nbr){
            for(Card c : hand){
                if(c.isClicked()){
                    chosenCards.add(c);
                    c.setClicked(false);
                }
            }
        }
        return chosenCards;
    }

    public Card clickOnCard(){
        while (true) {
            for (Card c : hand) {
                if (c.isClicked()) {
                    c.setClicked(false);
                    return c;
                }
            }
        }
    }

    public boolean hasClickedOnCard(){
        for (Card c : hand) {
            if (c.isClicked()) {
                c.setClicked(false);
                return true;
            }
        }
        return false;
    }

    public void replaceCard(Card card, Card newCard){
        // We replace the card in the hand
        hand.remove(card);
        hand.add(newCard);
        // We replace the card in the grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == card){
                    grid[i][j] = newCard;
                    newCard.setX(i*(newCard.getWidth()+5)+ x);
                    newCard.setY(j*(newCard.getHeight()+5)+ y);
                }
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}