package com.trevorism.poker.bots

import com.brooks.poker.cards.HandValue
import com.brooks.poker.game.GameActions
import com.brooks.poker.game.data.GameState
import com.brooks.poker.outcome.BettingOutcome
import com.brooks.poker.outcome.BettingOutcomeFactory
import com.brooks.poker.player.Player
import com.brooks.poker.player.action.PlayerAction

class DumbPokerBot implements PlayerAction {

    @Override
    BettingOutcome getBettingOutcome(GameState gameState, Player player) {
        boolean canCheck = canCheck(gameState)

        if (isPreFlop(player)) {
            return computePreflopAction(player, canCheck, gameState)
        }

        if (gameState.getCommunityCards().getCards().size() == 3) {
            return computeFlopAction(player, gameState, canCheck)
        }
        if (gameState.getCommunityCards().getCards().size() == 4) {
            return computeTurnAction(player, gameState, canCheck)
        }
        if (gameState.getCommunityCards().getCards().size() == 5) {
            return computeRiverAction(player, gameState, canCheck)
        }

        return defaultBettingAction(canCheck)
    }

    private BettingOutcome defaultBettingAction(boolean canCheck) {
        if (canCheck)
            return BettingOutcomeFactory.createCallOutcome()

        return BettingOutcomeFactory.createFoldOutcome()
    }

    private BettingOutcome computePreflopAction(Player player, boolean canCheck, GameState gameState) {
        PreflopScore preflopScore = new PreflopScore(player.getHand().getCards())
        if (preflopScore.score < 30 && !canCheck)
            return BettingOutcomeFactory.createFoldOutcome()
        if (preflopScore.score > 80)
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 4)
        if (preflopScore.score > 65)
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 2)
        return BettingOutcomeFactory.createCallOutcome()
    }

    private BettingOutcome computeFlopAction(Player player, GameState gameState, boolean canCheck) {
        if (player.getHand().handValue.type >= HandValue.HandValueType.THREE_OF_A_KIND) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 4)
        }
        if (player.getHand().handValue.type >= HandValue.HandValueType.TWO_PAIR) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 2)
        }
        if (player.getHand().handValue.type > HandValue.HandValueType.HIGH_CARD) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState))
        }
        return defaultBettingAction(canCheck)
    }

    BettingOutcome computeTurnAction(Player player, GameState gameState, boolean canCheck) {
        if (player.getHand().handValue.type >= HandValue.HandValueType.THREE_OF_A_KIND) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 2)
        }
        if (player.getHand().handValue.type >= HandValue.HandValueType.TWO_PAIR) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState))
        }
        return defaultBettingAction(canCheck)
    }

    BettingOutcome computeRiverAction(Player player, GameState gameState, boolean canCheck) {
        if (player.getHand().handValue.type >= HandValue.HandValueType.FULL_HOUSE) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 6)
        }
        return BettingOutcomeFactory.createCallOutcome()
    }

    private boolean isPreFlop(Player player) {
        !player.getHand().getHandValue().isValidHandValue()
    }

    boolean canCheck(GameState gameState) {
        int currentBet = gameState.getPots().currentBet
        if (currentBet == 0 || gameState.getTable().getActivePlayersSize() == 1)
            return true
        return false
    }

}
