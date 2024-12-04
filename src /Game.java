
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {


    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of players (2-5): ");
        int numPlayers = scanner.nextInt();
        if (numPlayers < 2 || numPlayers > 5) {
            System.out.println("Invalid number of players. Exiting.");
            return;
        }
        game.startGame(numPlayers);
    }
    private List<Player> players = new ArrayList<>();
    private Deck deck = new Deck();
    private List<Card> discardPile = new ArrayList<>();
    private int currentPlayer = 0;
    private int direction = 1;

    public void startGame(int numPlayers) {
        deck.initializeDeck();
        deck.shuffleDeck();

        // Initialize players
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }

        // Deal cards
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.drawCard(deck.drawCard());
            }
        }

        // Start discard pile
        discardPile.add(deck.drawCard());

        // Game loop
        while (true) {
            playTurn();
            if (checkGameOver()) break;
        }
        scanner.close();
    }

    private void playTurn() {
        Player player = players.get(currentPlayer);
        System.out.println("\n" + player.getName() + "'s turn.");
        System.out.println("Your hand: " + player.getHand());
        System.out.println("Top of discard pile: " + discardPile.get(discardPile.size() - 1));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose a card to play (0-based index) or -1 to draw: ");
        int choice = scanner.nextInt();

        if (choice == -1) {
            player.drawCard(deck.drawCard());
        } else {
            Card card = player.playCard(choice);
            if (card != null && isValidMove(card)) {
                discardPile.add(card);
                handleSpecialCard(card);
            } else {
                System.out.println("Invalid move! You draw a card.");
                player.drawCard(card);
            }
        }

        currentPlayer = (currentPlayer + direction + players.size()) % players.size();
    }

    private boolean isValidMove(Card card) {
        Card topCard = discardPile.get(discardPile.size() - 1);
        return card.getColor().equals(topCard.getColor()) || card.getType().equals(topCard.getType()) || card.getColor().equals("Wild");
    }

    private void handleSpecialCard(Card card) {
        switch (card.getType()) {
            case "Reverse":
                direction *= -1;
                break;
            case "Skip":
                currentPlayer = (currentPlayer + direction + players.size()) % players.size();
                break;
            case "Draw Two":
                int nextPlayer = (currentPlayer + direction + players.size()) % players.size();
                players.get(nextPlayer).drawCard(deck.drawCard());
                players.get(nextPlayer).drawCard(deck.drawCard());
                break;
            case "Wild Draw Four":
                int targetPlayer = (currentPlayer + direction + players.size()) % players.size();
                for (int i = 0; i < 4; i++) {
                    players.get(targetPlayer).drawCard(deck.drawCard());
                }
                break;
        }
    }

    private boolean checkGameOver() {
        for (Player player : players) {
            if (player.hasWon()) {
                System.out.println(player.getName() + " wins the game!");
                return true;
            }
        }
        return false;
    }

   
}
