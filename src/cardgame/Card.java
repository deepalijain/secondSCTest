package cardgame;

/**
 * @author George "Agent 10" Troulis
 */
public class Card
{
    public static final String suits[] = { "Spades", "Hearts", "Diamonds",
                                           "Clubs"
                                         };

    public static final String values[] =   { "Joker", "Ace", "2", "3", "4",
                                              "5", "6", "7", "8", "9", "10",
                                              "Jack", "Queen", "King"
                                            };
                                          
    public static final int _numericValues[] = { 0, 11, 2, 3, 4, 5, 6, 7, 8, 9,
                                                10, 10, 10, 10
                                              };

    /**
     * Number of cards in one deck
     */
    public static final int numCards = 52;

    private int _valId; // unique index of Card
    private int _suitId; // unique index of suit

    private int _numericValue;

    private String _value; // String representation of Card
    private String _suit; // same for suit

    public Card(int value, int suit)
    {
        // joker value is 0
        if(value < 1 || value > 13)
            throw new IllegalArgumentException(
                "Card value must be an int between 1 and 13, inclusive");

        if(suit < 0 || suit > 3)
            throw new IllegalArgumentException(
                "Suit value must be an int between 0 and 3, inclusive");

        _valId = value;
        _suitId = suit;

        _value = values[_valId];
        _suit = suits[_suitId];
        _numericValue = _numericValues[value];
    }

    public Card()
    {
        this(1, 0);
    }

    public static Card getRandomCard()
    {
        int value = (int) Math.floor(Math.random() * 13 + 1);
        int suit = (int) Math.floor(Math.random() * 4);
        return new Card(value, suit);
    }

    public String getValue()
    {
        return _value;
    }

    public String getSuit()
    {
        return _suit;
    }

    public int getNumericValue()
    {
        return _numericValues[this._valId];
    }

    @Override
    public String toString()
    {
        return _value + " of " + _suit;
    }

    // Reduce aces from 11 to 1
    public boolean reduce()
    {
        if(_valId == 1 && _numericValue == 11)
        {
            _numericValue = 1;
            return true;
        }
        else
            return false;
    }
}
