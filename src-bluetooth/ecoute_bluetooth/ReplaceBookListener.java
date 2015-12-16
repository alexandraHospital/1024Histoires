package ecoute_bluetooth;

import pack.LivrePack;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import couverture.CouvertureActivity;
import databasetest.Livre;

public class ReplaceBookListener implements DialogInterface.OnClickListener
{
	private Activity ac;
	private LivrePack lpk;
	private long idLivre;
	
	public ReplaceBookListener(Activity ac, LivrePack lpk)
	{
		this.ac 		= ac;
		this.lpk		= lpk;
		this.idLivre	= Livre.getLivreByTitre(lpk.getTitre()).getId();
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		Livre.getLivreByID(idLivre).deleteByID();
		
		lpk.insertThisIntoDatabase();
		
		Intent intent = new Intent(ac, CouvertureActivity.class);
		intent.putExtra("LivreID", this.idLivre);
		
		ac.startActivity(intent);
		ac.finish();
	}
	
	
}
