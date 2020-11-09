package com.trevorism.poker.bots

import com.brooks.poker.game.GameActions
import com.brooks.poker.game.data.GameState
import com.brooks.poker.outcome.BettingOutcome
import com.brooks.poker.outcome.BettingOutcomeFactory
import com.brooks.poker.player.Player
import com.brooks.poker.player.action.PlayerAction

class ChaosPokerBot implements PlayerAction {

    @Override
    BettingOutcome getBettingOutcome(GameState gameState, Player player) {

        Random random = new Random()
        int value = random.nextInt(10)
        boolean canCheck = canCheck(gameState)
        if (value <= 1 && !canCheck) {
            return BettingOutcomeFactory.createFoldOutcome()
        }
        if (value <= 4) {
            return BettingOutcomeFactory.createCallOutcome()
        }
        if (value <= 7) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState))
        } else {
            int amount = random.nextInt(Math.abs(player.getChipCount() - GameActions.getMinBet(gameState)) + 1)
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) + amount)
        }
    }

    boolean canCheck(GameState gameState) {
        int currentBet = gameState.getPots().currentBet
        if (currentBet == 0 || gameState.getTable().getAllPlayers().size() - gameState.getTable().getSortedActivePlayers().size() == 1)
            return true
        return false
    }

}
