package com.schottenTotten.model;

/**
 * Variante de base du jeu Schotten-Totten
 * - 6 cartes en main par joueur
 * - Pas de cartes tactiques
 */
public class VarianteBase implements Variante {
    
    @Override
    public int getNombreCartesMain() {
        return 6; // 6 cartes en main pour la variante de base
    }
    
    @Override
    public boolean utiliseCartesTactiques() {
        return false; // Pas de cartes tactiques en variante de base
    }
    
    @Override
    public String getNom() {
        return "Base";
    }
}