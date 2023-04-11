package lp2a.game.skyjogame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private List<Card> cards;

    public CardDeck(boolean is_empty) {
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


    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card pick_up_card(){
        Card c = cards.get(cards.size()-1);
        cards.remove(cards.get(cards.size()-1));
        return c;
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
