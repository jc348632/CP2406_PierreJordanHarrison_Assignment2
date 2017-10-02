import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Pierre Jordan Harrison on 9/30/2017.
 */
public class MineralSuperTrumpGUI extends JFrame{
    private Player lastPlayerMoved;
    private Card lastPlayedCard;
    private ArrayList<Card> deck;
    private ArrayList<Card> graveyardCard;
    private ArrayList<Player> players;
    private ArrayList<Player> winningPlayers;
    private String gameMode;
    private int passCounter;
    private int playerNumberTurn;
    private int turnCounter;
    private Player currentPlayer;
    private boolean justWin;

    JPanel mainScreen = new JPanel();
    JLabel mainText = new JLabel("Loading, please wait");
    JPanel gameScreen = new JPanel();
    JPanel tableScreen = new JPanel(new FlowLayout());
    JPanel cardInHand = new JPanel(new GridLayout(2,8));
    JLabel lastCard = new JLabel("No Card played previously");


    MineralSuperTrumpGUI() {
        turnCounter = -1;
        passCounter = 0;
        ImageIcon img = new ImageIcon("images\\Slide65.jpg");

        lastPlayedCard = new SupertrumpCardWithPicture("None","You are playing first",new ImageIcon(img.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH)));
        deck = new ArrayList<>();
        players = new ArrayList<>();
        winningPlayers = new ArrayList<>();
        graveyardCard = new ArrayList<>();
        gameMode = "none";
        justWin = false;


