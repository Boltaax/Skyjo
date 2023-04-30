
package lp2a.game.skyjogame;

import java.util.ArrayList;
import java.util.List;

import static lp2a.game.skyjogame.Skyjo.XMAX;
import static lp2a.game.skyjogame.Skyjo.YMAX;

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

    public void game(){
        // Initialisation
        // Create the deck
        CardDeck deck = new CardDeck(false, 20*XMAX/50, 20*YMAX/50);
        // Deal the cards
        deck.deal(players);
        // Create the draw pile
        CardDeck discard = new CardDeck(true, 36*XMAX/50, 20*YMAX/50);
        // Add the first card of the discard pile to the draw pile
        Card firstCard = deck.pick_up_card();
        discard.addCard(firstCard);
        firstCard.setVisible(true);
        /*
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
            // Wait for the player to click on the deck or the discard pile
            while (!deck.isClicked() && !discard.isClicked()) { // not good
                // Attendre le click du joueur sur la pile de draw ou sur la pile de discard
            }

            if (discard.isClicked()) {
                // Attendre le click du joueur sur une carte de sa main
                Card clickedCard = currentPlayer().clickOnCard();
                // Ajouter la carte de la pile de draw a la main du joueur
                currentPlayer().replaceCard(clickedCard, discard.pick_up_card());
                // Ajouter la carte de la main du joueur a la pile de discard
                discard.addCard(clickedCard);
                deck.setClicked(false);
            } else if (deck.isClicked()) {
                // Add the card to the discard pile
                discard.addCard(deck.pick_up_card());
                // Attendre le click du joueur sur une carte de sa main ou sur la pile de discard
                while (!discard.isClicked() && !currentPlayer().hasClickedOnCard()) { // not good
                    // Attendre le click du joueur sur la pile de draw ou sur la pile de discard
                }
                if (discard.isClicked()) {
                    // Attendre le click du joueur sur une carte de sa main
                    Card clickedCard = currentPlayer().clickOnCard();
                    if (clickedCard != null) {
                        // Ajouter la carte de la pile de draw a la main du joueur
                        currentPlayer().replaceCard(clickedCard, discard.pick_up_card());
                        // Ajouter la carte de la main du joueur a la pile de discard
                        discard.addCard(clickedCard);
                    }
                } else {
                    // Attendre le click du joueur sur une carte de sa main
                    Card clickedCard = currentPlayer().clickOnCard();
                    if (clickedCard != null) {
                        // Si la carte est visible
                        if (clickedCard.isVisible()) {
                            // Ajouter la carte de la pile de draw a la main du joueur
                            currentPlayer().replaceCard(clickedCard, deck.pick_up_card());
                            // Ajouter la carte de la main du joueur a la pile de discard
                            discard.addCard(clickedCard);
                        } else {
                            // Reveler la carte du joueur
                            clickedCard.setVisible(true);
                        }
                    }
                }
                deck.setClicked(false);
            }

            // Next Player
            if(CurrentPlayerIndex < players.size()-1){
                CurrentPlayerIndex++;
            } else {
                CurrentPlayerIndex = 0;
            }
        }
         */
    }

    public static void discardExchange(Player player, Card c, CardDeck discard) {
        player.replaceCard(c, discard.pick_up_card());
        discard.addCard(c);
        // make the first card of the discard pile visible
        discard.setVisible(true);
    }

    public static void resetPlayerCardsClick(Player player) {
        for (Card c : player.getHand()) {
            c.setClicked(false);
        }
    }

    public static int nextPlayer(List<Player> players, int CurrentPlayerIndex) {
        int nextPlayerIndex;
        if(CurrentPlayerIndex < players.size()-1){
            nextPlayerIndex = CurrentPlayerIndex + 1;
        } else {
            nextPlayerIndex = 0;
        }
        return nextPlayerIndex;
    }
}
