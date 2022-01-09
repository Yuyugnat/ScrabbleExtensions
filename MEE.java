/**
 * classe Multi-Ensemble d'Entiers qui servira à manipuler des chevalets de letttres,
 * des sacs de lettres, etc.
 * Pour les fonctions les plus complèxes, la stratégie sera explicitée
 */
public class MEE {

    /**
     * descriptioon : tableau répertoriant le nombre d'exemplaires de chaque entier (correspondant
     * aux indices) dans notre MEE
     */
    private int[] tabFreq;

    /**
     * description : entier répresentant le nombre total d'éléments dans notre multi-ensemble
     * d'entiers
     */
    private int nbTotEx;

    /**
     * pré-requis : l'entier placé en paramètre est supérieur à zero
     * action : instancie un MEE dont tous les éléments seront inférieurs à la valeur en paramètre
     * @param max
     * @return une instance de MEE vide qui peut contenir des entiers de 0 à max - 1
     */
    public MEE(int max) {

        this.tabFreq = new int[max];
        this.nbTotEx = 0;

    }

    /**
     * pré-requis : tous les éléments de tab sont positifs ou nuls
     * action : instancie un MEE à partir d'un tableau de fréquence (dont l'utilité est
     * décrite plus haut) déjà créé
     * @param tab
     * @return une instance de MEE dont le tableau de fréquence est le tableau en paramètre
     */
    public MEE(int[] tab) {

        this.tabFreq = new int[tab.length];

        for (int i = 0; i < tab.length - 1; i++) {

            this.tabFreq[i] = tab[i];

        }

        this.nbTotEx = 0;

        for (int j = 0; j < tab.length - 1; j++) {

            this.nbTotEx += tab[j];

        }
    }

    /**
     * action : instancie un MEE à partir d'un autre MEE dont les valeurs des variables
     * seront copiées dans le MEE instancié
     * @param e
     * @return une instance de la classe MEE
     */
    public MEE(MEE e) {

        this.nbTotEx = e.nbTotEx;

        this.tabFreq = new int[e.tabFreq.length];

        for (int i = 0; i < this.tabFreq.length; i++) {

            this.tabFreq[i] = e.tabFreq[i];

        }

    }

    /**
     * Accesseur du nombre total d'exemplaire
     * @return le nombre d'entiers contenus dans this
     */
    public int getNbTotEx() {

        return this.nbTotEx;

    }

    /**
     * fonction d'affichage qui permet de tester aisément les différentes
     * méthodes de cette classe (donc utile lors de sa création)
     */
    public String toString() {

        String result = "Nombre total d'exemplaires : " + this.nbTotEx + "\n\n";

        for (int i = 0; i < this.tabFreq.length; i++) {

            result += "Exemplaires de " + i + " : " + this.tabFreq[i] + "\n";

        }

        return result;

    }

