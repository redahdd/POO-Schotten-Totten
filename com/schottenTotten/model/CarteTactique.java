package com.schottenTotten.model;

import com.schottenTotten.controller.Partie;

/**
 * Classe représentant les cartes tactiques du jeu Schotten-Totten
 * Ces cartes ont des effets spéciaux et ne sont utilisées que dans la variante Tactique
 */
public class CarteTactique extends Carte {
    
    private TypeCarteTactique type;
    private String description;
    
    /**
     * Énumération des différents types de cartes tactiques
     */
    public enum TypeCarteTactique {
        // Cartes de Combat (améliorent les formations)
        JOKER("Joker", "Peut représenter n'importe quelle carte de clan de votre choix"),
        ESPION("Espion", "Peut représenter n'importe quelle carte de clan de valeur 7"),
        PORTE_BOUCLIER("Porte-Bouclier", "Peut représenter n'importe quelle carte de clan de valeur 1, 2 ou 3"),
        
        // Cartes Ruses (effets spéciaux)
        COLIN_MAILLARD("Colin-Maillard", "Regardez la main de votre adversaire"),
        COMBAT_DE_BOUE("Combat de Boue", "La borne la plus forte l'emporte (pas de suite ni couleur)"),
        BANSHEE("Banshee", "Détruisez une carte clan déjà jouée par votre adversaire"),
        
        // Cartes Météo (changent les règles)
        BROUILLARD("Brouillard", "Une formation de 4 cartes est nécessaire pour cette borne"),
        TEMPETE("Tempête", "Cette borne ne peut être revendiquée qu'avec une formation de 4 cartes identiques");
        
        private final String nom;
        private final String description;
        
        TypeCarteTactique(String nom, String description) {
            this.nom = nom;
            this.description = description;
        }
        
        public String getNom() { return nom; }
        public String getDescription() { return description; }
    }
    
    /**
     * Constructeur pour les cartes tactiques
     * @param type Le type de carte tactique
     */
    public CarteTactique(TypeCarteTactique type) {
        // Les cartes tactiques n'ont pas de couleur ni valeur numériques fixes
        super(null, 0);
        this.type = type;
        this.description = type.getDescription();
    }
    
    /**
     * Getter pour le type de carte tactique
     * @return Le type de la carte tactique
     */
    public TypeCarteTactique getTypeTactique() {
        return type;
    }
    
    /**
     * Getter pour la description de l'effet
     * @return La description de l'effet de la carte
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Vérifie si cette carte tactique est une carte de combat (remplace une carte clan)
     * @return true si c'est une carte de combat
     */
    public boolean estCarteCombat() {
        return type == TypeCarteTactique.JOKER || 
               type == TypeCarteTactique.ESPION || 
               type == TypeCarteTactique.PORTE_BOUCLIER;
    }
    
    /**
     * Vérifie si cette carte tactique est une ruse (effet immédiat)
     * @return true si c'est une ruse
     */
    public boolean estRuse() {
        return type == TypeCarteTactique.COLIN_MAILLARD || 
               type == TypeCarteTactique.COMBAT_DE_BOUE || 
               type == TypeCarteTactique.BANSHEE;
    }
    
    /**
     * Vérifie si cette carte tactique affecte la météo (change les règles de la borne)
     * @return true si c'est une carte météo
     */
    public boolean estCarteMéteo() {
        return type == TypeCarteTactique.BROUILLARD || 
               type == TypeCarteTactique.TEMPETE;
    }
    
    /**
     * Obtient la valeur effective de la carte si elle agit comme une carte clan
     * @param valeurChoisie La valeur choisie par le joueur (pour Joker)
     * @return La valeur effective
     */
    public int getValeurEffective(int valeurChoisie) {
        switch (type) {
            case ESPION:
                return 7;
            case PORTE_BOUCLIER:
                // Par défaut, on prend la valeur minimale, mais le joueur peut choisir 1, 2 ou 3
                return Math.min(Math.max(valeurChoisie, 1), 3);
            case JOKER:
                // Le joueur peut choisir n'importe quelle valeur de 1 à 9
                return Math.min(Math.max(valeurChoisie, 1), 9);
            default:
                return 0; // Les autres cartes n'ont pas de valeur numérique
        }
    }
    
