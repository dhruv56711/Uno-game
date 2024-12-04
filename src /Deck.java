
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<>();

    public void initializeDeck() {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        String[] specialCards = {"Draw Two", "Skip", "Reverse"};

        for (String color : colors) {
            // Add number cards
            cards.add(new Card(color, "0"));
            for (int i = 1; i <= 9; i++) {
                cards.add(new Card(color, String.valueOf(i)));
                cards.add(new Card(color, String.valueOf(i)));
            }
            // Add special cards
            for (String special : specialCards) {
                cards.add(new Card(color, special));
                cards.add(new Card(color, special));
            }
        }
        // Add Wild and Wild Draw Four cards
        for (int i = 0; i < 4; i++) {
            cards.add(new Card("Wild", "Wild"));
            cards.add(new Card("Wild", "Wild Draw Four"));
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) return null;
        return cards.remove(0);
    }

    public void replenishDeck(List<Card> discardPile) {
        cards.addAll(discardPile);
        discardPile.clear();
        shuffleDeck();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
