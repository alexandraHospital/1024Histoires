package ecoute_wifi;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.example.histoires.R;

public class RecevoirLivreWifiActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ecoute_wifi);

		TextView ipPublic = (TextView) findViewById(R.id.ip_public);
		TextView ipPrive = (TextView) findViewById(R.id.ip_prive);

		ipPublic.setVisibility(View.GONE);

		if(getLocalIpAddress() == null)
		{
			ipPrive.setText(getString(R.string.no_conenction));
		}
		else
		{
			ipPrive.setText(getString(R.string.localIp)+ " : " + getLocalIpAddress());
		}
		
		EcouteWifiAsyncTask ecouteThread = new EcouteWifiAsyncTask(this);
		ecouteThread.execute();
	}

	public static String getLocalIpAddress()
	{
		try
		{
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
			{
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
				{
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address)
					{
						return inetAddress.getHostAddress();
					}
				}
			}
		}
		catch (SocketException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

}
