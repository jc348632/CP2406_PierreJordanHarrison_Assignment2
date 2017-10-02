/**
 * Created by Pierre Jordan Harrison on 9/9/2017.
 */
public class NormalCard extends Card {
    private float hardness;
    private float specificGravity;
    private String cleavage;
    private int cleavagePoint;
    private String crustalAbundance;
    private int crustalAbundancePoint;
    private String ecoValue;
    private int ecoValuePoint;

    NormalCard(String cardName, float cardHardness, float cardSpecificGravity, String cardCleavage,
               String cardCrustalAbundance, String cardEcoValue) {
        super(cardName);
        hardness = cardHardness;
        specificGravity = cardSpecificGravity;
        cleavage = cardCleavage;
        crustalAbundance = cardCrustalAbundance;
        ecoValue = cardEcoValue;
        cleavagePoint = convertCleavageValue(cardCleavage);
        crustalAbundancePoint = convertAbuValue(cardCrustalAbundance);
        ecoValuePoint = convertEcoValue(cardEcoValue);
    }

    public float getHardness() {
        return hardness;
    }

    public float getSpecificGravity() {
        return specificGravity;
    }

    public String getCleavage() {
        return cleavage;
    }

    public int getCleavagePoint() {
        return cleavagePoint;
    }

    public String getCrustalAbundance() {
        return crustalAbundance;
    }

    public int getCrustalAbundancePoint() {
        return crustalAbundancePoint;
    }

    public String getEcoValue() {
        return ecoValue;
    }

    public int getEcoValuePoint() {
        return ecoValuePoint;
    }

    public int convertCleavageValue(String cleavage)                                           //Converting all the Cleavage into int number to be compared
    {
        int cleaValue = 0;
        switch(cleavage) {
            case "none":
                cleaValue = 1;
                break;
            case "poor/none":
                cleaValue = 2;
                break;
            case "1 poor":
                cleaValue = 3;
                break;
            case "2 poor":
                cleaValue = 4;
                break;
            case "1 good":
                cleaValue = 5;
                break;
            case "1 good/1 poor":
                cleaValue = 6;
                break;
            case "2 good":
                cleaValue = 7;
                break;
            case "3 good":
                cleaValue = 8;
                break;
            case "1 perfect":
                cleaValue = 9;
                break;
            case "1 perfect/1 good":
                cleaValue = 10;
                break;
            case "1 perfect/2 good":
                cleaValue = 11;
                break;
            case "2 perfect/1 good":
                cleaValue = 12;
                break;
            case "3 perfect":
                cleaValue = 13;
                break;
            case "4 perfect":
                cleaValue = 14;
                break;
            case "6 perfect":
                cleaValue = 15;
                break;

        }
        return cleaValue;
    }
    public int convertAbuValue(String crustalAbundance)
    {
        int aValue = 0;
        switch(crustalAbundance){
            case "ultratrace":
                aValue=1;
                break;
            case "trace":
                aValue=2;
                break;
            case "low":
                aValue=3;
                break;
            case "moderate":
                aValue=4;
                break;
            case "high":
                aValue=5;
                break;
            case "very high":
                aValue=6;
                break;
        }
        return aValue;
    }
    public int convertEcoValue(String ecoValue)                //Converting the economic value into int to be compared
    {
        int eValue = 0;
        switch (ecoValue){
            case "trivial":
                eValue=1;
                break;
            case "low":
                eValue=2;
                break;
            case "moderate":
                eValue=3;
                break;
            case "high":
                eValue=4;
                break;
            case "very high":
                eValue=5;
                break;
            case "I'm rich!":
                eValue=6;
                break;
        }
        return eValue;
    }

    @Override
    public String toString() {
        return String.format("Name: %-17s Hardness: %5.2f   Specific Gravity: %5.2f   Cleavage: %-16s  " +
                        " Crustal Abundance: %-10s  Economic Value: %-10s", super.getName(),getHardness(),getSpecificGravity(),getCleavage(),
                getCrustalAbundance(),getEcoValue());
    }
}
