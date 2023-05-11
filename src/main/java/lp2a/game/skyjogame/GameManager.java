
package lp2a.game.skyjogame;

import java.util.ArrayList;
import java.util.List;

import static lp2a.game.skyjogame.Skyjo.*;

public class GameManager {
    /**
     * This method is used to manage the game
     */
    public static void game(){
        // State machine
        switch (gameState) {
            // if the state is PRE_ROUND, we initialize the deck and the discard pile, and we deal the cards to the players
            case PRE_ROUND -> {
                // initialize the deck and the discard pile
                deck = new CardDeck(false, 20*XMAX/50, 20*YMAX/50);
                discard = new CardDeck(true, 36*XMAX/50, 20*YMAX/50);
                // remove all the cards from the players' hands
                for (Player p : players) {
                    p.getHand().clear();
                }
                // deal the cards to the players
                deck.deal(players);
                // Pick up the first card of the deck and put it in the discard
                discard.addCard(deck.pick_up_card());
                //make the discard card visible
                discard.setVisible(true);
                // For each player in the game we assigned them a position in function of their position in the list
                for(int i = 0; i < players.size(); i++){
                    displayPlayer(i);
                    //And this is for assigned the position for their cards, so that the cards positions are relatives based on each player position
                    players.get(i).fillGrid();
                }
                // change the state to ROUND_START
                gameState = GameState.ROUND_START;
            }
            // if the state is ROUND_START, each player can choose 2 cards from his hand
            case ROUND_START -> {
                // if the current player is a bot, we call the play method
                if (players.get(currentPlayerIndex).isBot()){
                    Bot bot = (Bot) players.get(currentPlayerIndex);
                    bot.play();
                }
                //make the current player choose 2 cards
                List<Card> chosenCards = new ArrayList<>();
                for (Card c : players.get(currentPlayerIndex).getHand()){
                    if (c.isClicked() && chosenCards.size() < 2){
                        chosenCards.add(c);
                        c.setVisible(true);
                    }
                }
                // go to the next player if the current player has chosen 2 cards
                if (chosenCards.size() == 2 && currentPlayerIndex < players.size()-1) {
                    // display the player
                    displayPlayer(currentPlayerIndex);
                    // reset the clicked state of the cards
                    GameManager.resetPlayerCardsClick(players.get(currentPlayerIndex));
                    currentPlayerIndex++;
                } else if (chosenCards.size() == 2 && currentPlayerIndex == players.size()-1){
                    // count the points of each player, the player with the highest score will start the next round, if there is a tie, the first player will start the next round
                    int max = 0;
                    Player firstPlayer = null;
                    displayPlayer(currentPlayerIndex);
                    for (Player p : players){
                        if (p.calculatePoints() >= max){
                            if (p.calculatePoints() == max){
                                // the player with the lowest index will start the next round
                                if (players.indexOf(p) < players.indexOf(firstPlayer)){
                                    firstPlayer = p;
                                }
                            } else {
                                max = p.calculatePoints();
                                firstPlayer = p;
                            }
                        }
                    }
                    // the first player will start the next round
                    currentPlayerIndex = players.indexOf(firstPlayer);
                    // reorganize the list of players so that the first player is at the beginning of the list
                    players = GameManager.reorganizePlayers(players, currentPlayerIndex);
                    for (int i = 0; i<players.size(); i++){
                        Skyjo.displayPlayer(i);
                    }
                    currentPlayerIndex = 0;
                    // reset the clicked state of the cards
                    for (Player p : players){
                        GameManager.resetPlayerCardsClick(p);
                    }
                    // reset the clicked state of deck and discard pile
                    discard.setClicked(false);
                    deck.setClicked(false);
                    // change the state to WAITING
                    gameState = GameState.WAITING;
                }
            }
            // if the state is WAITING, the player can click on the deck or the discard pile
            case WAITING -> {
                // if the current player is a bot, we call the play method
                if (players.get(currentPlayerIndex).isBot()){
                    Bot bot = (Bot) players.get(currentPlayerIndex);
                    bot.play();
                }
                // if the player click on the discard pile, we change the state to DISCARD_CLICK
                if (discard.isClicked()) {
                    gameState = GameState.DISCARD_CLICK;
                }
                // if the player click on the deck pile, we change the state to DECK_CLICK
                if (deck.isClicked()) {
                    gameState = GameState.DECK_CLICK;
                    // reset the clicked state of the first card of the deck pile
                    deck.setClicked(false);
                    // put the card on the deck pile in the discard pile
                    discard.addCard(deck.pick_up_card());
                    // make the card visible
                    discard.setVisible(true);
                }
            }
            // if the state is DISCARD_CLICK, the player can exchange a card from his hand with the card on the discard pile
            case DISCARD_CLICK -> {
                // if the current player is a bot, we call the play method
                if (players.get(currentPlayerIndex).isBot()){
                    Bot bot = (Bot) players.get(currentPlayerIndex);
                    bot.play();
                }
                for (Card c : players.get(currentPlayerIndex).getHand()) {
                    if (c.isClicked()) {
                        // change the state to DISCARD_EXCHANGE
                        gameState = GameState.DISCARD_EXCHANGE;
                        break;
                    }
                }
            }
            // if the state is DISCARD_EXCHANGE, the player exchange a card from his hand with the card on the discard pile
            case DISCARD_EXCHANGE -> {
                // we get the card that the player wants to exchange
                Card c = null;
                for (Card card : players.get(currentPlayerIndex).getHand()) {
                    if (card.isClicked()) {
                        c = card;
                        break;
                    }
                }
                // we exchange the card
                GameManager.discardExchange(players.get(currentPlayerIndex), c, discard);
                // reset the clicked state of the cards
                GameManager.resetPlayerCardsClick(players.get(currentPlayerIndex));
                discard.setClicked(false);
                // we go to the next player
                currentPlayerIndex = nextPlayer();
            }
            // if the state is DECK_CLICK, the player can exchange a card from his hand with the card on the deck pile or reveal one of his cards
            case DECK_CLICK -> {
                // if the current player is a bot, we call the play method
                if (players.get(currentPlayerIndex).isBot()){
                    Bot bot = (Bot) players.get(currentPlayerIndex);
                    bot.play();
                }
                // if the player click on the discard pile, he can exchange a card from his hand with the card on the discard pile
                if (discard.isClicked()) {
                    gameState = GameState.DISCARD_CLICK;
                }
                // if the player click one of his card which is not visible, he can reveal it
                for (Card c : players.get(currentPlayerIndex).getHand()) {
                    if (c.isClicked() && !c.isVisible()) {
                        // we reveal the card
                        c.setVisible(true);
                        // reset the clicked state of the cards
                        resetPlayerCardsClick(players.get(currentPlayerIndex));
                        // we go to the next player
                        currentPlayerIndex = nextPlayer();
                    }
                }
            }
        }
    }

