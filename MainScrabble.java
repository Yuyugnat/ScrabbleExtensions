public class MainScrabble  {

	public static void main(String[] args){

		int nbJoueur=0;
		boolean commencer= false;

		while(!commencer){

			Ut.afficher("Saisissez le nombre de joueur.");
			nbJoueur =Ut.saisirEntier();

			if(1<=nbJoueur && nbJoueur<15 ){
				commencer=true;
			}

		}
		
		String [] nomJoueur= new String [nbJoueur];

		for(int i=0;i<nbJoueur;i++){
			nomJoueur[i]=Ut.saisirChaine();
		}

		Scrabble scrabble= new Scrabble(nomJoueur);
		scrabble.partie();
		
	}
	
} //end class