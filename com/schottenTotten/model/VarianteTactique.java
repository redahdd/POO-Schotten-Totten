package com.schottenTotten.model;

/**
 * Variante tactique du jeu Schotten-Totten
 * - 7 cartes en main par joueur (au lieu de 6)
 * - Utilise les cartes tactiques
 */
public class VarianteTactique implements Variante {
    
    @Override
    public int getNombreCartesMain() {
        return 7; // 7 cartes en main pour la variante tactique
    }
    
    @Override
    public boolean utiliseCartesTactiques() {
        return true; // Utilise les cartes tactiques
    }
    
    @Override
    public String getNom() {
        return "Tactique";
    }
}