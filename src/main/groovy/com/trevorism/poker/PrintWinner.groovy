package com.trevorism.poker

import com.brooks.poker.game.data.GameState
import com.brooks.poker.game.handler.GameStateHandlerAdaptor
import com.brooks.poker.player.Player

class PrintWinner extends GameStateHandlerAdaptor {

    @Override
    void handleEndGameState(GameState gameState) {
        printPlayer(gameState.table.sortedActivePlayers[0])
    }

    private void printPlayer(Player player){
        print "${player} :: "
        player.hand.cards.each {
            print "${it}, "
        }
        println player.hand.handValue
    }
}