        setLayout(new BorderLayout());
        gameScreen.setLayout(new BoxLayout(gameScreen,BoxLayout.Y_AXIS));
        cardInHand.setMaximumSize(new Dimension(1920,720));
        add(mainScreen,BorderLayout.CENTER);
        add(mainText,BorderLayout.NORTH);
        mainText.setHorizontalAlignment(JLabel.CENTER);
        setSize(400,250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void cardScan(){
        String[] array;
        String string;
        Path file = Paths.get("C:\\Users\\dellp\\Desktop\\CP2406_PierreJordanHarrison_Assignment2\\Pierre_Assn2\\src\\card.txt");
        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            reader.readLine();
            int y = 1;
            while ((string = reader.readLine()) != null){
                array = string.split(",");
                ImageIcon img = new ImageIcon("images\\slide"+(y)+".jpg");//Inserting the image icon from the images folder
                System.out.println("images\\slide"+(y)+".jpg");
                deck.add(new NormalCardWithPicture(array[0],Float.valueOf(array[1]),Float.valueOf(array[2]),array[3],array[4],array[5],new ImageIcon(img.getImage().getScaledInstance(200,300, Image.SCALE_SMOOTH))));
                y++;
            }
            ImageIcon img = new ImageIcon("images\\Slide58.jpg");
            deck.add(new SupertrumpCardWithPicture("The Mineralogist","Change trump category to cleavage",new ImageIcon(img.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            img = new ImageIcon("images\\Slide60.jpg");
            deck.add(new SupertrumpCardWithPicture("The Geologist","Change to trump category of your choice",new ImageIcon(img.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            img = new ImageIcon("images\\Slide59.jpg");
            deck.add(new SupertrumpCardWithPicture("The Geophysicist","Change trump category to specific gravity or throw magnetite",new ImageIcon(img.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            img = new ImageIcon("images\\Slide56.jpg");
            deck.add(new SupertrumpCardWithPicture("The Petrologist","Change trump category to crustal abundance",new ImageIcon(img.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            img = new ImageIcon("images\\Slide55.jpg");
            deck.add(new SupertrumpCardWithPicture("The Miner","Change trump category to economic value",new ImageIcon(img.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            img = new ImageIcon("images\\Slide57.jpg");
            deck.add(new SupertrumpCardWithPicture("The Gemmologist","Change trump category to hardness",new ImageIcon(img.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            Collections.shuffle(deck);
        }
        catch(Exception e) {
            System.out.println("Message: " + e.getMessage());           //To show error message
        }
    }

    public void playerSelection(){
        mainText.setText("Chose the number of player");
        ArrayList<JButton> playerButton = new ArrayList<>();
        for ( int x = 3; x < 6; x++ ){
            JButton playerNumber = new JButton("" + x + "");
            playerButton.add(playerNumber);
        }
        for(int x = 0; x < 3; x++){
            int finalX = x;
            playerButton.get(x).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    inputPlayerName(finalX+3);
                }
            });
            mainScreen.add(playerButton.get(x));
        }
        repaint();
        revalidate();

    }

    public void inputPlayerName(int numberOfPlayer){
        mainScreen.removeAll();
        JTextField playerName = new JTextField(20);
        mainScreen.add(playerName);
        mainText.setText("Enter player "+(players.size()+1)+ " name");
        JButton done = new JButton("Done");
        mainScreen.add(done);
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = playerName.getText();
                players.add(new Player(name));
                mainText.setText("Enter player "+(players.size()+1)+ " name");
                playerName.setText("");
                repaint();
                revalidate();
                if(players.size()==numberOfPlayer){
                    mainScreen.removeAll();
                    nextPlayer();
                    initialDraw();
                    modeChoice();
                }
            }
        });
    }

    public void modeChoice(){
        setSize(1500,1000);
        setLocationRelativeTo(null);
        mainText.setText(currentPlayer.getName()+"\'s turn, choose your mode");
        mainScreen.removeAll();
        gameScreen.removeAll();
        tableScreen.removeAll();
        cardInHand.removeAll();
        mainScreen.add(gameScreen);
        gameScreen.add(tableScreen);
        gameScreen.add(cardInHand);
        lastPlayerMoved = new Player("None");
        ImageIcon img = new ImageIcon("images\\Slide65.jpg");
        lastPlayedCard = new SupertrumpCardWithPicture("None","You are playing first",new ImageIcon(img.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH)));


        ArrayList<JButton> categoryList = new ArrayList<>();
        JButton hardnessChoice = new JButton("Hardness");
        hardnessChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Hardness";
                cardSelection();
            }
        });
        tableScreen.add(hardnessChoice);

        JButton spegraChoice = new JButton("Specific Gravity");
        spegraChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Specific Gravity";
                cardSelection();
            }
        });
        tableScreen.add(spegraChoice);

        JButton cleavageChoice = new JButton("Cleavage");
        cleavageChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Cleavage";
                cardSelection();
            }
        });
        tableScreen.add(cleavageChoice);

        JButton crustalChoice = new JButton("Crustal Abundance");
        crustalChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Crustal Abundance";
                cardSelection();
            }
        });
        tableScreen.add(crustalChoice);

