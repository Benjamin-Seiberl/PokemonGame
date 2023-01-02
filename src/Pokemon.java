import java.util.ArrayList;

public class Pokemon {

    //region fields
    private int id;
    private String name;
    private int currentHp;
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private String type1;
    private String type2;
    private ArrayList<String> abilities;
    private ArrayList<Double> effectiveAgainstType;
    private int pokebagIndex;
    //endregion

    //region Constructors
    public Pokemon(int id, String name, int currentHp, int hp, int attack, int defense, int speed, String type1, String type2, ArrayList<String> abilities, ArrayList<Double> effectiveAgainstType, int pokebagIndex) {
        this.id = id;
        this.name = name;
        this.currentHp = currentHp;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.type1 = type1;
        this.type2 = type2;
        this.abilities = abilities;
        this.effectiveAgainstType = effectiveAgainstType;
        this.pokebagIndex = pokebagIndex;
    }


    public Pokemon(Pokemon another) {
        this.id = another.id;
        this.name = another.name;
        this.currentHp = another.currentHp;
        this.hp = another.hp;
        this.attack = another.attack;
        this.defense = another.defense;
        this.speed = another.speed;
        this.type1 = another.type1;
        this.type2 = another.type2;
        this.abilities = another.abilities;
        this.effectiveAgainstType = another.effectiveAgainstType;
        this.pokebagIndex = another.pokebagIndex; // todo maybe problem
    }
    //endregion

    //region Getter and Setter
    public ArrayList<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
    }

    public ArrayList<Double> getEffectiveAgainstType() {
        return effectiveAgainstType;
    }

    public void setEffectiveAgainstType(ArrayList<Double> effectiveAgainstType) {
        this.effectiveAgainstType = effectiveAgainstType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public int getPokebagIndex() {
        return pokebagIndex;
    }

    public void setPokebagIndex(int pokebagIndex) {
        this.pokebagIndex = pokebagIndex;
    }

    //endregion

    //region ToString
    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", speed=" + speed +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                '}';
    }
    //endregion

}
