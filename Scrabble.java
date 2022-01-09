public class Scrabble {

    private Joueur[] joueurs;
    private int numJoueur;
    private Plateau plateau;
    private MEE sac;
    public static Dico scrabbleDico = new Dico("dicoref.txt");

    public static int[] nbPointsJeton = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10,
            10 };

    public Scrabble(String[] joueurs) {

        this.joueurs = new Joueur[joueurs.length];

        for (int i = 0; i < joueurs.length; i++)
            this.joueurs[i] = new Joueur(joueurs[i]);

        this.numJoueur = Ut.randomMinMax(0, joueurs.length - 1);
        this.plateau = new Plateau();
        int[] distributionLettres = { 9, 2, 2, 3, 15, 2, 2, 2, 8, 1, 1, 5, 3, 6, 6, 2, 1, 6, 6, 6, 6, 2, 1, 1, 1, 1 };
        this.sac = new MEE(distributionLettres);

    }

    
    public String toString() {

        String res = "";
        res += this.numJoueur + "\n";
        res += this.plateau + "\n";
        res += "Joueur " + (this.numJoueur + 1) + " : " + this.joueurs[this.numJoueur].getNom() + ", c'est à vous de jouer\n" + "\n";
        res += this.joueurs[this.numJoueur].getChevalet().toStringBis() + "\n";

        return res;
    }

    public void partie() {

        Joueur joueurCourant = this.joueurs[this.numJoueur];

        int nbJPassantLeurTour = 0;

        int[] tableauDesScores = new int[this.joueurs.length];

        for (int i = 0; i < this.joueurs.length; i++)
            tableauDesScores[i] = 0;

        for (int i = 0; i < this.joueurs.length; i++)
            this.joueurs[i].prendJetons(this.sac, 7);

        while ((this.sac.getNbTotEx() > 0 || joueurCourant.getChevalet().getNbTotEx() > 0) && nbJPassantLeurTour != this.joueurs.length) {

            System.out.println(this);

            joueurCourant = this.joueurs[this.numJoueur];

            int action = joueurCourant.joue(this.plateau, this.sac, Scrabble.nbPointsJeton);

            nbJPassantLeurTour = action == -1
                    ? nbJPassantLeurTour + 1
                    : 0;

            joueurCourant.prendJetons(this.sac, 7 - joueurCourant.getChevalet().getNbTotEx());

            this.numJoueur = this.joueurSuivant();

        }

        String gagnants = "\n\nLe ou les gagnants sont ";
        int scoreGagnants = this.scoreMax();

        for (int i = 0; i < this.joueurs.length; i++)
            if (this.joueurs[i].getScore() == scoreGagnants)
                gagnants += this.joueurs[i].getNom() + " ";

        System.out.println(gagnants + "avec un score de " + scoreGagnants +" !");
    }

    private int joueurSuivant() {

        return this.numJoueur == this.joueurs.length - 1 ? this.numJoueur = 0 : this.numJoueur + 1;

    }

    private int scoreMax() {

        int max = this.joueurs[0].getScore();

        for (int i = 1; i < this.joueurs.length; i++)
            if (max < this.joueurs[i].getScore())
                max = this.joueurs[i].getScore();

        return max;
    } 
}
