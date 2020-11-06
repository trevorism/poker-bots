package com.trevorism.poker.hand

import com.brooks.poker.cards.Card
import com.brooks.poker.cards.Deck
import com.brooks.poker.cards.Hand
import com.brooks.poker.cards.HandValue

class HandCalculationUtils {

    static boolean isOneCardAway(Hand hand, HandValue.HandValueType handValueType) {
        Deck deck = new Deck()
        for (int i = 0; i < 52; i++) {
            Card card = deck.dealCard();
            if (cardIsAlreadyInHand(hand, card)) {
                continue
            }
            Hand thisHand = new Hand(hand.cards)
            thisHand.addCard(card)
            if (thisHand.handValue.type == handValueType) {
                return true
            }
        }
        return false
    }

    private static boolean cardIsAlreadyInHand(Hand hand, Card card) {
        hand.cards.contains(card)
    }
}
