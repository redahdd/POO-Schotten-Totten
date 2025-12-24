package com.schottenTotten.model;

/**
 * Interface pour définir les différentes variantes du jeu Schotten-Totten
 */
public interface Variante {
    int getNombreCartesMain();
    boolean utiliseCartesTactiques();
    String getNom();
}