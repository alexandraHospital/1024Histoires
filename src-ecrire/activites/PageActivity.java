package activites;

import java.util.ArrayList;
import java.util.List;

import adapters.ChoixAdapterEcrire;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.histoires.R;

import databasetest.Choix;
import databasetest.Livre;
import databasetest.Objet;
import databasetest.Page;
//import android.view.View.OnClickListener;

public class PageActivity extends Activity implements OnClickListener, OnItemClickListener{

	//Arguments pour les valeurs
	int numeroPageCible=2;
	long numeroPage;
	long idPage=2;
	long id;
	int nombreDePages=2;
	boolean existe=false;
	int CODE_RETOUR=0;
	long idObjetRequis=0;

	//Arguments pour les widgets
	EditText texte=null;
	EditText letexteChoix=null;
	EditText objetRequis=null;
	Button enregistrer=null;
	Button allerCreationChoix=null;
	String titreDuLivre="";
	boolean cochee=false;
	TextView objetPage=null;

	//Arguments pour les listes contenant l'ensemble des choix et de leurs pages cibles
	List<Choix> tousLesChoix = new ArrayList<Choix>();
	List<Page> listePagesCibles = new ArrayList<Page>();

	//Arguments pour afficher la liste des choix d�j� cr��s
	List<Choix> lesChoix = new ArrayList<Choix>();
	ListView listeChoix;
	ChoixAdapterEcrire adapter = null;

