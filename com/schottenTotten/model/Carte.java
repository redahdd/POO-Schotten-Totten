package com.schottenTotten.model;

public class Carte implements Comparable<Carte> {
    private Couleur couleur;
    private int valeur;

    public enum Couleur {
        ROUGE,
        BLEU,
        VERT,
        JAUNE,
        ORANGE,
        VIOLET
    }
    
    public Carte(Couleur couleur, int valeur) {
        this.couleur = couleur;
        this.valeur = valeur;
    }
    
    public Couleur getCouleur() {
        return couleur;
    }
    
    public int getValeur() {
        return valeur;
    }
    
    @Override
    public int compareTo(Carte autre) {
        return this.valeur - autre.valeur;
    }
}