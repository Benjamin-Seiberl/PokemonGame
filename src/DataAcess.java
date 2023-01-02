import java.sql.SQLException;
import java.util.ArrayList;

public interface DataAcess {

    Pokedex pokedex = null;

    public Pokedex fillPokedex() throws SQLException;

    public Trainer getTrainer(int id);

    public ArrayList<Integer> lookUpGameState();

    public void saveData(int enemy, ArrayList<Integer> enemies);

    public void deleteData(Trainer player);

    public void updateTrainer(Trainer player);
}