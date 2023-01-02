import java.util.ArrayList;

public class Arena {
    private Trainer player;
    private Trainer enemy;
    private ArrayList<String> modifierNames;
    private Pokedex pokedex;


    public Arena() {
    }

    public Arena(Trainer player, Trainer enemy, Pokedex pokedex) {
        this.player = player;
        this.enemy = enemy;
        this.modifierNames = pokedex.getModifierNames();
        this.pokedex = pokedex;
    }

    public boolean battle() {
        printCurrentPokemon();
        while (!whipedOut(player) && !whipedOut(enemy)) {
            int option = chooseOption();
            if (option == 1) {
                attack(player, enemy,false);
            } else if (option == 2) {
                if(player.getPokebag().get(1) != null) {
                    swapPokemon();
                }else{
                    System.out.printf("\t\tYou don't have a Pokemon to swap!");
                    attack(player, enemy,false);
                }
            }
            attack(enemy, player,true);
        }

        if (!whipedOut(player)) {
            return true;
        } else {
            return false;
        }
    }

    private void swapPokemon() {
        PokeBag pokeBag = player.getPokebag();
        Engine.printPoints();
        for (int i = 0; i < 6; i++) {
            Pokemon p = pokeBag.get(i);
            if(p != null){
                Engine.printLine(String.format("(%d) %20s (%d/%d <3)", i+1, p.getName(), p.getCurrentHp(), p.getHp()));
            }
        }
        Engine.printPoints();
        System.out.println();
        int option = 0;
        while(player.getCurrentPokemon().equals(pokeBag.get(option)) || (pokeBag.get(option).getCurrentHp() == 0)) {
            option = UserInput.getInt(1, 6, "Choose an valid option!") - 1;
            if(pokeBag.get(option).getCurrentHp() == 0){
                System.out.println("\t\t" + pokeBag.get(option).getName() + " is not able to fight. Choose another pokemon!");
            }
            if(option == 0 && pokeBag.get(option).getCurrentHp() != 0){
                System.out.println("\t\tYou choose not to swap your pokemon!");
                break;
            }
        }
        player.getPokebag().swap(option );


    }


    private void attack(Trainer trainer1, Trainer trainer2, boolean com) {
        int option = 0;
        if(!com) {
            printAbilities();
            option = UserInput.getInt(1, trainer1.getCurrentPokemon().getAbilities().size(), "Choose an valid option!") - 1;
        }else{
            option = (int) Math.random() * (trainer1.getCurrentPokemon().getAbilities().size()-1);
        }
        int damage = calculateDamage(trainer1.getCurrentPokemon(), trainer2.getCurrentPokemon());
        System.out.println("\t\t" + trainer1.getCurrentPokemon().getName() + " attacks with " + trainer1.getCurrentPokemon().getAbilities().get(option) + ". " + trainer1.getCurrentPokemon().getName() + " deals " + damage + " damage.\n");
        trainer2.getCurrentPokemon().setCurrentHp(trainer2.getCurrentPokemonHP() - damage);
        if (trainer2.getCurrentPokemonHP() <= 0) {
            trainer2.getCurrentPokemon().setCurrentHp(0);
            System.out.println("\t\t" + trainer2.getName() + "'s "+ trainer2.getCurrentPokemon().getName() + " fainted!" );
            if(com){
                System.out.println("\t\tComeback " + trainer2.getCurrentPokemon().getName());
                if(!whipedOut(player)) {
                    swapPokemon();
                    System.out.println("\t\t" + trainer2.getName() + " chooses " + trainer2.getCurrentPokemon().getName() + "!");
                }
            }else {
                if(!whipedOut(enemy)) {
                    trainer2.getPokebag().swap();
                    System.out.println("\t\t" + trainer2.getName() + " chooses " + trainer2.getCurrentPokemon().getName() + "!");
                    System.out.println();
                }
            }
        }
    }

