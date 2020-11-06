package com.trevorism.poker

import com.brooks.poker.game.PokerGame
import com.brooks.poker.game.data.BlindsAnte
import com.brooks.poker.game.data.GameState
import com.brooks.poker.player.Player
import com.brooks.poker.player.action.AlwaysCallPlayerAction
import com.trevorism.poker.bots.ChaosPokerBot
import com.trevorism.poker.bots.DumbPokerBot
import com.trevorism.poker.bots.SimplePokerBot

class PlayGame {

    static void main(String[] args) {
        def adaptor = new PrintWinner()
        int chipCount = 600

        50.times {
            Player trevor = new Player("Trevor", chipCount, new DumbPokerBot())
            Player vaughn = new Player("Vaughn", chipCount, new AlwaysCallPlayerAction())
            Player brooks = new Player("Brooks", chipCount, new SimplePokerBot())
            Player sean = new Player("Sean", chipCount, new ChaosPokerBot())
            List<Player> players = [trevor, vaughn, brooks, sean]

            Player trevor1 = new Player("Trevor1", chipCount, new DumbPokerBot())
            Player vaughn1 = new Player("Vaughn1", chipCount, new AlwaysCallPlayerAction())
            Player brooks1 = new Player("Brooks1", chipCount, new SimplePokerBot())
            Player sean1 = new Player("Sean1", chipCount, new ChaosPokerBot())
            players = [trevor, vaughn, brooks, sean, trevor1, vaughn1, brooks1, sean1]


            GameState gameState = GameState.configureTournamentGameState(BlindsAnte.STANDARD_TOURNAMENT, players)
            gameState.addGameStateHandler(adaptor)

            PokerGame.playGame(gameState)
        }

        println adaptor.playerCount
    }
}
