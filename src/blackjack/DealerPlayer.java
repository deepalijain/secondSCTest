package blackjack;

import cardgame.Card;

public class DealerPlayer extends Player
{

    public DealerPlayer(Card card1, Card card2)
    {
        super("Dealer", card1, card2);
        if(this.getScore() >= 17)
            _state = "stand";
    }

    @Override
    public void hit(Card card)
    {
        if(_state == "canHit")
        {
            _hand.hit(card);

            if(_hand.getScore() > 21)
                _state = "bust";
            else if(_hand.getScore() >= 17)
                _state = "stand"; // dealer must stand at 17
        }
        else
            throw new RuntimeException("Can't hit. Object state: " + _state);
    }

    @Override
    public void newHand(Card card1, Card card2)
    {
        _state = "canHit";
        _hand.newHand(card1, card2);

        if(_hand.getScore() == 21)
            _state = "blackjack";
        else if(_hand.getScore() >= 17)  // dealer must stand at 17
            _state = "stand";
    }

    public Card getPocketCard()
    {
        return _hand.getCards().get(0);
    }
}
