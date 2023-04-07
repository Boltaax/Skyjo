package lp2a.game.skyjogame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private List<Card> cards;

    public CardDeck() {
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

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
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
