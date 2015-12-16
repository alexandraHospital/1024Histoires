package envoi_bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadCastReceiverPerso extends BroadcastReceiver
{
	Activity						ac;
	DeviceAdapter					adapter;
	
	public BroadCastReceiverPerso(Activity ac, DeviceAdapter adapter)
	{
		this.ac					= ac;
		this.adapter			= adapter;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		//String remoteDeviceName 		= intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
		BluetoothDevice remoteDevice 	= intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		
		
		// Ajout du device � notre listView si il n'y est pas d�j�
		if(!this.adapter.getDevices().contains(remoteDevice))
		{
			this.adapter.add(remoteDevice);
			Log.d("New UnKnow Device", remoteDevice.getName());
			Toast.makeText(ac, remoteDevice.getName()+" ajout�", Toast.LENGTH_SHORT).show();
			
		}
		
	}

}
