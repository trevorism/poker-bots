package com.trevorism.poker.hand

import com.brooks.poker.cards.Card
import com.brooks.poker.cards.Hand
import com.brooks.poker.cards.HandValue
import org.junit.Test

class HandCalculationUtilsTest {
    @Test
    void testIsOneCardAway() {
        List<Card> cards = [new Card(Card.Suit.CLUBS, Card.Value.JACK), new Card(Card.Suit.SPADES, Card.Value.JACK), new Card(Card.Suit.HEARTS, Card.Value.NINE), new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT)]

        Hand hand = new Hand(cards)

        assert HandCalculationUtils.isOneCardAway(hand, HandValue.HandValueType.PAIR)
        assert HandCalculationUtils.isOneCardAway(hand, HandValue.HandValueType.TWO_PAIR)
        assert HandCalculationUtils.isOneCardAway(hand, HandValue.HandValueType.THREE_OF_A_KIND)

        assert !HandCalculationUtils.isOneCardAway(hand, HandValue.HandValueType.STRAIGHT)
        assert !HandCalculationUtils.isOneCardAway(hand, HandValue.HandValueType.FLUSH)
        assert !HandCalculationUtils.isOneCardAway(hand, HandValue.HandValueType.FULL_HOUSE)


    }
}
