package com.trevorism.poker.hand

import com.brooks.poker.cards.Card

class PreflopScore {

    int score

    PreflopScore(List<Card> cards){
        score = 0
        cards.each {card ->
            computeCardBaseline(card)
        }

        //Connectors
        score = computeConnectorBonus(cards)

        //Suited
        if(cards[0].suit == cards[1].suit){
            score += 22
        }


    }

    private void computeCardBaseline(Card card) {
        if (card.value == Card.Value.ACE) {
            score += 30
        } else if (card.value == Card.Value.KING) {
            score += 25
        } else if (card.value == Card.Value.QUEEN) {
            score += 20
        } else if (card.value == Card.Value.JACK) {
            score += 15
        } else {
            score += card.value.ordinal()
        }
    }

    private int computeConnectorBonus(List<Card> cards) {
        if (Math.abs(cards[0].value.ordinal() - cards[1].value.ordinal()) == 4) {
            score += 2
        }
        if (Math.abs(cards[0].value.ordinal() - cards[1].value.ordinal()) == 3) {
            score += 4
        }
        if (Math.abs(cards[0].value.ordinal() - cards[1].value.ordinal()) == 2) {
            score += 8
        }
        if (Math.abs(cards[0].value.ordinal() - cards[1].value.ordinal()) == 1) {
            score += 16
        }
        if (Math.abs(cards[0].value.ordinal() - cards[1].value.ordinal()) == 0) {
            score += 60
        }
        score
    }
}
