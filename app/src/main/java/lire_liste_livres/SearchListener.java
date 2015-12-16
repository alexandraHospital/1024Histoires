package lire_liste_livres;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import com.example.histoires.R;

public class SearchListener implements OnClickListener
{
	Activity	ac;

	public SearchListener(Activity ac)
	{
		this.ac = ac;
	}

	// lance le pop-up pour faire la recherche avanc�e
	@Override
	public void onClick(View v)
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(ac);
		popup.setTitle(R.string.searchpop_title);

		View view = View.inflate(ac, R.layout.alert_dialog_recherche, null);
		popup.setView(view);

		EditText motscle = (EditText) view.findViewById(R.id.keyword_input);

		CheckBox checktitre = (CheckBox) view.findViewById(R.id.check_titre);
		CheckBox checkauteur = (CheckBox) view.findViewById(R.id.check_author);
		CheckBox checksynopsis = (CheckBox) view.findViewById(R.id.check_synopsis);
		CheckBox checkpage = (CheckBox) view.findViewById(R.id.check_page);

		DialogInterface.OnClickListener l = new YesSearchListener(ac, motscle, checktitre, checkauteur, checksynopsis, checkpage);
		popup.setPositiveButton(R.string.lauch_search, l);

		popup.create();
		popup.show();
	}

}
