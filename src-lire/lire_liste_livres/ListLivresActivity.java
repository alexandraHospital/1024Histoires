package lire_liste_livres;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.histoires.R;
import databasetest.DBManager;
import databasetest.Livre;

public class ListLivresActivity extends Activity
{
	ListView	listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_livres);

		// Crée la base de données et ses tables :
		DBManager.setContext(getApplicationContext());

		String requete = getIntent().getExtras().getString("motscle");
		boolean checktitre = getIntent().getExtras().getBoolean("checktitre");
		boolean checkauthor = getIntent().getExtras().getBoolean("checkauthor");
		boolean checksynopsis = getIntent().getExtras().getBoolean("checksynopsis");
		boolean checkpage = getIntent().getExtras().getBoolean("checkpage");

		if(requete == null)
		{
			this.finish();
		}
		
		// Adaptateur :
		LivreAdapter adapter = new LivreAdapter(this, Livre.getAll(), requete, checktitre, checkauthor, checksynopsis, checkpage);

		listView = (ListView) findViewById(R.id.add);
		listView.setAdapter(adapter);

		// Listener pour supprimer un livre
		listView.setOnItemLongClickListener(new LongClickLivreListener(this));

		// Listener pour acceder à une couverture
		listView.setOnItemClickListener(new ClickLivreListener(this));

		// Listener pour ouvrir le pop-up de la recherche avanc�e
		ImageButton recherche = (ImageButton) findViewById(R.id.recherche);
		recherche.setOnClickListener(new SearchListener(this));
	}
}
