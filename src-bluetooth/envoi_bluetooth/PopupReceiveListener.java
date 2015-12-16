package envoi_bluetooth;

import lire_liste_livres.ListLivresActivity;
import pack.LivrePack;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import databasetest.Livre;

public class PopupReceiveListener implements DialogInterface.OnClickListener
{
	private Activity 	ac;
	private boolean 	exist;
	private boolean 	replace;
	private LivrePack	lpk;

	public PopupReceiveListener(Activity ac, boolean exist, boolean replace, LivrePack lpk)
	{
		this.ac 		= ac;
		this.exist 		= exist;
		this.replace 	= replace;
		this.lpk		= lpk;
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{

		if (exist && replace)
		{
			Livre lv = Livre.getLivreByTitre(lpk.getTitre());
			lv.deleteByID();
			
			lpk.insertThisIntoDatabase();
		}
		
		goListLivres();
	}

	public void goListLivres()
	{
		// On va sur la liste des livres
		Intent intent = new Intent(ac, ListLivresActivity.class);
		ac.startActivity(intent);
		ac.finish();
	}
}
