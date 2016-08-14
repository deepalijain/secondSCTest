package cardgame;

import java.util.ArrayList;
import cardgame.Card;

public class Hand
{
    private ArrayList<Card> cards;

    public Hand()
    {
        cards = new ArrayList<Card>();
    }

    public void add(Card card)
    {
        cards.add(card);

    }

    @Override
    public String toString()
    {
        String toPrint = "";
        for(Card card : cards)
        {
            toPrint += card + "\n";
        }
        return toPrint;
    }

    public ArrayList<Card> getCards()
    {
        return cards;
    }

    public ArrayList<Card> returnCards()
    {
        ArrayList<Card> toreturn = new ArrayList<Card> (cards);
        cards.clear();
        return toreturn;
    }
}
