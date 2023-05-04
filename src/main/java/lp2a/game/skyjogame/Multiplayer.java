
package lp2a.game.skyjogame;

import java.util.ArrayList;
import java.util.List;

import static lp2a.game.skyjogame.Skyjo.*;

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
    public static List<Player> reorganizePlayers(List<Player> players, int CurrentPlayerIndex){
        List<Player> newPlayers = new ArrayList<>();
        for(int i = CurrentPlayerIndex; i < players.size(); i++){
            newPlayers.add(players.get(i));
        }
        for(int i = 0; i < CurrentPlayerIndex; i++){
            newPlayers.add(players.get(i));
        }
        return newPlayers;
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

    public static int nextPlayer() {
        // if a player has 3 same visible cards on a same column, remove the cards from the column and put them in the discard pile
        for (int col = 0; col < 4; col++) {
            int index1 = col * 3;
            int index2 = index1 + 1;
            int index3 = index1 + 2;
            if (Skyjo.players.get(currentPlayerIndex).getCard(index1).isVisible()
                    && Skyjo.players.get(currentPlayerIndex).getCard(index2).isVisible()
                    && Skyjo.players.get(currentPlayerIndex).getCard(index3).isVisible()) {
                if (Skyjo.players.get(currentPlayerIndex).getCard(index1).getValue() == Skyjo.players.get(currentPlayerIndex).getCard(index2).getValue()
                        && Skyjo.players.get(currentPlayerIndex).getCard(index1).getValue() == Skyjo.players.get(currentPlayerIndex).getCard(index3).getValue()) {
                    Skyjo.discard.addCard(Skyjo.players.get(currentPlayerIndex).getCard(index1));
                    Skyjo.discard.addCard(Skyjo.players.get(currentPlayerIndex).getCard(index2));
                    Skyjo.discard.addCard(Skyjo.players.get(currentPlayerIndex).getCard(index3));
                    Skyjo.players.get(currentPlayerIndex).removeCard(index1);
                    // removeCard(index2) will remove the card at index2, so we need to remove the card at index1 again
                    Skyjo.players.get(currentPlayerIndex).removeCard(index1);
                    Skyjo.players.get(currentPlayerIndex).removeCard(index1);
                }
            }
        }

        Skyjo.displayPlayer(Skyjo.currentPlayerIndex);
        // if the current player has all his cards visible, we set the last player index to the player before him
        if (lastPlayerIndex == -1 && Skyjo.players.get(Skyjo.currentPlayerIndex).getAllVisibleCards()) {
            if (Skyjo.currentPlayerIndex == 0) {
                lastPlayerIndex = Skyjo.players.size() - 1;
            } else {
                lastPlayerIndex = Skyjo.currentPlayerIndex - 1;
            }
        }

        int nextPlayerIndex;
        if (Skyjo.currentPlayerIndex != lastPlayerIndex) {
            if (Skyjo.currentPlayerIndex < Skyjo.players.size() - 1) {
                nextPlayerIndex = Skyjo.currentPlayerIndex + 1;
            } else {
                nextPlayerIndex = 0;
                Skyjo.turn++;
            }
        } else {
            // set the nextPlayerIndex to the player after the last player
            if (Skyjo.lastPlayerIndex < Skyjo.players.size() - 1) {
                nextPlayerIndex = Skyjo.lastPlayerIndex + 1;
            } else {
                nextPlayerIndex = 0;
            }
            // it's the end of the round
            System.out.println("End of the round");
            // add the hand points of the players to their total points
            for (Player player : Skyjo.players) {
                player.setPoints(player.calculatePoints());
            }
            // check if the player which has finished first has the least points
            int leastPoints = Skyjo.players.get(nextPlayerIndex).calculatePoints();
            for (Player player : Skyjo.players) {
                if (player.calculatePoints() < leastPoints) {
                    leastPoints = player.calculatePoints();
                }
            }
            // if it's not the case, we double his points
            if (Skyjo.players.get(nextPlayerIndex).calculatePoints() != leastPoints) {
                Skyjo.players.get(nextPlayerIndex).setPoints(Skyjo.players.get(nextPlayerIndex).calculatePoints() * 2);
            }
            // If a player has 100 or more points, the game is over
            for (Player player : Skyjo.players) {
                if (player.getPoints() >= 100) {
                    Skyjo.gameOver = true;
                    break;
                }
            }
            nextPlayerIndex = -1;
            lastPlayerIndex = -1;
        }
        System.out.println("Current player index: " + Skyjo.currentPlayerIndex);
        System.out.println("Next player index: " + nextPlayerIndex);
        return nextPlayerIndex;
    }
}
