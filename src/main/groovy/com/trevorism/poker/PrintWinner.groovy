package com.trevorism.poker

import com.brooks.poker.game.data.GameState
import com.brooks.poker.game.handler.GameStateHandlerAdaptor
import com.brooks.poker.player.Player

class PrintWinner extends GameStateHandlerAdaptor {

    int index = 0
    Map<String, Integer> playerCount = [:]

    void handleEndHandState(GameState gameState) {
        index++
    }

    @Override
    void handleEndGameState(GameState gameState) {
        print "Hand count: $index :: "
        printPlayer(gameState.getTable().getAllPlayers()[0])
        updatePlayerCount(gameState)
        index=0
    }

    private void printPlayer(Player player){
        println player
    }

    void updatePlayerCount(GameState gameState) {
        Player player = gameState.table.getAllPlayers()[0]
        def value = playerCount.get(player.name)
        if(value == null)
            value = 0
        playerCount.put(player.name, value+1)
    }
}
