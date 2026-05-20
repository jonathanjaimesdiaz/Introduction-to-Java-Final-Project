/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.blackjackcasino;

/**Name: Jonathan Jaimes-Diaz
 *Date: 5//2026
 *Assignment: Final project 
 *Description: 
 */
import java.util.Scanner;

public class BlackjackCasino {
    public static final int BLACKJACK = 21;
    public static final int DEALER_MIN = 17;
    public static final int MAX_CARDS = 10;
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int choice;
        boolean keepRunning = true;
        
        //This array of obects a requirement, which stores player objects.
        //Even thoguh this game uses one player, the array shows how objects can be stored in an array. 
        Player player = null;
        
        
        //Casino menu, took this idea from our midterm. 
        //2D array also a requirement, thid stores the cansion menu chocies and game names.
        //The first column holds the menu number, and the second colum holds the game name. 
        String[][] gameMenu = {
            {"1", "Blackjack"},
            {"2", "Baccarat"},
            {"3", "Texas Hold'em"},
            {"4", "Poker"},
            {"5", "Exit"}
        };
        
        System.out.println("Welcome to Blackjack Casino!");
        
        
        //While loop one of the reqirments, this is what keep the main casino menu running, 
        //until the user chooses blackjack or exits the program. 
        while (keepRunning) {
            showMenu(gameMenu);
            choice = getMenuChoice(input);
            
            
            //Another requirment switch, this contorls what happens based on the users menu choice. 
            switch (choice) {
                case 1: 
                    player = createPlayer(input);
                    playBlackjack(input, player);
                    keepRunning = false;
                    break;
                case 2: 
                case 3:
                case 4:
                    System.out.println("Sorry, this game is full.");
                    System.out.println("How about you pick something else.");
                    break;
                case 5:
                    System.out.println("Thanks for playing. GoodBye");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            System.out.println();
        }
    }
    
    
    //Enhanced loop another requirement, this displays each row from the 2D menu.
    // I used this loop here because it is a simple way to print each game option.  
    public static void showMenu(String[][] gameMenu) {
        System.out.println("Casino Menu");
        for (int i = 0; i < gameMenu.length; i++) {
            System.out.println(gameMenu[i][0] + ". " + gameMenu[i][1]);
        }
    }
    
   // This is where the user makes their choice on which game them want to play.
   // This try / catch helps stop the code from crashing if the user types words instead of numbers. 
    public static int getMenuChoice(Scanner input) {
        int choice = 0;
        
        try {
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            input.nextLine();
        }
        return choice;
    }
    
    // Here is the backbone of how you create your perosnal gambling addict.
    // You decided how much money you have and your name. 
    // Have to make sure one add more than $0 into there account.
    // After all thatthe method returns a player object. 
    public static Player createPlayer(Scanner input) {
        String name;
        double money = 0;
        
        System.out.print("Enter your name: ");
        name = input.nextLine();
        
        //The do/while loop ANOTHER requirement, keeps asking for money until the user enters a valid amount.
        //This make sure the player starts with more than $0 dollars.
        do {
            try {
                System.out.print("Enter starting money: ");
                money = input.nextDouble();
                input.nextLine();
                
                if (money <= 0) {
                    System.out.println("Money must be greater than 0.");
                }
            } catch (Exception e) {
                System.out.println("Invalid amount. Please enter a number.");
                input.nextLine();
                money = 0;
            }
        } while (money <= 0);
        
        return new Player(name, money);
    } 
    
