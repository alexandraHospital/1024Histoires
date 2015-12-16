package ecoute_wifi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import pack.LivrePack;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import databasetest.Livre;
import ecoute_bluetooth.BackListener;
import ecoute_bluetooth.VoirLivreListener;

public class EcouteWifiAsyncTask extends AsyncTask<Void, Void, Void>
{
	private Activity		ac;
	private ServerSocket	serverSocket;
	private boolean			exist;
	private LivrePack		lpk;
	private Socket			socket;

	public EcouteWifiAsyncTask(Activity ac)
	{
		this.ac = ac;
		serverSocket = null;
		Log.d("alert", "On lance un thread d'ecoute wifi");
		try
		{
			serverSocket = new ServerSocket(12345);
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
			builder.setMessage("Vous avez reçu un livre");

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

		// new EcouteWifiAsyncTask(ac, this.adapter).execute();
	}

	public void manageConnectedSocket(Socket socket) throws IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		lpk = (LivrePack) in.readObject();

		List<Livre> livres = Livre.getAll();
		exist = false;
		for (Livre lv : livres)
		{
			if (lv.getTitre().equals(lpk.getTitre()))
			{
				exist = true;
				break;
			}
		}

		if (!exist)
		{
			lpk.insertThisIntoDatabase();
		}

		// On ferme les flux
		in.close();
	}
}
