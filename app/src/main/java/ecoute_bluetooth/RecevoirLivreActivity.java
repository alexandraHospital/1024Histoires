package ecoute_bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.example.histoires.R;

public class RecevoirLivreActivity extends Activity
{
	private final static int REQUEST_CODE_ENABLE_BLUETOOTH 	= 0;
	private final static int REQUEST_CODE_ENABLE_VISIBILITY = 1;

	private BluetoothAdapter 			bluetoothAdapter;
	private EcouteBluetoothAsyncTask 			listen;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ecoute_blue_tooth);

		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter ();

		if(this.bluetoothAdapter != null)
		{
			if(!bluetoothAdapter.isEnabled())
			{
				Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
			}
			else
			{
				enableVisibility();	
			}
		}
		else
		{
			this.finish();
		}

	}

	public void enableVisibility()
	{
		// Se mettre en visibility on
		if(!this.bluetoothAdapter.isDiscovering())
		{
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);
			startActivityForResult(discoverableIntent, REQUEST_CODE_ENABLE_VISIBILITY);
		}
		else
		{
			startLookingForSocket();
		}

	}

	/**
	 * Creer et lance un thread
	 */
	public void startLookingForSocket()
	{
		// Thread ecoutant le serverSocket à l'aide d'une AsyncTask (Thread + Handler)
		listen = new EcouteBluetoothAsyncTask(this, this.bluetoothAdapter);
		listen.execute();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);

		// if (resultCode == RESULT_OK) 
		if(resultCode != RESULT_CANCELED)
		{
			// Si l'on � activ� le bluetooth, on va alors activer notre visibilit�
			if (requestCode == REQUEST_CODE_ENABLE_BLUETOOTH)
			{
				enableVisibility();
			}
			if (requestCode == REQUEST_CODE_ENABLE_VISIBILITY)
			{
				startLookingForSocket();
			}
		} 
		else 
		{
			// Si l'on active pas le bluetooth on retourne � l'activity pr�c�dente
			this.finish();
		}    
	}

	@Override
	public void onBackPressed()
	{
		if(!this.listen.isCancelled())
		{
			this.listen.cancel(true);
		}
		
		this.finish();
	}
}
