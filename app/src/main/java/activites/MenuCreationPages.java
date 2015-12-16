package activites;

import java.util.ArrayList;
import java.util.List;

import menu.MainActivity;
import adapters.PageNonSauvegardeesAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.histoires.R;

import databasetest.Choix;
import databasetest.Livre;
import databasetest.Page;

public class MenuCreationPages extends Activity implements OnClickListener, OnItemClickListener {

	//Arguments pour les valeurs
	int CODE_RETOUR=0;
	long numeroPage;
	String titreDuLivre=""; 
	boolean afficher=true;

	//Boutons
	Button creerPage=null;
	Button retourMenu=null;

	//Arguments pour afficher la liste des pages d�j� cr��es
	List<Page> pages = new ArrayList<Page>();
	List<Page> toutesLesPages = new ArrayList<Page>();
	ListView listePages;
	PageNonSauvegardeesAdapter adapter;

	//L'activite appelante
	Intent activiteAppelante =null;

	//Le livre
	Livre livre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_creation_pages);


		//Recupere le livre cree dans l'activite PageCouverture
		//uniquement si l'activite appelante est PageCouverture et que le livre existe deja
		activiteAppelante = getIntent();	
		if (activiteAppelante.getExtras().containsKey("titreDuLivre")){
			titreDuLivre=activiteAppelante.getStringExtra("titreDuLivre");
			livre=Livre.getLivreByTitre(titreDuLivre);

			//Recupere toutes les pages du livre
			toutesLesPages=Page.getAll(livre);
			for(int i=0;i<toutesLesPages.size();i++){

				pages.add(toutesLesPages.get(i));

			}

		}


		//Recupere le livre donc le titre a ete saisi
		//Uniquement si l'activite appelante est MainActivity
		else if (activiteAppelante.getExtras().containsKey("titreDuLivreAModifier")){
			titreDuLivre=activiteAppelante.getStringExtra("titreDuLivreAModifier");
			livre=Livre.getLivreByTitre(titreDuLivre);

			//Recupere toutes les pages du livre
			toutesLesPages=Page.getAll(livre);
			for(int i=0;i<toutesLesPages.size();i++){

				pages.add(toutesLesPages.get(i));

			}

		}

		//On initialise les boutons
		creerPage=(Button)findViewById(R.id.ajouterNouvellePage);
		creerPage.setOnClickListener(this);

		retourMenu=(Button)findViewById(R.id.retourMenu);
		retourMenu.setOnClickListener(this);

		//affiche les pages deja creees sous forme de liste
		listePages = (ListView)findViewById(R.id.lvListe);
		adapter = new PageNonSauvegardeesAdapter(this,pages);
		listePages.setAdapter(adapter);

		//permet de selectionner une page dans la liste des pages
		listePages.setOnItemClickListener(this);

	}

	@Override
	public void onBackPressed() {

		//On retourne directement a l'activite principale
		Intent intent5 = new Intent(this, MainActivity.class);
		startActivity(intent5);
		finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_creation_pages, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {


		//On prend en compte l'option du menu selectionne
		switch (item.getItemId()) { 

		//S'il est egal a ajouterUnePage
		case R.id.ajouterUnePage:

			Intent intent = new Intent(this, PageActivity.class);

			//recupere le numero de la derniere page creee
			numeroPage = adapter.numeroDernierePage();

			//envoie le numero de la derniere page ainsi que le titre du livre a l'activite suivante
			intent.putExtra("titreDuLivre", livre.getTitre());
			intent.putExtra("numeroPage", numeroPage+1);

			//envoie le nombre de pages creees a l'activite suivante
			if(!pages.isEmpty())intent.putExtra("nombreDePages", pages.size()+1);
			else intent.putExtra("nombreDePages", 2);


			//demarre une nouvelle activite
			startActivityForResult(intent, CODE_RETOUR);
			return true;

			//S'il est egal a modifierPageCouverture
		case R.id.modifierPageCouverture:

			//Ouvre le menu de la page de couverture
			Intent intent1 = new Intent(this, PageCouverture.class);
			intent1.putExtra("titreDuLivreModifie", livre.getTitre());
			startActivity(intent1);

			finish();
			return true;

			//S'il est egal a supprimerLivre
		case R.id.supprimerLivre:

			final Context contexte = this;

			//Ouverture d'une boite de dialogue demandant 
			//La confirmation de la suppression
			AlertDialog.Builder ad3 = new AlertDialog.Builder(this);
			ad3.setMessage(R.string.verifSuppressionLivre);
			//Si oui
			ad3.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					//On supprime tous les choix de chaque page
					for(int i=1;i<pages.size();i++){

						Choix.deleteAll(pages.get(i));

					}

					//On supprime toutes les pages de ce livre
					Page.deleteAll(livre);

					//On supprime le livre
					livre.deleteByTitre();

					//On revient au menu principal
					Intent intent = new Intent(contexte, MainActivity.class);
					startActivity(intent);
					finish();

				}});

			//Si non
			ad3.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					//Il ne se passe rien

				}});
			ad3.show();

			return true;

			// S'il est egal a supprimerToutesLesPages
		case R.id.supprimerToutesLesPages:


			//Ouverture d'une boite de dialogue demandant 
			//La confirmation de la suppression
			AlertDialog.Builder ad4 = new AlertDialog.Builder(this);
			ad4.setMessage(R.string.verifSuppressionPages);
			//Si oui
			ad4.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					//On supprime tous les choix de chaque page
					for(int i=1;i<pages.size();i++){

						Choix.deleteAll(pages.get(i));

					}

					//On supprime toutes les pages et on actualise la liste
					Page.deleteAll(livre);
					pages.clear();

					//On cree une nouvelle page vierge
					Page page1 = new Page(livre.getId(), "", 1);
					page1.create(livre);
					livre.setIdFirstPage(page1.getId());
					livre.updateFirstPage(page1.getId());

					//On remet le compteur des pages a 1
					pages.add(page1);
					adapter.numeroDernierePage=1;
					adapter.notifyDataSetChanged();

				}});
			//Si non
			ad4.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					//Il ne se passe rien

				}});

			ad4.show();
			return true;

			// S'il est egal a menuPrincipal
		case R.id.menuPrincipal:

			//On retourne au menu principal
			Intent intent5 = new Intent(this, MainActivity.class);
			startActivity(intent5);
			finish();

			return true;

		}	

		return super.onOptionsItemSelected(item);

	}


	@Override
	public void onClick(View v) {

		//On regarde le bouton appelant
		switch(v.getId()) {

		//Si le bouton clique est 'ajouter une page'
		case (R.id.ajouterNouvellePage):

			//On ouvre l'activite creation de page
			Intent intent = new Intent(this, PageActivity.class);

		//recupere le numero de la derniere page creee
		numeroPage = adapter.numeroDernierePage();

		//envoie le numero de la derniere page ainsi que le titre du livre a l'activite suivante
		intent.putExtra("titreDuLivre", livre.getTitre());
		intent.putExtra("numeroPage", numeroPage+1);

		//envoie le nombre de pages creees a l'activite suivante
		if(!pages.isEmpty())intent.putExtra("nombreDePages", pages.size()+1);
		else intent.putExtra("nombreDePages", 2);

		//demarre une nouvelle activite
		startActivityForResult(intent, CODE_RETOUR);

		break;

		//Si le bouton clique est 'retourner au menu'
		case (R.id.retourMenu): 

			Intent intent5 = new Intent(this, MainActivity.class);
		startActivity(intent5);
		finish();

		}
	}

	/**
	 * Recupere les donnees de l'activite appelante
	 * Ici, enregistre la page creee dans la base de donnees
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// Verification du code de retour
		if(resultCode == RESULT_OK) {


			// On recupere les parametres de l'intent
			//L'id de la page
			long idPage = data.getLongExtra("idPage", 1);
			String textePage = data.getStringExtra("textePage");

			//L'id de l'objet
			long idObjet = data.getLongExtra("idObjet", 1);
			boolean existe = data.getBooleanExtra("existe", true);

			//On recupere la page d'identifiant idPage
			Page page = Page.getPageByID(idPage);	

			//Si la page porte le numero 1 on l'identifie en tant que firstPage
			if(page.getNumero()==1){
				livre.setIdFirstPage(page.getId());
				livre.updateFirstPage(page.getId());
			}

			//On met a jour le texte de la page 
			page.setTexte(textePage);
			page.updatePage(textePage);

			//On met a jour l'objet de la page
			page.setObjetDonne(idObjet);
			page.updateObjetDonne(idObjet);

			//On ajoute la page a la liste si elle n'existe pas encore
			//sinon, on met la liste a jour
			if(!existe) {
				pages.add(page);
				//Si la page n'existait pas on ajoute un au nombre de pages
				numeroPage++;
			}
			else pages.set((int) (page.getNumero()-1), page); 

			//On rajoute toutes les pages cibles a la liste si elles n'existent pas encore
			//Sinon, on met la liste a jour
			for(int i=0;data.getExtras().containsKey("idPageCible"+i);i++){

				long idPageCible = data.getLongExtra("idPageCible"+i, 1);
				Page pageCible = Page.getPageByID(idPageCible);

				if ((idPageCible!=1)&&(!pages.contains(Page.getPageByID(idPageCible))))
					pages.add(pageCible);
			}

			//Mise a jour de l'adapter pour afficher les elements de la liste
			adapter.notifyDataSetChanged();

		}

	}

	/**
	 * Appelle l'activite PageActivity en mettant en parametre l'id de la page et le nombre de pages
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		Intent intent = new Intent(parent.getContext(), PageActivity.class);
		intent.putExtra("idPage", ((Page)parent.getItemAtPosition(position)).getId());

		intent.putExtra("nombreDePages", pages.size());
		startActivityForResult(intent, CODE_RETOUR);

	}

}