    // The Brain, this methods run the blackjack game. 
    //It controls the betting, card dealing, player choices, dealer turn, and final results.
    public static void playBlackjack(Scanner input, Player player) {
        boolean playing = true;
        
        // I honestly think I passed the requirements for loops but here is while loop. 
        //This one keeps blackjack running while the player has money, and wnats to continue playing. 
        while (playing && player.getMoney() > 0) {
            double bet = getBetAmount(input, player);
            
            
            //1D array, they hold the player and dealer cards 
            // Each hand can hold up to MAX_CARDS cards. 
            int[] playerHand = new int[MAX_CARDS];
            int[] dealerHand = new int[MAX_CARDS];
            
            int playerCount = 0;
            int dealerCount = 0;
            
            //dealer/code starts dealing here. the first two cards to the player and dealer. 
            playerHand[playerCount++] = drawCard();
            playerHand[playerCount++] = drawCard();
            
            dealerHand[dealerCount++] = drawCard();
            dealerHand[dealerCount++] = drawCard();
            
            
            //The player sees their full hand, but only the first card is shown for the dealer. 
            displayHand("Player", playerHand, playerCount);
            System.out.println("Dealer: " + getCardName(dealerHand[0]) + " ?");
            
            // I tested adding a split option, but honestly it was very diificult and I messed up other parts,
            // it eventually stoped working. SO I just commented everything out. 
//             boolean splitUsed = false;
//            
//            if (canSplit(playerHand) && bet * 2 <= player.getMoney()) {
//                System.out.print("You can split. Do you want to split? (yes/no): ");
//                String splitAnswer = input.nextLine();
//                
//                if (splitAnswer.equalsIgnoreCase("yes")) {
//                    splitUsed = true;
//                }
//            } 
            
            //
            boolean playerTurn = true;
            
            //This loop controls the player's tunrs.
            //In this game you could hit, stand, or double down. 
            while (playerTurn) {
                int playerTotal = getHandValue(playerHand, playerCount);
                
                System.out.println("Your total: " + playerTotal);
                              
                // If/else statement, which checks if the player busted.
                if (playerTotal > BLACKJACK) {
                    System.out.println("You busted!");
                    player.placeBet(bet);
                    player.loseBet();
                    playerTurn = false;
                    break;
                }
                System.out.print("Hit or Stand, or Double?");
                String choice = input.nextLine();
                
                if (choice.equalsIgnoreCase("hit")) {
                    playerHand[playerCount++] = drawCard();
                    displayHand("Player", playerHand, playerCount);
                    
                } else if (choice.equalsIgnoreCase("double")) {
                    
                    // Double down checks if the player has enough money to double the bet.  
                    if (bet * 2 > player.getMoney()) {
                        System.out.println("Not enough money to doublr down.");
                    } else {
                   
                        //this doubles the bet 
                        bet = bet * 2;
                    
                        System.out.println("You doubled down!");
                    
                        //Once you double down thats it, it only allows you one card. 
                        playerHand[playerCount++] = drawCard();
                        displayHand("Player", playerHand, playerCount);
                    
                        //The player then is forced to stand.  
                        playerTurn = false;
                        }
                } else {
                    playerTurn = false;
                }
            }
            
            int playerTotal = getHandValue(playerHand, playerCount);
            
            if (playerTotal <= BLACKJACK) {
                System.out.println();
                System.out.println("Dealer reveals hand:");
                dealerCount = dealerTurn(dealerHand, dealerCount);
                
                int dealerTotal = getHandValue(dealerHand, dealerCount);
                
                System.out.println("Your total: " + playerTotal);
                System.out.println("Dealer total: " + dealerTotal);
                
                player.placeBet(bet);
                
                String winner = determineWinner(playerTotal, dealerTotal);
                
                if (winner.equals("player")) {
                    System.out.println("You won the hand!");
                    player.winBet();
                } else if (winner.equals("dealer")) {
                    System.out.println("Dealer wins the hand.");
                    player.loseBet();
                } else {
                    System.out.println("Push. Nobody wins.");
                    player.pushBet();
                }
            }
            System.out.println("Current Money: $" + player.getMoney());
            
            if (player.getMoney() <= 0) {
                System.out.println("You are out of money. GAME OVER.");
                playing = false;
            } else {
                System.out.print("Play another hand? (yes/no): ");
                String answer = input.nextLine();
                
                if (!answer.equalsIgnoreCase("yes")) {
                    playing = false; 
                }
            }
            System.out.println();
        }
    }

