package com.trevorism.poker.bots


import com.brooks.poker.game.GameActions
import com.brooks.poker.game.data.GameState
import com.brooks.poker.outcome.BettingOutcome
import com.brooks.poker.outcome.BettingOutcomeFactory
import com.brooks.poker.player.Player
import com.brooks.poker.player.action.PlayerAction
import com.trevorism.poker.hand.PreflopScore

class CheatingPokerBot implements PlayerAction {
    @Override
    BettingOutcome getBettingOutcome(GameState gameState, Player player) {
        if (isPreFlop(player)) {
            return computePreflopAction(player, canCheck(gameState), gameState)
        }

        for(Player aPlayer : gameState.table.getSortedActivePlayers()){
            if(aPlayer.hand > player.hand){
                return BettingOutcomeFactory.createFoldOutcome()
            }
        }
        return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 3)
    }

    private boolean isPreFlop(Player player) {
        !player.getHand().getHandValue().isValidHandValue()
    }

    private static BettingOutcome computePreflopAction(Player player, boolean canCheck, GameState gameState) {
        PreflopScore preflopScore = new PreflopScore(player.getHand().getCards())
        if (preflopScore.score < 30 && !canCheck)
            return BettingOutcomeFactory.createFoldOutcome()
        if (preflopScore.score > 80)
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 4)
        if (preflopScore.score > 65)
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 2)
        return BettingOutcomeFactory.createCallOutcome()
    }

    boolean canCheck(GameState gameState) {
        int currentBet = gameState.getPots().currentBet
        if (currentBet == 0 || gameState.getTable().getAllPlayers().size() - gameState.getTable().getSortedActivePlayers().size() == 1)
            return true
        return false
    }
}
