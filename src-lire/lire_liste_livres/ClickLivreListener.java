package lire_liste_livres;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import couverture.CouvertureActivity;
import databasetest.Livre;

public class ClickLivreListener implements OnItemClickListener
{
	private Activity 	ac;
	
	public ClickLivreListener(Activity ac)
	{
		this.ac			= ac;
	}
	
	@Override
	public void onItemClick(AdapterView<?> av, View v, int position, long arg3)
	{
		Intent monIntent = new Intent(ac, CouvertureActivity.class);
		Bundle b = new Bundle();
		b.putLong("LivreID",  ((Livre) av.getItemAtPosition(position)).getId());
		monIntent.putExtras(b);
		ac.startActivity(monIntent);
	}

}
