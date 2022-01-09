public class Case {

    private int couleur;
    private char lettre; // O sera la valeur par défaut lorsque la case n'est pas recouverte




    public Case(int couleur){
        this.couleur=couleur;
        this.lettre='0'; 
    }

    public int getCouleur(){
        return this.couleur;
    }

    public char getLettre(){
        return this.lettre;
    }

    public void setLettre(char let){
        this.lettre=let;
    }

    public boolean estRecouverte(){
        boolean res= false;
        if(this.lettre!='0'){
            res=true;
        }
        return res;
    }
    public String toString(){
        return this.couleur+ " " + this.lettre;
    } 


}