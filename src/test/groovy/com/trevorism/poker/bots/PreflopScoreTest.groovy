package com.trevorism.poker.bots

import com.brooks.poker.cards.Card
import com.brooks.poker.cards.Deck
import com.trevorism.poker.hand.PreflopScore
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


    @Test
    void testAFewRandomHands(){
        Deck deck = new Deck()
        deck.reset()
        for(int i = 0; i < 25; i++){
            Card card1 = deck.dealCard()
            Card card2 = deck.dealCard()

            println "${card1}, ${card2} :: ${new PreflopScore([card1, card2]).score}"
        }
    }
}
