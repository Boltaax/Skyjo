package lp2a.game.skyjogame;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private List<Card> cards;
    private int x;
    private int y;
    private boolean clicked = false;

    public CardDeck(boolean is_empty, int x, int y) {
        this.x = x;
        this.y = y;
        if(!is_empty){
            cards = new ArrayList<>();
            // Add 1 to 12 numbers cards
            for (int i = 1; i <= 12; i++) {
                for (int j = 0; j < 10; j++) { // Il y a 10 cartes de chaque numéro
                    cards.add(new Card(i, Integer.toString(i)));
                }
            }

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
            shuffle();

        }
        else {
            cards = new ArrayList<>();
        }
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void clicked(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        int width = Skyjo.XMAX/25;
        int height = Skyjo.YMAX/10;
        if (x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
            this.clicked = true;
        }
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card pick_up_card(){
        Card c = cards.get(cards.size()-1);
        cards.remove(cards.get(cards.size()-1));
        return c;
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

    //1st deal
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
}