    public String toStringBis() {

        String res = "Chevalet : ";

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < this.tabFreq[i]; j++) {
                res += MEE.lettreDIndice(i) + " ";
            }
        }

        return res;
    }

    /**
     * action : cette méthode teste si l'instance sur laquelle on l'appelle est vide
     * @return un boolean qui vaut true si et seulement si l'instance de MEE est vide
     */
    public boolean estVide() {

        return this.nbTotEx == 0;

    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * cette méthode ajoute un exemplaire de l'entier passé en paramètre
     * à l'instance sur laquelle on l'appelle
     * @param i
     * @return void
     */
    public void ajoute(int i) {

        this.tabFreq[i]++;
        this.nbTotEx++;

    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * retire un exemplaire de l'entier passé en paramètre de l'instance sur laquelle
     * on appelle cette méthode
     * @param i
     * @return un boolean qui vaut true si et seulement si l'action demandée a été réalisée
     */
    public boolean retire(int i) {

        boolean result = this.tabFreq[i] > 0;

        if (result) {

            this.tabFreq[i]--;
            this.nbTotEx--;

        }

        return result;

    }

    /**
     * pré-requis : this est non vide
     * action : retire un élément aléatoire de this et le retourne
     * stratégie : on commence par compter le nombre d'entiers différents présents dans this
     * on créé un tableau pour pouvoir les contenir, puis on les ajoute. On a donc un tableau
     * dont les valeurs correspondent aux entiers présents dans this. On utilise Ut.randomMinMax() 
     * pour et selectionner un, le retirer de this et le retourner
     * @return un entier aléatoire contenu dans this
     */
    public int retireAleat() {

        int nombreExemplairesDifferents = 0;

        for (int i = 0; i < this.tabFreq.length; i++) {

            if (tabFreq[i] > 0) {

                nombreExemplairesDifferents++;

            }

        }

        int[] tableauExemplairesPresents = new int[nombreExemplairesDifferents];

        int indiceTabFreq = 0;
        int indiceTabExempPres = 0;

        while (indiceTabExempPres < tableauExemplairesPresents.length && indiceTabFreq < this.tabFreq.length) {

            if (this.tabFreq[indiceTabFreq] > 0) {

                tableauExemplairesPresents[indiceTabExempPres] = indiceTabFreq;
                indiceTabExempPres++;
                indiceTabFreq++;

            } else {

                indiceTabFreq++;

            }

        }

        int indiceAleat = Ut.randomMinMax(0, tableauExemplairesPresents.length - 1);

        int result = tableauExemplairesPresents[indiceAleat];

        this.tabFreq[result]--;
        this.nbTotEx--;

        return result;

    }

    /**
     * pré-requis : 0 <= i < this.tabFreq.length et i < e.tabFrequ.length*
     * action : transfère un exemplaire de i de this vers e s'il en existe
     * @param e
     * @param i
     * @return true si et seulement si l'action a pu être réalisée
     */
    public boolean transfere(MEE e, int i) {

        boolean result = this.tabFreq[i] > 0;

        if (result) {

            this.tabFreq[i]--;
            this.nbTotEx--;

            e.tabFreq[i]++;
            e.nbTotEx++;

        }

        return result;

    }

    /**
     * pré-requis : k >= 0 et e.tabFreq >= this.tabFreq
     * action : tranfère k exemplaires choisis aléatoirement de this vers e
     * dans la limite du contenu de this
     * stratégie : je me suis servi d'une fonction auxiliaire transfereAleatAux() dont
     * le fonctionnement est défini juste après
     * @param e
     * @param k
     * @return le nombre d'exemplaire qui ont pu être transférés
     */
    public int transfereAleat(MEE e, int k) {

        return this.transfereAleatAux(e, k, 0);

    }

    /**
     * pré-requis : identiques à transfereAleat()
     * action : transfere k exemplaires choisis aléatoirement de this vers e
     * dans la limite du contenu de this
     * stratégie : j'ai choisi une fonction récursive du  fait de mon aisance avec
     * ce type de fonctions. C'est pour cela qu'elle prend en paramètre result qui sera retourner
     * dans le cas de base
     * cas de base -> this est vide et on ne peut plus retier d'élément, 
     * ou alors k vaut zéros et on a retiré tous les éléments. Dans ce cas la boucle 
     * récursive s'arrête et la fonction renvoie result
     * cas général -> on compte le nombre d'exemplaires différents présents dans this,
     * on créé un tableau pouvant les contenir et on les ajoute. On se sert ensuite de la 
     * fonction Ut.randomMinMax() pour on choisir un aléatoiirement et le transférer
     * on appelle alors la fonction à nouveau avec k = k - 1 et result = result + 1
     * @param e le MEE vers lequelle on transfère
     * @param k le nombre d'élément restant à transférer
     * @param result
     * @return result lorsque l'on a transféré le maximum d'exemplaire (k ou la limute de this)
     */
    private int transfereAleatAux(MEE e, int k, int result) {

        if (this.nbTotEx == 0 || k == 0) {

            return result;

        } else {

            int nombreExemplairesDifferents = 0;

            for (int i = 0; i < this.tabFreq.length; i++) {

                if (this.tabFreq[i] > 0)
                    nombreExemplairesDifferents++;

            }

            int[] tableauExemplairesPresents = new int[nombreExemplairesDifferents];

            int indiceTabFreq = 0;
            int indiceTabExempPres = 0;

            while (indiceTabExempPres < tableauExemplairesPresents.length
                    && indiceTabFreq < this.tabFreq.length) {

                if (this.tabFreq[indiceTabFreq] > 0) {

                    tableauExemplairesPresents[indiceTabExempPres] = indiceTabFreq;
                    indiceTabExempPres++;
                    indiceTabFreq++;

                } else {

                    indiceTabFreq++;

                }

            }

            int indiceAleat = Ut.randomMinMax(0, tableauExemplairesPresents.length - 1);

            this.transfere(e, tableauExemplairesPresents[indiceAleat]);

            return this.transfereAleatAux(e, k - 1, result + 1);

        }

    }

    /**
     * pré-requis : this.tabFreq.length <= v.length
     * action : calcule et renvoie la somme des valeurs des éléments de 
     * this en fonction du tableau de valeur v où la valeur d'un entier i
     * est donnée par la valeur v[i]
     * @param v
     * @return la somme des valeurs des éléments de this
     */
    public int sommeValeurs(int[] v) {

        int result = 0;

        for (int i = 0; i < this.tabFreq.length; i++) result += this.tabFreq[i] * v[i];

        return result;

    }

    /**
     * cette fonction sert à diviser les taches de la fonction Plateau.placementValide()
     * pré-requis : 'lettre' correspond à une des 26 lettres de notre alphabet en majuscule
     * action : renvoie la valeur d'indice d'une lettre dans un MEE 
     * ('A' -> 0, 'B' -> 1, etc.)
     * @param lettre
     * @return un valeur numérique correspondant à lettre
     */
    public static int valeurLettre(char lettre) {

        return (int) lettre - 65;

    }

    public static char lettreDIndice(int indice) {

        return (char) (indice + 65);
    }

    /**
     * cette fonction sert également à diviser les taches de la fonction
     * Plateau.placementValide()
     * action : teste si le mot pllacé en paramètre est contenu dans this 
     * (qui représentera un chevalet)
     * @param mot
     * @return true si et seulement si les lettres représentées dans this peuvent
     * contenir mot
     */
    public boolean contientMot(String mot) {

        MEE temporaire = new MEE(this.tabFreq.length);

        boolean testLettre;
        int indiceLettre;

        boolean resultat = true;
        int i = 0;

        while (resultat && i < mot.length()) {

            indiceLettre = MEE.valeurLettre(mot.charAt(i));
            testLettre = this.transfere(temporaire, indiceLettre);

            if (!testLettre)
                resultat = false;

            i++;

        }

        temporaire.transfereAleat(this, temporaire.nbTotEx);

        return resultat;

    }

}
