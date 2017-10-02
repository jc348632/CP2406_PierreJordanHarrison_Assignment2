import java.util.ArrayList;

/**
 * Created by Pierre Jordan Harrison on 9/9/2017.
 */
public class Player {
    private ArrayList<Card> hand;
    private String name;
    private boolean win;
    Player(String nameInput){
        name = nameInput;
        hand = new ArrayList<Card>();
        win = false;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card playCard(int cardNumber){
        Card card = hand.get(cardNumber);
        return card;
    }

    public void useCard(int cardNumber){
        hand.remove(cardNumber);
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void setWin() {
        this.win = true;
    }


    public boolean hasMagnetite(){
        boolean indicator = false;
        for(Card card: hand){
            if(card.getName().equals("Magnetite")){
                indicator = true;
            }
        }
        return indicator;
    }
}
