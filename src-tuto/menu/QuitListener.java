package menu;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class QuitListener implements OnClickListener
{
	Activity ac;
	
	public QuitListener(Activity ac)
	{
		this.ac = ac;
	}
	
	@Override
	public void onClick(View v)
	{
		ac.finish();
	}

}
