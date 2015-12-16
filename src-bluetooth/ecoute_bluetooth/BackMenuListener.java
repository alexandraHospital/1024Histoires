package ecoute_bluetooth;

import android.app.Activity;
import android.content.DialogInterface;

public class BackMenuListener implements DialogInterface.OnClickListener
{
	private Activity ac;
	
	public BackMenuListener(Activity ac)
	{
		this.ac 		= ac;
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		ac.finish();
	}
	
	
}
