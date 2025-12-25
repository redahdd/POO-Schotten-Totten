package com.schottenTotten.controller;

import com.schottenTotten.model.*;

/**
 * Factory pour créer des instances de Partie selon la variante choisie
 * Implémente le Design Pattern Factory
 */
public class JeuFactory {
    
    public enum TypeVariante {
        BASE,
        TACTIQUE
    }
    
    /**
     * Crée une partie selon le type de variante spécifié
     * @param typeVariante Le type de variante (BASE ou TACTIQUE)
     * @param joueur1 Le premier joueur
     * @param joueur2 Le second joueur
     * @return Une instance de Partie configurée selon la variante
     */
    public static Partie creerPartie(TypeVariante typeVariante, Joueur joueur1, Joueur joueur2) {
        Variante variante = creerVariante(typeVariante);
        return new PartieAvecVariante(variante, joueur1, joueur2);
    }
    
    /**
     * Crée une partie avec une variante spécifique
     * @param variante L'instance de variante à utiliser
     * @param joueur1 Le premier joueur
     * @param joueur2 Le second joueur
     * @return Une instance de Partie configurée selon la variante
     */
    public static Partie creerPartie(Variante variante, Joueur joueur1, Joueur joueur2) {
        return new PartieAvecVariante(variante, joueur1, joueur2);
    }
    
    /**
     * Factory pour créer les variantes
     * @param typeVariante Le type de variante
     * @return L'instance de la variante correspondante
     */
    private static Variante creerVariante(TypeVariante typeVariante) {
        switch (typeVariante) {
            case BASE:
                return new VarianteBase();
            case TACTIQUE:
                return new VarianteTactique();
            default:
                throw new IllegalArgumentException("Type de variante non supporté: " + typeVariante);
        }
    }
    
    /**
     * Méthode utilitaire pour créer une partie de base
     * @param joueur1 Premier joueur
     * @param joueur2 Second joueur
     * @return Partie en variante de base
     */
    public static Partie creerPartieBase(Joueur joueur1, Joueur joueur2) {
        return creerPartie(TypeVariante.BASE, joueur1, joueur2);
    }
    
    /**
     * Méthode utilitaire pour créer une partie tactique
     * @param joueur1 Premier joueur
     * @param joueur2 Second joueur
     * @return Partie en variante tactique
     */
    public static Partie creerPartieTactique(Joueur joueur1, Joueur joueur2) {
        return creerPartie(TypeVariante.TACTIQUE, joueur1, joueur2);
    }
    
    /**
     * Obtient la liste des variantes disponibles
     * @return Array des types de variantes disponibles
     */
    public static TypeVariante[] getVariantesDisponibles() {
        return TypeVariante.values();
    }
}