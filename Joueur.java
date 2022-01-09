public class Joueur {

    private String nom; // nom du joueur
    private MEE chevalet; // représente les lettres que possède le joueur
    private int score; // score du joueur

    /**
     * action : instancie un Joueur à partir du nom placé en paramètre
     * le chevalet est initialisé comme un MEE pouvant contenir les
     * chiffres de zéro à 25 (pour les 26 lettres)
     * le score est initialisé à zéro
     * 
     * @param unNom
     */
    public Joueur(String unNom) {

        this.nom = unNom;
        this.chevalet = new MEE(26);
        this.score = 0;

    }

    /**
     * metode basique d'affichage rendant compte du nom et du score du Joueur
     */
    public String toString() {

        return "\nNom : " + this.nom + "\nScore : " + this.score;

    }

    /**
     * action : accède au score du joueur (nécessaire car la variable est 'private')
     * 
     * @return le score du joueur
     */
    public int getScore() {

        return this.score;

    }

    public String getNom() {
        return this.nom;
    }

    public MEE getChevalet() {
        return this.chevalet;
    }

    /**
     * action : augmente le score du Joueur du nombre de points passé en paramètre
     * 
     * @param nombre
     */
    public void ajouteScore(int nombre) {

        this.score += nombre;

    }

    /*
     * pré-requis : nbPointsJet indique le nombre de points rapportés par
     * chaque jeton/lettre
     * action : renvoie le nombre de point du chevalet du Joueur en fonction
     * de nbPointsJet où la valeur de point d'un entier i est donnée par
     * la valeur de nbPointsJet[i]
     * 
     * @param nbPointsJet
     * 
     * @return le nombre de points contenus dans le chevalet du Joueur
     */
    public int nbPointsChevalet(int[] nbPointsJet) {

        return this.chevalet.sommeValeurs(nbPointsJet);

    }

    /**
     * pré-requis : les éléments de s sont inférieur à 26
     * action : simule la prise de nbJetons jetons dans le sac s, dans la limite de
     * son contenu
     * 
     * @param s
     * @param nbJetons
     */
    public void prendJetons(MEE s, int nbJetons) {

        s.transfereAleat(this.chevalet, nbJetons);

    }

    public int joue(Plateau p, MEE s, int[] nbPointsJet) {

        int resultat;

        if (this.chevalet.estVide()) {

            resultat = 1;
            System.out.println(this.nom + " votre chevalet est vide, vous ne pouvez pas jouer");

        } else {

            System.out.println("Que voulez vous faire ?");
            System.out.println("a) Passer votre tour\nb)Echanger des jetons\nc)Placer un mot");
            System.out.println("\nTapez a, b ou c en fonction de votre choix");

            char reponse = Ut.saisirCaractere();

            switch (reponse) {

                case 'a':
                    resultat = -1;
                    break;

                case 'b':
                    resultat = 0;
                    this.echangeJetons(s);
                    break;

                case 'c':
                    resultat = 0;
                    this.joueMot(p, s, nbPointsJet);
                    break;

                default:
                    System.out.println("Erreur, ce char n'est pas reconnu");
                    resultat = this.joue(p, s, nbPointsJet);
                    break;

            }
        }

        return resultat;
    }

    public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet) {

        System.out.print("Quel mot souhaitez vous placer ? ");
        String mot = Ut.saisirChaine().toUpperCase();

        System.out.println("Saisissez la position de la première lettre sur le plateau.");
        System.out.print("Numéro de ligne : ");
        int numLig = Ut.saisirEntier();
        System.out.print("Numéro de colonne : ");
        int numCol = Ut.saisirEntier();

        System.out.println("Dans quelle sens voulez vous placer le mot ?");
        System.out.println("Tapez v pour vertical ou h pour h");
        char sens = Ut.saisirCaractere();

        if (p.placementValide(mot, numLig, numCol, sens, this.chevalet) && Plateau.verifCapeloDico(mot)) {

            this.joueMotAux(p, s, nbPointsJet, mot, numLig, numCol, sens);
            return true;

        } else {

            System.out.println("Placment non valide");
            return false;

        }

    }

    public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot, int numLig, int numCol, char sens) {

        p.place(mot, numLig, numCol, sens, this.chevalet);
        int points  = p.nbPointsPlacement(mot, numLig, numCol, sens, Scrabble.nbPointsJeton);
        this.ajouteScore(points);
        System.out.println(this.nom + points + " points !");

    }

    public void echangeJetons(MEE sac) {

        System.out.println("Quelles lettres voulez vous échanger ?");
        String mot = Ut.saisirChaine().toUpperCase();

        while (!this.estCorrectPourEchange(mot) || !(sac.getNbTotEx() >= mot.length())) {

            if (!this.estCorrectPourEchange(mot)) {

                System.out.println("Vous ne possédez pas une ou plusieurs de ces lettres, veuillez recommencer");

                System.out.println("Quelles lettres voulez vous échanger ?");
                mot = Ut.saisirChaine().toUpperCase();

            } else {

                System.out.println("Le sac n'a pas assez de lettres, il n'en contient que " + sac.getNbTotEx() + ", veuillez recommencer");

                System.out.println("Quelles lettres voulez vous échanger ?");
                mot = Ut.saisirChaine().toUpperCase();
                
            }

        }

        this.echangeJetonsAux(sac, mot);

    }

    public boolean estCorrectPourEchange(String mot) {

        return this.chevalet.contientMot(mot);

    }

    public void echangeJetonsAux(MEE sac, String ensJetons) {

        MEE temporaire = new MEE(25);
        int longueurMot = ensJetons.length();

        for (int i = 0; i < longueurMot; i++)
            this.chevalet.transfere(sac, MEE.valeurLettre(ensJetons.charAt(i)));

        sac.transfereAleat(this.chevalet, longueurMot);
        temporaire.transfereAleat(sac, longueurMot);
        
    }

    public static void main(String[] args) {
        Joueur j1 = new Joueur("anais");
        Plateau p = new Plateau();
        int[] nbPJetons = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
                25 };
        MEE s = new MEE(25);
        /*
         * j1.chevalet.ajoute(4);
         * j1.joue(p,s, nbPJetons);
         */

        j1.joueMot(p, s, nbPJetons);
    }

}
