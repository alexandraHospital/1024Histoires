package menu;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import ecoute_bluetooth.RecevoirLivreActivity;
import ecoute_wifi.RecevoirLivreWifiActivity;

public class ListenerWifi implements OnClickListener
{
	Activity ac;
	
	public ListenerWifi(Activity ac)
	{
		this.ac = ac;
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(ac, RecevoirLivreWifiActivity.class);
		ac.startActivity(intent);
	}

}
