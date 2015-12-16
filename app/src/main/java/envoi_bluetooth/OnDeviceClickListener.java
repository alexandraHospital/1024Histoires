package envoi_bluetooth;

import java.io.IOException;
import java.io.ObjectOutputStream;
import menu.MainActivity;
import pack.LivrePack;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.example.histoires.R;

public class OnDeviceClickListener implements OnItemClickListener
{
	private Activity			ac;
	private BluetoothAdapter	bluetoothAdapter;
	private long				livreID;
	private BluetoothDevice		device;

	public OnDeviceClickListener(Activity ac, BluetoothAdapter bluetoothAdapter, long livreID)
	{
		this.ac = ac;
		this.bluetoothAdapter = bluetoothAdapter;
		this.livreID = livreID;
	}

	@Override
	public void onItemClick(AdapterView<?> av, View v, int position, long arg3)
	{
		device = (BluetoothDevice) av.getItemAtPosition(position);

		AlertDialog.Builder builder = new AlertDialog.Builder(ac);

		this.bluetoothAdapter.cancelDiscovery();

		BluetoothSocket socket = null;

		try
		{
			socket = device.createRfcommSocketToServiceRecord(MainActivity.uuid);

			socket.connect();

			manageConnectedSocket(socket);

			builder.setMessage(R.string.book_sent);

		}
		catch (IOException e1)
		{
			builder.setMessage(R.string.failure);

			e1.printStackTrace();
		}

		builder.setPositiveButton("OK", null);
		builder.create();
		builder.show();

		if (socket != null)
		{
			try
			{
				socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	public void manageConnectedSocket(BluetoothSocket socket) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

		LivrePack lpk = new LivrePack(this.livreID);

		out.writeObject(lpk);

		out.close();
	}
}
