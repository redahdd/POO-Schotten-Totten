import java.util.ArrayList;
public class Partie{

    private String edition;
    private String variante;
    private int nombreJoueurs;

    protected Pioche pioche;
    private ArrayList<Borne> bornes;

//constructeurs:
    public Partie(String edition, String variante, int nombreJoueurs){
        this.edition = edition;
        this.variante = variante;
        this.nombreJoueurs = nombreJoueurs;
        initialiserBornes();
    }


//methodes:
    public String getEdition() { return edition; }
    public String getVariante() { return variante; } 

    public void initialiserBornes(){
        bornes = new ArrayList<>();
        for ( int i=1; i<10; i++){
            bornes.add(new Borne("Borne n°"+i));
            }
        }

    public String jouerTour(joueur j){

    }



}