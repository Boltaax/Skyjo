
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

    private void game(){
        // Initialisation
        // Create the deck
        CardDeck deck = new CardDeck(false);
        // Deal the cards
        deck.deal(players);
        // Create the draw pile
        CardDeck discard = new CardDeck(true);
        // Add the first card of the discard pile to the draw pile
        discard.addCard(deck.pick_up_card());

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

        // round 1
        while(currentPlayer().getHandSize() != 0 || !currentPlayer().getAllVisibleCards()){
            // Soit click sur la pile de draw et click sur une carte de sa main, la carte de la pile est ajoute a la main du joueur et la carte de la main est ajoute a la pile de draw
            // Soit click sur la pile de discard, une carte et revele sur la pile de draw et le joueur click sur une carte de sa main
            // Si la carte est visible, la carte de la pile est ajoute a la main du joueur et la carte de la main est ajoute a la pile de draw
            // Sinon, le joueur choisi de l'echanger ou non? Si oui, la carte de la pile est ajoute a la main du joueur et la carte de la main est ajoute a la pile de draw. Si non, la carte du joueur est revelee

            if (deck.isClicked()) {
                // Attendre le click du joueur sur une carte de sa main
                Card clickedCard = currentPlayer().clickOnCard();
                if (clickedCard != null) {
                    // Ajouter la carte de la pile de draw a la main du joueur
                    currentPlayer().addCard(deck.pick_up_card()); //add a replace method instead
                    // Ajouter la carte de la main du joueur a la pile de discard
                    discard.addCard(clickedCard);
                }
                deck.setClicked(false);
            } else if (discard.isClicked()) {
                // Attendre le click du joueur sur une carte de sa main
                Card clickedCard = currentPlayer().clickOnCard();
                if (clickedCard != null) {
                    // Si la carte est visible
                    if (clickedCard.isVisible()) {
                        // Ajouter la carte de la pile de draw a la main du joueur
                        currentPlayer().addCard(deck.pick_up_card()); //add a replace method instead
                        // Ajouter la carte de la main du joueur a la pile de discard
                        discard.addCard(clickedCard);
                    } else {
                        /*
                        // Attendre le click du joueur sur "echanger" ou "ne pas echanger"
                        if (askForExchange()) {
                            // Ajouter la carte de la pile de discard a la main du joueur
                            // Ajouter la carte de la main du joueur a la pile de discard
                        } else  {
                            // Revele la carte du joueur
                        }

                         */
                    }
                    discard.setClicked(false);
                }
            }

            // Next Player
            if(CurrentPlayerIndex < players.size()+bots.size()-1){
                CurrentPlayerIndex++;
            } else {
                CurrentPlayerIndex = 0;
            }
        }
    }

}
