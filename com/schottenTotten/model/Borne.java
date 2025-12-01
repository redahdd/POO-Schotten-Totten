package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;

public class Borne {

    private List<Carte> cartesJoueur1;
    private List<Carte> cartesJoueur2;
    private EtatBorne etat;
    private int index;

    public Borne(int index) {
        this.cartesJoueur1 = new ArrayList<>();
        this.cartesJoueur2 = new ArrayList<>();
        this.etat = EtatBorne.LIBRE;
        this.index = index;
    }

    public boolean ajouterCarte(Joueur j, Carte c) {
        if (j.getId() == 1 && cartesJoueur1.size() < 3) {
            cartesJoueur1.add(c);
            return true;
        }
        if (j.getId() == 2 && cartesJoueur2.size() < 3) {
            cartesJoueur2.add(c);
            return true;
        }
        return false;
    }

    public enum EtatBorne {
        LIBRE,
        GAGNÉE_J1,
        GAGNÉE_J2
    }

    public void setEtat(EtatBorne e) {
        this.etat = e;
    }

    public boolean estComplet(){
        return cartesJoueur1.size() >= 3 && cartesJoueur2.size() >= 3;
    }

    public EtatBorne getEtat() {
        return etat;
    }
    public int getCartesJoueur1Size(Liste<Carte> cartesJoueur1) {
        return cartesJoueur1.size();
    }
    public int getCartesJoueur2Size(Liste<Carte> cartesJoueur2) {
        return cartesJoueur2.size();
    }
}