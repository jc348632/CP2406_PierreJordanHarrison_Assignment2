/**
 * Created by Pierre Jordan Harrison on 9/9/2017.
 */
public class SupertrumpCard extends Card {
    private String description;
    SupertrumpCard(String cardName, String cardDescription) {
        super(cardName);
        description = cardDescription;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("Name: %-17s Description: %s",super.getName(),getDescription());
    }
}
