package main;

import cardgame.Deck;
import cardgame.Card;
import blackjack.Player;
import blackjack.DealerPlayer;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        Deck deck = new Deck(true); // Generate a new shuffled deck
        ArrayList<Player> players = new ArrayList<Player>(0);

        System.out.println("Welcome to George's Java BlackJack!");
        System.out.println("Type h for help when your turn comes");

        int numPlayers = -1; // Initialize so that while loop will run at least once
        System.out.println("How many players? (Max of 10 players allowed)");
        numPlayers = parseInt(br.readLine());

        while(numPlayers < 1 || numPlayers > 10)
        {
            System.out.print("Bad input, try again: ");
            numPlayers = parseInt(br.readLine());
        }

        // Grab Players' names
        for(int i = 1; i <= numPlayers; i++)
        {
            System.out.println("Name of Player " + i + ": ");
            String name = br.readLine();
            if(name.length() > 40)
            {
                System.out.println("Name is too long, try again. (Max of 40 characters)");
                i --; // Redo the current iteration
                continue;
            }

            players.add(new Player(name, deck.draw(), deck.draw()));
        }

        DealerPlayer dealer = new DealerPlayer(deck.draw(), deck.draw());

        // Main Game Loop
        while(true)
        {
            System.out.println("================");
            System.out.println("Enter amount of money to bet for this round, and press enter");

            ArrayList<Player> playersToRemove = new ArrayList<Player> ();
            for(Player player : players)
            {
                int moneyLeft = player.getMoney();
                // Add broke players to temporary array to remove later
                if(moneyLeft == 0)
                {
                    System.out.println(player.getName() + " has no more money left!");
                    playersToRemove.add(player);
                    continue;
                }

                System.out.print("($" + moneyLeft + " left) " + player.getName() + " bets $");
                int bet = parseInt(br.readLine());
                while(bet < 1 || bet > moneyLeft)
                {
                    System.out.print("You can't bet that much! Try again: ");
                    bet = parseInt(br.readLine());
                }
                player.bet(bet);
            }
            // Remove broke players
            players.removeAll(playersToRemove);

            if(players.size() == 0)
            {
                System.out.println("No more players left!");
                break;
            }

            // Reveal Dealer's one card
            System.out.println("================");
            System.out.println("Dealer's card: " + dealer.getPocketCard().toString());
            System.out.println("================");

            for(Player player : players)
            {
                System.out.print(player.printFormatted());
                while(player.getState() == "canHit")
                {
                    System.out.print(player.getName() + "'s turn (h for help): ");
                    String decision = br.readLine();
                    switch(decision)
                    {
                        case "hit":
                            player.hit(deck.draw());
                            break;
                        case "stand":
                            player.stand();
                            break;
                        case "double":
                            if(player.getBet() < player.getMoney())
                                player.doubleDown(deck.draw());
                            else
                                System.out.println("Not enough money to double down");
                            break;
                        case "surrender":
                            player.surrender();
                            break;
                        case "split":
                            System.out.println("Unimplemented feature!");
                            break;
                        case "exit":
                        case "quit":
                        case "stop":
                            System.out.println("Have a nice day!");
                            System.exit(0);
                        case "h": // Display Help Commands
                            System.out.println("===============\nCommands:");
                            System.out.println("hit        - receive another card from the deck");
                            System.out.println("stand      - stop receiving cards; next player's turn");
                            System.out.println("double     - double your bet, and receive one last card");
                            System.out.println("surrender  - stop playing for this round, but retrieve only half your bet");
                            System.out.println("help       - display this help message");
                            System.out.println("quit/stop  - stop this game");
                            break;
                        default:
                            System.out.println("Unrecognized input, try again");
                    }

                    //if(player.getState() != "surrender")
                    System.out.print(player.printFormatted());
                }
                System.out.print("Press Enter to continue");
                System.in.read();
                System.out.println("===============");
            }

            System.out.println("Dealer's turn:\n");
            System.out.print(dealer.printFormatted());
            while(dealer.getState() == "canHit")
            {
                dealer.hit(deck.draw());
                System.out.print(dealer.printFormatted());
            }

            int dealerScore = dealer.getScore();
            int playerScore = 0;

            // Go through different loops depending on if dealer has busted or not
            if(dealer.getState() != "bust")
            {
                for(Player current : players)
                {
                    playerScore = current.getScore();
                    String state = current.getState();
                    if(state == "bust")
                    {
                        System.out.print(current.getName() + " busted! (lost $" + current.getBet() +
                                         ")");
                        // Don't need to pay dealer, he has infinite money
                    }
                    else if(state == "surrender")
                    {
                        System.out.print(current.getName() + " surrendered! (lost $" + current.getBet()
                                         / 2 + ")");
                    }
                    else if(playerScore > dealerScore)
                    {
                        if(state == "blackjack")
                        {
                            System.out.print(current.getName() + " has BlackJack! (won $" + current.getBet()
                                             * 1.5 + ")");
                            current.blackjack();
                        }
                        else
                        {
                            System.out.print(current.getName() + " won $" + current.getBet() +
                                             " from the Dealer!");
                            current.winDealer();
                        }
                    }
                    else if(playerScore == dealerScore)
                    {
                        System.out.print(current.getName() + " tied with the Dealer! (no money lost)");
                        current.tie();
                    }
                    else
                    {
                        System.out.print(current.getName() + " lost $" + current.getBet() +
                                         " to the Dealer!");
                        // Don't need to pay dealer, he has infinite money
                    }
                    //Additionally, print the score of the player
                    if(state != "bust" || state != "blackjack")
                        System.out.println(" (Score of " + current.getScore() + ")");
                    else
                        System.out.println();
                }
            }
            else
            {
                System.out.println("Dealer busted!");

                for(Player current : players)
                {
                    String state = current.getState();
                    if(state == "bust")
                    {
                        System.out.println(current.getName() + " busted too! (no money lost)");
                        current.tie();
                    }
                    else if(state == "surrender")
                    {
                        System.out.println(current.getName() + " surrendered! (lost $" +
                                           current.getBet() / 2 + ")");
                    }
                    else
                    {
                        if(state == "blackjack")
                        {
                            System.out.println(current.getName() + " has BlackJack! (won $" +
                                               current.getBet() * 1.5 + ")");
                            current.blackjack();
                        }
                        else
                        {
                            System.out.println(current.getName() + " won $" + current.getBet() +
                                               " from the Dealer!");
                            current.winDealer();
                        }
                    }
                }
            }

            // Return the players' cards to the deck, and give them new cards
            for(Player current : players)
            {
                deck.returnCards(current.resetHand());
                current.newHand(deck.draw(), deck.draw());
            }

            // Dealer also returns his cards
            deck.returnCards(dealer.resetHand());

            // Give dealer new cards
            dealer.newHand(deck.draw(), deck.draw());

            System.out.print("Press Enter to continue");
            System.in.read();

            System.out.println("====================\nNew Game!");

        }
    }

    //For our purposes, parse a non-negative number that has less than 7 digits
    private static int parseInt(String toParse)
    {
        int length = toParse.length();
        if(length > 7)
            return -1;

        int num = 0;
        for(int i = 0; i < length; i ++)
        {
            char current = toParse.charAt(i);
            if((int) current < '0' && (int) current > '9')
                return -1;
            int digit = current - '0';

            num *= 10;
            num += digit;
        }

        return num;
    }
}



