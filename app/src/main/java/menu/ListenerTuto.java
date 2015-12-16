package menu;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ListenerTuto implements OnClickListener
{
	Activity	ac;

	public ListenerTuto(Activity ac)
	{
		this.ac = ac;
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(this.ac, MenuTutoActivity.class);
		ac.startActivity(intent);
	}

}