    //This method gets the bet amount money from the player. 
    // It makes sure the bet is greater than zero and not more than the players moeny. 
    public static double getBetAmount(Scanner input, Player player) {
        double bet = 0;
    
        do {
            try {
                System.out.println("Current Money: $" + player.getMoney());
                System.out.print("Enter your bet: ");
                bet = input.nextDouble();
                input.nextLine();
            
                if (bet <= 0 || bet > player.getMoney()) {
                    System.out.println("Invalid bet amount.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
                input.nextLine();
                bet = 0;
            }
        } while (bet <= 0 || bet > player.getMoney());
    
        return bet;
    }
    
    // This method draws a random card from 1 to 13 - (J, Q, K)
    public static int drawCard() {
        return (int)(Math.random() * 13) + 1;
    }
    
    //this for loop method sloops through the cards in a hand and adds their value. 
    //It also handles Aces by counting them as 11 first then lowering them to 1 if needed. 
    public static int getHandValue(int[] hand, int cardCount) {
        int total = 0;
        int aceCount = 0;
        
        for (int i = 0; i < cardCount; i++) {
            int card = hand[i];
            
            if (card ==1) {
                total += 11;
                aceCount++;
            } else if (card > 10) {
                total += 10;
            } else {
                total += card;
            }
        }
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }
    
    //This methods displays the cards in a hand 
    //It uses getCardName so face cards show as J, Q, and K instead of 11, 12, 13. 
    public static void displayHand(String name, int[] hand, int count) {
        System.out.print(name + ": ");
        
        for (int i = 0; i < count; i++) {
            System.out.print(getCardName(hand[i]) + " ");
        }
        System.out.println();
    }
    
    
    // Jack, Queen, and King will appear as J/Q/K, because as of right now they show up like 11,12,13,
    public static String getCardName(int card) {
        switch (card) {
            case 1: 
                return "A";
            case 11: 
                return "J";
            case 12: 
                return "Q";
            case 13: 
                return "K";
            default: 
                return String.valueOf(card);
        }
    }
    
    //This method cotnrls the delear's turn. 
    //This will give the dealer power to keep drawing cards till they at least reach 17
    public static int dealerTurn(int[] dealerHand, int dealerCount) {
        displayHand("Dealer", dealerHand, dealerCount);
        
        while (getHandValue(dealerHand, dealerCount) < DEALER_MIN) {
            dealerHand[dealerCount++] = drawCard();
            System.out.println("Dealer Hits.");
            displayHand("Dealer", dealerHand, dealerCount);
        }
        return dealerCount;
    }
    
    //This meothd compares the player and dealer totlas to decide who won. 
    public static String determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > BLACKJACK) {
            return "player";
        } else if (playerTotal > dealerTotal) {
            return "player";
        } else if (playerTotal < dealerTotal) {
            return "dealer";
        } else {
            return "push";
        }
    }
    

    
    
    // 
//    public static boolean canSplit(int[] hand) {
//        return getCardValue(hand[0]) == getCardValue(hand[1]);
//    }
//    
//    // 
//    public static int getCardValue(int card) {
//        if (card == 1) {
//            return 11;
//        } else if (card > 10) {
//            return 10;
//        } else {
//            return card;
//        }
//    }
//    
//    //
//    public static int playOneHand(Scanner input, int[] hand, int cardCount, String handName){
//        boolean playerTurn = true;
//        
//        while (playerTurn) {
//            displayHand(handName, hand, cardCount);
//            
//            int total = getHandValue(hand, cardCount);
//            System.out.println(handName + "total: " + total);
//            
//            
//            if (total > BLACKJACK) {
//                System.out.println(handName + " busted!");
//                playerTurn = false;
//            } else {
//                System.out.print("Hit or Stand? ");
//                String choice = input.nextLine();
//                
//                if (choice.equalsIgnoreCase("hit")) {
//                    hand[cardCount++] = drawCard();
//                } else {
//                    playerTurn = false; 
//                }
//            }
//        }
//        return cardCount;
//    }
}



