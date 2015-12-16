package lire_livre;

import android.view.View;
import android.view.View.OnClickListener;
import databasetest.Page;

public class LireChangePageListener implements OnClickListener
{
	private LireActivity	ac;
	private long			newID;

	public LireChangePageListener(LireActivity ac, long newID)
	{
		this.ac = ac;
		this.newID = newID;
	}

	@Override
	/**
	 * Lorsque l'on clique sur un choix on recupere la page donc le num�ro � �t� pass� en argument et l'on effectue une maj() de l'activit� avec cette page.
	 */
	public void onClick(View v)
	{
		Page pg = Page.getPageByID(newID);
		ac.maj(pg);
	}
}
