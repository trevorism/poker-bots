package com.trevorism.poker.bots

import com.brooks.poker.cards.Card
import org.junit.Test

class PreflopScoreTest {

    @Test
    void testPreflopScore(){

        println new PreflopScore([new Card(Card.Suit.CLUBS, Card.Value.ACE), new Card(Card.Suit.SPADES, Card.Value.ACE)]).score
        println new PreflopScore([new Card(Card.Suit.CLUBS, Card.Value.ACE), new Card(Card.Suit.CLUBS, Card.Value.KING)]).score
        println new PreflopScore([new Card(Card.Suit.CLUBS, Card.Value.EIGHT), new Card(Card.Suit.SPADES, Card.Value.EIGHT)]).score
        println new PreflopScore([new Card(Card.Suit.SPADES, Card.Value.ACE), new Card(Card.Suit.CLUBS, Card.Value.KING)]).score
        println new PreflopScore([new Card(Card.Suit.CLUBS, Card.Value.SIX), new Card(Card.Suit.CLUBS, Card.Value.SEVEN)]).score
        println new PreflopScore([new Card(Card.Suit.CLUBS, Card.Value.TWO), new Card(Card.Suit.SPADES, Card.Value.TWO)]).score

        println new PreflopScore([new Card(Card.Suit.HEARTS, Card.Value.JACK), new Card(Card.Suit.CLUBS, Card.Value.SEVEN)]).score
        println new PreflopScore([new Card(Card.Suit.SPADES, Card.Value.TWO), new Card(Card.Suit.CLUBS, Card.Value.SIX)]).score
        println new PreflopScore([new Card(Card.Suit.SPADES, Card.Value.TWO), new Card(Card.Suit.CLUBS, Card.Value.SEVEN)]).score


    }
}
