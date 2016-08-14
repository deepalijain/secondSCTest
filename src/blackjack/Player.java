package blackjack;

import java.util.ArrayList;
import cardgame.Card;
import cardgame.Hand;

public class Player
{
    private int _money;
    private int _bet;
    protected BlackJackHand _hand;
    protected String _name = "(no name)";
    protected String _state = "canHit";

    public static int numPlayers = 0;

    public Player(String name, Card card1, Card card2)
    {
        this(name, card1, card2, 1000);
    }

    public Player(String name, Card card1, Card card2, int money)
    {
        _name = name;
        _money = money;
        _hand = new BlackJackHand(card1, card2);
        if(_hand.getScore() == 21)
            _state = "blackjack";
    }

    public void newHand(Card card1, Card card2)
    {
        _state = "canHit";
        _hand.newHand(card1, card2);
        if(_hand.getScore() == 21)
            _state = "blackjack";
    }

    public String printFormatted()
    {
        String toPrint = "";
        toPrint += getName() + "'s cards:\n";
        toPrint += _hand.toString();
        toPrint += "\nScore: " + _hand.getScore() + "\n";

        if(_state == "bust")
        {
            toPrint += "\n" + _name + " busted!\n";
            toPrint += "===============\n";
        }
        else if(_state == "blackjack")
        {
            toPrint += "\nBlackJack!\n";
            toPrint += "===============\n";
        }
        else if(_state == "stand")
        {
            toPrint += "\n" + _name + " stands!\n";
            toPrint += "===============\n";
        }
        else if(_state == "surrender")
        {
            toPrint += "\n" + _name + " surrenders!\n";
            toPrint += "===============\n";
        }
        else
        {
            toPrint += "---------------\n";
        }

        return toPrint;
    }

    public void bet(int amount)
    {
        _bet = amount;
        _money -= amount;
    }

    public void hit(Card card)
    {
        if(_state == "canHit")
        {
            _hand.hit(card);

            if(_hand.getScore() > 21)
                _state = "bust";
            else if(_hand.getScore() == 21)
                _state = "stand"; // must stand at 21
        }
        else
            throw new RuntimeException("Can't hit. Object state: " + _state);
    }

    public boolean stand()
    {
        if(_state == "canHit")
        {
            _state = "stand";
            return true;
        }
        else
            return false;
    }

    public void doubleDown(Card card)
    {
        // Double the bet
        _money -= _bet;
        _bet *= 2;

        _hand.hit(card);

        if(_hand.getScore() > 21)
            _state = "bust";
        else
            _state = "stand"; // Can't receive any more cards
    }

    public void surrender()
    {
        _money += _bet / 2;
        _state = "surrender";
    }

    public void tie()
    {
        // Don't lose any money
        _money += _bet;
    }

    public void winDealer()
    {
        // Receive your original bet + same amount from dealer
        _money += 2 * _bet;
    }

    public void blackjack()
    {
        // Dealer pays 1.5 times the original bet in addition to original bet
        _money += 2.5 * _bet;
    }

    public ArrayList<Card> resetHand()
    {
        _bet = 0;
        return _hand.returnCards();
    }

    public String getName()
    {
        return _name;
    }

    public String getState()
    {
        return _state;
    }

    public int getScore()
    {
        return _hand.getScore();
    }

    public Hand getHand()
    {
        return _hand;
    }

    public int getMoney()
    {
        return _money;
    }

    public int getBet()
    {
        return _bet;
    }
}
