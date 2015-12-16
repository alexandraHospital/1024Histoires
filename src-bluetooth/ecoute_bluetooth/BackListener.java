package ecoute_bluetooth;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class BackListener implements DialogInterface.OnClickListener
{
	private Activity			ac;
	private AsyncTask<?, ?, ?>	task;

	public BackListener(Activity ac, AsyncTask<?, ?, ?> task)
	{
		this.ac = ac;
		this.task = task;
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		if(!task.isCancelled())
		{
			task.cancel(true);
		}
		

		ac.finish();
	}

}
