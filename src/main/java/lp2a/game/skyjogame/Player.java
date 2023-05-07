package lp2a.game.skyjogame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Player implements Playable {
    // Attributes
    private int x; // x position on the screen
    private int y; // y position on the screen
    private String name;
    private List<Card> hand;
    private int points;
    private Card[][] grid = new Card[4][3]; // 4 rows and 3 columns
    private Color playercolor = Color.RED;

    // Methods
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

    /**
     * This method check if the player has all the cards visible in his hand
     */
    public boolean getAllVisibleCards(){
        for (Card card : hand) {
            if(!card.isVisible()){
                return false;
            }
        }
        return true;

        /* for debug
        int count = 0;
        for (Card card : hand) {
            if(card.isVisible()){
                count++;
            }
        }
        return count == 2;
        */
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

    /**
     * This method display first the grid of the player and set the position of each card in the grid
     */
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

    /**
     * This method calculate the points of the hand of the player
     * @return the total points of the hand
     */
    public int calculatePoints() {
        int totalPoints = 0;
        for (Card card : hand) {
            if(card.isVisible()){
                totalPoints += card.getValue();
            }
        }
        return totalPoints;
    }

    /**
     * This method display the hand of the player
     * @param gc the graphic context
     */
    public void drawHand(GraphicsContext gc){
        for(Card c : hand){
            c.draw(gc);
        }
    }

    /**
     * This method replace a card of the hand by another card
     * @param card the card to replace
     * @param newCard the new card
     */
    public void replaceCard(Card card, Card newCard){
        // We get the index of the card to replace
        int index = hand.indexOf(card);
        // We remove the card to replace, and we add the new card at the same index
        hand.remove(card);
        hand.add(index, newCard);
    }

    /**
     * This method display the hand of the player in the center of the screen
     */
    public void displayCenter(){
        // We set the position of the player's hand in the center of the screen
        this.x = (45*Skyjo.XMAX/100);
        this.y = (40*Skyjo.YMAX/100);
        // We set the size of the cards
        for(Card c : this.hand){
            c.setHeight(Skyjo.YMAX/6);
            c.setWidth(Skyjo.XMAX/16);
        }
        // We set the position of the cards in the hand
        fillGrid();
    }

    @Override
    public String toString() {
        return name;
    }
}