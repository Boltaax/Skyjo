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
        // AFFICHER JOUEUR I
        // RETOURNE 2 CARTES
        // CURRENTINDEXPLAYER++
        // COMPTE LE NOMBRE DE PTS DE CHACUN
        // SE MET A L'INDEX DU PLUS DE PTS
        // LAUNCH GAME
    }

    private void game(){
        while(currentPlayer().getHandSize() != 0 || !currentPlayer().getAllVisibleCards()){
            // CLIC SUR LE DECK OU NON
            // CLIC SUR UNE CARTE DE TA MAIN
            // SI VISIBLE : ECHANGE DERNIERE CARTE DEFAUSSE
            // SI NON  : AFFICHE CARTE


            // Next Player
            if(CurrentPlayerIndex < players.size()+bots.size()-1){
                CurrentPlayerIndex++;
            }
            else{
                CurrentPlayerIndex = 0;
            }
        }
    }

}
