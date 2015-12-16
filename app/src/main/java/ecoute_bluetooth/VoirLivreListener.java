package ecoute_bluetooth;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import couverture.CouvertureActivity;

public class VoirLivreListener implements DialogInterface.OnClickListener
{
	private Activity ac;
	private AsyncTask<?, ?, ?> task;
	private long idLivre;
	
	public VoirLivreListener(Activity ac, AsyncTask<?, ?, ?> task, long id)
	{
		this.ac 		= ac;
		this.task		= task;
		this.idLivre 	= id;
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		if(!task.isCancelled())
		{
			task.cancel(true);
		}
		
		Intent intent = new Intent(ac, CouvertureActivity.class);
		intent.putExtra("LivreID", this.idLivre);
		
		ac.startActivity(intent);
		ac.finish();
	}
	
	
}
