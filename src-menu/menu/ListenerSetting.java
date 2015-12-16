package menu;

import activity.ParametreActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ListenerSetting implements OnClickListener
{
	Activity ac;
	
	public ListenerSetting(Activity ac)
	{
		this.ac = ac;
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(this.ac, ParametreActivity.class);
		ac.startActivity(intent);
	}

}
