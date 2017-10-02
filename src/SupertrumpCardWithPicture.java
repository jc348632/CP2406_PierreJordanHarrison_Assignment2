import javax.swing.*;

/**
 * Created by Pierre Jordan Harrison on 9/30/2017.
 */
public class SupertrumpCardWithPicture extends SupertrumpCard {
    private ImageIcon cardImage;
    SupertrumpCardWithPicture(String cardName, String cardDescription, ImageIcon image) {
        super(cardName, cardDescription);
        cardImage = image;
    }

    public ImageIcon getCardImage() {
        return cardImage;
    }
}
