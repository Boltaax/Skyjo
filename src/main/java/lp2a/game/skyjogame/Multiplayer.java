package lp2a.game.skyjogame;

import java.util.ArrayList;
import java.util.List;

public class Multiplayer {
    private List<Player> players;
    private List<Bot> bots;
    private int CurrentPlayerIndex = 0;

    public Multiplayer(List<Player> players){
        this.players = players;
    }

    public Multiplayer(List<Player> players, List<Bot> bots){
        this.players = players;
        this.bots = bots;
    }

    private Player currentPlayer(){
        return players.get(CurrentPlayerIndex);
    }

    public void pregame(){
        // Create the deck
        CardDeck deck = new CardDeck(false);
        deck.shuffle();
        // Deal the cards
        deck.deal(players);
        // Create the discard pile
        CardDeck discard = deck;
        // Create the draw pile
        CardDeck draw = new CardDeck(true);
        // Add the first card of the discard pile to the draw pile
        draw.addCard(discard.pick_up_card());

        // round 0 : all players choose 2 cards from their hand, the one with the most points starts
        int maxPoints = 0;
        for (Player player : players) {
            // CLIC SUR 2 CARTES
            List<Card> cards = player.chooseCards(2);
            // COMPTE LE NOMBRE DE PTS
            int pts = 0;
            for (Card card : cards) {
                pts += card.getValue();
            }
            player.setPoints(pts);
            // SI PLUS DE PTS : MAXPOINTS = PTS
            if (pts > maxPoints) maxPoints = pts;
            else if (pts == maxPoints) maxPoints = 0;
            // SI MAXPOINTS = 0 : CURRENTPLAYERINDEX = 0
            if (maxPoints == 0) CurrentPlayerIndex = 0;
                // SINON : CURRENTPLAYERINDEX = INDEX DU JOUEUR AVEC LE PLUS DE PTS
            else CurrentPlayerIndex = players.indexOf(player);
        }
        game();
    }

    private void game(){
        // round 1
        while(currentPlayer().getHandSize() != 0 || !currentPlayer().getAllVisibleCards()){
            // CLIC SUR LE DECK OU NON
            // CLIC SUR UNE CARTE DE TA MAIN
            // SI VISIBLE : ECHANGE DERNIERE CARTE DEFAUSSE
            // SI NON  : AFFICHE CARTE


            // Next Player
            if(CurrentPlayerIndex < players.size()+bots.size()-1){
                CurrentPlayerIndex++;
            } else {
                CurrentPlayerIndex = 0;
            }
        }
    }

}
