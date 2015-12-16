package activites;

import java.util.ArrayList;

import lire_livre.ObjectAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.example.histoires.R;
import databasetest.Objet;

public class ObjetActivity extends Activity implements OnItemClickListener {


	//La liste de tous les objets
	ArrayList<Objet> 	tousLesObjetsList;
	ListView listeObjets;
	ObjectAdapter adapter = null;

	//L'activite appelante
	Intent activiteAppelante = null;
	String origine;
	String texteDansChoix = "";

	//Differentes valeurs a utiliser
	int indexChoix = 1;
	long numPageCible = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//On identifie l'activite appelante
		activiteAppelante = getIntent();	

		//Si on appelle cette activite depuis un choix
		if (activiteAppelante.getExtras().containsKey("origine")){
			origine = activiteAppelante.getStringExtra("origine");	

			//Si l'activite appelante est la creation de choix
			if(origine.equals("choix")){
				texteDansChoix = activiteAppelante.getStringExtra("texteDansChoix");
				numPageCible = activiteAppelante.getLongExtra("numeroPageCible", 1);
			}

			//Si l'activite appelante est la modification de choix
			else if(origine.equals("choixMod")){
				indexChoix = activiteAppelante.getIntExtra("indexChoix", 1);
			}
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_objets_page);

		//On instancie la liste des objets
		tousLesObjetsList = (ArrayList<Objet>) Objet.getAll();
		listeObjets = (ListView) findViewById(R.id.all_items_list);	
		ObjectAdapter adapter = new ObjectAdapter(this, R.layout.activity_objets_page, tousLesObjetsList);
		listeObjets.setAdapter(adapter);

		//Permet de selectionner un objet dans la liste
		listeObjets.setOnItemClickListener(this);

	}

	/**
	 * Lorsqu'un objet est selectionne on retourne
	 * Sur l'activite appelante en envoyant 
	 * L'identifiant de l'objet choisi
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		Intent intent = new Intent(this, PageActivity.class);

		//On envoie l'identifiant de l'objet donne
		intent.putExtra("objetDonne", ((Objet)parent.getItemAtPosition(position)).getId());

		//On envoie l'identifiant de l'activite appelante
		intent.putExtra("origine", origine);

		//On envoie l'identifiant de la page cible
		intent.putExtra("numeroPageCible", numPageCible);

		//On envoie le texte dans le choix 
		//Si l'activite appelante est la creation de choix
		if(origine.equals("choix"))intent.putExtra("texteDansChoix", texteDansChoix);

		//On envoie l'index du choix 
		//Si l'activite appelante est la modification de choix
		else if(origine.equals("choixMod"))intent.putExtra("indexChoix", indexChoix);

		setResult(RESULT_OK, intent);
		finish();

	}

}


