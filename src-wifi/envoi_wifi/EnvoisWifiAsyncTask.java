package envoi_wifi;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import pack.LivrePack;
import com.example.histoires.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;

public class EnvoisWifiAsyncTask extends AsyncTask<Void, Void, Void>
{
	private Activity	ac;
	private long		livreID;
	private String		ip;

	private int			etat;

	public EnvoisWifiAsyncTask(Activity ac, long livreID, String ip)
	{
		this.ac = ac;
		this.ip = ip;
		this.livreID = livreID;
		Log.d("alert", "lancement d'un thread d'envois wifi");
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		try
		{
			Log.d("alert", "tentative de connexion par wifi");
			Socket socket = new Socket(ip, 12345);

			manageConnectedSocket(socket);

			this.etat = 0;
		}
		catch (UnknownHostException e)
		{
			this.etat = 1;
			// e.printStackTrace();
		}
		catch (ConnectException e)
		{
			this.etat = 1;
			// e.printStackTrace();
		}
		catch (IOException e)
		{
			this.etat = 2;
			// e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ac);

		if (this.etat == 0)
		{
			builder.setTitle(R.string.info);
			builder.setMessage(R.string.book_sent);
		}
		else if (this.etat == 1)
		{
			builder.setTitle(R.string.info);
			builder.setMessage(ac.getString(R.string.failure_ip)+" "+ip);
		}
		else if (this.etat == 2)
		{
			builder.setTitle(R.string.info);
			builder.setMessage("Erreur lors de l'écriture");
		}

		builder.create();
		builder.show();
	}

	private void manageConnectedSocket(Socket socket) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

		LivrePack lpk = new LivrePack(this.livreID);

		out.writeObject(lpk);

		out.close();
	}
}
