
public class Card {
    private String color;
    private String type;

    public Card(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return color + " " + type;
    }
}
