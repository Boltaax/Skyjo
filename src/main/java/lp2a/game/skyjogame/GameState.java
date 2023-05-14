package lp2a.game.skyjogame;

/**
 * GameState is an enum that represents the different states of the game.
 * It is used to know what the player can do at a given moment.
 */
public enum GameState {
    PRE_ROUND,
    ROUND_START,
    DISCARD_CLICK,
    DISCARD_EXCHANGE,
    DECK_CLICK,
    WAITING
}
