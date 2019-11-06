import java.io.Serializable;

// https://www.developer.com/design/article.php/3597071/Serializing-an-Object-via-a-ClientServer-Connection.html
public class InstrumentList implements Serializable {

    private int potionNumber;
    private String potionName;

    InstrumentList(int num, String name) {
        potionNumber = num;
        potionName = name;
    }

    public int getPotionNumber() {
        return potionNumber;
    }

    public void setPotionNumber(int num) {
        potionNumber = num;
    }

    public void addPotionNumber() {
        potionNumber++;
    }

    public void reducePotionNumber() {
        potionNumber--;
    }

    public String getPotionName() {
        return potionName;
    }

    public void setPotionName(String name) {
        potionName = name;
    }
}