    /**
     * Obtient la couleur effective de la carte si elle agit comme une carte clan
     * @param couleurChoisie La couleur choisie par le joueur
     * @return La couleur effective
     */
    public Couleur getCouleurEffective(Couleur couleurChoisie) {
        if (estCarteCombat()) {
            return couleurChoisie; // Le joueur peut choisir la couleur
        }
        return null; // Les autres cartes n'ont pas de couleur
    }
    
    /**
     * Vérifie si cette carte tactique peut être jouée dans le contexte donné
     * @param borne La borne sur laquelle on veut jouer la carte
     * @param joueur Le joueur qui veut jouer la carte
     * @return true si la carte peut être jouée
     */
    public boolean peutEtreJouee(Borne borne, Joueur joueur) {
        // Règle générale : maximum 1 carte tactique par borne par joueur
        // Cette logique devrait être implémentée dans la classe Borne
        
        // Règles spécifiques selon le type
        switch (type) {
            case BANSHEE:
                // Peut être jouée seulement s'il y a des cartes adverses à détruire
                return (joueur.getId() == 1 && borne.getCartesJoueur2Size() > 0) ||
                       (joueur.getId() == 2 && borne.getCartesJoueur1Size() > 0);
            default:
                return true; // Les autres cartes peuvent généralement être jouées
        }
    }
    
    /**
     * Applique l'effet de la carte tactique
     * @param borne La borne concernée
     * @param joueur Le joueur qui joue la carte
     * @param partie La partie en cours (pour certains effets)
     */
    public void appliquerEffet(Borne borne, Joueur joueur, Partie partie) {
        switch (type) {
            case COLIN_MAILLARD:
                // L'effet sera géré par l'interface utilisateur
                System.out.println(joueur.getNom() + " peut voir la main de son adversaire !");
                break;
                
            case BANSHEE:
                // Détruire une carte adverse (logique à implémenter dans Borne)
                System.out.println(joueur.getNom() + " peut détruire une carte adverse !");
                break;
                
            case COMBAT_DE_BOUE:
                // Modifier les règles d'évaluation de la borne
                System.out.println("Combat de boue activé sur la borne " + borne.getIndex() + " !");
                break;
                
            case BROUILLARD:
                System.out.println("Brouillard activé sur la borne " + borne.getIndex() + " !");
                break;
                
            case TEMPETE:
                System.out.println("Tempête activée sur la borne " + borne.getIndex() + " !");
                break;
                
            default:
                // Les cartes de combat n'ont pas d'effet immédiat
                break;
        }
    }
    
    /**
     * Override de toString pour l'affichage
     */
    @Override
    public String toString() {
        return type.getNom() + " (Tactique)";
    }
    
    /**
     * Override de compareTo pour les cartes tactiques
     */
    @Override
    public int compareTo(Carte autre) {
        if (autre instanceof CarteTactique) {
            return this.type.ordinal() - ((CarteTactique) autre).type.ordinal();
        }
        // Les cartes tactiques sont considérées comme ayant une valeur spéciale
        return -1; // Elles apparaissent en premier lors du tri
    }
    
    /**
     * Méthode statique pour créer toutes les cartes tactiques
     * @return Liste de toutes les cartes tactiques disponibles
     */
    public static java.util.List<CarteTactique> creerToutesLesCartesTactiques() {
        java.util.List<CarteTactique> cartes = new java.util.ArrayList<>();
        for (TypeCarteTactique type : TypeCarteTactique.values()) {
            cartes.add(new CarteTactique(type));
        }
        return cartes;
    }
}