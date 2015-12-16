package lire_liste_livres;

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
		livre.deleteByID();
		MainActivity.playSound(ac, R.raw.supprimer);

		Intent intent = new Intent(av.getContext(), ListLivresActivity.class);
		intent.putExtra("motscle", "");
		intent.putExtra("checktitre", false);
		intent.putExtra("checkauthor", false);
		intent.putExtra("checksynopsis", false);
		intent.putExtra("checkpage", false);
		ac.startActivity(intent);

		ac.finish();
	}

}
