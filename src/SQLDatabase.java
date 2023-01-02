import java.sql.*;
import java.util.ArrayList;

public class SQLDatabase implements DataAcess {

    private Pokedex pokedex;

    @Override
    public Pokedex fillPokedex() throws SQLException {
        pokedex = new Pokedex();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/pokemon", "pokemon", "pokemon");
            PreparedStatement ps = connection.prepareStatement("select * from pokedex limit 151");
            ResultSet resultSet = ps.executeQuery();

            ArrayList<String> modifierNames = new ArrayList<>();
            ResultSetMetaData meta = resultSet.getMetaData();
            for (int i = 2; i < 20; i++) {
                String[] names = meta.getColumnName(i).split("_");
                modifierNames.add(names[1]);
            }
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("pokedex_number"));
                String name = resultSet.getString("name");
                int hp = resultSet.getInt("hp");
                int attack = resultSet.getInt("attack");
                int defense = resultSet.getInt("defense");
                int speed = resultSet.getInt("speed");
                String type1 = resultSet.getString("type1");
                String type2 = resultSet.getString("type2");
                String[] abilitiesToFilter = resultSet.getString("abilities").split("'");
                ArrayList<String> abilities = new ArrayList<>();
                for (int i = 0; i < abilitiesToFilter.length; i++) {
                    if (i % 2 != 0) {
                        abilities.add(abilitiesToFilter[i]);
                    }
                }
                ArrayList<Double> typeModifiers = new ArrayList<>();
                for (int i = 2; i < 20; i++) {
                    typeModifiers.add(resultSet.getDouble(i));
                }
                Pokemon p = new Pokemon(id, name, hp, hp, attack, defense, speed, type1, type2, abilities, typeModifiers, -1);
                pokedex.add(p);
                pokedex.setModifierNames(modifierNames);
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return pokedex;
    }

    @Override
    public Trainer getTrainer(int id) {
        PokeBag pokebag = new PokeBag();
        Trainer trainer;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/pokemon", "pokemon", "pokemon");
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `pokebagpokedex` WHERE pokebag_id = ? order by id asc");
            ps.setString(1, "" + id);
            ResultSet resultSet = ps.executeQuery();
            int pokebagIndex = 1;
            while (resultSet.next()) {
                Pokemon temp = pokedex.get(Integer.parseInt(resultSet.getString("pokedex_id")) - 1);
                temp.setPokebagIndex(pokebagIndex);
                String hp = resultSet.getString("hp");
                if (resultSet.getString("hp") != null) {
                    temp.setCurrentHp(resultSet.getInt("hp"));
                }
                Pokemon p = new Pokemon(temp);
                pokebag.add(p);
                pokebagIndex++;
            }
            if (pokebag.get(0).getCurrentHp() == 0) {
                pokebag.swap();
            }
            ps = connection.prepareStatement("select * from trainer where id =?");
            ps.setString(1, "" + id);
            resultSet = ps.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            trainer = new Trainer(name, pokebag);
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trainer;
    }

    @Override
    public ArrayList<Integer> lookUpGameState() {
        ArrayList<Integer> trainerList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/pokemon", "pokemon", "pokemon");
            PreparedStatement ps = connection.prepareStatement("Select count(id) as count from trainer");
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int numberOfTrainers = resultSet.getInt("count");
            for (int i = 2; i <= numberOfTrainers; i++) {
                trainerList.add(i);
            }
            ps = connection.prepareStatement("SELECT * FROM `trainerwonagainst` WHERE id =? order by trainer_id asc");
            ps.setString(1, "1");
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int trainerId = resultSet.getInt("trainer_id");
                if (trainerList.contains(trainerId)) {
                    System.out.println();
                    trainerList.remove(0);
                }
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trainerList;
    }

    @Override
    public void saveData(int enemy, ArrayList<Integer> enemies) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/pokemon", "pokemon", "pokemon");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `trainerwonagainst`(`id`, `trainer_id`) VALUES ('1',?)");
            for (int i = 0; enemies.get(0) != enemy; i++) {
                ps.setString(1, "" + enemies.get(0));
                ps.executeUpdate();
                enemies.remove(0);
            }
            ps.setString(1, "" + enemies.get(0));
            ps.executeUpdate();
            connection.close();
            System.out.println("\t\tYour data has been saved. Game will now shutdown!");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteData(Trainer player) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/pokemon", "pokemon", "pokemon");
            PreparedStatement ps = connection.prepareStatement("Delete from trainerwonagainst where id = 1");
            ps.executeUpdate();
            ps = connection.prepareStatement("Update pokebagpokedex set hp = null");
            ps.executeUpdate();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTrainer(Trainer player) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/pokemon", "pokemon", "pokemon");
            PreparedStatement ps = connection.prepareStatement("Update pokebagpokedex set hp = ? where id = ? and pokebag_id = 1");
            for (int i = 0; i < 6; i++) {
                if (player.getPokebag().get(i) != null) {
                    int hp = player.getPokebag().get(i).getCurrentHp();
                    ps.setString(1, "" + hp);
                    ps.setString(2, "" + player.getPokebag().get(i).getPokebagIndex());
                    ps.executeUpdate();
                }
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
