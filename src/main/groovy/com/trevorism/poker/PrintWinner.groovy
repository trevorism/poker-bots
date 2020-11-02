package com.trevorism.poker

import com.brooks.poker.game.data.GameState
import com.brooks.poker.game.handler.GameStateHandlerAdaptor
import com.brooks.poker.player.Player

class PrintWinner extends GameStateHandlerAdaptor {

    int i = 0;

    void handleEndHandState(GameState gameState) {
        i++
    }

    @Override
    void handleEndGameState(GameState gameState) {
        print "Hand count: $i :: "
        printPlayer(gameState.table.sortedActivePlayers[0])
    }

    private void printPlayer(Player player){
        println player
    }
}