	//Arguments pour la base de donnees
	Page newPageCible=null;
	Livre livre;
	Page page;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page);

		texte=(EditText)findViewById(R.id.textePage);
		enregistrer=(Button)findViewById(R.id.enregistrerPage);
		allerCreationChoix=(Button)findViewById(R.id.ajouterNouveauChoix);
		listeChoix = (ListView)findViewById(R.id.lvListe2);
		objetPage = (TextView) findViewById(R.id.objetPage);

		//L'activite appelante
		Intent activiteAppelante = getIntent();	

		//Recupere le livre et cree une page dans ce livre que l'on remplira par la suite 
		//Attribue un numero a cette page, et met a jour le nombre de pages 
		//Uniquement si l'activite appelante est MenuCreationPages 
		if (activiteAppelante.getExtras().containsKey("titreDuLivre")){

			//On recupere le livre
			titreDuLivre=activiteAppelante.getStringExtra("titreDuLivre");
			livre=Livre.getLivreByTitre(titreDuLivre);

			//On cree la page
			//Et on actualise le nombre de pages
			numeroPage=activiteAppelante.getLongExtra("numeroPage", 1);
			nombreDePages=activiteAppelante.getIntExtra("nombreDePages", 2);
			page=new Page(livre.getId(), "", numeroPage);
			page.create(livre);

			//Si la page est la premiere page
			//On indique comment creer un choix
			if(numeroPage==1) Toast.makeText(this, R.string.indiceChoix, Toast.LENGTH_SHORT).show();

		}

		//Recupere la page selectionnee, les choix lies a la page, le livre et le nombre de pages
		//Uniquement si l'activite appelante est MenuCreationPages et qu'une page a ete selectionnee
		else if(activiteAppelante.getExtras().containsKey("idPage")){

			//Recupere la page et le nombre de pages
			idPage=activiteAppelante.getLongExtra("idPage", 2);
			nombreDePages=activiteAppelante.getIntExtra("nombreDePages", 2);
			page=Page.getPageByID(idPage);

			//On recupere le livre
			livre=Livre.getLivreByID(page.getIdLivre());

			//Ajoute le texte, l'objet et les choix � la page
			texte.setText(page.getTexte());
			if(page.getObjetDonne()!=0) 
				objetPage.setText(Objet.getObjetByID(page.getObjetDonne()).getNom());
			lesChoix = Choix.getAll(page);
			for(int i=0;i<lesChoix.size();i++){

				tousLesChoix.add(lesChoix.get(i));

			}

			//Indique que la page existe
			existe=true;

		}

		//Affiche les choix de la page sous forme de liste
		adapter = new ChoixAdapterEcrire(this,tousLesChoix, this);
		listeChoix.setAdapter(adapter);
		listeChoix.setOnItemClickListener(this);

		//Lorsqu'on selectionne une page le curseur est place a la fin du texte deja ecrit
		texte.setSelection(texte.length());

		objetPage.setOnClickListener(this);
		enregistrer.setOnClickListener(this);
		allerCreationChoix.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {

		// Creation de l'intent
		Intent intent = new Intent(this, MenuCreationPages.class);

		// On envoie le texte et l'id de la page
		//Ansi que l'id de son objet
		//Et la liste des pages cibles des choix de la page
		intent.putExtra("textePage", texte.getText().toString());
		intent.putExtra("idObjet", page.getObjetDonne());
		if (!listePagesCibles.isEmpty()){
			for(int i=listePagesCibles.size()-1;i>=0;i--){
				intent.putExtra("idPageCible"+i, listePagesCibles.get(i).getId());
			}
		}
		intent.putExtra("idPage", page.getId());

		//On precise si la page existait ou pas
		intent.putExtra("existe", existe);

		// On retourne le resultat avec l'intent
		setResult(RESULT_OK, intent);

		//Si la page n'existait pas on ajoute un au nombre de pages
		if (!existe)numeroPage++;

		// On termine cette activite
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.page, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		//On prend en compte l'option du menu selectionne
		switch (item.getItemId()) { 

		//Si l'option est d'ajouter un choix
		case R.id.ajouterChoix:
			//Si le nombre de choix deja crees sur la page est egal a 4
			//On affiche un message pour dire qu'on ne peut pas en creer plus
			if((tousLesChoix).size()==4) 	Toast.makeText(this, R.string.choixMax, Toast.LENGTH_SHORT).show();  

			//Sinon on ouvre la creation de choix
			else	creerBoiteDialogue(page, livre, tousLesChoix,nombreDePages+1, "", getString(R.string.ajouter));
			break;

			//Si l'option est d'ajouter un objet	
		case R.id.ajouterObjet:

			//On ouvre l'activite de selection d'un objet
			Intent intent2 = new Intent(this, ObjetActivity.class);
			intent2.putExtra("origine", "page");
			startActivityForResult(intent2, CODE_RETOUR);

			break;

			//Si l'option est d'enregistrer la page
			//La procedure est la meme que lors du clic
			//Sur le bouton retour
		case R.id.enregistrerPage:

			// Creation de l'intent
			Intent intent = new Intent(this, MenuCreationPages.class);

			// On envoie le texte et l'id de la page
			//Ansi que l'id de son objet
			//Et la liste des pages cibles des choix de la page
			intent.putExtra("textePage", texte.getText().toString());
			intent.putExtra("idObjet", page.getObjetDonne());
			if (!listePagesCibles.isEmpty()){
				for(int i=listePagesCibles.size()-1;i>=0;i--){
					intent.putExtra("idPageCible"+i, listePagesCibles.get(i).getId());
				}
			}
			intent.putExtra("idPage", page.getId());

			//On precise si la page existait ou pas
			intent.putExtra("existe", existe);

			// On retourne le resultat avec l'intent
			setResult(RESULT_OK, intent);

			//Si la page n'existait pas on ajoute un au nombre de pages
			if (!existe)numeroPage++;

			// On termine cette activite
			finish();

			break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Cree une boite de dialogue afin de creer un choix
	 * @param lapage : la page qui sera la page source du choix
	 * @param lelivre : le livre contenant cette page et qui contiendra la future page cible
	 * @param lesChoix : tableau qui contiendra l'ensemble des choix de la page source
	 * @param pageCible : le numero de la page cible initialement dans le choix
	 * @param texteDansChoix : le texte initialement dans le choix
	 * @param objet : l'objet requis initialement dans le choix
	 */
	public void creerBoiteDialogue(final Page lapage, final Livre lelivre,  final List<Choix> lesChoix, long pageCible, String texteDansChoix, String objet){

		//cree la boite de dialogue 
		LayoutInflater factory = LayoutInflater.from(this);
		final View alertDialogView = factory.inflate(R.layout.activity_choix, null);
		final AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setView(alertDialogView);

		//L'affichage du numero de page cible est en fonction 
		//Du nombre de pages deja contenues dans le livre
		TextView pourPageCible = (TextView) alertDialogView.findViewById(R.id.pourPageCible);
		pourPageCible.setText(getString(R.string.pageCible)+" (max "+String.valueOf(nombreDePages+1)+") : ");

		//Pour l'affichage
		final TextView textePourChoix = (TextView)alertDialogView.findViewById(R.id.pourTexteChoix);
		final EditText letexteChoix=(EditText)alertDialogView.findViewById(R.id.texteChoix);
		final EditText lapageCible=(EditText)alertDialogView.findViewById(R.id.pageCible);
		final CheckBox objetSup = (CheckBox) alertDialogView.findViewById(R.id.objetSupprime);
		final TextView objetRequis=(TextView)alertDialogView.findViewById(R.id.objetRequis);
		//Pour determiner si la checkbox est cochee
		cochee = false;

		//On remplit le texte du choix, de l'objet requis et le numero de la page cible
		letexteChoix.setText(texteDansChoix);
		lapageCible.setText(String.valueOf(pageCible));
		objetRequis.setText(objet);

		//Pour pouvoir supprimer un objet dans la page cible
		objetSup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//est ce que la checkbox est cochee
				if (((CheckBox) v).isChecked()) {
					cochee=true;
				}

				else cochee=false;

			}
		});


		//On indique ce qui se passe si on clique sur le bouton "Ajouter" 
		//Pour ajouter un choix
		adb.setPositiveButton(R.string.ajouter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				//On recupere le texte du choix et de l'objet requis
				String texteDuChoix = letexteChoix.getText().toString();
				String lobjetRequis = objetRequis.getText().toString();


				//Pour verifier que le choix est valide
				boolean choixValide=true;

				//Le choix n'est pas valide si son texte est deja pris 
				//Par un autre choix sur la meme page source
				List<Choix> listeDesChoix = Choix.getAll(lapage);
				for(int i=0;i<listeDesChoix.size();i++){
					if(texteDuChoix.equals(listeDesChoix.get(i).getTexte())) choixValide=false;
				}

				//Le choix n'est pas valide si le numero de la page cible  
				//Est superieur au numero de la prochaine page
				if(Long.valueOf(lapageCible.getText().toString())>(nombreDePages+1)) choixValide=false;

				//On teste la validite du choix
				if (choixValide==true){

					//Si le choix est valide, et que la page cible n'existe pas, on cree une nouvelle page
					//Et on l'ajoute a la liste des pages cibles
					if(nombreDePages<Long.valueOf(lapageCible.getText().toString())){

						newPageCible = new Page(lelivre.getId(), "", Long.valueOf(lapageCible.getText().toString()));
						newPageCible.create(lelivre);
						listePagesCibles.add(newPageCible);
						nombreDePages++;

					}

					//Si le choix n'a pas d'objet requis
					//On cree le choix avec sa page source, sa page cible, et son texte
					//Et on l'ajoute a la liste des choix
					if(lobjetRequis.equals(getString(R.string.ajouter))){
						Choix choix = new Choix(lapage.getId(), (Page.getPageByNumero(lelivre.getId(), Long.valueOf(lapageCible.getText().toString())).getId()), letexteChoix.getText().toString());
						choix.create(lapage);
						lesChoix.add(choix);
						adapter.notifyDataSetChanged();
					}

					//Si le choix a un objet requis
					//On cree le choix avec sa page source, sa page cible, son objet requis et son texte
					//Et on l'ajoute a la liste des choix
					else{
						Choix choix = new Choix(lapage.getId(), (Page.getPageByNumero(lelivre.getId(), Long.valueOf(lapageCible.getText().toString())).getId()), letexteChoix.getText().toString(), Objet.getObjetByNom(lobjetRequis).getId());
						choix.create(lapage);
						lesChoix.add(choix);
						adapter.notifyDataSetChanged();

						//Si la checkbox est cochee, l'objet sera supprime dans la page cible
						if (cochee) {
							Page.getPageByID(Page.getPageByNumero(lelivre.getId(), Long.valueOf(lapageCible.getText().toString())).getId()).setObjetSupprime(Objet.getObjetByNom(lobjetRequis).getId());
							Page.getPageByID(Page.getPageByNumero(lelivre.getId(), Long.valueOf(lapageCible.getText().toString())).getId()).updateObjetSupprime(Objet.getObjetByNom(lobjetRequis).getId());
						}
						
					}
				}

				//Si le choix n'est pas valide
				else {

					textePourChoix.setText(R.string.dejaUtilise);

				}

			}
		});

		//On ajoute un bouton "annuler" qui fermera simplement la boite de dialogue
		adb.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			} });


		//On affiche la boite de dialogue
		final AlertDialog alert = adb.create();
		alert.show();

		//Pour pouvoir cliquer sur 'Ajouter' un objet
		//Et ainsi ajouter un objet requis
		objetRequis.setOnClickListener(new OnClickListener(){
			public void onClick(View v){

				//On ouvre l'activite de selection d'objet
				//En precisant que l'activite appelante est la creation de choix
				Intent intent2 = new Intent(v.getContext(), ObjetActivity.class);
				intent2.putExtra("origine", "choix");
				intent2.putExtra("texteDansChoix", letexteChoix.getText().toString());
				intent2.putExtra("numeroPageCible", Long.valueOf(lapageCible.getText().toString()));
				startActivityForResult(intent2, CODE_RETOUR);
				alert.dismiss();
			}

		});

		//Pour l'apparence des boutons
		Button positif1 = alert.getButton(AlertDialog.BUTTON_POSITIVE);
		positif1.setBackgroundResource(R.drawable.b);
		positif1.setTextColor(Color.WHITE);
		Button negatif1 = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
		negatif1.setBackgroundResource(R.drawable.b);
		negatif1.setTextColor(Color.WHITE);

	}

	/**
	 * Cree une boite de dialogue pour modifier un choix
	 * @param choix : le choix a modifier
	 * @param tlesChoix : la liste des choix
	 * @param lelivre2 : le livre contenant la page source du choix ainsi que sa page cible
	 * @param objet : l'objet initialement requis pour le choix
	 */
	public void reCreerBoiteDialogue(final Choix choix,  final List<Choix> tlesChoix, final Livre lelivre2, String objet){

		//cree la boite de dialogue 
		LayoutInflater factory = LayoutInflater.from(this);
		final View alertDialogView = factory.inflate(R.layout.activity_choix, null);
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setView(alertDialogView);

		//Pour preciser la page cible initiale
		final EditText lapageCible=(EditText)alertDialogView.findViewById(R.id.pageCible);
		lapageCible.setText(String.valueOf(Page.getPageByID(choix.getIdNextPage()).getNumero()));

		//Pour preciser le texte initial du choix
		letexteChoix=(EditText)alertDialogView.findViewById(R.id.texteChoix);
		letexteChoix.setText(choix.getTexte());

		//La checkbox pour supprimer ou non l'objet
		//Dans la page cible
		final CheckBox objetSup = (CheckBox) alertDialogView.findViewById(R.id.objetSupprime);

		//Pour savoir si elle est initialement cochee ou decochee
		if (Page.getPageByID(choix.getIdNextPage()).getObjetSupprime()!=0){
			objetSup.setChecked(true);
		}

		//Pour pouvoir supprimer un objet dans la page cible 
		objetSup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Si la check box est checkee
				if (((CheckBox) v).isChecked()) {
					cochee=true;
				}

				//sinon
				else cochee=false;

			}
		});

		//Pour preciser l'objet requis
		final TextView objetRequis=(TextView)alertDialogView.findViewById(R.id.objetRequis);
		objetRequis.setText(objet);

		//L'affichage est en fonction du nombre de pages deja contenues dans le livre
		TextView pourPageCible = (TextView) alertDialogView.findViewById(R.id.pourPageCible);
		pourPageCible.setText(getString(R.string.pageCible)+" (max "+String.valueOf(nombreDePages+1)+") : ");

		//L'index du choix dans la liste des choix
		final int indexOfChoix = tlesChoix.indexOf(choix);

		//On indique ce qui se passe si on clique sur le bouton "Modifier"
		adb.setPositiveButton(R.string.modifier, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				//On recupere le texte du choix
				TextView textePourChoix = (TextView)alertDialogView.findViewById(R.id.pourTexteChoix);
				String texteDuChoix = letexteChoix.getText().toString();
				String lobjetRequis = objetRequis.getText().toString();

				//Pour verifier que le choix est valide
				boolean choixValide=true;

				//Le choix n'est pas valide si le texte est deja pris par un autre choix
				for(int i=0;(i<tlesChoix.size())&&(i!=indexOfChoix);i++){

					if(texteDuChoix.equals(tlesChoix.get(i).getTexte())) choixValide=false;

				}

				//Le choix n'est pas valide si le numero de la page cible  
				//Est superieur au numero de la prochaine page
				if(Long.valueOf(lapageCible.getText().toString())>(nombreDePages+1)) choixValide=false;

				if (choixValide==true){

					//On affiche le texte deja compris dans le choix
					choix.setTexte(texteDuChoix);

					//Si la page n'existe pas encore on la cree
					if((Page.getPageByNumero(livre.getId(), Long.valueOf(lapageCible.getText().toString()))==null)&&
							(Long.valueOf(lapageCible.getText().toString())!=Page.getPageByID(choix.getIdNextPage()).getNumero())){
						newPageCible = new Page(lelivre2.getId(), "", Long.valueOf(lapageCible.getText().toString()));
						newPageCible.create(lelivre2);
						nombreDePages++;
						listePagesCibles.add(newPageCible);

						//Si le choix contient un objet requis
						if(!lobjetRequis.equals(getString(R.string.ajouter))){	
							//Si la checkbox est cochee
							//L'objet sera supprimé dans la page cible
							if (cochee) {
								newPageCible.setObjetSupprime(Objet.getObjetByNom(lobjetRequis).getId());
								newPageCible.updateObjetSupprime(Objet.getObjetByNom(lobjetRequis).getId());
							}
							//Sinon aucun objet ne sera supprime 
							else {
								newPageCible.setObjetSupprime(0);
								newPageCible.updateObjetSupprime(0);
							}
						}

						//Sinon aucun objet ne sera supprime 
						else{
							newPageCible.setObjetSupprime(0);
							newPageCible.updateObjetSupprime(0);
						}
					}

					//Si la page cible existe deja
					else{

						//On fait en sorte que le choix mene vers la page cible
						choix.setIdNextPage(Page.getPageByNumero(lelivre2.getId(), Long.valueOf(lapageCible.getText().toString())).getId());
						choix.updateNextPage(Page.getPageByNumero(lelivre2.getId(), Long.valueOf(lapageCible.getText().toString())).getId());

						//Si le choix contient un objet requis
						if(!lobjetRequis.equals(getString(R.string.ajouter))){
							//Si la checkbox est cochee
							//L'objet est supprime dans la page cible
							if (cochee) {
								Page.getPageByID(choix.getIdNextPage()).setObjetSupprime(Objet.getObjetByNom(lobjetRequis).getId());
								Page.getPageByID(choix.getIdNextPage()).updateObjetSupprime(Objet.getObjetByNom(lobjetRequis).getId());
							}
							//Sinon aucun objet n'est supprime
							else {
								Page.getPageByID(choix.getIdNextPage()).setObjetSupprime(0);
								Page.getPageByID(choix.getIdNextPage()).updateObjetSupprime(0);
							}
						}
						//Sinon aucun objet n'est supprime
						else {
							Page.getPageByID(choix.getIdNextPage()).setObjetSupprime(0);
							Page.getPageByID(choix.getIdNextPage()).updateObjetSupprime(0);
						}
					}

					//On actualise le choix dans la liste des choix
					//On actualise sa page cible et son objet requis egalement
					choix.updateChoix(texteDuChoix);
					choix.setIdNextPage(Page.getPageByNumero(lelivre2.getId(), Long.valueOf(lapageCible.getText().toString())).getId());
					choix.updateNextPage(Page.getPageByNumero(lelivre2.getId(), Long.valueOf(lapageCible.getText().toString())).getId());

					//Si le choix possede un objet requis
					if(!lobjetRequis.equals(getString(R.string.ajouter))){
						choix.setObjetRequis(Objet.getObjetByNom(lobjetRequis).getId());
						choix.updateObjetRequis(Objet.getObjetByNom(lobjetRequis).getId());
					}
					//Si le choix ne possede pas d'objet requis
					else{
						choix.setObjetRequis(0);
						choix.updateObjetRequis(0);

					}

					tlesChoix.set(indexOfChoix, choix);
					adapter.notifyDataSetChanged();

				}

				//Si le choix n'est pas valide
				else {

					textePourChoix.setText(R.string.dejaUtilise);

				}
			}
		});

		//On ajoute un bouton "annuler" qui fermera simplement la boite de dialogue
		adb.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			} });

		//On affiche la boite de dialogue
		final AlertDialog alert = adb.create();
		alert.show();

		//Pour pouvoir cliquer sur 'Ajouter' un objet
		//Et ainsi ajouter un objet requis
		objetRequis.setOnClickListener(new OnClickListener(){
			public void onClick(View v){

				//On ouvre l'activite de selection d'objet
				//En precisant que l'activite appelante est la modification de choix
				Intent intent2 = new Intent(v.getContext(), ObjetActivity.class);
				intent2.putExtra("origine", "choixMod");
				intent2.putExtra("indexChoix", indexOfChoix);
				intent2.putExtra("numeroPageCible", Long.valueOf(lapageCible.getText().toString()));
				startActivityForResult(intent2, CODE_RETOUR);
				alert.dismiss();
			}

		});

		//Pour l'apparence des boutons
		Button positif1 = alert.getButton(AlertDialog.BUTTON_POSITIVE);
		positif1.setBackgroundResource(R.drawable.b);
		positif1.setTextColor(Color.WHITE);
		Button negatif1 = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
		negatif1.setBackgroundResource(R.drawable.b);
		negatif1.setTextColor(Color.WHITE);

	}

	/**
	 * 
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		//On vérifie que le retour est OK 
		if (requestCode == CODE_RETOUR) { 
			// On verifie que le résultat est OK 
			if (resultCode == RESULT_OK) 
			{ 
				//On recupere l'identifiant de l'objet choisi
				long idObjet = data.getLongExtra("objetDonne", 0); 

				//On recupere l'origine de l'activite appelante
				String appel = data.getStringExtra("origine"); 

				//On recupere le numero de la page cible
				long numPageCible = data.getLongExtra("numeroPageCible", 0); 

				//On recupere le texte contenu dans le choix
				String texteDansLeChoix = data.getStringExtra("texteDansChoix"); 

				//On recupere l'index du choix 
				int indexChoixRecu = data.getIntExtra("indexChoix", 1); 

				//Si l'activite appelante etait la page
				if (appel.equals("page")) { 
					//On actualise l'objet donne de la page
					page.updateObjetDonne(idObjet); 

					//Si l'objet selectionne est 'Aucun'
					//On n'attribue aucun objet a la page
					if (page.getObjetDonne() == 1) { 
						objetPage.setText(R.string.ajouter); 
						page.updateObjetDonne(0); 
					} 
					//Sinon on attribue l'objet selectionne
					else { 
						objetPage.setText(Objet.getObjetByID(idObjet).getNom()); 
					} 
				} 

				//Si l'activite appelante etait la creation de choix
				else if (appel.equals("choix")) { 

					//Si l'objet selectionne est 'Aucun'
					//On n'attribue aucun objet au choix
					//Et on recree une boite de dialogue
					if (idObjet == 1) { 
						creerBoiteDialogue(page, livre, tousLesChoix, numPageCible, texteDansLeChoix, getString(R.string.ajouter)); 

					}

					//Sinon on attribue l'objet selectionne
					//Et on recree une boite de dialogue
					else{

						creerBoiteDialogue(page, livre, tousLesChoix, numPageCible, texteDansLeChoix, Objet.getObjetByID(idObjet) .getNom()); 
					}
				} 

				//Si l'activite appelante etait la modification de choix
				else if (appel.equals("choixMod")) { 

					//Si l'objet selectionne est 'Aucun'
					//On n'attribue aucun objet au choix
					//Et on recree une boite de dialogue
					if (idObjet == 1) { 

						Choix choix = tousLesChoix.get(indexChoixRecu); 
						reCreerBoiteDialogue(choix, tousLesChoix, livre, getString(R.string.ajouter));

					}

					//Sinon on attribue l'objet selectionne
					//Et on recree une boite de dialogue
					else{

						Choix choix = tousLesChoix.get(indexChoixRecu); 
						reCreerBoiteDialogue(choix, tousLesChoix, livre, Objet .getObjetByID(idObjet).getNom());

					}

				} 
			} 
			//Sinon on indique que le choix d'objet a ete abandonne
			else Toast.makeText(this, R.string.abandonObj, Toast.LENGTH_SHORT).show(); 
		} 
	}



	@Override
	public void onClick(View v) {

		switch(v.getId()) {

		//Si on clique sur 'Enregistrer la page'
		//La procedure est la meme que si on clique
		//Sur le bouton 'retour'
		case (R.id.enregistrerPage):

			// Creation de l'intent
			Intent intent = new Intent(this, MenuCreationPages.class);

		// On envoie le texte et l'id de la page
		//Ansi que l'id de son objet
		//Et la liste des pages cibles des choix de la page
		intent.putExtra("textePage", texte.getText().toString());
		intent.putExtra("idObjet", page.getObjetDonne());
		if (!listePagesCibles.isEmpty()){
			for(int i=listePagesCibles.size()-1;i>=0;i--){
				intent.putExtra("idPageCible"+i, listePagesCibles.get(i).getId());
			}
		}
		intent.putExtra("idPage", page.getId());

		//On precise si la page existait ou pas
		intent.putExtra("existe", existe);

		// On retourne le resultat avec l'intent
		setResult(RESULT_OK, intent);

		//Si la page n'existait pas on ajoute un au nombre de pages
		if (!existe)numeroPage++;

		// On termine cette activite
		finish();

		break;

		//On ouvre la boite de dialogue pour creer un choix en cliquant sur Ajouter
		case (R.id.ajouterNouveauChoix):

			//Si le nombre de choix déjà crees sur la page est egal a 4
			//On affiche un message pour dire qu'on ne peut pas en creer plus
			if((tousLesChoix).size()==4) 	Toast.makeText(this, R.string.choixMax, Toast.LENGTH_SHORT).show();  

			else creerBoiteDialogue(page, livre, tousLesChoix,nombreDePages+1, "", getString(R.string.ajouter));

		break;

		//Si on clique sur 'Ajouter' un objet
		case (R.id.objetPage):
			//On ouvre l'activite de selection d'objet
			Intent intent2 = new Intent(this, ObjetActivity.class);
		intent2.putExtra("origine", "page");
		startActivityForResult(intent2, CODE_RETOUR);

		break;

		}
	}

	@Override
	/**
	 * On ouvre la boite de dialogue pour modifier un choix en cliquant sur un des choix dans la liste
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		if(((Choix)parent.getItemAtPosition(position)).getObjetRequis()==0)		
			reCreerBoiteDialogue((Choix)parent.getItemAtPosition(position), tousLesChoix, livre, getString(R.string.ajouter));

		else reCreerBoiteDialogue((Choix)parent.getItemAtPosition(position), tousLesChoix, livre, Objet.getObjetByID(((Choix)parent.getItemAtPosition(position)).getObjetRequis()).getNom());

	}

}
