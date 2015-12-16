package liste_livres;

import java.util.ArrayList;
import java.util.List;

import lire_liste_livres.SearchListener;
import activites.PageCouverture;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.histoires.R;
import databasetest.DBManager;
import databasetest.Livre;

public class ListLivresActivity extends Activity implements OnClickListener
{
	ListView listView;
	List<Livre> listeLivres = new ArrayList<Livre>();
	LivreAdapter adapter;

	static final int PICK_CONTACT_REQUEST = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ecrire_list_livre);

		// Cr�e la base de donn�es et ses tables :
		DBManager.setContext(getApplicationContext());

		String requete = getIntent().getExtras().getString("requete");

		// Adaptateur :
		adapter = new LivreAdapter(this, Livre.getAll(), requete);

		listView = (ListView) findViewById(R.id.add);
		listView.setAdapter(adapter);

		// Listener pour supprimer un livre
		listView.setOnItemLongClickListener(new LongClickLivreListener(this));	
		// Listener pour acceder � une couverture
		listView.setOnItemClickListener(new ClickLivreListener(this));

		Button b = (Button) findViewById(R.id.add_livre);
		b.setOnClickListener(this);

		ImageButton recherche = (ImageButton) findViewById(R.id.search_ecrire);
		recherche.setOnClickListener(new SearchListener(this));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.livres, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		//On prend en compte l'option
		//du menu s�lectionn�e

		final Context contexte = this; 
		switch (item.getItemId()) { 

		case R.id.supprimerLivres:
			AlertDialog.Builder ad4 = new AlertDialog.Builder(this);
			ad4.setMessage(R.string.verifSuppressionTousLivres);

			ad4.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Livre.deleteAll();
					adapter = new LivreAdapter(contexte, Livre.getAll(), "");
					adapter.notifyDataSetChanged();
					listView.setAdapter(adapter);
				}});
			ad4.setNegativeButton("Non", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}});
			ad4.show();
			return true;
		}	

		return super.onOptionsItemSelected(item);

	}




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(this, PageCouverture.class);
		startActivity(intent);
		finish();
	}
}
