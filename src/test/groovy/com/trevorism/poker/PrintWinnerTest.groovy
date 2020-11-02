package com.trevorism.poker

import com.brooks.poker.game.data.BlindsAnte
import com.brooks.poker.game.data.GameState
import org.junit.Test

class PrintWinnerTest {
    @Test
    void testHandleEndGameState() {
        PrintWinner printWinner = new PrintWinner()
        GameState gameState = GameState.configureTournamentGameState(BlindsAnte.STANDARD_TOURNAMENT, [])

        printWinner.handleEndHandState(gameState)
    }
}
