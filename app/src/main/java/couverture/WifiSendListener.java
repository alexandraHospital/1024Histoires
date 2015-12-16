package couverture;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import envoi_wifi.EnvoisLivreWifiActivity;

public class WifiSendListener implements OnClickListener
{
	Activity ac;
	long livreID;
	
	public WifiSendListener(Activity ac, long livreID)
	{
		this.ac = ac;
		this.livreID = livreID;
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(ac, EnvoisLivreWifiActivity.class);
		intent.putExtra("LivreID", livreID);
		ac.startActivity(intent);
	}

}
