package cardgame;

import java.util.ArrayList;

/**
 * A deck to hold cards
 *
 * @author George "Agent 10" Troulis
 */
public class Deck
{
    private ArrayList<Card> cards = new ArrayList<Card>(Card.numCards);

    private void scramble()
    {
        int index1;
        int index2;
        Card temp;
        // swap 100 random cards
        for(int i = 0; i < 100; i++)
        {
            index1 = (int) Math.floor(Math.random() * Card.numCards);
            index2 = (int) Math.floor(Math.random() * Card.numCards);

            temp = cards.get(index2);
            cards.set(index2, cards.get(index1));
            cards.set(index1, temp);

        }
    }

    /**
     * Resets the Deck
     */
    public void reset()
    {
        cards.clear();
        for(int s = 0; s < 4; s++)
        {
            for(int v = 1; v <= 13; v++)
            {
                cards.add(new Card(v, s));
            }
        }
    }

    /**
     * Create a new Deck object
     *
     * @param isScrambled
     *            Determines if the deck should be scrambled in the beginning
     */
    public Deck(boolean isScrambled)
    {
        this.reset();
        if(isScrambled)
            this.scramble();
    }

    /**
     * Create a new Deck object that is not scrambled
     */
    public Deck()
    {
        this(false);
    }

    /**
     * Removes the first card from the deck and returns it
     *
     * @return The first card in the deck
     */
    public Card draw()
    {
        return cards.remove(0);
    }

    public void returnCards(ArrayList<Card> toreturn)
    {
        cards.addAll(toreturn);
    }

    /**
     * Gets the number of cards in the deck
     *
     * @return The number of cards in the deck
     */
    public int getNumCards()
    {
        return cards.size();
    }

    @Override
    public String toString()
    {
        String toDisplay = "";
        for(Card card : cards)
        {
            toDisplay += card.toString() + '\n';
        }
        return toDisplay;
    }
}
