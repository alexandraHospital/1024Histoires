package ecoute_wifi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.widget.TextView;

public class GetIpPublicAddressAsyncTask extends AsyncTask<Void, Void, Void>
{
	private String					ip;
	private TextView 				ipPublic;
	
	public GetIpPublicAddressAsyncTask(TextView ipPublic)
	{
		this.ipPublic	= ipPublic;
	}
	
	@Override
	protected Void doInBackground(Void... params)
	{
		ip = getIpPublic();
		return null;
	}
	
	

	@Override
    protected void onPostExecute(Void result) 
	{
		ipPublic.setText("Votre IP public : "+ip);
    }
	
	public String getIpPublic()
	{
		String ip = null;
		URL url;
		BufferedReader bufferedReader = null;
		InputStreamReader in = null;
		HttpURLConnection urlConnection = null;
		try
		{
			url = new URL("http://ip2country.sourceforge.net/ip2c.php?format=JSON");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			in = new InputStreamReader(urlConnection.getInputStream());
			bufferedReader = new BufferedReader(in);
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null)
			{
				buffer.append(line);
				buffer.append('\r');
			}
			bufferedReader.close();
			in.close();
			JSONObject json_data = new JSONObject(buffer.toString());
			ip = json_data.getString("ip");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (urlConnection != null)
				{
					urlConnection.disconnect();
				}
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
				if (in != null)
				{
					in.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		// check your logcat :)
		
		return ip;
	}

}
