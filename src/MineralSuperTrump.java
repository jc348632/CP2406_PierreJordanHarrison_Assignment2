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
import java.util.Scanner;

/**
 * Created by Pierre Jordan Harrison on 9/9/2017.
 */
public class MineralSuperTrump {
    private Card lastPlayedCard;
    private ArrayList<Card> deck;
    private ArrayList<Player> players;
    private ArrayList<Player> winningPlayers;
    private String gameMode;
    private Scanner sc;
    private int passCounter;

    MineralSuperTrump() {
        passCounter = 0;
        lastPlayedCard = new SupertrumpCard("None","You are playing first");
        deck = new ArrayList<>();
        players = new ArrayList<>();
        winningPlayers = new ArrayList<>();
        gameMode = "none";
        sc = new Scanner(System.in);
    }


    public void start(){
        String[] array;
        String string;
        Path file = Paths.get("C:\\Users\\dellp\\Desktop\\CP2406_PierreJordanHarrison_Assignment2\\Pierre_Assn2\\src\\card.txt");
        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            reader.readLine();
            while ((string = reader.readLine()) != null){
                array = string.split(",");
                deck.add(new NormalCard(array[0],Float.valueOf(array[1]),Float.valueOf(array[2]),array[3],array[4],array[5]));
            }
            deck.add(new SupertrumpCard("The Mineralogist","Change trump category to cleavage"));
            deck.add(new SupertrumpCard("The Geologist","Change to trump category of your choice"));
            deck.add(new SupertrumpCard("The Geophysicist","Change trump category to specific gravity or throw magnetite"));
            deck.add(new SupertrumpCard("The Petrologist","Change trump category to crustal abundance"));
            deck.add(new SupertrumpCard("The Miner","Change trump category to economic value"));
            deck.add(new SupertrumpCard("The Gemmologist","Change trump category to hardness"));
            Collections.shuffle(deck);
        }
        catch(Exception e)
        {
            System.out.println("Message: " + e.getMessage());           //To show error message
        }


