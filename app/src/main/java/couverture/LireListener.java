package couverture;

import lire_livre.LireActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class LireListener implements OnClickListener
{
	Activity	ac;
	long		pageID;
	long		livreNumero;

	public LireListener(Activity ac, long pagNumero, long livreID)
	{
		this.ac = ac;
		this.pageID = pagNumero;
		this.livreNumero = livreID;

	}

	public void onClick(View v)
	{
		Intent intent = new Intent(ac, LireActivity.class);
		intent.putExtra("LivreID", livreNumero);
		intent.putExtra("pageID", pageID);
		ac.startActivity(intent);
		ac.finish();
	}
}