    /**
     * This method reorganize the list of players so that the player who have the index provided in parameter is at the beginning of the list
     * @param players the list of players
     * @param CurrentPlayerIndex the index of the player who will be at the beginning of the list
     * @return the reorganized list of players
     */
    private static List<Player> reorganizePlayers(List<Player> players, int CurrentPlayerIndex){
        List<Player> newPlayers = new ArrayList<>();
        // we add the players after the player with the index provided in parameter
        for(int i = CurrentPlayerIndex; i < players.size(); i++){
            newPlayers.add(players.get(i));
        }
        // we add the players before the player with the index provided in parameter
        for(int i = 0; i < CurrentPlayerIndex; i++){
            newPlayers.add(players.get(i));
        }
        return newPlayers;
    }

    /**
     * This method exchange a card from the player's hand with the card on the discard pile
     * @param player the player who exchange a card
     * @param c the card that the player wants to exchange
     * @param discard the discard pile
     */
    private static void discardExchange(Player player, Card c, CardDeck discard) {
        // exchange the card of the player with the card on the discard pile
        player.replaceCard(c, discard.pick_up_card());
        // put the card of the player in the discard pile
        discard.addCard(c);
        // make the first card of the discard pile visible
        discard.setVisible(true);
    }

    /**
     * This method reset the clicked state of the cards of a player
     * @param player the player whose cards will be reset
     */
    private static void resetPlayerCardsClick(Player player) {
        for (Card c : player.getHand()) {
            c.setClicked(false);
        }
    }

