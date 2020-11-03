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
        printPlayer(gameState.table.allPlayers[0])

        if(i == 1){
            println ""
            println "------------------------"
            println gameState.gamePhase
            println gameState.table.allPlayers.each {
                println(it.toString() + " " + it.hand)
            }
            println gameState.pots.pots
            println "------------------------"

        }
    }

    private void printPlayer(Player player){
        println player
    }
}
