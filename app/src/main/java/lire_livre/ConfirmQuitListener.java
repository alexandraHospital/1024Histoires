package lire_livre;

import menu.MainActivity;
import com.example.histoires.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

public class ConfirmQuitListener implements OnClickListener
{
	Activity ac;
	
	public ConfirmQuitListener(Activity ac)
	{
		this.ac = ac;
		
	}


	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		MainActivity.playSound(ac, R.raw.fermerlivre);
		ac.finish();
	}

}