    /**
     * This method check if the game is finished (if a player has more than 100 points)
     * @return true if the game is finished, false otherwise
     */
    private static boolean isGameFinished() {
        for (Player player : players) {
            if (player.getPoints() >= 100) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method get the next player index and also check if a round is finished or if there is a column to remove each time a player has played
     * @return the next player index
     */
    private static int nextPlayer() {
        // if a player has 3 same visible cards on a same column, remove the cards from the column and put them in the discard pile
        checkForColumnToRemove();
        // display the current player
        Skyjo.displayPlayer(Skyjo.currentPlayerIndex);
        // update the last player index
        updateLastPlayerIndex();
        // if a round is finished, we go to the next round, otherwise we go to the next player
        if (Skyjo.currentPlayerIndex != lastPlayerIndex) {
            return getNextPlayerIndex();
        } else {
            endRound();
            return 0;
        }
    }

    /**
     * This method check if a player has 3 same visible cards on a same column, if it's the case, it removes the cards from the column and put them in the discard pile
     */
    private static void checkForColumnToRemove() {
        // todo fix bug when a column is removed, we dont go to the next player and we pick up cards for the discard pile infinitely
        // Count the number of columns in the player's hand (3 cards = 1 column, 6 cards = 2 columns, 9 cards = 3 columns, 12 cards = 4 columns)
        int nbColumns = 0;
        if (Skyjo.players.get(currentPlayerIndex).getHand().size() == 6) {
            nbColumns = 2;
        } else if (Skyjo.players.get(currentPlayerIndex).getHand().size() == 9) {
            nbColumns = 3;
        } else if (Skyjo.players.get(currentPlayerIndex).getHand().size() == 12) {
            nbColumns = 4;
        }
        // check for each column
        for (int col = nbColumns; col < 4; col++) {
            int index1 = col * 3;
            int index2 = index1 + 1;
            int index3 = index1 + 2;
            // if the 3 cards are visible
            if (Skyjo.players.get(currentPlayerIndex).getCard(index1).isVisible()
                    && Skyjo.players.get(currentPlayerIndex).getCard(index2).isVisible()
                    && Skyjo.players.get(currentPlayerIndex).getCard(index3).isVisible()) {
                // if the 3 cards have the same value
                if (Skyjo.players.get(currentPlayerIndex).getCard(index1).getValue() == Skyjo.players.get(currentPlayerIndex).getCard(index2).getValue()
                        && Skyjo.players.get(currentPlayerIndex).getCard(index1).getValue() == Skyjo.players.get(currentPlayerIndex).getCard(index3).getValue()) {
                    // put the 3 cards in the discard pile
                    Skyjo.discard.addCard(Skyjo.players.get(currentPlayerIndex).getCard(index1));
                    Skyjo.discard.addCard(Skyjo.players.get(currentPlayerIndex).getCard(index2));
                    Skyjo.discard.addCard(Skyjo.players.get(currentPlayerIndex).getCard(index3));
                    // remove the 3 cards from the player's hand
                    Skyjo.players.get(currentPlayerIndex).removeCard(index1);
                    // removeCard(index2) will remove the card at index2, so we need to remove the card at index1 again
                    Skyjo.players.get(currentPlayerIndex).removeCard(index1);
                    Skyjo.players.get(currentPlayerIndex).removeCard(index1);
                }
            }
        }
    }

    /**
     * This method update the last player index
     */
    private static void updateLastPlayerIndex() {
        // if the current player has all his cards visible, we set the last player index to the player before him
        if (lastPlayerIndex == -1 && Skyjo.players.get(Skyjo.currentPlayerIndex).getAllVisibleCards()) {
            lastPlayerIndex = (Skyjo.currentPlayerIndex == 0) ? Skyjo.players.size() - 1 : Skyjo.currentPlayerIndex - 1;
        }
    }

    /**
     * This method return the index of the next player
     * @return the index of the next player
     */
    public static int getNextPlayerIndex() {
        int nextPlayerIndex = (Skyjo.currentPlayerIndex < Skyjo.players.size() - 1) ? Skyjo.currentPlayerIndex + 1 : 0;
        gameState = GameState.WAITING;
        return nextPlayerIndex;
    }

    /**
     * This method end the round
     */
    private static void endRound() {
        // add the hand points of the players to their total points
        for (Player player : Skyjo.players) {
            player.setPoints(player.calculatePoints());
        }

        // check if the player which has finished first has the least points (this is the player after the last player)
        int leastPoints = Skyjo.players.get(getNextPlayerIndex()).calculatePoints();
        for (Player player : Skyjo.players) {
            if (player.calculatePoints() < leastPoints) {
                leastPoints = player.calculatePoints();
            }
        }

        // if it's not the case, we double his points
        if (Skyjo.players.get(getNextPlayerIndex()).calculatePoints() != leastPoints) {
            Skyjo.players.get(getNextPlayerIndex()).setPoints(Skyjo.players.get(getNextPlayerIndex()).calculatePoints() * 2);
        }

        // If a player has 100 or more points, the game is over
        if (isGameFinished()) {
            Skyjo.gameOver = true;
            // we display the winner
            //Skyjo.displayWinner(); // todo
            // reinitialize the deck and the discard pile
            deck = new CardDeck(false, 20*XMAX/50, 20*YMAX/50);
            discard = new CardDeck(true, 36*XMAX/50, 20*YMAX/50);
            // remove all the cards from the players' hands
            for (Player p : players) {
                p.getHand().clear();
            }
            // reset the current player index
            Skyjo.currentPlayerIndex = 0;
            // reset the game state
            Skyjo.gameState = GameState.ROUND_START;
            // reset the list of players
            Skyjo.players.clear();
        } else {
            gameState = GameState.PRE_ROUND;
        }

        lastPlayerIndex = -1;
    }
}
