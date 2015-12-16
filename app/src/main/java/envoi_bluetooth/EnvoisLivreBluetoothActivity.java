package envoi_bluetooth;

import java.util.ArrayList;
import java.util.Set;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import com.example.histoires.R;


public class EnvoisLivreBluetoothActivity extends Activity
{
	private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;

	private long								livreID;
	private BluetoothAdapter 					bluetoothAdapter;
	private ListView 							devicesListView;
	private DeviceAdapter 	 			 		adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_envois_livre_bluetooth);

		popup();


		livreID = getIntent().getExtras().getLong("LivreID");

		// Initialisation
		bluetoothAdapter 	= BluetoothAdapter.getDefaultAdapter();


		// Cette liste repertorie tout les appareils déjà rencontrés
		devicesListView		= (ListView) findViewById(R.id.devices_know_list);
		devicesListView.setOnItemClickListener(new OnDeviceClickListener(this, bluetoothAdapter, livreID));



		// Initialisation de l'adaptater des périphériques
		adapter = new DeviceAdapter(this, R.layout.activity_envois_livre_bluetooth, new ArrayList<BluetoothDevice>());
		// Ecoute sur l'ajout de nouveaux éléments
		devicesListView.setAdapter(adapter);
		// Pour permettre de réagir au modifications (ajout de nouveau périphériques)
		adapter.notifyDataSetChanged();



		// Le bouton scan permet de lancer la détéction de nouveaux appareils
		Button scan			= (Button) findViewById(R.id.scan);
		scan.setOnClickListener(new ScanListener(this, bluetoothAdapter));
		// Suppresion de cette fonctionalité
		scan.setVisibility(View.GONE);


		if(this.bluetoothAdapter != null)
		{
			if(!bluetoothAdapter.isEnabled())
			{
				Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
			}
			else
			{
				showDevices();
			}
		}
		else
		{
			this.finish();
		}
	}




	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
		{
			return;
		}

		if (resultCode == RESULT_OK) 
		{
			showDevices();
		} 
		else 
		{
			// Si l'on active pas le bluetooth on retourne à l'activity précédente
			//this.finish();
		}    
	}


	private void popup()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.alert_dialog_info_title);
		builder.setMessage(R.string.alert_dialog_info);
		builder.setPositiveButton(R.string.alert_return, null);

		builder.create();
		builder.show();
	}

	/**
	 * Cette méthode permet d'ajouter à notre liste tous les équipements que l'on connait déjà
	 */
	public void showDevices()
	{
		// Récupération des devices déjà connus
		Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
		for (BluetoothDevice blueDevice : devices)
		{
			adapter.add(blueDevice);
			Log.d("New Know Device", blueDevice.getName());
		}
	}


	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		if(bluetoothAdapter != null)
		{
			bluetoothAdapter.cancelDiscovery();
		}
	}
}
