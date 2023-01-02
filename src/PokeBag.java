public class PokeBag {
    private Pokemon[] pokemon;

    public PokeBag() {
        this.pokemon = new Pokemon[6];
    }

    public PokeBag(Pokemon[] pokemon) {
        this.pokemon = pokemon;
    }

    public void add(Pokemon p){
        for (int i = 0; i < 6; i++) {
            if(pokemon[i] == null){
                pokemon[i] = p;
                break;
            }
            if(i == 6){
                System.out.println("Pokebag is full! Couldn't add Pokemon.");
            }
        }
    }

    public Pokemon get(int number){
        if(number >= 0 && number <= 5){
            if(pokemon[number] != null){
                return pokemon[number];
            }
        }
        return null;
    }

    public void swap(int number){
        if(number >= 0 && number <= 5){
            if(pokemon[number] != null){
                Pokemon tmp = new Pokemon(pokemon[0]);
                pokemon[0] = pokemon[number];
                pokemon[number] = tmp;
            }
        }
    }
    public void swap(){
        boolean swapped = false;
        for (int i = 4; (i > 0 && !swapped); i--) {
            if(pokemon[i].getCurrentHp() > 0){
                swap(i);
                swapped = true;
            }
        }
    }
}
