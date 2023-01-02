import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CSV implements DataAcess {

    private Pokedex pokedex;
    private HashMap<Integer, String> trainers;
    @Override
    public Pokedex fillPokedex() {
        int number = 0;
        pokedex = new Pokedex();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/Pokedex.csv"));
            String line = bufferedReader.readLine();
            //Adds Modifier names
            String[] againstNames = line.split("_");
            ArrayList modifierNames = new ArrayList<>();
            for (int i = 1; i < againstNames.length; i++) {
                String[] names = againstNames[i].split(",");
                modifierNames.add(names[0]);
            }
            pokedex.setModifierNames(modifierNames);
            do {
                line = bufferedReader.readLine();
                String[] abilitiesAndStats = line.split("]");

                String[] abilitiesToFilter = abilitiesAndStats[0].split("'");

                String[] stats = abilitiesAndStats[1].split(",");

                //Adds all Abilities
                ArrayList<String> abilities = new ArrayList<>();
                for (int i = 0; i < abilitiesToFilter.length; i++) {
                    if (i % 2 != 0) {
                        abilities.add(abilitiesToFilter[i]);
                    }
                }

                //Adds all type modifiers
                ArrayList<Double> typeModifiers = new ArrayList<>();
                for (int i = 1; i < 19; i++) {
                    typeModifiers.add(Double.parseDouble(stats[i]));
                }

                Pokemon p = new Pokemon(Integer.parseInt(stats[32]), stats[30], Integer.parseInt(stats[28]), Integer.parseInt(stats[28]), Integer.parseInt(stats[19]), Integer.parseInt(stats[25]), Integer.parseInt(stats[35]), stats[36], stats[37], abilities, typeModifiers, -1);
                pokedex.add(p);
                number++;
            } while (number != 151);
        } catch (IOException e) {
            System.out.println("Fehler beim einlesen");
            e.printStackTrace();
        }
        return pokedex;
    }

    @Override
    public Trainer getTrainer(int id) {
        PokeBag pokebag = new PokeBag();
        Trainer trainer = new Trainer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("./src/pokebagpokedex.csv"));
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while(line != null){
                String[] splitLine = line.replaceAll("\"", "").split(",");
                if(Integer.parseInt(splitLine[1]) == id) {
                    Pokemon pokemon = new Pokemon(pokedex.get(Integer.parseInt(splitLine[2])-1));
                    pokebag.add(pokemon);
                }
                line = bufferedReader.readLine();
            }
            trainer.setName(trainers.get(id).toString());
            trainer.setPokebag(pokebag);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return trainer;
    }

    @Override
    public ArrayList<Integer> lookUpGameState() {
        trainers = new HashMap<>();
        ArrayList<Integer> trainerList = new ArrayList<>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader("./src/trainer.csv"));
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while(line != null){
                String[] splitLine = line.replaceAll("\"", "").split(",");
                if(Integer.parseInt(splitLine[0]) != 1) {
                    trainerList.add(Integer.parseInt(splitLine[0]));
                }
                trainers.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return trainerList;
    }

    @Override
    public void saveData(int enemy, ArrayList<Integer> enemies) {

    }

    @Override
    public void deleteData(Trainer player) {

    }

    @Override
    public void updateTrainer(Trainer player) {

    }
}
