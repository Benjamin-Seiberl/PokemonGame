public class Trainer {
    String name;
    private PokeBag pokebag;

    public Trainer(String name, PokeBag pokemon) {
        this.name = name;
        this.pokebag = pokemon;
    }

    public Trainer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PokeBag getPokebag() {
        return pokebag;
    }

    public void setPokebag(PokeBag pokemon) {
        this.pokebag = pokemon;
    }

    public int getCurrentPokemonHP(){
        return pokebag.get(0).getCurrentHp();
    }

    public Pokemon getCurrentPokemon(){
        return pokebag.get(0);
    }
}
