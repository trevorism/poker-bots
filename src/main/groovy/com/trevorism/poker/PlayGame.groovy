package com.trevorism.poker

import com.brooks.poker.game.PokerGame
import com.brooks.poker.game.data.BlindsAnte
import com.brooks.poker.game.data.GameState
import com.brooks.poker.player.Player
import com.brooks.poker.player.action.AlwaysCallPlayerAction
import com.trevorism.poker.bots.DumbPokerBot

class PlayGame {

    static void main(String [] args){
        20.times {
            Player trevor = new Player("Trevor", 500, new DumbPokerBot())
            Player vaughn = new Player("Vaughn", 500, new AlwaysCallPlayerAction())
            Player brooks = new Player("Brooks", 500, new AlwaysCallPlayerAction())
            Player sean = new Player("sean", 500, new AlwaysCallPlayerAction())
            List<Player> players = [trevor, vaughn, brooks, sean]

            GameState gameState = GameState.configureTournamentGameState(BlindsAnte.STANDARD_TOURNAMENT, players)
            def adaptor = new PrintWinner()
            gameState.addGameStateHandler(adaptor)

            PokerGame.playGame(gameState)
            gameState = null
        }
    }
}
