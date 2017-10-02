import javax.swing.*;

/**
 * Created by Pierre Jordan Harrison on 9/30/2017.
 */
public class NormalCardWithPicture extends NormalCard {
    private ImageIcon cardImage;
    NormalCardWithPicture(String cardName, float cardHardness, float cardSpecificGravity, String cardCleavage, String cardCrustalAbundance, String cardEcoValue, ImageIcon image) {
        super(cardName, cardHardness, cardSpecificGravity, cardCleavage, cardCrustalAbundance, cardEcoValue);
        cardImage= image;
    }

    public ImageIcon getCardImage() {
        return cardImage;
    }
}
