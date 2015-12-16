package couverture;

import envoi_bluetooth.EnvoisLivreBluetoothActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class BluetoothSendListener implements OnClickListener
{
	private Activity ac;
	private long	livreID;
	
	public BluetoothSendListener(Activity ac, long livreID)
	{
		this.ac = ac;
		this.livreID = livreID;
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(ac, EnvoisLivreBluetoothActivity.class);
		intent.putExtra("LivreID", livreID);
		ac.startActivity(intent);
	}

}
