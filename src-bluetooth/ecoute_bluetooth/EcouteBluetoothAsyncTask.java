package ecoute_bluetooth;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import menu.MainActivity;
import pack.LivrePack;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;
import com.example.histoires.R;
import databasetest.Livre;

public class EcouteBluetoothAsyncTask extends AsyncTask<Void, Void, Void>
{
	private Activity				ac;
	private BluetoothServerSocket	serverSocket;
	private boolean					exist;
	private LivrePack				lpk;
	private BluetoothSocket			socket;
	private BluetoothAdapter		adapter;

	public EcouteBluetoothAsyncTask(Activity ac, BluetoothAdapter adapter)
	{
		this.ac = ac;
		serverSocket = null;
		this.adapter = adapter;

		try
		{
			serverSocket = adapter.listenUsingRfcommWithServiceRecord("Server", MainActivity.uuid);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	protected Void doInBackground(Void... params)
	{
		socket = null;

		while (true)
		{
			try
			{
				Log.d("try", "SocketConnect");
				socket = serverSocket.accept();
				Log.d("succes", "SocketConnect");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			if (socket != null)
			{
				Log.d("succes", "socket != null");

				try
				{
					manageConnectedSocket(socket);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}

				break;
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ac);
		long livreID = Livre.getLivreByTitre(lpk.getTitre()).getId();

		if (!exist)
		{
			builder.setMessage(R.string.book_received);
			builder.setPositiveButton("Lire", new VoirLivreListener(ac, this, livreID));
		}
		else
		{
			builder.setMessage("Vous avez reçu un livre mais le titre existe déjà");
			builder.setPositiveButton("OK", new BackListener(ac, this));
		}

		builder.create();
		builder.show();

		try
		{	
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		new EcouteBluetoothAsyncTask(ac, this.adapter).execute();
	}

	public void manageConnectedSocket(BluetoothSocket socket) throws IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		lpk = (LivrePack) in.readObject();
		
		exist = Livre.exists(lpk.getTitre());

		if (!exist)
		{
			lpk.insertThisIntoDatabase();
		}

		// On ferme les flux
		in.close();
	}
}
