package envoi_bluetooth;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.example.histoires.R;

public class ScanListener implements OnClickListener
{
	Activity 							ac;
	BluetoothAdapter 					bluetoothAdapter;
	DeviceAdapter						adapter;

	public ScanListener(Activity ac, BluetoothAdapter bluetoothAdapter)
	{
		this.ac 				= ac;
		this.bluetoothAdapter	= bluetoothAdapter;
	}


	@Override
	public void onClick(View v)
	{
		AlertDialog.Builder builder  = new AlertDialog.Builder(ac);

		builder.setTitle("Scan");


		View view = View.inflate(ac, R.layout.alertdialog_scan, null);
		builder.setView(view);

		ListView devicesListView = (ListView) view.findViewById(R.id.devices_unknow);
		devicesListView.setOnItemClickListener(new OnDeviceClickListener(ac, bluetoothAdapter, 0));

		adapter = new DeviceAdapter(ac, R.layout.alertdialog_scan, new ArrayList<BluetoothDevice>());
		adapter.notifyDataSetChanged();
		devicesListView.setAdapter(adapter);

		builder.create();
		builder.show();

		scanForNewDevices();
	}



	public void scanForNewDevices()
	{
		// Le broadCaster permet d'ecouter l'arriv� de nouveau p�riph�riques
		BroadCastReceiverPerso discoveryResult = new BroadCastReceiverPerso(ac, adapter);

		// On l'ajoute au registre
		ac.registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));

		if(bluetoothAdapter.isEnabled())
		{
			bluetoothAdapter.startDiscovery();
			Log.d("tag", "startDiscovering");
		}


	}

}
