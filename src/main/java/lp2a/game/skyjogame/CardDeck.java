package lp2a.game.skyjogame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    // Attributes
    private List<Card> cards;
    private int x; // x position on the screen
    private int y; // y position on the screen

    // Methods

    /**
     * Constructor of the CardDeck class
     * @param is_empty if the deck is empty or not, if it's not empty, the deck is filled with all the cards
     * @param x x position on the screen
     * @param y y position on the screen
     */
    public CardDeck(boolean is_empty, int x, int y) {
        // Set the position of the deck
        this.x = x;
        this.y = y;
        // If the deck is not empty, fill it with all the cards
        if(!is_empty){
            cards = new ArrayList<>();
            // Add 1 to 12 numbers cards
            for (int i = 1; i <= 12; i++) {
                for (int j = 0; j < 10; j++) { // There are 10 cards of each number
                    cards.add(new Card(i, Integer.toString(i)));
                }
            }

            //Settings the number of cards that can be picked in the game
            for (int i = 1; i<15; i++){
                Skyjo.possible_cards[i] = 10;
            }
            Skyjo.possible_cards[0] = 5;
            Skyjo.possible_cards[2] = 15;

            // Add 0's cards
            for(int i = 0; i<15; i++){
                cards.add(new Card(0, "0"));
            }

            // Add negatives card
            for(int i = 0; i<10; i++){
                cards.add(new Card(-1, "-1"));
            }
            for(int i = 0; i<5; i++){
                cards.add(new Card(-2, "-2"));
            }
            // Shuffle the deck
            shuffle();
        } else { // If the deck is empty, create an empty deck
            cards = new ArrayList<>();
        }
    }

    /**
     * This method check if the deck is clicked
     * @return true if the deck is clicked, false otherwise
     */
    public boolean isClicked() {
        if (this.getCards().size() > 0) {
            // Return the clicked attribute of the last card of the deck
            return this.getCards().get(this.size() - 1).isClicked();
        } else {
            return false;
        }
    }

    /**
     * This method set the clicked attribute of the last card of the deck (the one which will be on the top of the deck)
     * @param clicked the new value of the clicked attribute
     */
    public void setClicked(boolean clicked) {
        if (this.getCards().size() > 0) {
            // Set the clicked attribute of the last card of the deck
            this.getCards().get(this.size() - 1).setClicked(clicked);
        }
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * This method pick up the last card of the deck (the one which will be on the top of the deck)
     * @return the last card of the deck or null if the deck is empty
     */
    public Card pickUpCard(){
        // return the last card of the deck, and remove it from the deck if it's not empty
        if (cards.size() > 0) {
            // return the last card of the deck and remove it from the deck
            return cards.remove(cards.size() - 1);
        } else {
            return null;
        }
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * This method deal 12 cards to each player
     * @param players the list of players
     */
    public void deal(List<Player> players) {
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++ ){
                    player.addCard(cards.remove(0));
                }
            }
        }
    }
    public int size() {
        return cards.size();
    }

    /**
     * This method set the visibility of the last card of the deck (the one which will be on the top of the deck)
     */
    public void setVisible(boolean visible) {
        // make the first card visible
        cards.get(cards.size()-1).setVisible(visible);
    }
}