    public int calculateDamage(Pokemon p1, Pokemon p2) {
        int attack = p1.getAttack();
        int defense = p2.getDefense();
        String t1 = p1.getType1();
        String t2 = p1.getType2();
        int index1 = modifierNames.indexOf(t1);
        int index2 = modifierNames.indexOf(t2);
        double typeModifier1 = p2.getEffectiveAgainstType().get(index1);
        double typeModifier2 = 1;
        if (index2 != -1) {
            typeModifier2 = p2.getEffectiveAgainstType().get(index2);
        }
        if (typeModifier1 * typeModifier2 > 1) {
            System.out.println("\t\tIt's super effective!");
        } else if (typeModifier1 * typeModifier2 < 1) {
            System.out.println("\t\tIt's not very effective!");
        }
        double attackRoll = Math.random();
        double defenseRoll = (Math.random() * 0.5);
        int attackAfterRoll = (int) (attack * attackRoll * typeModifier1 * typeModifier2);
        int defenseAfterRoll = (int) (defense * defenseRoll);
        int damage = attackAfterRoll - defenseAfterRoll;
        if (damage < 0) {
            damage = 0;
        }
//        if(p1.getName().equals("Mewtwo")){
//            return 200;
//        }
        return damage;
    }


    private int chooseOption() {
        Engine.print("What will " + player.getCurrentPokemon().getName() + " do?", "(1) Fight ", "(2) Pokemon ", "", "");
        return UserInput.getInt(1, 2, "Choose option 1 or 2!");
    }

    private boolean whipedOut(Trainer trainer) {
        for (int i = 0; i < 6; i++) {
            if (trainer.getPokebag().get(i) != null) {
                if(trainer.getPokebag().get(i).getCurrentHp() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printAbilities() {
        Pokemon p = player.getCurrentPokemon();
        Pokemon p2 = enemy.getCurrentPokemon();
        String textplayer = p.getName() + " (" + p.getCurrentHp() + "/" + p.getHp() + ")";
        String textenemy = p2.getName() + " (" + p2.getCurrentHp() + "/" + p2.getHp() + ")";
        String text1 = String.format("%-34s%-34s", textplayer, textenemy);
        ArrayList<String> strings = new ArrayList<>();
        int length = player.getCurrentPokemon().getAbilities().size() >= enemy.getCurrentPokemon().getAbilities().size() ? player.getCurrentPokemon().getAbilities().size() : enemy.getCurrentPokemon().getAbilities().size();
        for (int i = 0; i < length; i++) {
            String text = "";
            if (i < player.getCurrentPokemon().getAbilities().size()) {
                text = (String.format("%-30s", player.getCurrentPokemon().getAbilities().get(i)));
            } else {
                text = (String.format("%-30s", ""));
            }
            if (i < enemy.getCurrentPokemon().getAbilities().size()) {
                text += (String.format("%-34s", enemy.getCurrentPokemon().getAbilities().get(i)));
            } else {
                text += (String.format("%-30s", ""));
            }
            strings.add(text);
        }
        if(strings.size() >= 3) {
            Engine.print("Choose an ability:", text1, "(1) " + strings.get(0), "(2) " + strings.get(1), "(3) " + strings.get(2));
        }else if(strings.size() == 2){
            Engine.print("Choose an ability:", text1, "(1) " + strings.get(0), "(2) " + strings.get(1), String.format("%68s", ""));
        }else{
            Engine.print("Choose an ability:", text1, "(1) " + strings.get(0), String.format("%68s", ""), String.format("%68s", ""));
        }
    }

    private void printCurrentPokemon() {
        pokedex.print_pokemon(player.getCurrentPokemon().getId());
        System.out.printf("\n\n\t\t\t\t%-10s vs %10s\n\n", player.getCurrentPokemon().getName(), enemy.getCurrentPokemon().getName());
        pokedex.print_pokemon(enemy.getCurrentPokemon().getId());
        System.out.println("\n\n");
    }
}
