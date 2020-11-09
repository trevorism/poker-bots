package com.trevorism.poker.bots

import com.brooks.poker.cards.Hand
import com.brooks.poker.cards.HandValue
import com.brooks.poker.game.GameActions
import com.brooks.poker.game.data.GameState
import com.brooks.poker.outcome.BettingOutcome
import com.brooks.poker.outcome.BettingOutcomeFactory
import com.brooks.poker.player.Player
import com.brooks.poker.player.action.PlayerAction
import com.trevorism.poker.hand.HandCalculationUtils
import com.trevorism.poker.hand.PreflopScore

class SimplePokerBot implements PlayerAction {

    @Override
    BettingOutcome getBettingOutcome(GameState gameState, Player player) {
        if (isPreFlop(player)) {
            return computePreflopAction(player, canCheck(gameState), gameState)
        }

        if (gameState.getCommunityCards().getCards().size() == 3) {
            return computeFlopAction(player, gameState)
        }
        if (gameState.getCommunityCards().getCards().size() == 4) {
            return computeTurnAction(player, gameState)
        }
        if (gameState.getCommunityCards().getCards().size() == 5) {
            return computeRiverAction(player, gameState)
        }

        return defaultBettingAction(gameState)
    }

    private BettingOutcome defaultBettingAction(GameState gameState) {
        if (gameState.getPots().getCurrentBet() <= gameState.blindsAnte.bigBlind)
            return BettingOutcomeFactory.createCallOutcome()

        return BettingOutcomeFactory.createFoldOutcome()
    }

    private BettingOutcome computePreflopAction(Player player, boolean canCheck, GameState gameState) {
        PreflopScore preflopScore = new PreflopScore(player.getHand().getCards())
        int numberOfPlayers = gameState.getTable().getAllPlayers().size() - gameState.getTable().getSortedActivePlayers().size()
        if (preflopScore.score < 26 + 3 * numberOfPlayers && !canCheck)
            return BettingOutcomeFactory.createFoldOutcome()
        if (preflopScore.score > 66 + 3 * numberOfPlayers)
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 5)
        if (preflopScore.score > 46 + 3 * numberOfPlayers && gameState.pots.getCurrentBet() == gameState.getBlindsAnte().bigBlind)
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 2)
        return BettingOutcomeFactory.createCallOutcome()
    }

    private BettingOutcome computeFlopAction(Player player, GameState gameState) {
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

        return defaultBettingAction(gameState)
    }

    BettingOutcome computeTurnAction(Player player, GameState gameState) {
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
        return defaultBettingAction(gameState)
    }

    BettingOutcome computeRiverAction(Player player, GameState gameState) {
        if (player.getHand().getHandValue().type == HandValue.HandValueType.FULL_HOUSE) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 6)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.FLUSH) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 4)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.STRAIGHT) {
            return BettingOutcomeFactory.createRaiseOutcome(GameActions.getMinBet(gameState) * 3)
        }
        Hand communityHand = new Hand(gameState.getCommunityCards().cards)
        if (communityHand.handValue.type == player.getHand().handValue.type) {
            return defaultBettingAction(gameState)
        }
        if (player.getHand().getHandValue().type == HandValue.HandValueType.HIGH_CARD && gameState.getPots().getCurrentBet() < 50) {
            return BettingOutcomeFactory.createFoldOutcome()
        }
        return BettingOutcomeFactory.createCallOutcome()
    }

    private static boolean isPreFlop(Player player) {
        !player.getHand().getHandValue().isValidHandValue()
    }

    static boolean canCheck(GameState gameState) {
        int currentBet = gameState.getPots().currentBet
        if (currentBet == 0 || gameState.getTable().getAllPlayers().size() - gameState.getTable().getSortedActivePlayers().size() == 1)
            return true
        return false
    }


}
