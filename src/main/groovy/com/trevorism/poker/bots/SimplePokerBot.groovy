package com.trevorism.poker.bots

import com.brooks.poker.cards.HandValue
import com.brooks.poker.game.GameActions
import com.brooks.poker.game.data.GameState
import com.brooks.poker.outcome.BettingOutcome
import com.brooks.poker.outcome.BettingOutcomeFactory
import com.brooks.poker.player.Player
import com.brooks.poker.player.action.PlayerAction
import com.trevorism.poker.hand.HandCalculationUtils

class SimplePokerBot implements PlayerAction {

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
        if (preflopScore.score < 45 && !canCheck)
            return BettingOutcomeFactory.createFoldOutcome()
        if (preflopScore.score > 84)
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 5)
        if (preflopScore.score > 65)
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 2)
        return BettingOutcomeFactory.createCallOutcome()
    }

    private BettingOutcome computeFlopAction(Player player, GameState gameState, boolean canCheck) {
        if (HandCalculationUtils.isOneCardAway(player.getHand(), HandValue.HandValueType.FULL_HOUSE)) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 6)
        }
        if (HandCalculationUtils.isOneCardAway(player.getHand(), HandValue.HandValueType.STRAIGHT_FLUSH)) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 4)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.FLUSH) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 3)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.STRAIGHT) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 3)
        }
        if (HandCalculationUtils.isOneCardAway(player.getHand(), HandValue.HandValueType.STRAIGHT)) {
            return BettingOutcomeFactory.createCallOutcome()
        }
        if (HandCalculationUtils.isOneCardAway(player.getHand(), HandValue.HandValueType.FLUSH)) {
            return BettingOutcomeFactory.createCallOutcome()
        }

        return defaultBettingAction(canCheck)
    }

    BettingOutcome computeTurnAction(Player player, GameState gameState, boolean canCheck) {
        if (player.getHand().getHandValue().type == HandValue.HandValueType.FULL_HOUSE) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 4)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.FLUSH) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 2)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.STRAIGHT) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 2)
        }
        if (HandCalculationUtils.isOneCardAway(player.getHand(), HandValue.HandValueType.STRAIGHT)) {
            return BettingOutcomeFactory.createCallOutcome()
        }
        if (HandCalculationUtils.isOneCardAway(player.getHand(), HandValue.HandValueType.FLUSH)) {
            return BettingOutcomeFactory.createCallOutcome()
        }
        return defaultBettingAction(canCheck)
    }

    BettingOutcome computeRiverAction(Player player, GameState gameState, boolean canCheck) {
        if (player.getHand().getHandValue().type == HandValue.HandValueType.FULL_HOUSE) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 6)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.FLUSH) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 4)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.STRAIGHT) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 3)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.HIGH_CARD && !canCheck) {
            return BettingOutcomeFactory.createFoldOutcome()
        }
        return BettingOutcomeFactory.createCallOutcome()
    }

    private static boolean isPreFlop(Player player) {
        !player.getHand().getHandValue().isValidHandValue()
    }

    static boolean canCheck(GameState gameState) {
        int currentBet = gameState.getPots().currentBet
        if (currentBet == 0 || gameState.getTable().getActivePlayersSize() == 1)
            return true
        return false
    }

}