        JButton ecovalueChoice = new JButton("Economic Value");
        ecovalueChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Economic Value";
                cardSelection();
            }
        });
        tableScreen.add(ecovalueChoice);


        for(int x = 0; x< currentPlayer.getHand().size();x++)   //To show all the player card
        {
            if(currentPlayer.getHand().get(x) instanceof NormalCard)
            {
                JButton cardbtn = new JButton(new ImageIcon(((NormalCardWithPicture) currentPlayer.getHand().get(x)).getCardImage().getImage()));
                cardbtn.setSize(150,200);
                cardInHand.add(cardbtn);

            }
            else if(currentPlayer.getHand().get(x) instanceof SupertrumpCard)
            {
                JButton cardbtn = new JButton(new ImageIcon(((SupertrumpCardWithPicture) currentPlayer.getHand().get(x)).getCardImage().getImage()));
                cardbtn.setSize(150,200);
                cardInHand.add(cardbtn);
            }
        }
    }

    public void start(){
        cardScan();
        playerSelection();
    }

    public void initialDraw(){
        for (int x = 0; x<8;x++){
            for (int y = 0; y<players.size(); y++){
                drawCard(players.get(y));
            }
        }
    }

    public void drawCard(Player player){
        Card drawedCard =  deck.get(deck.size()-1);
        deck.remove(drawedCard);
        player.addCard(drawedCard);
    }
    
    public void nextPlayer(){
        turnCounter++;
        currentPlayer = players.get(turnCounter%players.size());
    }

    public void cardSelection() {
        if(winningPlayers.size() < players.size()-1) {
            if(winningPlayers.contains(currentPlayer)){
                nextPlayer();
                if(justWin){
                    modeChoice();
                    justWin = false;
                } else {
                    cardSelection();
                }
            } else {
                ArrayList<JButton> listOfJButton = new ArrayList<>();
                if (currentPlayer.equals(lastPlayerMoved)) {
                    modeChoice();
                } else {
                    mainText.setText("Player " + currentPlayer.getName() + " turn\'s, current mode = " + gameMode + ", please choose your card or pass");
                    mainScreen.removeAll();
                    tableScreen.removeAll();
                    cardInHand.removeAll();
                    gameScreen.add(tableScreen);
                    gameScreen.add(cardInHand);
                    if (!(lastPlayerMoved.getName().equals("None"))) {
                        JButton passButton = new JButton("PASS");
                        passButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(deck.size()==0){
                                    reAddDeck();
                                }
                                drawCard(currentPlayer);
                                nextPlayer();
                                cardSelection();
                            }
                        });
                        tableScreen.add(passButton);
                        tableScreen.add(new JLabel("Last card:"));
                        if (lastPlayedCard instanceof NormalCard) {
                            lastCard = new JLabel(new ImageIcon(((NormalCardWithPicture) lastPlayedCard).getCardImage().getImage()));
                        } else {
                            lastCard = new JLabel(new ImageIcon(((SupertrumpCardWithPicture) lastPlayedCard).getCardImage().getImage()));
                        }
                        tableScreen.add(lastCard);
                    }


                    for (int x = 0; x < currentPlayer.getHand().size(); x++) {
                        if (currentPlayer.getHand().get(x) instanceof NormalCard) {
                            JButton cardbtn = new JButton(new ImageIcon(((NormalCardWithPicture) currentPlayer.getHand().get(x)).getCardImage().getImage()));
                            cardbtn.setSize(150, 200);
                            cardInHand.add(cardbtn);
                            listOfJButton.add(cardbtn);
                        } else if (currentPlayer.getHand().get(x) instanceof SupertrumpCard) {
                            JButton cardbtn = new JButton(new ImageIcon(((SupertrumpCardWithPicture) currentPlayer.getHand().get(x)).getCardImage().getImage()));
                            cardbtn.setSize(150, 200);
                            cardInHand.add(cardbtn);
                            listOfJButton.add(cardbtn);
                        }
                    }
                    for (int button = 0; button < currentPlayer.getHand().size(); button++) {
                        listOfJButton.get(button).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JButton buttonSource = (JButton) e.getSource();
                                Card cardPlayed = currentPlayer.getHand().get(listOfJButton.indexOf(buttonSource));
                                Float current;
                                Float previous;
                                int comparison = 0;
                                if (cardPlayed instanceof NormalCard) {
                                    if (lastPlayedCard instanceof NormalCard) {
                                        switch (gameMode) {
                                            case "Hardness":
                                                current = new Float(((NormalCard) cardPlayed).getHardness());
                                                previous = new Float(((NormalCard) lastPlayedCard).getHardness());
                                                comparison = current.compareTo(previous);
                                                break;
                                            case "Specific Gravity":
                                                current = new Float(((NormalCard) cardPlayed).getSpecificGravity());
                                                previous = new Float(((NormalCard) lastPlayedCard).getSpecificGravity());
                                                comparison = current.compareTo(previous);
                                                break;
                                            case "Cleavage":
                                                current = new Float(((NormalCard) cardPlayed).getCleavagePoint());
                                                previous = new Float(((NormalCard) lastPlayedCard).getCleavagePoint());
                                                comparison = current.compareTo(previous);
                                                break;
                                            case "Crustal Abundance":
                                                current = new Float(((NormalCard) cardPlayed).getCrustalAbundancePoint());
                                                previous = new Float(((NormalCard) lastPlayedCard).getCrustalAbundancePoint());
                                                comparison = current.compareTo(previous);
                                                System.out.println(previous);
                                                break;
                                            case "Economic Value":
                                                current = new Float(((NormalCard) cardPlayed).getEcoValuePoint());
                                                previous = new Float(((NormalCard) lastPlayedCard).getEcoValuePoint());
                                                comparison = current.compareTo(previous);
                                                break;
                                        }
                                        if (comparison > 0) {
                                            lastPlayedCard = cardPlayed;
                                            graveyardCard.add(cardPlayed);
                                            currentPlayer.useCard(currentPlayer.getHand().indexOf(cardPlayed));
                                            lastPlayerMoved = currentPlayer;
                                            if (checkHasWin(currentPlayer)) {
                                                currentPlayer.setWin();
                                                winningPlayers.add(currentPlayer);
                                                justWin = true;
                                            }
                                            nextPlayer();
                                            cardSelection();
                                        } else {
                                            mainText.setText("Invalid card,Please play card with higher " + gameMode + " value. Or simply pass");
                                        }
                                    } else {
                                        lastPlayedCard = cardPlayed;
                                        graveyardCard.add(cardPlayed);
                                        currentPlayer.useCard(currentPlayer.getHand().indexOf(cardPlayed));
                                        lastPlayerMoved = currentPlayer;
                                        if (checkHasWin(currentPlayer)) {
                                            currentPlayer.setWin();
                                            winningPlayers.add(currentPlayer);
                                            justWin = true;
                                        }
                                        ;
                                        nextPlayer();
                                        cardSelection();
                                    }
                                } else {
                                    switch (cardPlayed.getName()) {
                                        case "The Mineralogist":
                                            gameMode = "Cleavage";
                                            break;
                                        case "The Geologist":
                                            modeChoice();
                                            break;
                                        case "The Geophysicist":
                                            gameMode = "Specific Gravity";
                                            if (currentPlayer.hasMagnetite()) {
                                                currentPlayer.setWin();
                                                winningPlayers.add(currentPlayer);
                                            }
                                            break;
                                        case "The Petrologist":
                                            gameMode = "Crustal abundance";
                                            break;
                                        case "The Miner":
                                            gameMode = "Economic Value";
                                            break;
                                        case "The Gemmologist":
                                            gameMode = "Hardness";
                                            break;
                                    }
                                    lastPlayedCard = cardPlayed;
                                    graveyardCard.add(cardPlayed);
                                    currentPlayer.useCard(currentPlayer.getHand().indexOf(cardPlayed));
                                    if (checkHasWin(currentPlayer)) {
                                        currentPlayer.setWin();
                                        winningPlayers.add(currentPlayer);
                                        justWin = true;
                                    }
                                    cardSelection();
                                }
                            }
                        });

                    }
                    mainScreen.add(gameScreen);
                    repaint();
                    revalidate();
                }
            }
        } else {
            showWinner();
        }
    }

    public boolean checkHasWin(Player player){
        boolean condition = false;
        if(player.getHand().size() == 0){
            condition = true;
        }
        return condition;
    }

    public void showWinner()
    {
        mainScreen.removeAll();
        JPanel winnerList = new JPanel();
        winnerList.setLayout(new BoxLayout(winnerList,BoxLayout.Y_AXIS));
        for(int x = 0; x < winningPlayers.size();x++)
        {
            String show = "No "+(x+1)+ "= " + winningPlayers.get(x).getName() +"";
            winnerList.add(new JLabel(show));
        }
        mainText.setText("Here are the winner");
        mainScreen.add(winnerList);
        revalidate();
        repaint();
    }

    public void reAddDeck(){
        for(Card card : graveyardCard){
            deck.add(card);
        }
        graveyardCard.clear();
        Collections.shuffle(deck);
    }
}
