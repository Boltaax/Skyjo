package lp2a.game.skyjogame;

/**
 *
 *
 *
 */

public class Bot extends Player{


    Card selectedCard = null;
    // Attributes
    public Bot() {
        super(botNames[random.nextInt(botNames.length)]);
    }

    public Bot(String name) {
        super(name);
    }

    /**
     * This method check if the player is a bot
     */
    @Override
    public boolean isBot() {
        return true;
    }



    public void play() {
        switch (Skyjo.gameState) {
            case ROUND_START:
                // Click on 2 different cards of the hand
                int index1 = random.nextInt(hand.size());
                int index2 = random.nextInt(hand.size());
                while (index1 == index2) {
                    index2 = random.nextInt(hand.size());
                }
                hand.get(index1).setClicked(true);
                hand.get(index2).setClicked(true);
                break;
            case WAITING:
                int value = Skyjo.discard.getCards().get(Skyjo.discard.size() - 1).getValue();
                selectedCard = selectCard(value);
                if (selectedCard != null) // The value is better so take the last card in the discard
                {
                    Skyjo.discard.setClicked(true);
                } else // The value is worse so take a new card in the deck
                {
                    Skyjo.deck.setClicked(true);
                }
                break;
            case DISCARD_CLICK:
                // Click on the selected card
                hand.get(hand.indexOf(selectedCard)).setClicked(true);
                break;
            case DECK_CLICK:
                value = Skyjo.discard.getCards().get(Skyjo.discard.size() - 1).getValue();
                selectedCard = selectCard(value);
                if (selectedCard != null) {// The value is better so take this card
                    Skyjo.discard.setClicked(true);
                } else {// The value is worse so reveal a card or exchange an unrevealed card

                    // Click randomly a card not visible from the hand
                    int index = random.nextInt(hand.size());
                    while (hand.get(index).isVisible()) {
                        index = random.nextInt(hand.size());
                    }
                    // If the expectation of our hand is bigger than the value of the card we take it (i.e : all our cards are 0, 1 and -2, we have a 3 in the discard but the expectation of our hands is 7.6 it's better to take the 3)
                    if(value <= expectation()){
                        selectedCard = hand.get(index);
                        Skyjo.discard.setClicked(true);
                    } else{ // If not, we reveal one of the card
                        hand.get(index).setClicked(true);
                    }
                }
                break;
        }
    }

    // This function select the card to play
    private Card selectCard(int value) {
        // Find the highest card in the hand
        int max = 0;
        int index = 0;
        for (int i = 0; i < hand.size(); i++)
        {
            if (hand.get(i).getValue() > max && hand.get(i).isVisible())
            {
                max = hand.get(i).getValue();
                index = i;
            }
        }
        // Check if the value of the card is worse than the value provided
        if (max > value) {
            return hand.get(index);
        } else {
            return null;
        }
    }

    private double expectation() {
        double value = 0;

        for(int i = -2; i<= 12; i++){
            value += i*Skyjo.possible_cards[i+2];
        }
        value = value/ Skyjo.pickable_cards;

        return value;
    }

}