        System.out.println("How many players (3-5)?");
        int numberOfPlayers = sc.nextInt();
        while (numberOfPlayers <3 || numberOfPlayers>5){
            System.out.println("Wrong number of players");
            System.out.println("How many players (3-5)?");
            numberOfPlayers = sc.nextInt();
        }
        for (int x = 0; x<numberOfPlayers; x++){
            System.out.println("Enter player "+ (x+1) +" name");
            String nameInput = sc.next();
            players.add(new Player(nameInput));
        }
        for (int x = 0; x<8;x++){
            for (int y = 0; y<numberOfPlayers; y++){
                drawCard(players.get(y));
            }
        }
        boolean playing = true;
        while(playing){
            for(Player player : players){
                if(winningPlayers.size() < players.size()-1) {
                    if (gameMode.equals("none")) {
                        showCard(player);
                        modeChoice();
                        playTurn(player);
                    } else if (passCounter == players.size() - 1) {
                        lastPlayedCard = new SupertrumpCard("None", "You can put anything");
                        showCard(player);
                        modeChoice();
                        playTurn(player);
                    } else {
                        playTurn(player);
                    }
                } else {
                    playing = false;
                }
            }
        }
        int e = 1;
        System.out.println("Winner list");
        for(Player player: winningPlayers){
            System.out.println(e+". "+player.getName());
            players.remove(player);
            e++;
        }
        System.out.println(players.get(0).getName() + " has lost");
    }

    public void drawCard(Player player){
        Card drawedCard =  deck.get(deck.size()-1);
        deck.remove(drawedCard);
        player.addCard(drawedCard);
    }

    public void showCard(Player player){
        System.out.println(player.getName()+"\'s card:");
        for(int x = 0; x<player.getHand().size();x++){
            System.out.println("No: " + x +" "+ player.getHand().get(x).toString());
        }
    }

    public void modeChoice(){
        String newMode = "Empty";
        System.out.println("Please pick a game mode");
        System.out.println("H = Hardness, S = Specific Gravity, C = Cleavage, A = Crustal Abundance, " +
                "E= Economic Value");
        String input = sc.next();
        while (!(input.equals("H")||input.equals("S")||input.equals("C")||input.equals("A")||
                input.equals("E"))){
            System.out.println("Wrong mode entered");
            System.out.println("Please pick a game mode");
            System.out.println("H = Hardness, S = Specific Gravity, C = Cleavage, A = Crustal Abundance, " +
                    "E= Economic Value");
            input = sc.next();
        }
        switch (input){
            case "H":
                newMode = "Hardness";
                break;
            case "S":
                newMode = "Specific Gravity";
                break;
            case "C":
                newMode = "Cleavage";
                break;
            case "A":
                newMode = "Crustal Abundance";
                break;
            case "E":
                newMode = "Economic Value";
                break;
        }
        gameMode = newMode;
    }

    public void playTurn(Player player){
        boolean turnValidator = false;
        while (!turnValidator) {
            if(winningPlayers.contains(player)){
                passCounter++;
                turnValidator = true;
            } else {
                System.out.println("It is a game of "+ gameMode);
                System.out.println("Last played card : " + lastPlayedCard.toString());
                showCard(player);
                System.out.println("What card are you going to play?");
                System.out.println("Enter the number to play that card");
                System.out.println("Else enter PASS to pass");
                String input = sc.next();
                if (input.toUpperCase().equals("PASS")) {
                    drawCard(player);
                    passCounter++;
                    turnValidator = true;
                } else {
                    try {
                        int cardNum = Integer.parseInt(input);
                        Card cardplayed = player.playCard(cardNum);
                        Float current;
                        Float previous;
                        int comparison = 0;
                        if (cardplayed instanceof NormalCard) {
                            if (lastPlayedCard instanceof NormalCard) {
                                switch (gameMode) {
                                    case "Hardness":
                                        current = new Float(((NormalCard) cardplayed).getHardness());
                                        previous = new Float(((NormalCard) lastPlayedCard).getHardness());
                                        comparison = current.compareTo(previous);
                                        break;
                                    case "Specific Gravity":
                                        current = new Float(((NormalCard) cardplayed).getSpecificGravity());
                                        previous = new Float(((NormalCard) lastPlayedCard).getSpecificGravity());
                                        comparison = current.compareTo(previous);
                                        break;
                                    case "Cleavage":
                                        current = new Float(((NormalCard) cardplayed).getCleavagePoint());
                                        previous = new Float(((NormalCard) lastPlayedCard).getCleavagePoint());
                                        comparison = current.compareTo(previous);
                                        break;
                                    case "Crustal Abundance":
                                        current = new Float(((NormalCard) cardplayed).getCrustalAbundancePoint());
                                        previous = new Float(((NormalCard) lastPlayedCard).getCrustalAbundancePoint());
                                        comparison = current.compareTo(previous);
                                        break;
                                    case "Economic Value":
                                        current = new Float(((NormalCard) cardplayed).getEcoValuePoint());
                                        previous = new Float(((NormalCard) lastPlayedCard).getEcoValuePoint());
                                        comparison = current.compareTo(previous);
                                        break;
                                }
                                if (comparison > 0) {
                                    lastPlayedCard = cardplayed;
                                    player.useCard(cardNum);
                                    passCounter = 0;
                                    if(checkHasWin(player)){
                                        System.out.println("Player "+ player.getName() + " has left the table");
                                        player.setWin();
                                        winningPlayers.add(player);
                                        passCounter = -1;
                                    };
                                    turnValidator = true;
                                } else {
                                    System.out.println("Invalid card");
                                    System.out.println("Please play card with higher " + gameMode + " value");
                                }
                            } else {
                                lastPlayedCard = cardplayed;
                                player.useCard(cardNum);
                                passCounter = 0;
                                if(checkHasWin(player)){
                                    System.out.println("Player "+ player.getName() + " has left the table");
                                    player.setWin();
                                    winningPlayers.add(player);
                                    passCounter = -1;
                                };
                                turnValidator = true;
                            }
                        } else {
                            switch (cardplayed.getName()) {
                                case "The Mineralogist":
                                    gameMode = "Cleavage";
                                    break;
                                case "The Geologist":
                                    modeChoice();
                                    break;
                                case "The Geophysicist":
                                    gameMode = "Specific Gravity";
                                    if(player.hasMagnetite()){
                                        System.out.println("Player "+ player.getName() + " has left the table");
                                        player.setWin();
                                        winningPlayers.add(player);
                                        passCounter = -1;
                                        turnValidator = true;
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
                            lastPlayedCard = cardplayed;
                            player.useCard(cardNum);
                            if(checkHasWin(player)){
                                System.out.println("Player "+ player.getName() + " has left the table");
                                player.setWin();
                                winningPlayers.add(player);
                                passCounter = -1;
                                turnValidator = true;
                            };
                        }
                    } catch (Throwable e) {
                            System.out.println(e.getMessage());
                    }
                }
            }
        }

    }

    public boolean checkHasWin(Player player){
        boolean condition = false;
        if(player.getHand().size() == 0){
            condition = true;
        }
        return condition;
    }
}
