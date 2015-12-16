package liste_livres;

import activites.MenuCreationPages;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import databasetest.Livre;

public class ClickLivreListener implements OnItemClickListener
{
	private Activity	ac;

	public ClickLivreListener(Activity ac)
	{
		this.ac = ac;
	}

	@Override
	public void onItemClick(AdapterView<?> av, View v, int position, long arg3)
	{

		Intent intent2 = new Intent(ac, MenuCreationPages.class);
		intent2.putExtra("titreDuLivreAModifier", ((Livre) av.getItemAtPosition(position)).getTitre());
		ac.startActivity(intent2);
	}

}
