package liste_livres;

import menu.MainActivity;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.AdapterView;
import com.example.histoires.R;
import databasetest.Livre;

public class YesDelListener implements DialogInterface.OnClickListener
{
	private Livre			livre;
	private AdapterView<?>	av;
	private Activity		ac;

	public YesDelListener(Activity ac, AdapterView<?> av, int position)
	{
		this.livre = ((Livre) av.getItemAtPosition(position));
		this.av = av;
		this.ac = ac;
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{

		/*
		 * for(Page pg : Page.getAll(livre)) { for(Choix ch : Choix.getAll(pg))
		 * { ch.delete(); } pg.delete(); }
		 */
		livre.deleteByID();

		MainActivity.playSound(ac, R.raw.supprimer);

		Intent intent = new Intent(av.getContext(), ListLivresActivity.class);
		intent.putExtra("requete", "");
		ac.startActivity(intent);

		ac.finish();
	}
}
